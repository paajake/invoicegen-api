package com.invoicegen.services

import com.invoicegen.models.Rank
import com.invoicegen.repositories.RankRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RankService (private val rankRepository: RankRepository){

    fun getRanks(): List<Rank> = rankRepository.findAll()


    fun addRank(rank: Rank): ResponseEntity<Rank> = ResponseEntity.ok(rankRepository.save(rank))


    fun getRankById(rankId: Long): ResponseEntity<Rank> = rankRepository.findById(rankId).map{
        rank -> ResponseEntity.ok(rank)
    }.orElse(ResponseEntity.notFound().build())


    fun putRank(rankId: Long, newRank: Rank): ResponseEntity<Rank> = rankRepository.findById(rankId).map {
        currentRank -> val updatedRank: Rank = currentRank.copy(
                                        name = newRank.name?: currentRank.name,
                                        rate = newRank.rate?: currentRank.rate,
                                        updatedAt = LocalDateTime.now()
                                )
                ResponseEntity.ok().body(rankRepository.save(updatedRank))
            }.orElse(ResponseEntity.notFound().build())


    fun deleteRank(rankId: Long): ResponseEntity<Void> = rankRepository.findById(rankId).map { rank ->
                rankRepository.delete(rank)
                ResponseEntity<Void>(HttpStatus.ACCEPTED)
            }.orElse(ResponseEntity.notFound().build())
}