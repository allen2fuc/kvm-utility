package asia.chengfu.kvm.service.impl;

import asia.chengfu.kvm.service.AbstractVirshService;
import asia.chengfu.kvm.service.VirshDomainService;
import asia.chengfu.kvm.service.VirshStorageVolumeService;
import asia.chengfu.kvm.util.Command;
import asia.chengfu.kvm.util.Options;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.Map;

public class VirshDomainServiceImpl extends AbstractVirshService implements VirshDomainService {
    // Virsh命令常量
    private static final String LIST_DOMAINS = "list --all";
    private static final String SHUTDOWN_DOMAIN = "shutdown --domain {name}";
    private static final String DESTROY_DOMAIN = "destroy --domain {name} --graceful";
    private static final String START_DOMAIN = "start --domain {name}";
    private static final String RESET_DOMAIN = "reset --domain {name}";
    private static final String GET_DOMAIN_INFO = "dominfo --domain {name}";
    private static final String SUSPEND_DOMAIN = "suspend --domain {name}";
    private static final String RESUME_DOMAIN = "resume --domain {name}";
    private static final String REBOOT_DOMAIN = "reboot --domain {name}";
    private static final String ENABLE_DOMAIN_AUTOSTART = "autostart --domain {name}";
    private static final String DISABLE_DOMAIN_AUTOSTART = "autostart --domain {name} --disable";
    private static final String DEFINE_DOMAIN = "define --file {file}";
    private static final String DUMP_DOMAIN_XML = "dumpxml --domain {name}";

    private final VirshStorageVolumeService volumeService;

    public VirshDomainServiceImpl(VirshStorageVolumeService volumeService) {
        this.volumeService = volumeService;
    }

    /**
     * 启动指定的域
     *
     * @param name 域名称
     * @return 命令执行结果
     */
    @Override
    public String startDomain(String name) {
        return run(StrUtil.format(START_DOMAIN, Map.of("name", name)));
    }

    /**
     * 挂起指定的域
     *
     * @param name 域名称
     * @return 命令执行结果
     */
    @Override
    public String suspendDomain(String name) {
        return run(StrUtil.format(SUSPEND_DOMAIN, Map.of("name", name)));
    }

    /**
     * 恢复指定的域
     *
     * @param name 域名称
     * @return 命令执行结果
     */
    @Override
    public String resumeDomain(String name) {
        return run(StrUtil.format(RESUME_DOMAIN, Map.of("name", name)));
    }

    /**
     * 重置指定的域
     *
     * @param name 域名称
     * @return 命令执行结果
     */
    @Override
    public String resetDomain(String name) {
        return run(StrUtil.format(RESET_DOMAIN, Map.of("name", name)));
    }

    /**
     * 关闭指定的域
     *
     * @param name  域名称
     * @param force 是否强制关闭
     * @return 命令执行结果
     */
    @Override
    public String shutdownDomain(String name, boolean force) {
        if (force) {
            return run(StrUtil.format(DESTROY_DOMAIN, Map.of("name", name)));
        } else {
            return run(StrUtil.format(SHUTDOWN_DOMAIN, Map.of("name", name)));
        }
    }

    /**
     * 重启指定的域
     *
     * @param name 域名称
     * @return
     */
    @Override
    public String rebootDomain(String name) {
        return run(StrUtil.format(REBOOT_DOMAIN, Map.of("name", name)));
    }

    /**
     * 创建一个新域
     *
     * @param name        域名称
     * @param memory      内存大小
     * @param cpu         CPU数量
     * @param iso         ISO镜像路径
     * @param osVariant   操作系统类型
     * @param pool        存储池名称
     * @param volName     卷名称
     * @param voltype     卷类型
     * @param volCapacity 卷容量
     * @return 命令执行结果
     */
    @Override
    public String createDomain(String name, String memory, String cpu, String iso, String osVariant, String pool, String volName, String voltype, String volCapacity) {
        // 1. 创建卷
        String volumeName = StrUtil.isBlank(volName) ? IdUtil.randomUUID() + "." + voltype : volName;
        volumeService.createVolume(volumeName, pool, volCapacity, voltype);
        String volumePath = volumeService.getVolumePath(volumeName, pool);

        Options params = Options.of(Map.of(
                "name", name,
                "virtType", "kvm",
                "memory", memory,
                "vcpus", cpu,
                "location", iso,
                "osVariant", osVariant,
                "disk", "vol=" + volumePath,
                "graphics", "vnc,listen=0.0.0.0"
        ), List.of("noautoconsole"));

        return Command.of("virt-install", params).execute();
    }

    /**
     * 获取指定域的XML描述
     *
     * @param name 域名称
     * @return 域的XML描述
     */
    @Override
    public String dumpXml(String name) {
        Map<String, String> param = Map.of("name", name);
        return run(StrUtil.format(DUMP_DOMAIN_XML, param));
    }

    /**
     * 获取指定域的格式化XML描述
     *
     * @param name 域名称
     * @return 域的XML描述，用Map表示
     */
    @Override
    public Map<String, Object> dumpXmlFormat(String name) {
        Map<String, String> param = Map.of("name", name);
        return runToXmlFormat(StrUtil.format(DUMP_DOMAIN_XML, param));
    }

    /**
     * 获取所有虚拟机信息
     * @return 虚拟机列表
     */
    @Override
    public List<Map<String, Object>> list(){
        return runToList(LIST_DOMAINS);
    }
}