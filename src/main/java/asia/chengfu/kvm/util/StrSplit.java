package asia.chengfu.kvm.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.text.StrSplitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * StrSplit 类提供了将字符串内容分割成列表或映射的方法。
 * 可以指定行分隔符、列分隔符、行过滤器、键映射函数和值映射函数。
 */
public final class StrSplit {

    /**
     * 将字符串内容按指定的行分隔符和列分隔符分割成列表，每个元素是一个映射。
     *
     * @param content       要分割的字符串内容
     * @param lineSeparator 行分隔符
     * @param columnSeparator 列分隔符
     * @param lineFilter    行过滤器，决定哪些行需要处理
     * @param keyMapper     键映射函数，将原始键转换为新的键
     * @param valueMapper   值映射函数，将原始值转换为新的值
     * @return 结果列表，每个元素是一个包含键值对的映射
     */
    public static List<Map<String, Object>> splitToList(String content,
                                                        String lineSeparator,
                                                        String columnSeparator,
                                                        Predicate<String> lineFilter,
                                                        Function<String, String> keyMapper,
                                                        BiFunction<String, String, Object> valueMapper) {

        List<Map<String, Object>> result = new ArrayList<>();
        List<String> lines = StrSplitter.splitTrim(content, lineSeparator, true);

        List<String> keys = null;

        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);

            if (lineFilter.test(line)) {
                continue;
            }

            boolean isFirst = index == 0;
            List<String> columns = StrSplitter.splitTrim(line, columnSeparator, true);

            if (isFirst) {
                keys = columns;
                continue;
            }

            if (keys == null || keys.size() != columns.size()) {
                continue;
            }

            MapBuilder<String, Object> builder = MapBuilder.create(true);
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = columns.get(i);
                builder.put(keyMapper.apply(key), valueMapper.apply(key, value));
            }

            result.add(builder.build());
        }

        return result;
    }

    /**
     * 将字符串内容按指定的行分隔符和列分隔符分割成映射，每个键值对对应一行内容。
     *
     * @param content       要分割的字符串内容
     * @param lineSeparator 行分隔符
     * @param columnSeparator 列分隔符
     * @param lineFilter    行过滤器，决定哪些行需要处理
     * @param keyMapper     键映射函数，将原始键转换为新的键
     * @param valueMapper   值映射函数，将原始值转换为新的值
     * @return 结果映射，包含从内容中提取的键值对
     */
    public static Map<String, Object> splitToMap(String content,
                                                 String lineSeparator,
                                                 String columnSeparator,
                                                 Predicate<String> lineFilter,
                                                 Function<String, String> keyMapper,
                                                 BiFunction<String, String, Object> valueMapper) {

        MapBuilder<String, Object> builder = MapBuilder.create(true);
        List<String> lines = StrSplitter.splitTrim(content, lineSeparator, true);

        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);

            if (lineFilter.test(line)) {
                continue;
            }

            List<String> columns = StrSplitter.splitTrim(line, columnSeparator, false);

            if (CollUtil.isEmpty(columns) || columns.size() < 3) {
                continue;
            }

            String key = columns.get(0);
            String value = columns.get(1);
            builder.put(keyMapper.apply(key), valueMapper.apply(key, value));
        }

        return builder.build();
    }
}
