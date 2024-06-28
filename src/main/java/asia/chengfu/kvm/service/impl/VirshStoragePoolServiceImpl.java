package asia.chengfu.kvm.service.impl;

import asia.chengfu.kvm.service.AbstractVirshService;
import asia.chengfu.kvm.service.VirshStoragePoolService;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.Map;

/**
 * @author fucheng on 2024/6/27
 */
public class VirshStoragePoolServiceImpl extends AbstractVirshService implements VirshStoragePoolService {

    // Virsh命令常量
    private static final String CREATE_POOL = "pool-create-as --name {name} --type {type} --target {target} --build";
    private static final String ENABLE_AUTOSTART = "pool-autostart --pool {name}";
    private static final String DISABLE_AUTOSTART = "pool-autostart --pool {name} --disable";
    private static final String START_POOL = "pool-start --pool {name}";
    private static final String LIST_POOLS = "pool-list --all --details";
    private static final String GET_POOL_INFO = "pool-info --pool {name}";
    private static final String DESTROY_POOL = "pool-destroy --pool {name}";
    private static final String UNDEFINE_POOL = "pool-undefine --pool {name}";
    private static final String DUMP_POOL_XML = "pool-dumpxml --pool {name}";
    private static final String REFRESH_POOL = "pool-refresh --pool {name}";


    /**
     * 创建存储池
     *
     * @param name 存储池名称
     * @param dir  存储池目录
     */
    @Override
    public void createStoragePool(String name, String dir) {
        createStoragePool(name, "dir", dir);
    }

    /**
     * 创建存储池
     * @param name 存储池名称
     * @param type 存储类型
     * @param dir 存储池地址
     */
    @Override
    public void createStoragePool(String name, String type, String dir) {
        Map<String, String> param = Map.of("name", name, "type", type, "target", dir);

        if (!FileUtil.isDirectory(dir)) {
            FileUtil.mkdir(dir);
        }

        run(StrUtil.format(CREATE_POOL, param));

        run(StrUtil.format(ENABLE_AUTOSTART, param));

        // 不需要这步操作
        // run(StrUtil.format(START_POOL, param));
    }

    /**
     * 列出所有存储池
     *
     * @return 存储池列表，每个存储池信息用Map表示
     */
    @Override
    public List<Map<String, Object>> listStoragePools() {
        return runToList(LIST_POOLS);
    }

    /**
     * 销毁存储池
     *
     * @param name 存储池名称
     */
    @Override
    public void destroyStoragePool(String name) {
        Map<String, String> param = Map.of("name", name);
        run(StrUtil.format(DESTROY_POOL, param));
        // run(StrUtil.format(UNDEFINE_POOL, param));
    }

    /**
     * 禁用存储池的自动启动
     *
     * @param name 存储池名称
     */
    @Override
    public void disableStoragePoolAutostart(String name) {
        Map<String, String> param = Map.of("name", name);
        run(StrUtil.format(DISABLE_AUTOSTART, param));
    }

    /**
     * 获取存储池信息
     *
     * @param name 存储池名称
     * @return 存储池信息，用Map表示
     */
    @Override
    public Map<String, Object> getStoragePoolInfo(String name) {
        return runToMap(StrUtil.format(GET_POOL_INFO, Map.of("name", name)));
    }

    /**
     * 创建默认存储池
     *
     * @param path 默认存储池的路径
     */
    @Override
    public void createDefaultStoragePool(String path) {
        String name = "default";
        createStoragePool(name, path);
    }

    /**
     * 导出存储池的XML描述
     *
     * @param name 存储池名称
     * @return 存储池的XML描述
     */
    @Override
    public String dumpStoragePoolXml(String name) {
        Map<String, String> param = Map.of("name", name);
        return run(StrUtil.format(DUMP_POOL_XML, param));
    }

    /**
     * 以格式化的方式导出存储池的XML描述
     *
     * @param name 存储池名称
     * @return 存储池的XML描述，用Map表示
     */
    @Override
    public Map<String, Object> dumpStoragePoolXmlFormatted(String name) {
        Map<String, String> param = Map.of("name", name);
        return runToXmlFormat(StrUtil.format(DUMP_POOL_XML, param));
    }

    /**
     * 激活存储池
     * @param name 存储池名称
     */
    @Override
    public void startStoragePool(String name){
        Map<String, String> param = Map.of("name", name);
        run(StrUtil.format(START_POOL, param));
    }

    /**
     * 刷新存储池
     * @param name 存储池名称
     */
    @Override
    public void refreshStoragePool(String name){
        Map<String, String> param = Map.of("name", name);
        run(StrUtil.format(REFRESH_POOL, param));
    }

    /**
     * 取消定义存储池
     * @param name 存储池名称
     */
    @Override
    public void undefineStoragePool(String name){
        Map<String, String> param = Map.of("name", name);
        run(StrUtil.format(UNDEFINE_POOL, param));
    }
}
