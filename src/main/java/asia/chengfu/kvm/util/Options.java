package asia.chengfu.kvm.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Options 类用于构建命令行参数字符串。
 * 支持添加键值对参数和独立选项，并可指定参数分隔符和键符号。
 */
public class Options {

    private char keySymbol = CharUtil.DASHED; // 键符号，默认值为短横线（-）
    private String separator = "--"; // 参数分隔符，默认值为双短横线（--）

    private StringBuilder builder;

    /**
     * 默认构造方法，初始化 StringBuilder。
     */
    public Options() {
        this.builder = new StringBuilder();
    }

    /**
     * 带 StringBuilder 参数的构造方法。
     * @param builder 传入的 StringBuilder 对象
     */
    public Options(StringBuilder builder) {
        this.builder = builder;
    }

    /**
     * 带参数映射表的构造方法。
     * @param params 参数映射表
     */
    public Options(Map<String, Object> params) {
        init(params, Collections.emptyList(), separator, keySymbol);
    }

    /**
     * 带参数映射表和选项列表的构造方法。
     * @param params 参数映射表
     * @param options 选项列表
     */
    public Options(Map<String, Object> params, List<String> options) {
        init(params, options, separator, keySymbol);
    }

    /**
     * 带选项列表的构造方法。
     * @param options 选项列表
     */
    public Options(List<String> options) {
        init(MapUtil.empty(), options, separator, keySymbol);
    }

    /**
     * 带参数映射表、选项列表、分隔符和键符号的构造方法。
     * @param optMaps 参数映射表
     * @param options 选项列表
     * @param separator 参数分隔符
     * @param keySymbol 键符号
     */
    public Options(Map<String, Object> optMaps, List<String> options, String separator, char keySymbol){
        init(optMaps, options, separator, keySymbol);
    }

    /**
     * 添加键值对参数。
     * @param key 参数键
     * @param value 参数值
     * @return 当前 Options 对象
     */
    public Options add(String key, Object value) {
        builder.append(StrUtil.SPACE)
                .append(separator)
                .append(NamingCase.toSymbolCase(key, keySymbol))
                .append(StrUtil.SPACE)
                .append(value);

        return this;
    }

    /**
     * 添加独立选项。
     * @param opt 选项
     * @return 当前 Options 对象
     */
    public Options add(String opt) {
        this.builder.append(StrUtil.SPACE)
                .append(separator)
                .append(NamingCase.toSymbolCase(opt, keySymbol));

        return this;
    }

    /**
     * 创建 Options 对象并添加键值对参数。
     * @param key 参数键
     * @param value 参数值
     * @return 新的 Options 对象
     */
    public static Options of(String key, String value) {
        return new Options().add(key, value);
    }

    /**
     * 创建 Options 对象并添加独立选项。
     * @param p 选项
     * @return 新的 Options 对象
     */
    public static Options of(String p) {
        return new Options().add(p);
    }

    /**
     * 创建 Options 对象并添加多个键值对参数。
     * @param k1 第一个参数键
     * @param v1 第一个参数值
     * @param k2 第二个参数键
     * @param v2 第二个参数值
     * @return 新的 Options 对象
     */
    public static Options of(String k1, String v1, String k2, String v2) {
        return new Options().add(k1, v1).add(k2, v2);
    }

    /**
     * 创建 Options 对象并添加参数映射表。
     * @param params 参数映射表
     * @return 新的 Options 对象
     */
    public static Options of(Map<String, Object> params) {
        return new Options(params);
    }

    /**
     * 创建 Options 对象并添加参数映射表和选项列表。
     * @param params 参数映射表
     * @param options 选项列表
     * @return 新的 Options 对象
     */
    public static Options of(Map<String, Object> params, List<String> options) {
        return new Options(params, options);
    }

    /**
     * 创建 Options 对象并添加参数映射表、选项列表、分隔符和键符号。
     * @param optMaps 参数映射表
     * @param options 选项列表
     * @param separator 参数分隔符
     * @param keySymbol 键符号
     * @return 新的 Options 对象
     */
    public static Options of(Map<String, Object> optMaps, List<String> options, String separator, char keySymbol) {
        return new Options(optMaps, options, separator, keySymbol);
    }

    /**
     * 创建 Options 对象并添加选项列表、分隔符和键符号。
     * @param options 选项列表
     * @param separator 参数分隔符
     * @param keySymbol 键符号
     * @return 新的 Options 对象
     */
    public static Options of(List<String> options, String separator, char keySymbol) {
        return new Options(MapUtil.empty(), options, separator, keySymbol);
    }

    /**
     * 创建 Options 对象并添加分隔符和键符号。
     * @param separator 参数分隔符
     * @param keySymbol 键符号
     * @return 新的 Options 对象
     */
    public static Options of(String separator, char keySymbol) {
        return new Options(MapUtil.empty(), Collections.emptyList(), separator, keySymbol);
    }

    /**
     * 初始化方法，用于设置参数映射表、选项列表、分隔符和键符号。
     * @param optMaps 参数映射表
     * @param options 选项列表
     * @param separator 参数分隔符
     * @param keySymbol 键符号
     */
    private void init(Map<String, Object> optMaps, List<String> options, String separator, char keySymbol) {
        this.builder = new StringBuilder();
        this.separator = separator;
        this.keySymbol = keySymbol;

        for (Map.Entry<String, Object> entry : optMaps.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            add(key, value);
        }

        for (String option : options) {
            add(option);
        }
    }

    @Override
    public String toString() {
        return this.builder.toString();
    }
}
