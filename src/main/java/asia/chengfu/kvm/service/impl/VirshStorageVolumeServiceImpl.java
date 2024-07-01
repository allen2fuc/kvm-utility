package asia.chengfu.kvm.service.impl;

import asia.chengfu.kvm.service.AbstractVirshService;
import asia.chengfu.kvm.service.VirshStorageVolumeService;
import cn.hutool.core.io.unit.DataSize;
import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.Map;

public class VirshStorageVolumeServiceImpl extends AbstractVirshService implements VirshStorageVolumeService {

    // Virsh命令常量
    private static final String GET_VOLUME_INFO = "vol-info --vol {name} --pool {pool}";
    private static final String GET_VOLUME_PATH = "vol-path --vol {name} --pool {pool}";
    private static final String LIST_VOLUMES = "vol-list --pool {pool}";
    private static final String DELETE_VOLUME = "vol-delete --vol {name} --pool {pool}";
    private static final String DUMP_VOLUME_XML = "vol-dumpxml --vol {name} --pool {pool}";
    private static final String CREATE_VOLUME = "vol-create-as --name {name} --pool {pool} --capacity {capacity} --format {format}";

    /**
     * 获取卷信息
     *
     * @param name 卷名称
     * @param pool 存储池名称
     * @return 卷信息，用Map表示
     */
    @Override
    public Map<String, Object> getVolumeInfo(String name, String pool) {
        return runToMap(StrUtil.format(GET_VOLUME_INFO, Map.of("name", name, "pool", pool)));
    }

    /**
     * 列出指定存储池中的所有卷
     *
     * @param pool 存储池名称
     * @return 卷列表，每个卷信息用Map表示
     */
    @Override
    public List<Map<String, Object>> listVolumes(String pool) {
        return runToList(StrUtil.format(LIST_VOLUMES, Map.of("pool", pool)));
    }

    /**
     * 删除指定的卷
     *
     * @param name 卷名称
     * @param pool 存储池名称
     * @return 命令执行结果
     */
    @Override
    public String deleteVolume(String name, String pool) {
        return run(StrUtil.format(DELETE_VOLUME, Map.of("name", name, "pool", pool)));
    }

    /**
     * 创建卷
     *
     * @param name     卷名称
     * @param pool     存储池名称
     * @param capacity 容量大小
     * @param format   卷格式类型：raw、bochs、qcow、qcow2、qed、vmdk
     * @return 命令执行结果
     */
    @Override
    public String createVolume(String name, String pool, String capacity, String format) {
        long capacityBytes = DataSize.parse(capacity).toBytes();
        return run(StrUtil.format(CREATE_VOLUME, Map.of("name", name, "pool", pool, "capacity", capacityBytes, "format", format)));
    }

    /**
     * 获取支持的卷格式类型
     *
     * @return 卷格式类型列表
     */
    @Override
    public List<String> getSupportedVolumeTypes() {
        return List.of("qcow2", "raw");
    }

    /**
     * 获取指定卷的路径
     *
     * @param name 卷名称
     * @param pool 存储池名称
     * @return 卷路径
     */
    @Override
    public String getVolumePath(String name, String pool) {
        return run(StrUtil.format(GET_VOLUME_PATH, Map.of("name", name, "pool", pool)));
    }

    /**
     * 导出卷的XML描述
     *
     * @param name 卷名称
     * @param pool 存储池名称
     * @return 卷的XML描述
     */
    @Override
    public String dumpVolumeXml(String name, String pool) {
        return run(StrUtil.format(DUMP_VOLUME_XML, Map.of("name", name, "pool", pool)));
    }

    /**
     * 以格式化的方式导出卷的XML描述
     *
     * @param name 卷名称
     * @param pool 存储池名称
     * @return 卷的XML描述，用Map表示
     */
    @Override
    public Map<String, Object> dumpVolumeXmlFormatted(String name, String pool) {
        return runToXmlFormat(StrUtil.format(DUMP_VOLUME_XML, Map.of("name", name, "pool", pool)));
    }
}