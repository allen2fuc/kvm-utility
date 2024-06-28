package asia.chengfu.kvm.service;

import java.util.List;
import java.util.Map;

/**
 * @author fucheng on 2024/6/27
 */
public interface VirshStoragePoolService {

    /**
     * 创建存储池
     *
     * @param name   存储池名称
     * @param target 存储池目标目录
     */
    void createStoragePool(String name, String target);

    /**
     * 列出所有存储池
     *
     * @return 存储池列表，每个存储池信息用Map表示
     */
    List<Map<String, Object>> listStoragePools();

    /**
     * 销毁指定存储池
     *
     * @param name 存储池名称
     */
    void destroyStoragePool(String name);

    /**
     * 禁用指定存储池的自动启动
     *
     * @param name 存储池名称
     */
    void disableStoragePoolAutostart(String name);

    /**
     * 获取指定存储池的信息
     *
     * @param name 存储池名称
     * @return 存储池信息，用Map表示
     */
    Map<String, Object> getStoragePoolInfo(String name);

    /**
     * 创建默认存储池
     *
     * @param path 默认存储池路径
     */
    void createDefaultStoragePool(String path);

    /**
     * 导出指定存储池的XML描述
     *
     * @param name 存储池名称
     * @return 存储池的XML描述
     */
    String dumpStoragePoolXml(String name);

    /**
     * 获取指定存储池的格式化XML描述
     *
     * @param name 存储池名称
     * @return 存储池的XML描述，用Map表示
     */
    Map<String, Object> dumpStoragePoolXmlFormatted(String name);

}
