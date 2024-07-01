package asia.chengfu.kvm.service;

import java.util.List;
import java.util.Map;

/**
 * @author fucheng on 2024/6/27
 */
public interface VirshSnapshotService {

    /**
     * 列出指定域的所有快照
     *
     * @param domain 域名称
     * @return 快照列表，每个快照信息用Map表示
     */
    List<Map<String, Object>> listSnapshots(String domain);

    /**
     * 恢复到指定快照
     *
     * @param domain       域名称
     * @param snapshotName 快照名称
     * @return
     */
    String revertSnapshot(String domain, String snapshotName);

    /**
     * 删除指定快照
     *
     * @param domain       域名称
     * @param snapshotName 快照名称
     * @return
     */
    String deleteSnapshot(String domain, String snapshotName);

    /**
     * 创建一个新快照
     *
     * @param domain       域名称
     * @param snapshotName 快照名称
     * @return
     */
    String createSnapshot(String domain, String snapshotName);

    /**
     * 获取指定快照的信息
     *
     * @param domain       域名称
     * @param snapshotName 快照名称
     * @return 快照信息，用Map表示
     */
    Map<String, Object> getSnapshotInfo(String domain, String snapshotName);

    /**
     * 导出指定快照的XML描述
     *
     * @param domain       域名称
     * @param snapshotName 快照名称
     * @return 快照的XML描述
     */
    String dumpSnapshotXml(String domain, String snapshotName);

    /**
     * 获取指定快照的格式化XML描述
     *
     * @param domain       域名称
     * @param snapshotName 快照名称
     * @return 快照的XML描述，用Map表示
     */
    Map<String, Object> dumpSnapshotXmlFormatted(String domain, String snapshotName);

}
