package asia.chengfu.kvm.service;

import java.util.List;
import java.util.Map;

/**
 * OsSupportService接口定义了获取支持的操作系统列表的方法。
 */
public interface OsSupportService {
    /**
     * 获取支持的操作系统列表
     *
     * @return 系统集合列表
     */
    List<Map<String, Object>> listSupportedOperatingSystems();
}