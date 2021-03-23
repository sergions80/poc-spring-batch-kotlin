package br.com.maxmilhas.model

import java.time.LocalDate
import java.time.LocalDateTime

data class CustomerDbDTO(
    var id: Int = 0,
    var name: String = "",
    var email: String = "",
    var birth: LocalDate = LocalDate.now(),
)