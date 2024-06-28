package asia.chengfu.kvm.service;

import java.util.Map;

/**
 * @author fucheng on 2024/6/27
 */
public interface VirshDomainService {
    /**
     * 启动指定的域
     * @param name 域名称
     */
    void startDomain(String name);

    /**
     * 挂起指定的域
     * @param name 域名称
     */
    void suspendDomain(String name);

    /**
     * 恢复指定的域
     * @param name 域名称
     */
    void resumeDomain(String name);

    /**
     * 重置指定的域
     * @param name 域名称
     */
    void resetDomain(String name);

    /**
     * 关闭指定的域
     * @param name 域名称
     * @param force 是否强制关闭
     */
    void shutdownDomain(String name, boolean force);

    /**
     * 重启指定的域
     * @param name 域名称
     */
    void rebootDomain(String name);

    /**
     * 创建一个新域
     * @param name 域名称
     * @param memory 内存大小
     * @param cpu CPU数量
     * @param iso ISO镜像路径
     * @param osVariant 操作系统类型
     * @param pool 存储池名称
     * @param volName 卷名称
     * @param voltype 卷类型
     * @param volCapacity 卷容量
     */
    void createDomain(String name, String memory, String cpu, String iso, String osVariant, String pool, String volName, String voltype, String volCapacity);

    /**
     * 获取指定域的XML描述
     * @param name 域名称
     * @return 域的XML描述
     */
    String dumpXml(String name);

    /**
     * 获取指定域的格式化XML描述
     * @param name 域名称
     * @return 域的XML描述，用Map表示
     */
    Map<String, Object> dumpXmlFormat(String name);
}