package br.com.maxmilhas.model

import java.time.LocalDate

class CustomerRequestApiDTO(
    val idCustomer: Int,
    val fullname: String,
    val emailCustomer: String,
    val birthCustomer: LocalDate,
) {
    val firstname: String
        get() = fullname.split(" ").firstOrNull() ?: ""
}