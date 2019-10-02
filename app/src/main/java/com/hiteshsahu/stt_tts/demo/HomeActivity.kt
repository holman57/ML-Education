package com.hiteshsahu.stt_tts.demo

import android.annotation.SuppressLint
import java.util.*
import android.view.animation.AlphaAnimation
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.TextView
import android.view.View
import android.widget.Button
import com.hiteshsahu.stt_tts.translation_engine.ConversionCallback
import com.hiteshsahu.stt_tts.translation_engine.TranslatorFactory
import android.os.CountDownTimer
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar


class HomeActivity : BasePermissionActivity() {

    private var fadeIn = AlphaAnimation(0.0f, 1.0f)
    private var fadeOut = AlphaAnimation(1.0f, 0.0f)

    val history: MutableList<String> = mutableListOf()

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

        helloButton.visibility = View.VISIBLE
        helloButton.setOnClickListener {
            displayText.visibility = View.VISIBLE
            initText.text = ""
            helloButton.text = ""
            helloButton.visibility = View.GONE
            initText.visibility = View.GONE

            controller(leftButton, centerButton, rightButton, emphasisText, displayText)

        }
    }

    private fun controller(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView) {
        leftButton.visibility = View.GONE
        leftButton.text = ""
        centerButton.visibility = View.GONE
        centerButton.text = ""
        rightButton.visibility = View.GONE
        rightButton.text = ""
        emphasisText.visibility = View.GONE
        emphasisText.text = ""
        displayText.visibility = View.GONE
        displayText.text = ""


        when (Random().nextInt(2) + 1) {
            1 -> alphabet(leftButton, centerButton, rightButton, emphasisText, displayText)
            2 -> arithmetic(leftButton, centerButton, rightButton, emphasisText, displayText)
            3 -> thisIsTheLetterTimeline(4000, 1000, leftButton, centerButton, rightButton, emphasisText, displayText)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun arithmetic(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView) {
        emphasisText.visibility = View.VISIBLE
        emphasisText.textSize = 90.0F
        emphasisText.startAnimation(fadeIn)

        displayText.startAnimation(fadeIn)
        displayText.visibility = View.VISIBLE

        val firstRandomDigit = Random().nextInt(4) + 1
        val secondRandomDigit = Random().nextInt(4) + 1

        emphasisText.text = "$firstRandomDigit + $secondRandomDigit ="
        say("Can you evaluate this expression? $firstRandomDigit + $secondRandomDigit")
        displayText.text = "Can you evaluate this expression?"

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
        leftButton.visibility = View.VISIBLE
        leftButton.startAnimation(fadeIn)
        leftButton.text = leftButtonLabel.toString()
        leftButton.textSize = 90.0F
        leftButton.setOnClickListener {
            if (randomAnswer == 1) {
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }

        centerButtonLabel = if (randomAnswer == 2) {
            correctAnswer
        } else {
            shuffleDigit(correctAnswer, leftButtonLabel)
        }
        centerButton.visibility = View.VISIBLE
        centerButton.startAnimation(fadeIn)
        centerButton.text = centerButtonLabel.toString()
        centerButton.textSize = 90.0F
        centerButton.setOnClickListener {
            if (randomAnswer == 2) {
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }

        rightButtonLabel = if (randomAnswer == 3) {
            correctAnswer
        } else {
            shuffleDigit(correctAnswer, leftButtonLabel, centerButtonLabel)
        }
        rightButton.visibility = View.VISIBLE
        rightButton.startAnimation(fadeIn)
        rightButton.text = rightButtonLabel.toString()
        rightButton.textSize = 90.0F
        rightButton.setOnClickListener {
            if (randomAnswer == 3) {
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun alphabet(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView) {

        emphasisText.visibility = View.VISIBLE
        emphasisText.textSize = 90.0F
        emphasisText.startAnimation(fadeIn)

        displayText.startAnimation(fadeIn)
        displayText.visibility = View.VISIBLE

        val randomLetter = Random().nextInt(25) + 97
        var correctLetter = randomLetter

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
        leftButton.visibility = View.VISIBLE
        leftButton.startAnimation(fadeIn)
        leftButton.text = leftButtonLetter.toChar().toString()
        leftButton.textSize = 90.0F
        leftButton.setOnClickListener {
            if (randomAnswer == 1) {
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }

        centerButtonLetter = if (randomAnswer == 2) {
            correctLetter
        } else {
            shuffleLetter(correctLetter, leftButtonLetter)
        }
        centerButton.visibility = View.VISIBLE
        centerButton.startAnimation(fadeIn)
        centerButton.text = centerButtonLetter.toChar().toString()
        centerButton.textSize = 90.0F
        centerButton.setOnClickListener {
            if (randomAnswer == 2) {
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }

        rightButtonLetter = if (randomAnswer == 3) {
            correctLetter
        } else {
            shuffleLetter(correctLetter, leftButtonLetter, centerButtonLetter)
        }
        rightButton.visibility = View.VISIBLE
        rightButton.startAnimation(fadeIn)
        rightButton.text = rightButtonLetter.toChar().toString()
        rightButton.textSize = 90.0F
        rightButton.setOnClickListener {
            if (randomAnswer == 3) {
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }

    }

    private fun thisIsTheLetterTimeline(duration: Long, interval: Long, leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView) {
        ThisIsTheLetter(displayText, emphasisText)

        object : CountDownTimer(duration, interval) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                emphasisText.startAnimation(fadeOut)
                displayText.startAnimation(fadeOut)
                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
            }
        }.start()
    }


    private fun ThisIsTheLetter(displayText: TextView, emphasisText: TextView) {
        displayText.visibility = View.VISIBLE
        emphasisText.visibility = View.VISIBLE

        val random = Random().nextInt(26) + 97

        displayText.startAnimation(fadeIn)
        displayText.text = "This is the letter " + random.toChar() + "."
        say("This is the letter. " + random.toChar().toUpperCase() + ".")

        emphasisText.startAnimation(fadeIn)
        emphasisText.textSize = 90.0F
        emphasisText.text = random.toChar().toUpperCase() + " " + random.toChar()
    }

    private fun three_phase_failure(displayText: TextView, emphasisText: TextView, initText: TextView, helloButton: Button, speechToText: FloatingActionButton) {
        displayText.visibility = View.VISIBLE
        displayText.startAnimation(fadeIn)
        displayText.text = "This is the letter a"
        say("This is the letter. A")

        emphasisText.startAnimation(fadeIn)
        emphasisText.textSize = 90.0F
        emphasisText.visibility = View.VISIBLE
        emphasisText.text = "A a"

        initText.visibility = View.GONE
        helloButton.visibility = View.GONE

        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            @SuppressLint("RestrictedApi")
            override fun onFinish() {
                initText.visibility = View.GONE
                emphasisText.startAnimation(fadeIn)
                displayText.startAnimation(fadeIn)
                emphasisText.visibility = View.VISIBLE
                emphasisText.text = "A a"
                display(displayText, "What letter is this?")
                speechToText.visibility = View.VISIBLE

                speechToText.setOnClickListener { view ->
                    TranslatorFactory.instance.with(TranslatorFactory.TRANSLATORS.SPEECH_TO_TEXT,
                            object : ConversionCallback {
                                override fun onSuccess(result: String) {
                                    emphasisText.visibility = View.GONE
                                    speechToText.visibility = View.GONE
                                    emphasisText.text = ""
                                    displayText.startAnimation(fadeIn)
                                    display(displayText, "Good Job!")
                                    Snackbar.make(view, result, Snackbar.LENGTH_LONG).setAction("Action", null).show()
                                }
                                override fun onCompletion() {}
                                override fun onErrorOccurred(errorMessage: String) {
                                    emphasisText.visibility = View.GONE
                                    speechToText.visibility = View.GONE
                                    emphasisText.text = ""
                                    displayText.startAnimation(fadeIn)
                                    display(displayText, "Try Again.")
                                    object : CountDownTimer(2000, 1000) {
                                        override fun onTick(millisUntilFinished: Long) {}
                                        override fun onFinish() {
                                            emphasisText.visibility = View.VISIBLE
                                            speechToText.visibility = View.VISIBLE
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


