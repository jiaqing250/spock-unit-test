package org.dbunit.supper;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author 2022-12-19 zfz
 */
@Slf4j
public class DataSourceHolder {
    //    public static SqlSessionFactory builder = new MybatisSqlSessionFactoryBuilder().build(DataSourceHolder.class.getClassLoader().getResourceAsStream("mybatis-config.xml"));
    public static SqlSessionFactory builder;
    public static Configuration configuration;


    static {
        //这种方式主要是为了兼容dbUnit
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        try {
            factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
            factory.setDataSource(SpecUtils.mysqlDataSource());
            builder = factory.getObject();

            MybatisConfiguration configurationT = (MybatisConfiguration) builder.getConfiguration();
            GlobalConfig globalConfig = configurationT.getGlobalConfig();
//            globalConfig.setMetaObjectHandler(new MybatisPlusHandler()); --mybatisplus自定义的自动填充
            configuration = builder.getConfiguration();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //这里是原mybatis的方式
        //       SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(SkillServiceImplSpec.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/mybatis-config.xml"));
        //这里是mybatisPlus 封装后的
        // 作用一:处理entity类和mysql中字段不一致的问题，java中是驼峰式，java是下划线
        // 作用二:处理mapper 继承 baseMapper 原mapper没有baseMapper中封装的statement
        // 缺点一:因为是非spring模式，在启动后所有的插件都会失效
        //解决非spring模式下导致metaObjectHandler缺失的问题,主要就是新增或者更新时，自动填充字段的问题
        /*MybatisConfiguration configurationT = (MybatisConfiguration) DataSourceHolder.builder.getConfiguration();
        GlobalConfig globalConfig = configurationT.getGlobalConfig();
        globalConfig.setMetaObjectHandler(new MybatisPlusHandler());
        configuration = DataSourceHolder.builder.getConfiguration();*/

    }


    @SneakyThrows
    public static void initTable() {
        try (Connection connection = SpecUtils.mysqlDataSource().getConnection();
             Statement statement = connection.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(FileUtil.getAbsolutePath("classpath:test/db/init_table.sql")))) {
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("--") || line.startsWith("//")) { // 判断是否为注释
                        continue; // 忽略注释行
                    }
                    if (line.trim().endsWith(";")) { // 判断语句是否结束
                        sb.append(line);
                        log.trace("执行sql:{}", sb.toString());
                        statement.execute(sb.toString()); // 执行SQL语句
                        sb.setLength(0); // 清空sb
                    } else {
                        sb.append(line).append(" "); // 将多行合并为一行
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
