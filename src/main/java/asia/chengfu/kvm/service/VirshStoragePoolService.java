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
     * @return
     */
    String createStoragePool(String name, String target);

    /**
     * 创建存储池
     *
     * @param name 存储池名称
     * @param type 存储类型
     * @param dir  存储池地址
     * @return
     */
    String createStoragePool(String name, String type, String dir);

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
     * @return
     */
    String destroyStoragePool(String name);

    /**
     * 销毁指定存储池
     *
     * @param name 存储池名称
     * @param isDelDir 是否删除目录
     * @return 执行结果
     */
    String destroyStoragePool(String name, boolean isDelDir);

    /**
     * 禁用指定存储池的自动启动
     *
     * @param name 存储池名称
     * @return
     */
    String disableStoragePoolAutostart(String name);

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
     * @return
     */
    String createDefaultStoragePool(String path);

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

    /**
     * 激活存储池
     *
     * @param name 存储池名称
     * @return
     */
    String startStoragePool(String name);

    /**
     * 刷新定义存储池
     *
     * @param name 存储池名称
     * @return
     */
    String refreshStoragePool(String name);

    /**
     * 取消定义存储池
     *
     * @param name 存储池名称
     * @return
     */
    String undefineStoragePool(String name);

    /**
     * 删除存储池
     *
     * @param name 存储池名称
     * @return
     */
    String deleteStoragePool(String name);
}
