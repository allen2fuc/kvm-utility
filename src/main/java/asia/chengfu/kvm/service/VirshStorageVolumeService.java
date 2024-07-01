package asia.chengfu.kvm.service;

import java.util.List;
import java.util.Map;

/**
 * @author fucheng on 2024/6/27
 */
public interface VirshStorageVolumeService extends VirshService {

    /**
     * 获取卷信息
     * @param name 卷名称
     * @param pool 存储池名称
     * @return 卷信息，用Map表示
     */
    Map<String, Object> getVolumeInfo(String name, String pool);

    /**
     * 列出指定存储池中的所有卷
     * @param pool 存储池名称
     * @return 卷列表，每个卷信息用Map表示
     */
    List<Map<String, Object>> listVolumes(String pool);

    /**
     * 删除指定的卷
     *
     * @param name 卷名称
     * @param pool 存储池名称
     * @return
     */
    String deleteVolume(String name, String pool);

    /**
     * 创建卷
     *
     * @param name     卷名称
     * @param pool     存储池名称
     * @param capacity 容量大小
     * @param format   卷格式类型：raw、bochs、qcow、qcow2、qed、vmdk
     * @return
     */
    String createVolume(String name, String pool, String capacity, String format);

    /**
     * 获取支持的卷格式类型
     * @return 卷格式类型列表
     */
    List<String> getSupportedVolumeTypes();

    /**
     * 获取指定卷的路径
     * @param name 卷名称
     * @param pool 存储池名称
     * @return 卷路径
     */
    String getVolumePath(String name, String pool);

    /**
     * 导出卷的XML描述
     * @param name 卷名称
     * @param pool 存储池名称
     * @return 卷的XML描述
     */
    String dumpVolumeXml(String name, String pool);

    /**
     * 以格式化的方式导出卷的XML描述
     * @param name 卷名称
     * @param pool 存储池名称
     * @return 卷的XML描述，用Map表示
     */
    Map<String, Object> dumpVolumeXmlFormatted(String name, String pool);

}
