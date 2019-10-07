package com.hiteshsahu.stt_tts.controller

import com.hiteshsahu.stt_tts.data_structures.History

class Controller {

    fun shouldEncourage(history: History, title: String): Boolean {
        return Math.random() < 0.5
    }

    fun shouldTryAgain(history: History, title: String): Boolean {
        return Math.random() < 0.5
    }

    fun shouldExplain(history: History, title: String): Boolean {
        return Math.random() < 0.5
    }

}