package com.example.mongodemo

import com.example.mongodemo.persistence.entity.Player
import com.example.mongodemo.persistence.repository.PlayerRepository
import com.example.mongodemo.service.PlayerService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.util.AssertionErrors

@ExtendWith(SpringExtension::class)
@DataMongoTest
@ComponentScan
class MongoDemoApplicationTests {

    @Autowired
    lateinit var playerRepository: PlayerRepository
    @Autowired
    @Qualifier("playerService")
    lateinit var playerService: PlayerService

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MongoDemoApplicationTests::class.java)
        const val TEST_PLAYER_HANDLE = "testPlayer"
        val TEST_PLAYER_1 = Player("alice", 20)
        val TEST_PLAYER_2 = Player("bob", 15)
        val TEST_PLAYER_3 = Player("charlie", 25)
        val TEST_PLAYER_4 = Player("dawn", 30)
        val TEST_PLAYER_5 = Player("ed", 10)
    }

    @Test
    fun setupTestDatabase() {
        playerRepository.save(TEST_PLAYER_1)
        playerRepository.save(TEST_PLAYER_2)
        playerRepository.save(TEST_PLAYER_3)
        playerRepository.save(TEST_PLAYER_4)
        playerRepository.save(TEST_PLAYER_5)
    }

    @Test
    fun testLeaders() {
        logger.info("Begin testLeaders")
        val leaders = playerService.leaders()
        AssertionErrors.assertEquals("There should be 3 leaders.", 3, leaders.size)
        AssertionErrors.assertEquals("The first leader should be dawn.", TEST_PLAYER_4, leaders[0])
        AssertionErrors.assertEquals("The second leader should be charlie.", TEST_PLAYER_3, leaders[1])
        AssertionErrors.assertEquals("The third leader should be alice.", TEST_PLAYER_1, leaders[2])
        logger.info("End testLeaders")
    }

    @Test
    fun testScore() {
        logger.info("Begin testScore")
        playerRepository.save(Player(TEST_PLAYER_HANDLE))
        playerService.score(TEST_PLAYER_HANDLE, 10)
        val player = playerRepository.findById(TEST_PLAYER_HANDLE).get()
        AssertionErrors.assertEquals("Total score should be 10 after the first scoring event.", 10, player.totalScore)
        AssertionErrors.assertEquals("The history should have a single element.", 1, player.history.size)
        AssertionErrors.assertEquals("The recorded points should be 10.", 10, player.history[0].points)
        playerService.score(TEST_PLAYER_HANDLE, 5)
        val player2 = playerRepository.findById(TEST_PLAYER_HANDLE).get()
        AssertionErrors.assertEquals("Total score should be 15 after the second scoring event.", 15, player2.totalScore)
        AssertionErrors.assertEquals("The history should have a single element.", 2, player2.history.size)
        AssertionErrors.assertEquals("The first recorded points should be 10.", 10, player2.history[0].points)
        AssertionErrors.assertEquals("The second recorded points should be 5.", 5, player2.history[1].points)
        logger.info("End testScore")
    }

}
