package com.invoicegen.models

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email


@Entity
@Table(name = "clients")
data class Client(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val name: String? = null,
        val phone: String? = null,
        @field: Email val email: String? = null,
        val createdAt: LocalDateTime? = LocalDateTime.now(),
        val updatedAt: LocalDateTime? = LocalDateTime.now()
)