package com.invoicegen.repositories

import com.invoicegen.models.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional(Transactional.TxType.MANDATORY)
interface ClientRepository : JpaRepository<Client, Long>