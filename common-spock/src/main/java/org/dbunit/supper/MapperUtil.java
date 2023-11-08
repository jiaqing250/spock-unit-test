package org.dbunit.supper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author 2022-12-19 zfz
 */
public class MapperUtil {

    public static <T> T getMapper(Class<? super BaseMapper> clazz) {
        return (T) DataSourceHolder.configuration.getMapper(clazz, DataSourceHolder.builder.openSession(true));
    }
}
