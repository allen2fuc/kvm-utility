package asia.chengfu.kvm.util;

import cn.hutool.core.util.StrUtil;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author fucheng on 2024/6/28
 */
public class ContentSplitterListParam<K, V> {
    private String lineSeparator;
    private String columnSeparator;
    private Predicate<String> filterFun;
    private Function<String, K> keyFun;
    private BiFunction<String, K, V> valueFun;

    public static <K, V> ContentSplitterListParam<K, V> of(String lineSeparator,
                                                           String columnSeparator, Predicate<String> filterLine,
                                                           Function<String, K> keyFun, BiFunction<String, K, V> value) {
        ContentSplitterListParam<K, V> param = new ContentSplitterListParam<>();
        param.setLineSeparator(lineSeparator);
        param.setColumnSeparator(columnSeparator);
        param.setFilterFun(filterLine);
        param.setKeyFun(keyFun);
        param.setValueFun(value);
        return param;
    }

    public static ContentSplitterListParam<String, Object> str(String columnSeparator, Predicate<String> filterLine) {
        return of(StrUtil.LF, columnSeparator, filterLine, StrUtil::trim, (k, v) -> StrUtil.trim(v));
    }

    public static ContentSplitterListParam<String, Object> str(String columnSeparator) {
        return of(StrUtil.LF, columnSeparator, line -> false, StrUtil::trim, (k, v) -> StrUtil.trim(v));
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public String getColumnSeparator() {
        return columnSeparator;
    }

    public void setColumnSeparator(String columnSeparator) {
        this.columnSeparator = columnSeparator;
    }

    public Predicate<String> getFilterFun() {
        return filterFun;
    }

    public void setFilterFun(Predicate<String> filterFun) {
        this.filterFun = filterFun;
    }

    public Function<String, K> getKeyFun() {
        return keyFun;
    }

    public void setKeyFun(Function<String, K> keyFun) {
        this.keyFun = keyFun;
    }

    public BiFunction<String, K, V> getValueFun() {
        return valueFun;
    }

    public void setValueFun(BiFunction<String, K, V> valueFun) {
        this.valueFun = valueFun;
    }
}
