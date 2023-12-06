package org.dbunit.supper

import cn.hutool.core.date.DateUtil
import org.apache.tomcat.jdbc.pool.DataSource
import org.redisson.api.RedissonClient
import org.springframework.data.redis.core.RedisTemplate
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author 2022-12-19 zfz
 */
public class MyBaseSpec extends Specification {
    @Shared
    DataSource dataSource
    @Shared
    RedisTemplate redisTemplate
    @Shared
    RedissonClient redissonClient

    def setupSpec() {
        println("开始时间:" + DateUtil.date())
        dataSource = SpecUtils.inMemoryDataSource()
//        redisTemplate = SpecUtils.redisTemplate()
//        redissonClient = SpecUtils.redisson()
        dataSource?.with { DataSourceHolder.initTable() }
    }

    def cleanup() {
//        dataSource?.with { DataSourceHolder.initTable() } //清理表
    }


}
