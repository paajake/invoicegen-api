package com.invoicegen.controllers

import com.invoicegen.models.Rank
import com.invoicegen.services.RankService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(value = "Rank or Level of Lawyers and related information resource", tags = ["Ranks"])
@RestController
@RequestMapping("/api/ranks")
class RankController (private val rankService: RankService) {

    @ApiOperation(value = "Fetch all Ranks")
    @GetMapping
    fun getRanks(): List<Rank> = rankService.getRanks()

    @ApiOperation(value = "Store a New Rank")
    @ApiImplicitParam(name = "rank", value = "Rank Entity containing details of the new Rank",
            required = true, dataType = "Rank")
    @PostMapping
    fun addRank(@Valid @RequestBody rank: Rank): ResponseEntity<Rank> = rankService.addRank(rank)

    @ApiOperation(value = "Fetch Details of a Specific Rank")
    @ApiImplicitParam(name = "id", value = "ID of required Rank", required = true, dataType = "Long")
    @GetMapping("/{id}")
    fun getRankById(@PathVariable(value="id") rankId: Long): ResponseEntity<Rank> = rankService.getRankById(rankId)

    @ApiOperation(value = "Update a Rank's Details")
    @ApiImplicitParam(name = "rank", value = "Rank Entity containing details of the updated Rank",
            required = true, dataType = "Rank")
    @PutMapping("/{id}")
    fun updateRankById(@PathVariable(value = "id") rankId: Long,
                       @Valid @RequestBody newRank: Rank): ResponseEntity<Rank> = rankService.putRank(rankId, newRank)

    @ApiOperation(value = "Delete a Specific Rank")
    @ApiImplicitParam(name = "id", value = "ID of Rank to be deleted", required = true, dataType = "Long")
    @DeleteMapping("/{id}")
    fun deleteRank(@PathVariable(value = "id") rankId: Long): ResponseEntity<Void> = rankService.deleteRank(rankId)
}