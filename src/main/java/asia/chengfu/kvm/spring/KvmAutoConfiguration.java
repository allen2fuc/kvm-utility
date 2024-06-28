package asia.chengfu.kvm.spring;

import asia.chengfu.kvm.service.*;
import asia.chengfu.kvm.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fucheng on 2024/6/28
 */
@Configuration
public class KvmAutoConfiguration {

    @Bean
    public OsSupportService osSupportService(){
        return new OsSupportServiceImpl();
    }

    @Bean
    public VirshSnapshotService virshSnapshotService(){
        return new VirshSnapshotServiceImpl();
    }

    @Bean
    public VirshStoragePoolService virshStoragePoolService(){
        return new VirshStoragePoolServiceImpl();
    }

    @Bean
    public VirshStorageVolumeService virshStorageVolumeService(){
        return new VirshStorageVolumeServiceImpl();
    }

    @Bean
    public VirshNetworkInterfaceService virshNetworkInterfaceService(){
        return new VirshNetworkInterfaceServiceImpl();
    }

    @Bean
    public VirshDomainService virshDomainService(){
        return new VirshDomainServiceImpl(virshStorageVolumeService());
    }
}
