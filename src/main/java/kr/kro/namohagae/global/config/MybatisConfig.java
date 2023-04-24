package kr.kro.namohagae.global.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = {"kr.kro.namohagae.board.dao" +
        "                   kr.kro.namohagae.member.dao" +
        "                   kr.kro.namohagae.mall.dao" +
        "                   kr.kro.namohagae.puchingtest.dao "}
        ,sqlSessionFactoryRef="sqlSessionFactory"
        ,sqlSessionTemplateRef="sqlSessionTemplate")
public class MybatisConfig {

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        // mapper.xml 의 resultType 패키지 주소 생략
        sqlSessionFactoryBean.setTypeAliasesPackage("kr.kro.namohagae.board.entity");

        // mybatis 설정 파일 세팅
        sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mapper-mybatis-config.xml"));

        // mapper.xml 위치 패키지 주소
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis-mapper/*Mapper.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}