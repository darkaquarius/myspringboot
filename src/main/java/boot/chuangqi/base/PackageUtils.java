package boot.chuangqi.base;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author libo.ding
 * @since 2016-08-19
 */
public abstract class PackageUtils {

    public static Set<Class<?>> getClasses(String packageName) {
        Set<Class<?>> ret = new HashSet<>();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
            .concat(ClassUtils.convertClassNameToResourcePath(packageName))
            .concat("/").concat("**/*.class");
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        try {
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (!resource.isReadable())
                    continue;
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                Class<?> clazz = Class.forName(metadataReader.getClassMetadata().getClassName());
                ret.add(clazz);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    public static Set<Class<?>> getClasses(Collection<String> basePackages) {
        Set<Class<?>> ret = new HashSet<>();
        basePackages.forEach(pkg -> ret.addAll(getClasses(pkg)));
        return ret;
    }
}
