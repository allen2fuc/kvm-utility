package asia.chengfu.kvm.service;

import java.util.List;
import java.util.Map;

/**
 * @author fucheng on 2024/6/27
 */
public interface VirshInterfaceService {
    /**
     * 列出所有网络接口
     * @return 网络接口列表，每个接口信息用Map表示
     */
    List<Map<String, Object>> listInterfaces();

    /**
     * 导出指定接口的XML描述
     * @param name 接口名称
     * @return 接口的XML描述
     */
    String dumpInterfaceXml(String name);

    /**
     * 以格式化的方式导出指定接口的XML描述
     * @param name 接口名称
     * @return 接口的XML描述，用Map表示
     */
    Map<String, Object> dumpInterfaceXmlFormatted(String name);
}
