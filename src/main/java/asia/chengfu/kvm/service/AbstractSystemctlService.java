package asia.chengfu.kvm.service;

import asia.chengfu.kvm.util.Command;

import java.util.List;
import java.util.Map;

/**
 * @author fucheng on 2024/6/28
 */
public abstract class AbstractSystemctlService implements SystemctlService {

    protected String run(String command) {
        // 实现运行Virsh命令的逻辑
        return Command.of(SYSTEMCTL, command).execute();
    }

    /**
     * 运行指定的Virsh命令，并将结果转换为List
     * @param command Virsh命令
     * @return 命令结果的List表示
     */
    protected List<Map<String, Object>> runToList(String command) {
        // 实现运行Virsh命令并转换为List的逻辑
        return Command.of(SYSTEMCTL, command).executeToList();
    }

}
