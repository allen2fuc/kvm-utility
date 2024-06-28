package asia.chengfu.kvm.util;

import static cn.hutool.core.util.StrUtil.SPACE;

import java.util.List;
import java.util.Map;


/**
 * 命令构建器类，用于构建和执行命令，并与 VirshRun 工具类紧密集成。
 */
public class Command {

    private final StringBuilder builder;

    private Command() {
        this.builder = new StringBuilder();
    }

    private Command(String command) {
        this.builder = new StringBuilder(command);
    }

    private Command(String prefix, Options params) {
        this.builder = new StringBuilder(prefix);
        this.builder.append(params.toString());
    }

    private Command(String prefix, String params) {
        this.builder = new StringBuilder(prefix);
        this.builder.append(SPACE); // 添加参数
        this.builder.append(params);
    }

    private Command(String prefix, StringBuilder params) {
        this.builder = new StringBuilder(prefix);
        this.builder.append(SPACE); // 添加参数
        this.builder.append(params);
    }

    /**
     * 创建一个 Command 实例，指定命令字符串。
     *
     * @param command 命令字符串
     * @return Command 实例
     */
    public static Command of(String command) {
        return new Command(command);
    }

    /**
     * 创建一个 Command 实例，指定分段命令列表。
     *
     * @param segmentCommand 分段命令列表
     * @return Command 实例
     */
    public static Command of(List<String> segmentCommand) {
        return new Command(String.join(SPACE, segmentCommand));
    }

    /**
     * 创建一个 Command 实例，指定命令前缀和选项参数。
     *
     * @param prefix 命令前缀
     * @param params 选项参数
     * @return Command 实例
     */
    public static Command of(String prefix, Options params) {
        return new Command(prefix, params);
    }

    /**
     * 创建一个 Command 实例，指定分段命令列表和选项参数。
     *
     * @param segmentCommand 分段命令列表
     * @param params         选项参数
     * @return Command 实例
     */
    public static Command of(List<String> segmentCommand, Options params) {
        return new Command(String.join(SPACE, segmentCommand), params);
    }

    /**
     * 创建一个 Command 实例，指定命令前缀和字符串参数。
     *
     * @param prefix 命令前缀
     * @param params 字符串参数
     * @return Command 实例
     */
    public static Command of(String prefix, String params) {
        return new Command(prefix, params);
    }

    /**
     * 创建一个 Command 实例，指定命令前缀和 StringBuilder 参数。
     *
     * @param prefix 命令前缀
     * @param params StringBuilder 参数
     * @return Command 实例
     */
    public static Command of(String prefix, StringBuilder params) {
        return new Command(prefix, params);
    }


    /**
     * 执行命令，并返回命令执行结果字符串。
     *
     * @return 命令执行结果字符串
     */
    public String execute() {
        return VirshRun.executeCommand(builder.toString());
    }

    /**
     * 执行命令，并返回命令执行结果的 Map 对象。
     *
     * @return 命令执行结果的 Map 对象
     */
    public Map<String, Object> executeToMap() {
        return VirshRun.executeCommandToMap(builder.toString());
    }

    /**
     * 执行命令，并返回命令执行结果的列表。
     *
     * @return 命令执行结果的列表
     */
    public List<Map<String, Object>> executeToList() {
        return VirshRun.executeCommandToList(builder.toString());
    }

    /**
     * 获取当前 Command 对象的命令字符串表示。
     *
     * @return 当前 Command 对象的命令字符串
     */
    @Override
    public String toString() {
        return builder.toString();
    }
}