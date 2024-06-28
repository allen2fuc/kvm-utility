package asia.chengfu.kvm.util;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.Map;

/**
 * @author fucheng on 2024/6/26
 */
public final class VirshRespUtil {

    /**
     * 将info的详情数据转换为Map集合
     * @param infoContent 命令响应的内容
     * @return 键值对集合
     */
    public static Map<String, Object> infoToMap(String infoContent){
        return StrSplit.splitToMap(infoContent,
                StrPool.LF,
                StrPool.COLON,
                StrUtil::isNotBlank,
                StrUtil::trim,
                (k, v) -> StrUtil.trim(v));
    }

    /**
     * 将list的列表项转换为list
     * @param listContent 命令响应的内容
     * @return 集合列表
     */
    public static List<Map<String, Object>> listToList(String listContent){
        return StrSplit.splitToList(listContent, StrPool.LF, "  ", line -> StrUtil.startWith(line, "---"), StrUtil::trim, (k, v) -> StrUtil.trim(v));
    }
}
