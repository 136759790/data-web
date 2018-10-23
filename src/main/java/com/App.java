package com;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class App{
    public static void main( String[] args ){
        SpringApplication.run(App.class, args);
    }
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
    	MapperScannerConfigurer config=new MapperScannerConfigurer();
    	config.setBasePackage("com.qian.springmvc.**.dao");
    	config.setSqlSessionFactoryBeanName("sqlSessionFactory");
    	return config;
    }
}
