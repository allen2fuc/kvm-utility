package asia.chengfu.kvm.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.Map;

public class JsonKeyFormatter {

    /**
     * 将JSON对象的键从中横线格式转换为驼峰格式
     * 
     * @param jsonObject 需要转换的JSON对象
     * @return 转换后的JSON对象
     */
    public static JSONObject convertKeysToCamelCase(JSONObject jsonObject) {
        JSONObject result = new JSONObject();
        
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // 转换key为驼峰命名
            String camelCaseKey = StrUtil.toCamelCase(key);
            
            // 递归处理嵌套的JSONObject
            if (value instanceof JSONObject) {
                value = convertKeysToCamelCase((JSONObject) value);
            }

            result.put(camelCaseKey, value);
        }
        
        return result;
    }
}
