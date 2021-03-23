package br.com.maxmilhas.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun springBatchDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean(name = ["customerDataSource"])
    @ConfigurationProperties(prefix = "customer.datasource")
    fun customerDataSource(): DataSource = DataSourceBuilder.create().build()
}