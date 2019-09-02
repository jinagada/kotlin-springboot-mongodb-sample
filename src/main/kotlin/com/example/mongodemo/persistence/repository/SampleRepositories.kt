package com.example.mongodemo.persistence.repository

import com.example.mongodemo.persistence.entity.Player
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerRepository : CrudRepository<Player, String> {
    fun findTop3ByOrderByTotalScoreDesc() : List<Player>
}
