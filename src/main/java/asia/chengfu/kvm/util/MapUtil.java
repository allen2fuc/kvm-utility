package asia.chengfu.kvm.util;


import cn.hutool.core.util.StrUtil;

import java.util.Map;

/**
 * @author fucheng on 2024/7/1
 */
public final class MapUtil {

    /**
     * 获取多层深度值
     * @param map 键值对
     * @param keys 多层key
     * @return 值
     */
    public static String getStr(Map map, String... keys){

        if (cn.hutool.core.map.MapUtil.isEmpty(map)){
            return null;
        }

        int depth = keys.length;

        Map tmp = map;

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];

            Object obj = tmp.get(key);

            if (i == depth - 1){
                return StrUtil.toStringOrNull(obj);
            }

            if (obj instanceof Map){
                tmp = (Map)obj;
            }else {
                break;
            }

        }

        throw new RuntimeException("深度不一致，请检查 [" + StrUtil.join(",", keys) + "]");
    }

}
