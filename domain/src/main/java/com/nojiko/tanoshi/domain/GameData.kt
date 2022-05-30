package com.nojiko.tanoshi.domain

import java.io.Serializable

data class GameData(
    val score: Int,
    val maxScore: Int,
    val nbRightAnswer: Int,
    val nbQuestion: Int
) : Serializable