package boot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.assembler.MBeanInfoAssembler;
import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * Created by huishen on 17/7/6.
 *
 */

@Configuration
public class JMXConfig {

    // 配置MethodNameBasedMBeanInfoAssembler的MBeanExporter
    // @Bean
    // public MBeanExporter mBeanExporter(UserController userController,
    //                                    @Qualifier("assembler") MBeanInfoAssembler assembler) {
    //     MBeanExporter exporter = new MBeanExporter();
    //     Map<String, Object> beans = new HashMap<>();
    //     beans.put("user:name=UserController", userController);
    //     exporter.setBeans(beans);
    //     // 指定MBean暴露到哪个MBean服务器
    //     // exporter.setServer();
    //     // 配置MBeanInfoAssembler
    //     exporter.setAssembler(assembler);
    //     return exporter;
    // }

    // MethodNameBasedMBeanInfoAssembler
    // 限制哪些方法和属性在MBean上暴露
    // MethodExclusionMBeanInfoAssembler是其反操作，指定了不需要MBean托管的方法
    // @Bean
    // public MethodNameBasedMBeanInfoAssembler assembler() {
    //     MethodNameBasedMBeanInfoAssembler assembler =
    //         new MethodNameBasedMBeanInfoAssembler();
    //     assembler.setManagedMethods(new String[]{"getPerPage", "setPerPage"});
    //     return assembler;
    // }

    // 配置MetadataMBeanInfoAssembler的MBeanExporter
    public MBeanExporter mBeanExporter(
        @Qualifier("metadataMBeanInfoAssembler") MBeanInfoAssembler assembler) {
        MBeanExporter exporter = new MBeanExporter();
        exporter.setAssembler(assembler);
        exporter.setRegistrationPolicy(RegistrationPolicy.IGNORE_EXISTING);
        return exporter;
    }

    // MetadataMBeanInfoAssembler
    @Bean
    public MetadataMBeanInfoAssembler metadataMBeanInfoAssembler(
        @Qualifier("jmxAttributeSource") AnnotationJmxAttributeSource jmxAttributeSource) {

        MetadataMBeanInfoAssembler assembler = new MetadataMBeanInfoAssembler();
        assembler.setAttributeSource(jmxAttributeSource);
        return assembler;
    }

    // AnnotationJmxAttributeSource
    @Bean
    public AnnotationJmxAttributeSource jmxAttributeSource() {
        return new AnnotationJmxAttributeSource();
    }

    // // 使MBean成为远程对象
    // // ?????????????????
    // @Bean
    // public ConnectorServerFactoryBean connectorServerFactoryBean() {
    //     ConnectorServerFactoryBean csfb = new ConnectorServerFactoryBean();
    //     csfb.setServiceUrl("service:jmx:rmi://localhost/jndi/rmi://localhost:1099/address");
    //     return csfb;
    // }

    // // 启动一个RMI注册表
    // // ???????????????
    // @Bean
    // public RmiRegistryFactoryBean rmiRegistryFactoryBean() {
    //     RmiRegistryFactoryBean rrfb = new RmiRegistryFactoryBean();
    //     rrfb.setPort(1099);
    //     return rrfb;
    // }


}
