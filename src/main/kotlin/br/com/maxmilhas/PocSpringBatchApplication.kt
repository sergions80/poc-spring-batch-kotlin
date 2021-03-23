package br.com.maxmilhas

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.task.configuration.EnableTask

@SpringBootApplication
@EnableTask
@EnableBatchProcessing
class PocSpringBatchApplication

fun main(args: Array<String>) {
	runApplication<PocSpringBatchApplication>(*args)
}
