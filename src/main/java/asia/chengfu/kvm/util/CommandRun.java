package asia.chengfu.kvm.util;

import asia.chengfu.kvm.exception.CommandRunException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * CommandRun类用于执行命令，并处理命令的响应。
 */
public final class CommandRun {

    private static final Logger logger = LoggerFactory.getLogger(CommandRun.class);

    /**
     * 运行指定的命令，并返回响应字符串。
     * @param command 要执行的命令
     * @return 命令的响应
     */
    public static String executeCommand(String command) {
        // 执行命令
        String response = RuntimeUtil.execForStr(command);

        // 输出调试日志
        logger.debug("Execute command=[{}] Response=[\n{}]", command, response);

        // 如果响应以"error"开头，抛出异常
        if (StrUtil.startWith(response, "error")) {
            throw new CommandRunException(response);
        }

        // 返回响应
        return response;
    }

    /**
     * 运行指定的命令，并将响应转换为列表。
     * @param command 要执行的命令
     * @return 转换后的列表
     */
    public static List<Map<String, Object>> executeCommandToList(String command) {
        // 响应为空时，返回空列表
        return executeCommandTo(command, ContentSplitterUtil::listToList, Collections.emptyList());
    }

    /**
     * 执行命令返回集合
     * @param command 命令
     * @param existingParserParam 对成功的数据解析参数
     * @return 数据
     */
    public static List<Map<String, Object>> executeCommandToList(String command, ContentSplitterListParam<String, Object> existingParserParam) {
        // 响应为空时，返回空列表
        return executeCommandTo(command, f -> ContentSplitterUtil.listToList(existingParserParam), Collections.emptyList());
    }

    /**
     * 执行命令
     * @param command 命令
     * @param existing 数据存在则执行
     * @param missing 数据不存在则执行
     * @return 数据题
     */
    public static <T> T executeCommandTo(String command, Function<String, T> existing, T missing) {
        // 执行命令并获取响应
        String response = executeCommand(command);

        // 如果响应不为空，将其转换为列表
        if (StrUtil.isNotBlank(response)) {
            return existing.apply(response);
        }

        // 响应为空时，返回空列表
        return missing;
    }

    /**
     * 运行命令并返回Map对象
     * @param command 要执行的命令
     * @return 转换后的Map对象
     */
    public static Map<String, Object> executeCommandToMap(String command) {
        return executeCommandTo(command, ContentSplitterUtil::infoToMap, Collections.emptyMap());

    }

    /**
     * 执行带有XML内容的命令
     * @param xmlContent XML内容
     * @param command 要执行的命令模板（如：virsh create {fileDir}）
     * @return 命令的响应
     */
    public static String executeXmlCommand(String xmlContent, String command) {
        File file = null;
        try {
            // 获取系统临时目录
            String tempDir = SystemUtil.get(SystemUtil.TMPDIR);

            // 生成随机文件名
            String tempFile = IdUtil.randomUUID() + ".virsh";

            // 生成文件路径
            String filePath = tempDir + "/" + tempFile;

            // 将XML内容写入文件
            file = FileUtil.writeBytes(xmlContent.getBytes(), filePath);

            // 获取文件的绝对路径
            String absolutePath = file.getAbsolutePath();

            // 格式化命令，替换文件路径占位符
            String completedCommand = StrUtil.format(command, Map.of("fileDir", absolutePath));

            // 执行命令并返回响应
            return executeCommand(completedCommand);
        } finally {
            // 删除临时文件
            if (FileUtil.exist(file)) {
                FileUtil.del(file);
            }
        }
    }
}