package asia.chengfu.kvm.service.impl;

import asia.chengfu.kvm.service.AbstractSystemctlService;

import java.util.List;
import java.util.Map;

/**
 * @author fucheng on 2024/6/28
 */
public class SystemctlUnitServiceImpl extends AbstractSystemctlService {
    private static final String LIST_UNITS = "list-units";

    public List<Map<String, Object>> list(){
        return runToList(LIST_UNITS);
    }
}
