package com.invoicegen.services

import com.invoicegen.models.Client
import com.invoicegen.repositories.ClientRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ClientService (private val clientRepository: ClientRepository){

    fun getClients(): List<Client> = clientRepository.findAll()


    fun addClient(client: Client): ResponseEntity<Client> = ResponseEntity.ok(clientRepository.save(client))


    fun getClientById(clientId: Long): ResponseEntity<Client> = clientRepository.findById(clientId).map{
        client -> ResponseEntity.ok(client)
    }.orElse(ResponseEntity.notFound().build())


    fun putClient(clientId: Long, newClient: Client): ResponseEntity<Client> = clientRepository.findById(clientId).map {
        currentClient -> val updatedClient: Client = currentClient.copy(
                                        name = newClient.name?: currentClient.name,
                                        phone = newClient.phone?: currentClient.phone,
                                        email = newClient.email?: currentClient.email,
                                        updatedAt = LocalDateTime.now()
                                )
                ResponseEntity.ok().body(clientRepository.save(updatedClient))
            }.orElse(ResponseEntity.notFound().build())


    fun deleteClient(clientId: Long): ResponseEntity<Void> = clientRepository.findById(clientId).map { client ->
                clientRepository.delete(client)
                ResponseEntity<Void>(HttpStatus.ACCEPTED)
            }.orElse(ResponseEntity.notFound().build())
}