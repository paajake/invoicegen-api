package com.invoicegen

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InvoicegenApplication

fun main(args: Array<String>) {
	runApplication<InvoicegenApplication>(*args)
}
