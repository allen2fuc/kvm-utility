package asia.chengfu.kvm.service.impl;

import asia.chengfu.kvm.service.AbstractVirshService;
import asia.chengfu.kvm.service.VirshSnapshotService;
import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.Map;

public class VirshSnapshotServiceImpl extends AbstractVirshService implements VirshSnapshotService {
    // Virsh命令常量
    private static final String LIST_SNAPSHOTS = "snapshot-list --domain {domain}";
    private static final String GET_SNAPSHOT_INFO = "snapshot-info --domain {domain} --snapshotname {name}";
    private static final String REVERT_SNAPSHOT = "snapshot-revert --domain {domain} --snapshotname {name}";
    private static final String DELETE_SNAPSHOT = "snapshot-delete --domain {domain} --snapshotname {name}";
    private static final String CREATE_SNAPSHOT = "snapshot-create-as --domain {domain} --name {name}";
    private static final String DUMP_SNAPSHOT_XML = "snapshot-dumpxml --domain {domain} --snapshotname {name}";

    /**
     * 列出指定域的所有快照
     *
     * @param domain 虚拟机域名
     * @return 快照列表，每个快照信息用Map表示
     */
    @Override
    public List<Map<String, Object>> listSnapshots(String domain) {
        return runToList(StrUtil.format(LIST_SNAPSHOTS, Map.of("domain", domain)));
    }

    /**
     * 恢复指定的快照
     *
     * @param domain       虚拟机域名
     * @param snapshotName 快照名
     */
    @Override
    public void revertSnapshot(String domain, String snapshotName) {
        run(StrUtil.format(REVERT_SNAPSHOT, Map.of("domain", domain, "name", snapshotName)));
    }

    /**
     * 删除指定的快照
     *
     * @param domain       虚拟机域名
     * @param snapshotName 快照名
     */
    @Override
    public void deleteSnapshot(String domain, String snapshotName) {
        run(StrUtil.format(DELETE_SNAPSHOT, Map.of("domain", domain, "name", snapshotName)));
    }

    /**
     * 创建新的快照
     *
     * @param domain       虚拟机域名
     * @param snapshotName 快照名
     */
    @Override
    public void createSnapshot(String domain, String snapshotName) {
        run(StrUtil.format(CREATE_SNAPSHOT, Map.of("domain", domain, "name", snapshotName)));
    }

    /**
     * 获取指定快照的详细信息
     *
     * @param domain       虚拟机域名
     * @param snapshotName 快照名
     * @return 快照信息，用Map表示
     */
    @Override
    public Map<String, Object> getSnapshotInfo(String domain, String snapshotName) {
        return runToMap(StrUtil.format(GET_SNAPSHOT_INFO, Map.of("domain", domain, "name", snapshotName)));
    }

    /**
     * 导出指定快照的XML描述
     *
     * @param domain       虚拟机域名
     * @param snapshotName 快照名
     * @return 快照的XML描述
     */
    @Override
    public String dumpSnapshotXml(String domain, String snapshotName) {
        return run(StrUtil.format(DUMP_SNAPSHOT_XML, Map.of("domain", domain, "name", snapshotName)));
    }

    /**
     * 以格式化的方式导出指定快照的XML描述
     *
     * @param domain       虚拟机域名
     * @param snapshotName 快照名
     * @return 快照的XML描述，用Map表示
     */
    @Override
    public Map<String, Object> dumpSnapshotXmlFormatted(String domain, String snapshotName) {
        return runToXmlFormat(StrUtil.format(DUMP_SNAPSHOT_XML, Map.of("domain", domain, "name", snapshotName)));
    }
}
