package com.invoicegen.controllers

import com.invoicegen.models.Client
import com.invoicegen.services.ClientService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(value = "Clients of the Law firm and their related information resource", tags = ["Clients"])
@RestController
@RequestMapping("/api/clients")
class ClientController (private val clientService: ClientService) {

    @ApiOperation(value = "Fetch all Clients")
    @GetMapping
    fun getClients(): List<Client> = clientService.getClients()

    @ApiOperation(value = "Store a New Client")
    @ApiImplicitParam(name = "client", value = "Client Entity containing details of the new Client",
            required = true, dataType = "Client")
    @PostMapping
    fun addClient(@Valid @RequestBody client: Client): ResponseEntity<Client> = clientService.addClient(client)

    @ApiOperation(value = "Fetch Details of a Specific Client")
    @ApiImplicitParam(name = "id", value = "ID of required Client", required = true, dataType = "Long")
    @GetMapping("/{id}")
    fun getClientById(@PathVariable(value="id") clientId: Long): ResponseEntity<Client> = clientService.getClientById(clientId)

    @ApiOperation(value = "Update a Client's Details")
    @ApiImplicitParam(name = "client", value = "Client Entity containing details of the updated Client",
            required = true, dataType = "Client")
    @PutMapping("/{id}")
    fun updateClientById(@PathVariable(value = "id") clientId: Long,
                       @Valid @RequestBody newClient: Client): ResponseEntity<Client> = clientService.putClient(clientId, newClient)

    @ApiOperation(value = "Delete a Specific Client")
    @ApiImplicitParam(name = "id", value = "ID of Client to be deleted", required = true, dataType = "Long")
    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable(value = "id") clientId: Long): ResponseEntity<Void> = clientService.deleteClient(clientId)
}