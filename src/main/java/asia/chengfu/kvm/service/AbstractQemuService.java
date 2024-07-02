package asia.chengfu.kvm.service;

import asia.chengfu.kvm.util.Command;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author fucheng on 2024/7/2
 */
public abstract class AbstractQemuService implements QemuImgService{

    protected String run(String command){
        return Command.of(QEMU_IMG, command).execute();
    }

    /**
     * 解析qemu-img create命令的输出，并将结果转换为一个Map，其中包含文件名和其他属性。
     *
     * @param qemuImgOutput qemu-img create命令的输出字符串
     * @return 包含文件名和其他属性的Map，键为驼峰命名风格
     */
    protected Map<String, Object> parseQemuImgCreateOutput(String qemuImgOutput) {
        // 定义正则表达式以提取文件名
        String fileNameRegex = "Formatting '([^']*)'";
        String fileName = ReUtil.get(fileNameRegex, qemuImgOutput, 1);

        // 定义正则表达式以提取键值对
        Pattern keyValueRegex = Pattern.compile("(\\w+)=('[^']*'|\\w+)");

        // 使用Hutool的ReUtil来匹配键值对
        Map<String, Object> resultMap = new LinkedHashMap<>();

        // 将文件名放入Map并转换Key为驼峰命名
        resultMap.put("fileName", fileName);

        ReUtil.findAll(keyValueRegex, qemuImgOutput, (matcher) -> {
            String key = matcher.group(1);
            String value = matcher.group(2).replaceAll("'", ""); // 去掉引号

            // 将key转换为驼峰命名
            String camelCaseKey = StrUtil.toCamelCase(key);
            resultMap.put(camelCaseKey, value);
        });

        return resultMap;
    }
}
