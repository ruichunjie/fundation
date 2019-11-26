package cn.own.boot.fundation.config;

import cn.own.boot.fundation.condition.ConditionalOnDynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @description: mybatis数据配置
 * @author: xinYue
 * @time: 2019/11/20 10:36
 */
@Configuration
@MapperScan("cn.**.mapper")
@ConditionalOnDynamicDataSource
public class MybatisConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        sessionFactory.setTypeHandlersPackage("cn.own.boot.fundation.handler");
        sessionFactory.setConfiguration(getConfig());
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
    }

    public org.apache.ibatis.session.Configuration getConfig(){
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(Boolean.TRUE);
        return config;
    }
}
