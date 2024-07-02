package asia.chengfu.kvm.service;

import java.util.Map;

/**
 * @author fucheng on 2024/7/2
 */
public interface QemuImgService {
    String QEMU_IMG = "qemu-img";

    /**
     * 创建磁盘
     * @param type raw、qcow2，默认qcow2
     * @param backingFile 后端文件，可以为空，则不指定
     * @param filename 文件名
     * @param size 文件大小
     * @return 创建后信息
     */
    Map<String, Object> create(String filename, String size, String backingFile, String type);

    /**
     * 创建磁盘
     * @param filename 文件名
     * @param size 文件大小
     * @return 创建信息
     */
    Map<String, Object> create(String filename, String size);

    /**
     * 文件详情
     * @param fileName 文件名
     * @return 文件详情信息
     */
    Map<String, Object> info(String fileName);
}
