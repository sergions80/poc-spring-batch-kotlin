package br.com.maxmilhas.batch.jobs

import br.com.maxmilhas.model.CustomerDbDTO
import br.com.maxmilhas.model.CustomerRequestApiDTO
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import javax.sql.DataSource

@Configuration
class CustomerToApiConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun curtomerToApiJob(step: Step): Job = jobBuilderFactory.get("customerToApiJob")
        .start(step)
        .incrementer(RunIdIncrementer())
        .build()

    @Bean
    fun step1(
        itemReader: ItemReader<CustomerDbDTO>,
        itemProcessor: ItemProcessor<CustomerDbDTO, CustomerRequestApiDTO>,
        itemWriter: ItemWriter<CustomerRequestApiDTO>
    ): Step = stepBuilderFactory.get("step1")
        .chunk<CustomerDbDTO, CustomerRequestApiDTO>(1)
        .reader(itemReader)
        .processor(itemProcessor)
        .writer(itemWriter)
        .build()

    @Bean
    fun itemReader(
        @Qualifier(value = "customerDataSource") customerDataSource: DataSource
    ): ItemReader<CustomerDbDTO> = JdbcCursorItemReaderBuilder<CustomerDbDTO>()
        .name("customerDbDTOItemReader")
        .dataSource(customerDataSource)
        .sql("SELECT id, name, email, birth from CUSTOMER")
        .rowMapper(BeanPropertyRowMapper(CustomerDbDTO::class.java))
        .build()

    @Bean
    fun itemProcessor(): ItemProcessor<CustomerDbDTO, CustomerRequestApiDTO> = ItemProcessor {
        CustomerRequestApiDTO(
            idCustomer = it.id,
            fullname = it.name,
            emailCustomer = it.email,
            birthCustomer = it.birth
        )
    }

    @Bean
    fun itemWriter(webClient: WebClient): ItemWriter<CustomerRequestApiDTO> = ItemWriter {
        webClient.post()
            .uri("/customer-service")
            .body(Mono.just(it[0]), CustomerRequestApiDTO::class.java)
            .retrieve()
            .bodyToMono(Void::class.java)
            .block()
    }
}