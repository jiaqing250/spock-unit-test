package org.dbunit.supper


import com.alibaba.fastjson.parser.ParserConfig
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer
import org.apache.tomcat.jdbc.pool.DataSource
import org.redisson.Redisson
import org.redisson.config.Config
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

class SpecUtils {
    static def dataSource;
    static def redisTemplate;
    static def redisson;

    private SpecUtils() {}

    static DataSource inMemoryDataSource() {
        if (dataSource != null) {
            return dataSource
        }

        def source = new DataSource().with { dataSource ->
            dataSource.driverClassName = 'org.h2.Driver'
            dataSource.url = 'jdbc:h2:mem:test;MODE=MySQL'
            dataSource.username = 'sa'
            dataSource.password = ''
            dataSource
        }
        dataSource = source
        return dataSource

    }

    static DataSource mysqlDataSource() {
        if (dataSource != null) {
            return dataSource
        }
        //获取数据库连接池对象
        def source = new DataSource()
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/rlb?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true");
        source.setUsername("root");
        source.setPassword("123456");
        dataSource = source
        return source

    }


    static RedisTemplate<String, Object> redisTemplate() {
        if (redisTemplate != null) {
            return redisTemplate;
        }
        def configuration = new RedisStandaloneConfiguration("localhost", 6379)
        configuration.setDatabase(0)
        configuration.setPassword("1234")
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration);

        RedisTemplate<String, Object> redisTemplateT = new RedisTemplate();
        redisTemplateT.setConnectionFactory(jedisConnectionFactory);
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        redisTemplateT.setValueSerializer(fastJsonRedisSerializer);
        redisTemplateT.setHashValueSerializer(fastJsonRedisSerializer);
        //自定义key生成策略
//        def serializer = new RedisKeySerializer()
//        def keyPre = serializer.PREFIX_KEY + DateUtil.date().toString(DatePattern.PURE_DATETIME_MS_PATTERN) + ":"
//        serializer.serialize(keyPre)
//        serializer.deserialize(keyPre.getBytes("utf-8"))
//        redisTemplateT.setKeySerializer(serializer);
        redisTemplateT.setHashKeySerializer(fastJsonRedisSerializer);
        redisTemplateT.afterPropertiesSet();
        redisTemplate = redisTemplateT;
        return redisTemplateT;
    }

    static Redisson redisson() {
        if (redisson != null) {
            return redisson;
        }
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:6379")
                .setPassword("1234")
                .setDatabase(0)
        redisson = Redisson.create(config);
        return redisson;
    }

}