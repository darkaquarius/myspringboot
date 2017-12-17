package boot.config;

import boot.controller.auth.UserController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.assembler.MBeanInfoAssembler;
import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;
import org.springframework.jmx.export.assembler.MethodNameBasedMBeanInfoAssembler;
import org.springframework.jmx.support.RegistrationPolicy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huishen on 17/7/6.
 * 为了对MBean的属性和操作获得更细粒度的控制，Spring提供了几种选择：
 * 1. 通过名称来声明需要暴露或者忽略的bean方法
 * 2. 通过为Bean增加接口来选择需要暴露的方法
 * 3. 通过注解bean来标识托管的属性和方法
 */

@Configuration
public class JMXConfig {

    /*
        1. 通过名称来声明需要暴露或者忽略的bean方法
     */

    // 配置MethodNameBasedMBeanInfoAssembler的MBeanExporter
    @Bean
    public MBeanExporter mBeanExporter(UserController userController,
                                       @Qualifier("assembler") MBeanInfoAssembler assembler) {
        MBeanExporter exporter = new MBeanExporter();
        Map<String, Object> beans = new HashMap<>();
        beans.put("user:name=UserController", userController);
        exporter.setBeans(beans);
        // 指定MBean暴露到哪个MBean服务器
        // exporter.setServer();
        // 配置MBeanInfoAssembler
        exporter.setAssembler(assembler);
        return exporter;
    }

    // MethodNameBasedMBeanInfoAssembler, 限制哪些方法和属性在MBean上暴露
    // MethodExclusionMBeanInfoAssembler是其反操作，指定了不需要MBean托管的方法
    @Bean
    public MethodNameBasedMBeanInfoAssembler assembler() {
        MethodNameBasedMBeanInfoAssembler assembler =
            new MethodNameBasedMBeanInfoAssembler();
        assembler.setManagedMethods(new String[]{"getPerPage", "setPerPage"});
        return assembler;
    }


    /*
        2. 通过为Bean增加接口来选择需要暴露的方法
     */

    // InterfaceBasedMBeanInfoAssembler
    // @Bean
    // public InterfaceBasedMBeanInfoAssembler assembler() {
    //
    // }



    /*
        3. 通过注解bean来标识托管的属性和方法
     */

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
