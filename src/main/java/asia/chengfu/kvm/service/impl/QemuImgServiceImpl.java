package asia.chengfu.kvm.service.impl;

import asia.chengfu.kvm.service.AbstractQemuService;
import asia.chengfu.kvm.util.JsonKeyFormatter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.Map;

/**
 * @author fucheng on 2024/7/2
 */
public class QemuImgServiceImpl extends AbstractQemuService {

    /**
     * 创建磁盘
     * @param type raw、qcow2，默认qcow2
     * @param backingFile 后端文件，可以为空，则不指定
     * @param filename 文件名
     * @param size 文件大小
     * @return 创建后信息
     */
    @Override
    public Map<String, Object> create(String filename, String size, String backingFile, String type){
        // create [-q] [-f fmt] [-o options] filename [size]
        String tmpBackingFile = StrUtil.isBlank(backingFile) ? "" : "-b " + backingFile;
        String command = StrUtil.format("create -f %s %s %s %s", type, tmpBackingFile, filename, size);
        String resp = run(command);
        return parseQemuImgCreateOutput(resp);
    }

    /**
     * 创建磁盘
     * @param filename 文件名 文件名
     * @param size 文件大小
     * @return 创建后详情
     */
    @Override
    public Map<String, Object> create(String filename, String size) {
        return create(filename, size, null, "qcow2");
    }

    /**
     * 文件名
     * @param fileName 文件名
     * @return 返回磁盘信息
     */
    @Override
    public Map<String, Object> info(String fileName){
        // info [-f fmt] [--output=ofmt] [--backing-chain] filename
        String command = StrUtil.format("info %s --output=json", fileName);
        String jsonStr = run(command);
        JSONObject obj = JSONUtil.parseObj(jsonStr);
        return JsonKeyFormatter.convertKeysToCamelCase(obj);
    }
}
