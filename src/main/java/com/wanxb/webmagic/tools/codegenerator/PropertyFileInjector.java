package com.wanxb.webmagic.tools.codegenerator;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public final class PropertyFileInjector {

    private final Properties properties;

    public PropertyFileInjector(ClassLoader cl, String... propertyFiles) {
        properties = new Properties();
        Arrays.stream(propertyFiles).forEach(p -> {
            try {
                properties.load(new InputStreamReader(Objects.requireNonNull(cl.getResourceAsStream(p)), StandardCharsets.UTF_8));
            } catch (IOException e) {
                log.warn("cannot read property file: {}", p);
            }
        });
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * 通过反射填充数据
     * @param propertyClass 填充的实体
     * @param prefix 配置中的前缀
     * @param <T> 实例
     * @return 实例
     */
    public <T> T inject(Class<T> propertyClass, String prefix) {
        T object = BeanUtils.instantiateClass(propertyClass);
        val names = properties.propertyNames();
        while (names.hasMoreElements()) {
            final String keyName = (String) names.nextElement();
            final String value = properties.getProperty(keyName);
            if(Strings.isNullOrEmpty(value)) continue;
            String name = keyName;
            if(name.startsWith(prefix)) {
                name = name.substring(prefix.length());
                if(name.charAt(0) == '.')
                    name = name.substring(1);

                name = keyNameToClassPropertyName(name);
                val writer = ReflectionUtils.findMethod(propertyClass, name, (Class<?>[]) null);
                if(writer == null || writer.getParameterTypes().length != 1) continue;
                writer.setAccessible(true);
                try {
                    writer.invoke(object, ConvertUtils.convert(value, writer.getParameterTypes()[0]));
                } catch (InvocationTargetException | IllegalAccessException e) {
                    log.warn("cannot set property {} on field: {}", keyName, writer.getName());
                }
            }
        }
        return object;
    }

    private String keyNameToClassPropertyName(String keyName) {
        StringBuilder sb = new StringBuilder();
        char[] arr = keyName.toCharArray();
        int index = keyName.indexOf('_') + 1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '_') {
                sb.append(Character.toUpperCase(arr[i + 1]));
            } else if (i != index){
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }
}