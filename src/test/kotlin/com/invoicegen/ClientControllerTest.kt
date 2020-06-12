package com.invoicegen

import com.invoicegen.models.Client
import org.assertj.core.api.Assertions.within
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.client.postForObject
import org.springframework.http.HttpStatus
import java.time.temporal.ChronoUnit


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerTest(@Autowired val restTemplate: TestRestTemplate) : AbstractFeatureTest() {

	private val baseUrl: String = "/api/clients"

	fun insertData(): List<Client> {
		val requestData = listOf(
				Client(name = "JJ Partners", email = "finance@jjpartners.com", phone = "+233348521992"),
				Client(name = "Suits with Finesse", email = "accounting@suitsfinesse.com", phone = "+23342299453"),
				Client(name = "Litigators @ Law", email = "bussiness@litigatorsatlaw.com", phone = "+23311122234")
		)

		for (client in requestData){
			val entity = restTemplate.postForEntity<String>(baseUrl, client)
			assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		}
		return requestData
	}

	@Test
	fun `Post Client and Test Returned Client Object` (){
		val requestData = Client(name = "JJ Partners", email = "finance@jjpartners.com", phone = "+233348521992")
		val entity = restTemplate.postForObject<Client>(baseUrl, requestData)
		assertThat(entity).isNotNull
		assertThat(entity?.name).isEqualTo("JJ Partners")
		assertThat(entity?.email).isEqualTo("finance@jjpartners.com")
		assertThat(entity?.phone).isEqualTo("+233348521992")
		assertThat(entity?.createdAt).isCloseToUtcNow(within(1, ChronoUnit.SECONDS))
		assertThat(entity?.updatedAt).isCloseToUtcNow(within(1, ChronoUnit.SECONDS))
	}

	@Test
	fun `Post Client and Test Returned Client by API` (){
		val requestData = Client(name = "Litigators @ Law", email = "bussiness@litigatorsatlaw.com", phone = "+23311122234")
		val entity = restTemplate.postForEntity<String>(baseUrl, requestData)
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains(listOf(requestData.name, requestData.phone, requestData.email))
	}

	@Test
	fun `Get All Clients`() {
		val requestData = insertData()

		val entity = restTemplate.getForEntity<String>(baseUrl)
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())

		for (client in requestData){
			assertThat(entity.body).contains(listOf(client.name, client.email, client.phone))
		}
	}

	@Test
	fun `Get Client By ID`() {
		val requestData = insertData()

		val entity = restTemplate.getForEntity<String>("$baseUrl/2")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains(requestData[1].name, requestData[1].email, requestData[1].phone)
	}

	@Test
	fun `Update Client`() {
		insertData()

		var updatedClient = Client(name = "Brothers @ Law")

		restTemplate.put("$baseUrl/2", updatedClient)
		var entity = restTemplate.getForEntity<String>("$baseUrl/2")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains(updatedClient.name)

		updatedClient = updatedClient.copy(phone = "+23323341221")

		restTemplate.put("$baseUrl/2", updatedClient)
		entity = restTemplate.getForEntity("$baseUrl/2")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains(updatedClient.name, updatedClient.phone)

		updatedClient = Client(name = "Bros @ Law", phone = "+23312234221", email = "bros@broslaw.com")

		restTemplate.put("$baseUrl/2", updatedClient)
		entity = restTemplate.getForEntity("$baseUrl/2")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains(updatedClient.name, updatedClient.email, updatedClient.phone)
	}

	@Test
	fun `Delete Client`() {
		val requestData= insertData()

		restTemplate.delete("$baseUrl/2")
		var entity = restTemplate.getForEntity<String>("$baseUrl/2")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		entity = restTemplate.getForEntity(baseUrl)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains(requestData[0].name, requestData[0].email, requestData[0].phone)
		assertThat(entity.body).doesNotContain(requestData[1].name, requestData[1].email, requestData[1].phone)
	}
}
