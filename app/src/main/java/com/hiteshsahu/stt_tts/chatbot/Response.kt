package com.hiteshsahu.stt_tts.chatbot

import java.util.*

class Response {

    val hasntStarted = listOf(
            "Give it a try.",
            "Go for it.",
            "Why not?",
            "It’s worth a shot.",
            "What are you waiting for?",
            "What do you have to lose?",
            "You might as well.",
            "Just do it.")

    val encourage = listOf(
            "There you go!",
            "Keep up the good work.",
            "Keep it up.",
            "Correct!",
            "You'r doing great!",
            "You'r doing a great job.",
            "Way to go. You'r awesome!",
            "Awesome!",
            "That's right!",
            "That's correct.",
            "That's it. You've got it!",
            "Good job.",
            "Good job.",
            "Good job.",
            "Good job.",
            "Good job.",
            "Good job.",
            "Good job.",
            "Good job.",
            "Good job.",
            "Good job.",
            "Good job.",
            "Good job.",
            "Yes. That's it!",
            "Right!",
            "You'r right!",
            "That is correct!",
            "That's it. Way to go!",
            "You'r getting it. Good Job!",
            "I’m so proud of you!")

    val keepTrying = listOf(
            "Wrong. Hang in there.",
            "Try again. Hang in there.",
            "Don’t give up.",
            "Don’t give up. Try again.",
            "Keep pushing.",
            "Keep pushing. Try again",
            "That was incorrect",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Try again.",
            "Give it another shot.",
            "Try that again.",
            "One more time. That was incorrect.",
            "Wrong. Try that again.",
            "Wrong. Try again.",
            "Incorrect. Try again.",
            "That was incorrect. Keep fighting!",
            "That was wrong. Keep trying",
            "Never give up.",
            "Come on! You can do it!.")

    val inspire = listOf(
            "Follow your dreams.",
            "Reach for the stars.",
            "Do the impossible.",
            "Believe in yourself.",
            "The sky is the limit.")

    val mistake = listOf(
            "oversight",
            "slip up",
            "hiccup",
            "confusion",
            "bump",
            "hurdle",
            "challenge")

    val modifier_small = listOf(
            "slight",
            "little",
            "bit of",
            "minor",
            "small")

    val earlySiteWords = listOf(
            "all", "am", "are", "at", "ate", "now", "on", "our", "out", "please",
            "be", "black", "brown", "but", "came", "did", "do", "eat", "four",
            "get", "good", "have", "he", "into", "like", "must", "new", "no",
            "pretty", "ran", "ride", "saw", "say", "she", "so", "soon", "that",
            "there", "they", "this", "too", "went", "what", "white", "who", "will",
            "under", "want", "was", "well", "with", "yes", "a", "and", "away",
            "big", "blue", "can", "come", "down", "find", "for", "funny", "go",
            "help", "here", "I", "in", "is", "it", "jump", "little", "look",
            "make", "me", "my", "not", "one", "play", "red", "run", "said",
            "see", "the", "three", "to", "two", "up", "we", "where", "yellow", "you")

    fun encourage(): String{
        val randomAnswer = Random().nextInt(encourage.size) + 1
        return encourage[randomAnswer - 1]
    }

    fun keepTrying(): String{
        val randomAnswer = Random().nextInt(keepTrying.size) + 1
        return keepTrying[randomAnswer - 1]
    }

    fun siteWords(): String{
        val randomAnswer = Random().nextInt(earlySiteWords.size) + 1
        return earlySiteWords[randomAnswer - 1]
    }
}