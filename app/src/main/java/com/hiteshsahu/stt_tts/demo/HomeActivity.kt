package com.hiteshsahu.stt_tts.demo

import android.annotation.SuppressLint
import java.util.*
import android.view.animation.AlphaAnimation
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.TextView
import android.widget.Button
import com.hiteshsahu.stt_tts.translation_engine.ConversionCallback
import com.hiteshsahu.stt_tts.translation_engine.TranslatorFactory
import android.os.CountDownTimer
import android.support.constraint.solver.GoalRow
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View.GONE
import android.view.View.VISIBLE
import com.hiteshsahu.stt_tts.data_structures.History


class HomeActivity : BasePermissionActivity() {

    private var fadeIn = AlphaAnimation(0.0f, 1.0f)
    private var fadeOut = AlphaAnimation(1.0f, 0.0f)

    private val history = History()

    override fun getActivityLayout(): Int {
        return com.hiteshsahu.stt_tts.R.layout.activity_home
    }

    override fun setUpView() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.hiteshsahu.stt_tts.R.layout.activity_home)

        val constraintLayout = findViewById<ConstraintLayout>(com.hiteshsahu.stt_tts.R.id.constraintLayout)
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(5000)
        animationDrawable.setExitFadeDuration(2000)

        animationDrawable.start()

        val initText = findViewById<TextView>(com.hiteshsahu.stt_tts.R.id.InitText)
        val displayText = findViewById<TextView>(com.hiteshsahu.stt_tts.R.id.DisplayText)
        val emphasisText = findViewById<TextView>(com.hiteshsahu.stt_tts.R.id.EmphasisText)
        val speechToText = findViewById<FloatingActionButton>(com.hiteshsahu.stt_tts.R.id.speechToText)

        fadeIn.duration = 1200
        fadeIn.fillAfter = true
        fadeOut.startOffset = 1000

        fadeOut.duration = 1200
        fadeOut.fillAfter = true
        fadeOut.startOffset = 10

        initText.startAnimation(fadeIn)

        say("Hello")

        val helloButton = findViewById<Button>(com.hiteshsahu.stt_tts.R.id.HelloButton)
        val leftButton = findViewById<Button>(com.hiteshsahu.stt_tts.R.id.LeftButton)
        val centerButton = findViewById<Button>(com.hiteshsahu.stt_tts.R.id.CenterButton)
        val rightButton = findViewById<Button>(com.hiteshsahu.stt_tts.R.id.RightButton)

        helloButton.visibility = VISIBLE
        helloButton.setOnClickListener {
            displayText.visibility = VISIBLE
            initText.text = ""
            helloButton.text = ""
            helloButton.visibility = GONE
            initText.visibility = GONE

            controller(leftButton, centerButton, rightButton, emphasisText, displayText)

        }
    }

    @SuppressLint("SetTextI18n")
    private fun controller(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView) {

        println(history.print())

        clearView(leftButton, centerButton, rightButton, emphasisText, displayText)

        when (Random().nextInt(3) + 1) {
            1 -> {
                alphabet(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
            2 -> {
                var randomLetter = Random().nextInt(26) + 97
                var noRepeat = 0
                val letter = randomLetter.toChar()
                while (!history.contain("letter","$letter") && noRepeat < 26) {
                    randomLetter = Random().nextInt(26) + 97
                    noRepeat += 1
                }

                thisIsTheLetterTimeline(2500, 1000, leftButton, centerButton, rightButton, emphasisText, displayText, randomLetter)
            }
            3 -> {
                val firstRandomDigit = Random().nextInt(4) + 1
                val secondRandomDigit = Random().nextInt(4) + 1

                emphasisText.text = "$firstRandomDigit + $secondRandomDigit ="

                if (!history.contain("title","addition 1..9")) {
                    say("Can you evaluate this expression? $firstRandomDigit + $secondRandomDigit")
                    displayText.text = "Can you evaluate this expression?"
                    displayText.startAnimation(fadeIn)
                    displayText.visibility = VISIBLE
                }
                else {
                    say("$firstRandomDigit + $secondRandomDigit")
                    displayText.text = ""
                    displayText.visibility = GONE
                }

                arithmetic(leftButton, centerButton, rightButton, emphasisText, displayText, firstRandomDigit, secondRandomDigit)
            }

        }
    }

    private fun clearView(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView) {
        leftButton.visibility = GONE
        centerButton.visibility = GONE
        rightButton.visibility = GONE
        emphasisText.visibility = GONE
        displayText.visibility = GONE

        leftButton.text = ""
        centerButton.text = ""
        rightButton.text = ""
        emphasisText.text = ""
        displayText.text = ""
    }

    @SuppressLint("SetTextI18n")
    private fun arithmetic(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView, firstRandomDigit: Int, secondRandomDigit: Int) {
        history.startCard("title","addition 1..9")
        history.add("type","arithmetic")

        leftButton.alpha = 1F
        centerButton.alpha = 1F
        rightButton.alpha = 1F

        emphasisText.visibility = VISIBLE
        emphasisText.startAnimation(fadeIn)
        emphasisText.textSize = 90.0F

        val correctAnswer = firstRandomDigit + secondRandomDigit

        val randomAnswer = Random().nextInt(3) + 1
        val leftButtonLabel: Int
        val centerButtonLabel: Int
        val rightButtonLabel: Int

        leftButtonLabel = if (randomAnswer == 1) {
            correctAnswer
        } else {
            shuffleDigit(correctAnswer)
        }
        leftButton.visibility = VISIBLE
        leftButton.startAnimation(fadeIn)
        leftButton.text = leftButtonLabel.toString()
        leftButton.textSize = 90.0F
        leftButton.setOnClickListener {
            if (randomAnswer == 1) {
                history.endCard()
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }

        centerButtonLabel = if (randomAnswer == 2) {
            correctAnswer
        } else {
            shuffleDigit(correctAnswer, leftButtonLabel)
        }
        centerButton.visibility = VISIBLE
        centerButton.startAnimation(fadeIn)
        centerButton.text = centerButtonLabel.toString()
        centerButton.textSize = 90.0F
        centerButton.setOnClickListener {
            if (randomAnswer == 2) {
                history.endCard()
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }

        rightButtonLabel = if (randomAnswer == 3) {
            correctAnswer
        } else {
            shuffleDigit(correctAnswer, leftButtonLabel, centerButtonLabel)
        }
        rightButton.visibility = VISIBLE
        rightButton.startAnimation(fadeIn)
        rightButton.text = rightButtonLabel.toString()
        rightButton.textSize = 90.0F
        rightButton.setOnClickListener {
            if (randomAnswer == 3) {
                history.endCard()
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun alphabet(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView) {
        history.startCard("title","alphabet sequence")
        history.add("type","language")

        leftButton.alpha = 1F
        centerButton.alpha = 1F
        rightButton.alpha = 1F

        emphasisText.visibility = VISIBLE
        emphasisText.textSize = 90.0F
        emphasisText.startAnimation(fadeIn)

        displayText.startAnimation(fadeIn)
        displayText.visibility = VISIBLE

        val randomLetter = Random().nextInt(25) + 97
        var correctLetter = randomLetter

        history.add("starting letter","$randomLetter")

        when (randomLetter) {
            97 -> {
                emphasisText.text = "__, " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar() + ", " + (randomLetter + 3).toChar()
                display(displayText, "What letter goes in the blank?")
            }
            98 -> {
                emphasisText.text = (randomLetter - 1).toChar() + ", __, " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar()
                display(displayText, "What letter goes in the blank?")
            }
            99 -> {
                emphasisText.text = (randomLetter - 2).toChar() + ", " + (randomLetter - 1).toChar() + ", __, " + (randomLetter + 1).toChar()
                display(displayText, "What letter goes in the blank?")
            }
            in 100..118 -> {
                emphasisText.text = randomLetter.toChar() + ", " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar() + ", " + (randomLetter + 3).toChar() + ", __ "
                correctLetter = randomLetter + 4
                display(displayText, "What letter comes next?")
            }
            119 -> {
                emphasisText.text = "__, " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar() + ", " + (randomLetter + 3).toChar()
                display(displayText, "What letter goes in the blank?")
            }
            120 -> {
                emphasisText.text = (randomLetter - 1).toChar() + ", __, " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar()
                display(displayText, "What letter goes in the blank?")
            }
            121 -> {
                emphasisText.text = (randomLetter - 2).toChar() + ", " + (randomLetter - 1).toChar() + ", __, " + (randomLetter + 1).toChar()
                display(displayText, "What letter goes in the blank?")
            }
            122 -> {
                emphasisText.text = (randomLetter - 3).toChar() + ", " + (randomLetter - 2).toChar() + ", " + (randomLetter - 1).toChar() + ", __ "
                display(displayText, "What letter comes next?")
            }
        }

        val randomAnswer = Random().nextInt(3) + 1
        val leftButtonLetter: Int
        val centerButtonLetter: Int
        val rightButtonLetter: Int

        leftButtonLetter = if (randomAnswer == 1) {
            correctLetter
        } else {
            shuffleLetter(correctLetter)
        }
        leftButton.visibility = VISIBLE
        leftButton.startAnimation(fadeIn)
        leftButton.text = leftButtonLetter.toChar().toString()
        leftButton.textSize = 90.0F
        leftButton.setOnClickListener {
            if (randomAnswer == 1) {
                history.endCard()
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }

        centerButtonLetter = if (randomAnswer == 2) {
            correctLetter
        } else {
            shuffleLetter(correctLetter, leftButtonLetter)
        }
        centerButton.visibility = VISIBLE
        centerButton.startAnimation(fadeIn)
        centerButton.text = centerButtonLetter.toChar().toString()
        centerButton.textSize = 90.0F
        centerButton.setOnClickListener {
            if (randomAnswer == 2) {
                history.endCard()
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }

        rightButtonLetter = if (randomAnswer == 3) {
            correctLetter
        } else {
            shuffleLetter(correctLetter, leftButtonLetter, centerButtonLetter)
        }
        rightButton.visibility = VISIBLE
        rightButton.startAnimation(fadeIn)
        rightButton.text = rightButtonLetter.toChar().toString()
        rightButton.textSize = 90.0F
        rightButton.setOnClickListener {
            if (randomAnswer == 3) {
                history.endCard()
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }

    }

    private fun thisIsTheLetterTimeline(duration: Long, interval: Long, leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView, randomLetter: Int) {
        leftButton.alpha = 0.0F
        centerButton.alpha = 0.0F
        rightButton.alpha = 0.0F
        leftButton.visibility = GONE
        centerButton.visibility = GONE
        rightButton.visibility = GONE

        history.startCard("title","this is the letter")
        history.add("type","language")
        history.add("letter","$randomLetter")

        thisIsTheLetter(displayText, emphasisText, randomLetter)

        object : CountDownTimer(duration, interval) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                emphasisText.startAnimation(fadeOut)
                displayText.startAnimation(fadeOut)
                history.endCard()
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }.start()
    }


    @SuppressLint("SetTextI18n")
    private fun thisIsTheLetter(displayText: TextView, emphasisText: TextView, randomLetter: Int) {
        displayText.visibility = VISIBLE
        emphasisText.visibility = VISIBLE

        displayText.startAnimation(fadeIn)
        displayText.text = "This is the letter " + randomLetter.toChar() + "."
        say("This is the letter. " + randomLetter.toChar().toUpperCase() + ".")

        emphasisText.startAnimation(fadeIn)
        emphasisText.textSize = 90.0F
        emphasisText.text = randomLetter.toChar().toUpperCase() + " " + randomLetter.toChar()
    }

    private fun three_phase_failure(displayText: TextView, emphasisText: TextView, initText: TextView, helloButton: Button, speechToText: FloatingActionButton) {
        displayText.visibility = VISIBLE
        displayText.startAnimation(fadeIn)
        displayText.text = "This is the letter a"
        say("This is the letter. A")

        emphasisText.startAnimation(fadeIn)
        emphasisText.textSize = 90.0F
        emphasisText.visibility = VISIBLE
        emphasisText.text = "A a"

        initText.visibility = GONE
        helloButton.visibility = GONE

        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            @SuppressLint("RestrictedApi")
            override fun onFinish() {
                initText.visibility = GONE
                emphasisText.startAnimation(fadeIn)
                displayText.startAnimation(fadeIn)
                emphasisText.visibility = VISIBLE
                emphasisText.text = "A a"
                display(displayText, "What letter is this?")
                speechToText.visibility = VISIBLE

                speechToText.setOnClickListener { view ->
                    TranslatorFactory.instance.with(TranslatorFactory.TRANSLATORS.SPEECH_TO_TEXT,
                            object : ConversionCallback {
                                override fun onSuccess(result: String) {
                                    emphasisText.visibility = GONE
                                    speechToText.visibility = GONE
                                    emphasisText.text = ""
                                    displayText.startAnimation(fadeIn)
                                    display(displayText, "Good Job!")
                                    Snackbar.make(view, result, Snackbar.LENGTH_LONG).setAction("Action", null).show()
                                }
                                override fun onCompletion() {}
                                override fun onErrorOccurred(errorMessage: String) {
                                    emphasisText.visibility = GONE
                                    speechToText.visibility = GONE
                                    emphasisText.text = ""
                                    displayText.startAnimation(fadeIn)
                                    display(displayText, "Try Again.")
                                    object : CountDownTimer(2000, 1000) {
                                        override fun onTick(millisUntilFinished: Long) {}
                                        override fun onFinish() {
                                            emphasisText.visibility = VISIBLE
                                            speechToText.visibility = VISIBLE
                                            speechToText.startAnimation(fadeIn)
                                            displayText.startAnimation(fadeIn)
                                            emphasisText.startAnimation(fadeIn)
                                            emphasisText.text = "A a"
                                            display(displayText, "What letter is this?")
                                        }
                                    }.start()
                                }
                            }).initialize("", this@HomeActivity)
                }
            }
        }.start()
    }

    private fun display(textView: TextView, stringToSpeak: String) {
        textView.text = stringToSpeak
        say(stringToSpeak)
    }

    private fun say(stringToSpeak: String) {
        if (stringToSpeak.isNotEmpty()) {
            TranslatorFactory
                    .instance
                    .with(TranslatorFactory.TRANSLATORS.TEXT_TO_SPEECH,
                            object : ConversionCallback {
                                override fun onSuccess(result: String) {}
                                override fun onCompletion() {}
                                override fun onErrorOccurred(errorMessage: String) {
                                    //   erroConsole.text = "Text2Speech Error: $errorMessage"
                                }
                            })
                    .initialize(stringToSpeak, this)
        }
    }

    private fun shuffleLetter(checkLetter: Int): Int {
        var randomLetter = Random().nextInt(26) + 97
        while(randomLetter == checkLetter) {
            randomLetter = Random().nextInt(26) + 97
        }
        return randomLetter
    }

    private fun shuffleLetter(checkLetter1: Int, checkLetter2: Int): Int {
        var randomLetter = Random().nextInt(26) + 97
        while(randomLetter == checkLetter1 || randomLetter == checkLetter2) {
            randomLetter = Random().nextInt(26) + 97
        }
        return randomLetter
    }

    private fun shuffleLetter(checkLetter1: Int, checkLetter2: Int, checkLetter3: Int): Int {
        var randomLetter = Random().nextInt(26) + 97
        while(randomLetter == checkLetter1 || randomLetter == checkLetter2 || randomLetter == checkLetter3) {
            randomLetter = Random().nextInt(26) + 97
        }
        return randomLetter
    }

    private fun shuffleDigit(correctAnswer: Int): Int {
        var randomDigit = Random().nextInt(8) + 1
        while (correctAnswer == randomDigit) {
            randomDigit = Random().nextInt(8) + 1
        }
        return randomDigit
    }

    private fun shuffleDigit(correctAnswer: Int, avoidDigit1: Int): Int {
        var randomDigit = Random().nextInt(8) + 1
        while (correctAnswer == randomDigit || avoidDigit1 == randomDigit) {
            randomDigit = Random().nextInt(8) + 1
        }
        return randomDigit
    }

    private fun shuffleDigit(correctAnswer: Int, avoidDigit1: Int, avoidDigit2: Int): Int {
        var randomDigit = Random().nextInt(8) + 1
        while (correctAnswer == randomDigit || avoidDigit1 == randomDigit || avoidDigit2 == randomDigit) {
            randomDigit = Random().nextInt(8) + 1
        }
        return randomDigit
    }
}

