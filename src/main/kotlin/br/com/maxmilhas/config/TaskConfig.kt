package br.com.maxmilhas.config

import org.springframework.cloud.task.configuration.DefaultTaskConfigurer
import org.springframework.cloud.task.configuration.TaskConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class TaskConfig {

    @Bean
    fun taskConfigurer(dataSource: DataSource): TaskConfigurer = DefaultTaskConfigurer(dataSource)
}