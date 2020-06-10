package com.invoicegen

import com.invoicegen.models.Rank
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
import java.math.BigDecimal
import java.time.temporal.ChronoUnit


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RankControllerTest(@Autowired val restTemplate: TestRestTemplate) : AbstractFeatureTest() {

	private val baseUrl: String = "/api/ranks"

	fun insertData(): List<Rank> {
		val requestData = listOf(
				Rank(name = "Senior Litigator", rate = BigDecimal("2000.2")),
				Rank(name = "Junior Litigator", rate = BigDecimal("1000.1")),
				Rank(name = "Chief Litigator", rate = BigDecimal("10000.3"))
		)

		for (rank in requestData){
			val entity = restTemplate.postForEntity<String>(baseUrl, rank)
			assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		}
		return requestData
	}

	@Test
	fun `Post Rank and Test Returned Rank Object` (){
		val requestData = Rank(name = "Senior Litigator", rate = BigDecimal("2000.2"))
		val entity = restTemplate.postForObject<Rank>(baseUrl, requestData)
		assertThat(entity).isNotNull
		assertThat(entity?.name).isEqualTo("Senior Litigator")
		assertThat(entity?.rate).isEqualTo(BigDecimal("2000.2"))
		assertThat(entity?.createdAt).isCloseToUtcNow(within(1, ChronoUnit.SECONDS))
		assertThat(entity?.updatedAt).isCloseToUtcNow(within(1, ChronoUnit.SECONDS))
	}

	@Test
	fun `Post Rank and Test Returned Rank by API` (){
		val requestData = Rank(name = "Senior Litigator", rate = BigDecimal("2000.2"))
		val entity = restTemplate.postForEntity<String>(baseUrl, requestData)
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains(listOf(requestData.name, requestData.rate.toString()))
	}

	@Test
	fun `Get All Ranks`() {
		val requestData = insertData()

		val entity = restTemplate.getForEntity<String>(baseUrl)
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())

		for (rank in requestData){
			assertThat(entity.body).contains(listOf(rank.name, rank.rate.toString()))
		}
	}

	@Test
	fun `Get Rank By ID`() {
		val requestData = insertData()

		val entity = restTemplate.getForEntity<String>("$baseUrl/2")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains(requestData[1].name, requestData[1].rate.toString())
	}

	@Test
	fun `Update Rank`() {
		insertData()

		var updatedRank = Rank(name = "Special Litigator")

		restTemplate.put("$baseUrl/2", updatedRank)
		var entity = restTemplate.getForEntity<String>("$baseUrl/2")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains("Special Litigator")

		updatedRank = Rank(rate = BigDecimal("5000.3"))

		restTemplate.put("$baseUrl/2", updatedRank)
		entity = restTemplate.getForEntity<String>("$baseUrl/2")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains("Special Litigator", "5000.3")

		updatedRank = Rank(name = "Special Prosecutor", rate = BigDecimal("9000.13"))

		restTemplate.put("$baseUrl/2", updatedRank)
		entity = restTemplate.getForEntity<String>("$baseUrl/2")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains("Special Prosecutor", "9000.13")
	}

	@Test
	fun `Delete Rank`() {
		val requestData= insertData()

		restTemplate.delete("$baseUrl/2")
		var entity = restTemplate.getForEntity<String>("$baseUrl/2")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		entity = restTemplate.getForEntity<String>(baseUrl)
		assertThat(entity.hasBody())
		assertThat(entity.body).contains(requestData[0].name, requestData[0].rate.toString())
		assertThat(entity.body).doesNotContain(requestData[1].name, requestData[1].rate.toString())

	}

}
