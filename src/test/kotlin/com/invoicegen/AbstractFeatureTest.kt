package com.invoicegen

import com.invoicegen.services.DatabaseCleanupService
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired

open class AbstractFeatureTest {
    @field:Autowired
    private lateinit
    var truncateDatabaseService: DatabaseCleanupService

    @AfterEach
    open fun cleanupAfterEach() {
        println( "Cleanup up the test database")
        truncateDatabaseService.truncate()
    }
}