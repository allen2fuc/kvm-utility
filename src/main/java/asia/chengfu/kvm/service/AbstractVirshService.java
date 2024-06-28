package asia.chengfu.kvm.service;

import asia.chengfu.kvm.util.Command;
import asia.chengfu.kvm.xml.VirshXmlParser;

import java.util.List;
import java.util.Map;


/**
 * @author fucheng on 2024/6/27
 */
public abstract class AbstractVirshService implements VirshService {

    /**
     * 运行指定的Virsh命令，并返回结果
     * @param command Virsh命令
     * @return 命令结果
     */
    protected String run(String command) {
        // 实现运行Virsh命令的逻辑
        return Command.of(VIRSH, command).execute();
    }

    /**
     * 运行指定的Virsh命令，并将结果转换为Map
     * @param command Virsh命令
     * @return 命令结果的Map表示
     */
    protected Map<String, Object> runToMap(String command) {
        // 实现运行Virsh命令并转换为Map的逻辑
        return Command.of(VIRSH, command).executeToMap();
    }

    /**
     * 运行指定的Virsh命令，并将结果转换为List
     * @param command Virsh命令
     * @return 命令结果的List表示
     */
    protected List<Map<String, Object>> runToList(String command) {
        // 实现运行Virsh命令并转换为List的逻辑
        return Command.of(VIRSH, command).executeToList();
    }

    /**
     * 运行指定的Virsh命令，并将XML结果转换为Map
     * @param command Virsh命令
     * @return XML结果的Map表示
     */
    protected Map<String, Object> runToXmlFormat(String command) {
        return VirshXmlParser.parseXmlAndFormat(run(command));
    }

}
