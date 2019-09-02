package com.example.mongodemo.service

import com.example.mongodemo.persistence.entity.Player
import com.example.mongodemo.persistence.repository.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class PlayerService(@Autowired val playerRepository: PlayerRepository) {

    @PostConstruct
    fun init() {
        println("")
    }
    fun leaders(): List<Player> = playerRepository.findTop3ByOrderByTotalScoreDesc()

    fun score(handle: String, points: Int): Int {
        val player = playerRepository.findById(handle).orElse(Player(handle)) + points
        playerRepository.save(player)
        return player.totalScore
    }
}
