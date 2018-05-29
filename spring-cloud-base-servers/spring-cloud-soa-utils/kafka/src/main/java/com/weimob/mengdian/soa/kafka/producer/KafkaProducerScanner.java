package com.weimob.mengdian.soa.kafka.producer;

import com.google.common.collect.Sets;
import com.weimob.mengdian.soa.kafka.annotation.KafkaProduced;
import com.weimob.mengdian.soa.kafka.model.exception.KafkaConfigSettingException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * packageName下@KafkaProduced 扫描类
 *
 * @Author chenwp
 * @Date 2017-06-20 16:22
 * @Company WeiMob
 * @Description
 */
public class KafkaProducerScanner {
    private final List<String> packageNames;

    public KafkaProducerScanner(List<String> packageNames) {
        this.packageNames = packageNames;
    }

    /**
     * 扫描packageName下是interface并带有@KafkaProduced的class
     */
    public List<Class<?>> scan() throws IOException, ClassNotFoundException {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

        Set<String> pathSet = Sets.newHashSet();
        for (String packageName : packageNames) {
            String scanPath = getScanPath(packageName);
            pathSet.add(scanPath);
        }


        if (CollectionUtils.isEmpty(pathSet)) {
            throw new KafkaConfigSettingException("not setting packageNames for KafkaProducerInitialization");
        }
        List<Class<?>> classes = new ArrayList<>();

        for (String path : pathSet) {
            Resource[] resources = resourcePatternResolver.getResources(path);
            for (Resource resource : resources) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                if (isKafkaProducerClass(metadataReader)) {
                    classes.add(loadClass(metadataReader.getClassMetadata().getClassName()));
                }
            }
        }
        return classes;

    }

    private String getScanPath(String path) {
        return ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(path) + "/**/*.class";
    }

    private boolean isKafkaProducerClass(MetadataReader metadataReader) {
        return metadataReader.getClassMetadata().isInterface() && metadataReader.getAnnotationMetadata().hasAnnotation(KafkaProduced.class.getName());
    }

    private Class<?> loadClass(String className) throws ClassNotFoundException {
        return KafkaProducerScanner.class.getClassLoader().loadClass(className);
    }
}
