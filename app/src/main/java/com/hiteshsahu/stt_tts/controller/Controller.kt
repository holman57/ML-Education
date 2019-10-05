package com.hiteshsahu.stt_tts.controller

import com.hiteshsahu.stt_tts.data_structures.History

class Controller {
    fun shouldEncourage(history: History): Boolean {
        return Math.random() < 0.5
    }
    fun shouldTryAgain(history: History): Boolean {
        return Math.random() < 0.5
    }
}