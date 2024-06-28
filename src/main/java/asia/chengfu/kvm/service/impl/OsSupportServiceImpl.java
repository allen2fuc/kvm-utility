package asia.chengfu.kvm.service.impl;

import asia.chengfu.kvm.service.OsSupportService;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * OsSupportServiceImpl类实现了OsSupportService接口，用于获取支持的操作系统列表。
 */
@Service
public class OsSupportServiceImpl implements OsSupportService {
    private static final String OS_LIST = "osinfo-query os";

    /**
     * 获取支持的操作系统列表
     * @return 系统集合列表
     */
    @Override
    public List<Map<String, Object>> listSupportedOperatingSystems() {
        // 执行命令并获取输出的每一行
        List<String> lines = RuntimeUtil.execForLines(OS_LIST);

        // 结果列表
        List<Map<String, Object>> result = new ArrayList<>();

        // 表头列表
        List<String> header = null;

        // 遍历每一行
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            // 按"|"分割并去除首尾空白
            List<String> columns = StrUtil.splitTrim(line, "|");

            // 第一行为表头
            if (i == 0) {
                header = columns;
                continue;
            }

            // 创建MapBuilder以构建结果Map
            MapBuilder<String, Object> builder = MapUtil.builder();
            for (int j = 0; j < header.size(); j++) {
                String key = header.get(j);
                String value = columns.get(j);
                builder.put(key, value);
            }

            // 将构建的Map添加到结果列表中
            result.add(builder.build());
        }

        // 返回结果列表
        return result;
    }
}
