package asia.chengfu.kvm.service.impl;

import asia.chengfu.kvm.service.AbstractVirshService;
import asia.chengfu.kvm.service.VirshNetworkInterfaceService;
import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.Map;

public class VirshNetworkInterfaceServiceImpl extends AbstractVirshService implements VirshNetworkInterfaceService {
    // Virsh命令常量
    private static final String LIST_INTERFACES = "iface-list --all";
    private static final String GET_INTERFACE_INFO = "iface-dumpxml --interface {name}";

    /**
     * 列出所有网络接口
     * @return 网络接口列表，每个接口信息用Map表示
     */
    @Override
    public List<Map<String, Object>> listInterfaces() {
        return runToList(LIST_INTERFACES);
    }

    /**
     * 导出指定接口的XML描述
     * @param name 接口名称
     * @return 接口的XML描述
     */
    @Override
    public String dumpInterfaceXml(String name) {
        Map<String, String> param = Map.of("name", name);
        return run(StrUtil.format(GET_INTERFACE_INFO, param));
    }

    /**
     * 以格式化的方式导出指定接口的XML描述
     * @param name 接口名称
     * @return 接口的XML描述，用Map表示
     */
    @Override
    public Map<String, Object> dumpInterfaceXmlFormatted(String name) {
        Map<String, String> param = Map.of("name", name);
        return runToXmlFormat(StrUtil.format(GET_INTERFACE_INFO, param));
    }
}