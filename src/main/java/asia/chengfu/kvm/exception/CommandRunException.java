package asia.chengfu.kvm.exception;

/**
 * CommandRunException 是一个自定义运行时异常，用于处理命令执行中的错误。
 */
public class CommandRunException extends RuntimeException {

    /**
     * 构造方法，接受一个错误信息作为参数
     * @param message 错误信息
     */
    public CommandRunException(String message) {
        super(message);
    }
}
