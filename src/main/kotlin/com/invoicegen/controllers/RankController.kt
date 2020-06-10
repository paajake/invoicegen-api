package com.invoicegen.controllers

import com.invoicegen.models.Rank
import com.invoicegen.services.RankService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/ranks")
class RankController (private val rankService: RankService) {

    @GetMapping
    fun getTasks(): List<Rank> = rankService.getRanks()

    @PostMapping
    fun addRank(@Valid @RequestBody rank: Rank): ResponseEntity<Rank> = rankService.addRank(rank)

    @GetMapping("/{id}")
    fun getRankById(@PathVariable(value="id") rankId: Long): ResponseEntity<Rank> = rankService.getRankById(rankId)

    @PutMapping("/{id}")
    fun updateRankById(@PathVariable(value = "id") rankId: Long,
                       @Valid @RequestBody newRank: Rank): ResponseEntity<Rank> = rankService.putRank(rankId, newRank)

    @DeleteMapping("/{id}")
    fun deleteRank(@PathVariable(value = "id") rankId: Long): ResponseEntity<Void> = rankService.deleteRank(rankId)
}