package br.com.maxmilhas.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient = WebClient.create("http://localhost:8080")

}