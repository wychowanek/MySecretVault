package com.jakubwyc.mysecretvault

import com.jakubwyc.mysecretvault.repository.TreasureRepository
import com.jakubwyc.mysecretvault.repository.UserRepository

interface SystemContext {

    val treasureRepository: TreasureRepository
    val userRepository: UserRepository

}