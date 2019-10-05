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
import android.support.design.widget.FloatingActionButton
import android.view.View.GONE
import android.view.View.VISIBLE
import com.hiteshsahu.stt_tts.chatbot.Response
import com.hiteshsahu.stt_tts.controller.Controller
import com.hiteshsahu.stt_tts.data_structures.History


class HomeActivity : BasePermissionActivity() {

    private var fadeIn = AlphaAnimation(0.0f, 1.0f)
    private var fadeOut = AlphaAnimation(1.0f, 0.0f)

    private val history = History()
    private val response = Response()
    private val controller = Controller()

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

                thisIsTheLetter(2500, 1000, leftButton, centerButton, rightButton, emphasisText, displayText, randomLetter)
            }
            3 -> {
                arithmetic1_9(leftButton, centerButton, rightButton, emphasisText, displayText)
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

        leftButton.alpha = 0F
        centerButton.alpha = 0F
        rightButton.alpha = 0F
        emphasisText.alpha = 0F
        displayText.alpha = 0F
    }

    private fun clearView(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView) {
        leftButton.visibility = GONE
        centerButton.visibility = GONE
        rightButton.visibility = GONE
        emphasisText.visibility = GONE

        emphasisText.text = ""

        leftButton.alpha = 0F
        centerButton.alpha = 0F
        rightButton.alpha = 0F
        emphasisText.alpha = 0F
    }

    @SuppressLint("SetTextI18n")
    private fun arithmetic1_9(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView) {
        history.startCard("title","addition 1..9")
        history.add("type","arithmetic1_9")
        history.add("time", "${Calendar.getInstance().timeInMillis}")

        val firstRandomDigit = Random().nextInt(4) + 1
        val secondRandomDigit = Random().nextInt(4) + 1

        val questionPhrase = "$firstRandomDigit + $secondRandomDigit ="
        val answerPhrase = "$firstRandomDigit + $secondRandomDigit = ${(firstRandomDigit + secondRandomDigit)}"

        emphasisText.text = questionPhrase

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

        leftButton.alpha = 1F
        centerButton.alpha = 1F
        rightButton.alpha = 1F
        emphasisText.alpha = 1F
        displayText.alpha = 1F

        emphasisText.visibility = VISIBLE
        emphasisText.startAnimation(fadeIn)
        emphasisText.textSize = 90.0F

        val correctAnswer = firstRandomDigit + secondRandomDigit

        val randomAnswer = Random().nextInt(3) + 1
        val leftButtonLabel: Int
        val centerButtonLabel: Int
        val rightButtonLabel: Int
        var answeredQuestionWrong = false


        leftButtonLabel = if (randomAnswer == 1) {
            correctAnswer
        } else {
            shuffleDigit(correctAnswer)
        }
        displayButton(leftButton)
        leftButton.text = leftButtonLabel.toString()
        leftButton.setOnClickListener {
            if (!answeredQuestionWrong) {
                if (randomAnswer == 1) {
                    history.add("answered correctly", "true")
                    if (controller.shouldEncourage(history, "addition 1..9")) {
                        hideButtons(leftButton, centerButton, rightButton)
                        showTextView(displayText)
                        val encouragementPhrase = response.encourage()
                        history.add("response", encouragementPhrase)
                        display(displayText, encouragementPhrase)
                        emphasisText.text = answerPhrase
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    } else {
                        history.endCard()
                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                    }
                } else {
                    history.add("answered incorrectly", "true")
                    if (controller.shouldTryAgain(history, "addition 1..9")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                showButtonsEmphasisDisplayText(rightButton, centerButton, emphasisText, displayText)
                                emphasisText.text = questionPhrase
                                say("Can you evaluate this expression? $firstRandomDigit + $secondRandomDigit")
                                displayText.text = "Can you evaluate this expression?"
                                rightButton.startAnimation(fadeIn)
                                centerButton.startAnimation(fadeIn)
                                leftButton.setOnClickListener {}
                                history.add("wrong answer", questionPhrase.replace(",", ""))
                                answeredQuestionWrong = true
                            }
                        }.start()
                    } else {
                        leftButton.startAnimation(fadeOut)
                        leftButton.setOnClickListener {}
                        history.add("wrong answer", questionPhrase.replace(",", ""))
                        answeredQuestionWrong = true
                    }
                }
            } else {
                if (randomAnswer == 1) {
                    history.add("answered correctly", "true")
                    hideButtons(leftButton, centerButton, rightButton)
                    showTextView(displayText)
                    val encouragementPhrase = response.encourage()
                    history.add("response", encouragementPhrase)
                    display(displayText, encouragementPhrase)
                    emphasisText.text = answerPhrase
                    object : CountDownTimer(2000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {}
                        override fun onFinish() {
                            history.endCard()
                            controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                        }
                    }.start()
                } else {
                    history.add("answered correctly", "false")
                    if (controller.shouldTryAgain(history, "addition 1..9")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                showTextView(emphasisText)
                                emphasisText.text = answerPhrase
                                showTextView(displayText)
                                displayText.text = "$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit"
                                say("$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit")
                                object : CountDownTimer(5000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {}
                                    override fun onFinish() {
                                        history.endCard()
                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                                    }
                                }.start()
                            }
                        }.start()
                    } else {
                        hideButtons(leftButton, centerButton, rightButton)
                        emphasisText.text = answerPhrase
                        showTextView(displayText)
                        displayText.text = "$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit"
                        say("$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit")
                        object : CountDownTimer(5000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    }
                }
            }
        }

        centerButtonLabel = if (randomAnswer == 2) {
            correctAnswer
        } else {
            shuffleDigit(correctAnswer, leftButtonLabel)
        }
        displayButton(centerButton)
        centerButton.text = centerButtonLabel.toString()
        centerButton.setOnClickListener {
            if (!answeredQuestionWrong) {
                if (randomAnswer == 2) {
                    history.add("answered correctly", "true")
                    if (controller.shouldEncourage(history, "addition 1..9")) {
                        hideButtons(leftButton, centerButton, rightButton)
                        showTextView(displayText)
                        val encouragementPhrase = response.encourage()
                        history.add("response", encouragementPhrase)
                        display(displayText, encouragementPhrase)
                        emphasisText.text = answerPhrase
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    } else {
                        history.endCard()
                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                    }
                } else {
                    history.add("answered incorrectly", "true")
                    if (controller.shouldTryAgain(history, "addition 1..9")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                showButtonsEmphasisDisplayText(rightButton, leftButton, emphasisText, displayText)
                                emphasisText.text = questionPhrase
                                say("Can you evaluate this expression? $firstRandomDigit + $secondRandomDigit")
                                displayText.text = "Can you evaluate this expression?"
                                rightButton.startAnimation(fadeIn)
                                leftButton.startAnimation(fadeIn)
                                centerButton.setOnClickListener {}
                                history.add("wrong answer", questionPhrase.replace(",", ""))
                                answeredQuestionWrong = true
                            }
                        }.start()
                    } else {
                        centerButton.startAnimation(fadeOut)
                        centerButton.setOnClickListener {}
                        history.add("wrong answer", questionPhrase.replace(",",""))
                        answeredQuestionWrong = true
                    }
                }
            } else {
                if (randomAnswer == 2) {
                    history.add("answered correctly", "true")
                    hideButtons(leftButton, centerButton, rightButton)
                    showTextView(displayText)
                    val encouragementPhrase = response.encourage()
                    history.add("response", encouragementPhrase)
                    display(displayText, encouragementPhrase)
                    emphasisText.text = answerPhrase
                    object : CountDownTimer(2000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {}
                        override fun onFinish() {
                            history.endCard()
                            controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                        }
                    }.start()
                } else {
                    history.add("answered correctly", "false")
                    if (controller.shouldTryAgain(history, "addition 1..9")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                hideButtons(leftButton, centerButton, rightButton)
                                showTextView(emphasisText)
                                emphasisText.text = answerPhrase
                                showTextView(displayText)
                                displayText.text = "$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit"
                                say("$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit")
                                object : CountDownTimer(5000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {}
                                    override fun onFinish() {
                                        history.endCard()
                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                                    }
                                }.start()
                            }
                        }.start()
                    } else {
                        hideButtons(leftButton, centerButton, rightButton)
                        emphasisText.text = answerPhrase
                        showTextView(displayText)
                        displayText.text = "$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit"
                        say("$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit")
                        object : CountDownTimer(5000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    }
                }
            }
        }

        rightButtonLabel = if (randomAnswer == 3) {
            correctAnswer
        } else {
            shuffleDigit(correctAnswer, leftButtonLabel, centerButtonLabel)
        }
        displayButton(rightButton)
        rightButton.text = rightButtonLabel.toString()
        rightButton.setOnClickListener {
            if (!answeredQuestionWrong) {
                if (randomAnswer == 3) {
                    history.add("answered correctly", "true")
                    if (controller.shouldEncourage(history, "addition 1..9")) {
                        hideButtons(leftButton, centerButton, rightButton)
                        showTextView(displayText)
                        val encouragementPhrase = response.encourage()
                        history.add("response", encouragementPhrase)
                        display(displayText, encouragementPhrase)
                        emphasisText.text = answerPhrase
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    } else {
                        history.endCard()
                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                    }
                } else {
                    history.add("answered incorrectly", "true")
                    if (controller.shouldTryAgain(history, "addition 1..9")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                showButtonsEmphasisDisplayText(centerButton, leftButton, emphasisText, displayText)
                                emphasisText.text = questionPhrase
                                say("Can you evaluate this expression? $firstRandomDigit + $secondRandomDigit")
                                displayText.text = "Can you evaluate this expression?"
                                centerButton.startAnimation(fadeIn)
                                leftButton.startAnimation(fadeIn)
                                rightButton.setOnClickListener {}
                                history.add("wrong answer", questionPhrase.replace(",", ""))
                                answeredQuestionWrong = true
                            }
                        }.start()
                    } else {
                        rightButton.startAnimation(fadeOut)
                        rightButton.setOnClickListener {}
                        history.add("wrong answer", questionPhrase.replace(",",""))
                        answeredQuestionWrong = true
                    }
                }
            } else {
                if (randomAnswer == 3) {
                    history.add("answered correctly", "true")
                    hideButtons(leftButton, centerButton, rightButton)
                    showTextView(displayText)
                    val encouragementPhrase = response.encourage()
                    history.add("response", encouragementPhrase)
                    display(displayText, encouragementPhrase)
                    emphasisText.text = answerPhrase
                    object : CountDownTimer(2000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {}
                        override fun onFinish() {
                            history.endCard()
                            controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                        }
                    }.start()
                } else {
                    history.add("answered correctly", "false")
                    if (controller.shouldTryAgain(history, "addition 1..9")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                hideButtons(leftButton, centerButton, rightButton)
                                showTextView(emphasisText)
                                emphasisText.text = answerPhrase
                                showTextView(displayText)
                                displayText.text = "$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit"
                                say("$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit")
                                object : CountDownTimer(5000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {}
                                    override fun onFinish() {
                                        history.endCard()
                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                                    }
                                }.start()
                            }
                        }.start()
                    } else {
                        hideButtons(leftButton, centerButton, rightButton)
                        emphasisText.text = answerPhrase
                        showTextView(displayText)
                        displayText.text = "$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit"
                        say("$correctAnswer is the result of $firstRandomDigit + $secondRandomDigit")
                        object : CountDownTimer(5000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    }
                }
            }
        }

    }


    @SuppressLint("SetTextI18n")
    private fun alphabet(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView) {
        history.startCard("title","alphabet sequence")
        history.add("type","language")
        history.add("time", "${Calendar.getInstance().timeInMillis}")

        showButtonsEmphasisDisplayText(leftButton, centerButton, rightButton, emphasisText, displayText)

        val randomLetter = Random().nextInt(25) + 97
        var correctLetter = randomLetter
        var questionPhrase = ""
        var answerPhrase = ""

        history.add("starting letter","${randomLetter.toChar()}")

        when (randomLetter) {
            97 -> {
                questionPhrase = "__, " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar() + ", " + (randomLetter + 3).toChar()
                answerPhrase = randomLetter.toChar() + ", " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar() + ", " + (randomLetter + 3).toChar()
                emphasisText.text = questionPhrase
                display(displayText, "What letter goes in the blank?")
            }
            98 -> {
                questionPhrase = (randomLetter - 1).toChar() + ", __, " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar()
                answerPhrase = (randomLetter - 1).toChar() + ", " + randomLetter.toChar() + ", " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar()
                emphasisText.text = questionPhrase
                display(displayText, "What letter goes in the blank?")
            }
            99 -> {
                questionPhrase = (randomLetter - 2).toChar() + ", " + (randomLetter - 1).toChar() + ", __, " + (randomLetter + 1).toChar()
                answerPhrase = (randomLetter - 2).toChar() + ", " + (randomLetter - 1).toChar() + ", " + randomLetter.toChar() + ", " + (randomLetter + 1).toChar()
                emphasisText.text = questionPhrase
                display(displayText, "What letter goes in the blank?")
            }
            in 100..118 -> {
                questionPhrase = randomLetter.toChar() + ", " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar() + ", " + (randomLetter + 3).toChar() + ", __ "
                answerPhrase = randomLetter.toChar() + ", " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar() + ", " + (randomLetter + 3).toChar() + ", " + (randomLetter + 4).toChar()
                emphasisText.text = questionPhrase
                correctLetter = randomLetter + 4
                display(displayText, "What letter comes next?")
            }
            119 -> {
                questionPhrase = "__, " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar() + ", " + (randomLetter + 3).toChar()
                answerPhrase = randomLetter.toChar() + ", " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar() + ", " + (randomLetter + 3).toChar()
                emphasisText.text = questionPhrase
                display(displayText, "What letter goes in the blank?")
            }
            120 -> {
                questionPhrase = (randomLetter - 1).toChar() + ", __, " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar()
                answerPhrase = (randomLetter - 1).toChar() + ", " + randomLetter.toChar() + ", " + (randomLetter + 1).toChar() + ", " + (randomLetter + 2).toChar()
                emphasisText.text = questionPhrase
                display(displayText, "What letter goes in the blank?")
            }
            121 -> {
                questionPhrase = (randomLetter - 2).toChar() + ", " + (randomLetter - 1).toChar() + ", __, " + (randomLetter + 1).toChar()
                answerPhrase = (randomLetter - 2).toChar() + ", " + (randomLetter - 1).toChar() + ", " + randomLetter.toChar() + ", " + (randomLetter + 1).toChar()
                emphasisText.text = questionPhrase
                display(displayText, "What letter goes in the blank?")
            }
            122 -> {
                questionPhrase = (randomLetter - 3).toChar() + ", " + (randomLetter - 2).toChar() + ", " + (randomLetter - 1).toChar() + ", __ "
                answerPhrase = (randomLetter - 3).toChar() + ", " + (randomLetter - 2).toChar() + ", " + (randomLetter - 1).toChar() + ", " + randomLetter.toChar()
                emphasisText.text = questionPhrase
                display(displayText, "What letter comes next?")
            }
        }

        val randomAnswer = Random().nextInt(3) + 1
        var answeredQuestionWrong = false
        val leftButtonLetter: Int
        val centerButtonLetter: Int
        val rightButtonLetter: Int

        leftButtonLetter = if (randomAnswer == 1) {
            correctLetter
        } else {
            shuffleLetter(correctLetter)
        }
        displayButton(leftButton)
        leftButton.text = leftButtonLetter.toChar().toString()
        leftButton.setOnClickListener {
            if (!answeredQuestionWrong) {
                if (randomAnswer == 1) {
                    history.add("answered correctly", "true")
                    if (controller.shouldEncourage(history, "alphabet sequence")) {
                        hideButtons(leftButton, centerButton, rightButton)
                        showTextView(displayText)
                        val encouragementPhrase = response.encourage()
                        history.add("response", encouragementPhrase)
                        display(displayText, encouragementPhrase)
                        emphasisText.text = answerPhrase
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    } else {
                        history.endCard()
                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                    }
                } else {
                    history.add("answered incorrectly", "true")
                    if (controller.shouldTryAgain(history, "alphabet sequence")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                showButtonsEmphasisDisplayText(rightButton, centerButton, emphasisText, displayText)
                                emphasisText.text = questionPhrase
                                say("What letter comes next?")
                                rightButton.startAnimation(fadeIn)
                                centerButton.startAnimation(fadeIn)
                                leftButton.setOnClickListener {}
                                history.add("wrong answer", questionPhrase.replace(",", ""))
                                answeredQuestionWrong = true
                            }
                        }.start()
                    } else {
                        leftButton.startAnimation(fadeOut)
                        leftButton.setOnClickListener {}
                        history.add("wrong answer", questionPhrase.replace(",", ""))
                        answeredQuestionWrong = true
                    }
                }
            } else {
                if (randomAnswer == 1) {
                    history.add("answered correctly", "true")
                    hideButtons(leftButton, centerButton, rightButton)
                    showTextView(displayText)
                    val encouragementPhrase = response.encourage()
                    history.add("response", encouragementPhrase)
                    display(displayText, encouragementPhrase)
                    emphasisText.text = answerPhrase
                    object : CountDownTimer(2000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {}
                        override fun onFinish() {
                            history.endCard()
                            controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                        }
                    }.start()
                } else {
                    history.add("answered correctly", "false")
                    if (controller.shouldTryAgain(history, "alphabet sequence")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                showTextView(emphasisText)
                                emphasisText.text = answerPhrase
                                displayText.text = "This is the correct sequence of letters"
                                say("This is the correct sequence of letters. $answerPhrase")
                                object : CountDownTimer(8000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {}
                                    override fun onFinish() {
                                        history.endCard()
                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                                    }
                                }.start()
                            }
                        }.start()
                    } else {
                        hideButtons(leftButton, centerButton, rightButton)
                        emphasisText.text = answerPhrase
                        displayText.text = "This is the correct sequence of letters"
                        say("This is the correct sequence of letters. $answerPhrase")
                        object : CountDownTimer(8000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    }
                }
            }
        }

        centerButtonLetter = if (randomAnswer == 2) {
            correctLetter
        } else {
            shuffleLetter(correctLetter, leftButtonLetter)
        }
        displayButton(centerButton)
        centerButton.text = centerButtonLetter.toChar().toString()
        centerButton.setOnClickListener {
            if (!answeredQuestionWrong) {
                if (randomAnswer == 2) {
                    history.add("answered correctly", "true")
                    if (controller.shouldEncourage(history, "alphabet sequence")) {
                        hideButtons(leftButton, centerButton, rightButton)
                        showTextView(displayText)
                        val encouragementPhrase = response.encourage()
                        history.add("response", encouragementPhrase)
                        display(displayText, encouragementPhrase)
                        emphasisText.text = answerPhrase
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    } else {
                        history.endCard()
                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                    }
                } else {
                    history.add("answered incorrectly", "true")
                    if (controller.shouldTryAgain(history, "alphabet sequence")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                showButtonsEmphasisDisplayText(rightButton, leftButton, emphasisText, displayText)
                                emphasisText.text = questionPhrase
                                display(displayText, "What letter comes next?")
                                rightButton.startAnimation(fadeIn)
                                leftButton.startAnimation(fadeIn)
                                centerButton.setOnClickListener {}
                                history.add("wrong answer", questionPhrase.replace(",", ""))
                                answeredQuestionWrong = true
                            }
                        }.start()
                    } else {
                        centerButton.startAnimation(fadeOut)
                        centerButton.setOnClickListener {}
                        history.add("wrong answer", questionPhrase.replace(",",""))
                        answeredQuestionWrong = true
                    }
                }
            } else {
                if (randomAnswer == 2) {
                    history.add("answered correctly", "true")
                    hideButtons(leftButton, centerButton, rightButton)
                    showTextView(displayText)
                    val encouragementPhrase = response.encourage()
                    history.add("response", encouragementPhrase)
                    display(displayText, encouragementPhrase)
                    emphasisText.text = answerPhrase
                    object : CountDownTimer(2000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {}
                        override fun onFinish() {
                            history.endCard()
                            controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                        }
                    }.start()
                } else {
                    history.add("answered correctly", "false")
                    if (controller.shouldTryAgain(history, "alphabet sequence")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                hideButtons(leftButton, centerButton, rightButton)
                                showTextView(emphasisText)
                                emphasisText.text = answerPhrase
                                displayText.text = "This is the correct sequence of letters"
                                say("This is the correct sequence of letters. $answerPhrase")
                                object : CountDownTimer(8000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {}
                                    override fun onFinish() {
                                        history.endCard()
                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                                    }
                                }.start()
                            }
                        }.start()
                    } else {
                        hideButtons(leftButton, centerButton, rightButton)
                        emphasisText.text = answerPhrase
                        displayText.text = "This is the correct sequence of letters"
                        say("This is the correct sequence of letters. $answerPhrase")
                        object : CountDownTimer(8000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    }
                }
            }
        }

        rightButtonLetter = if (randomAnswer == 3) {
            correctLetter
        } else {
            shuffleLetter(correctLetter, leftButtonLetter, centerButtonLetter)
        }
        displayButton(rightButton)
        rightButton.text = rightButtonLetter.toChar().toString()
        rightButton.setOnClickListener {
            if (!answeredQuestionWrong) {
                if (randomAnswer == 3) {
                    history.add("answered correctly", "true")
                    if (controller.shouldEncourage(history, "alphabet sequence")) {
                        hideButtons(leftButton, centerButton, rightButton)
                        showTextView(displayText)
                        val encouragementPhrase = response.encourage()
                        history.add("response", encouragementPhrase)
                        display(displayText, encouragementPhrase)
                        emphasisText.text = answerPhrase
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    } else {
                        history.endCard()
                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                    }
                } else {
                    history.add("answered incorrectly", "true")
                    if (controller.shouldTryAgain(history, "alphabet sequence")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                showButtonsEmphasisDisplayText(centerButton, leftButton, emphasisText, displayText)
                                emphasisText.text = questionPhrase
                                display(displayText, "What letter comes next?")
                                centerButton.startAnimation(fadeIn)
                                leftButton.startAnimation(fadeIn)
                                rightButton.setOnClickListener {}
                                history.add("wrong answer", questionPhrase.replace(",", ""))
                                answeredQuestionWrong = true
                            }
                        }.start()
                    } else {
                        rightButton.startAnimation(fadeOut)
                        rightButton.setOnClickListener {}
                        history.add("wrong answer", questionPhrase.replace(",",""))
                        answeredQuestionWrong = true
                    }
                }
            } else {
                if (randomAnswer == 3) {
                    history.add("answered correctly", "true")
                    hideButtons(leftButton, centerButton, rightButton)
                    showTextView(displayText)
                    val encouragementPhrase = response.encourage()
                    history.add("response", encouragementPhrase)
                    display(displayText, encouragementPhrase)
                    emphasisText.text = answerPhrase
                    object : CountDownTimer(2000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {}
                        override fun onFinish() {
                            history.endCard()
                            controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                        }
                    }.start()
                } else {
                    history.add("answered correctly", "false")
                    if (controller.shouldTryAgain(history, "alphabet sequence")) {
                        clearView(leftButton, centerButton, rightButton, emphasisText)
                        displayText.startAnimation(fadeIn)
                        val keepTryingPhrase = response.keepTrying()
                        display(displayText, keepTryingPhrase)
                        history.add("response", keepTryingPhrase)
                        object : CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                hideButtons(leftButton, centerButton, rightButton)
                                showTextView(emphasisText)
                                emphasisText.text = answerPhrase
                                displayText.text = "This is the correct sequence of letters"
                                say("This is the correct sequence of letters. $answerPhrase")
                                object : CountDownTimer(8000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {}
                                    override fun onFinish() {
                                        history.endCard()
                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                                    }
                                }.start()
                            }
                        }.start()
                    } else {
                        hideButtons(leftButton, centerButton, rightButton)
                        emphasisText.text = answerPhrase
                        displayText.text = "This is the correct sequence of letters"
                        say("This is the correct sequence of letters. $answerPhrase")
                        object : CountDownTimer(8000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText)
                            }
                        }.start()
                    }
                }
            }
        }
    }

    private fun hideTextView(textView: TextView) {
        textView.visibility = GONE
        textView.text = ""
        textView.alpha = 0F
    }

    private fun showButtonsEmphasisDisplayText(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView) {
        leftButton.alpha = 1F
        centerButton.alpha = 1F
        rightButton.alpha = 1F
        leftButton.startAnimation(fadeIn)
        centerButton.startAnimation(fadeIn)
        rightButton.startAnimation(fadeIn)

        emphasisText.alpha = 1F
        emphasisText.textSize = 90.0F
        emphasisText.visibility = VISIBLE
        emphasisText.startAnimation(fadeIn)

        displayText.alpha = 1F
        displayText.visibility = VISIBLE
        displayText.startAnimation(fadeIn)
    }

    private fun showButtonsEmphasisDisplayText(button1: Button, button2: Button, emphasisText: TextView, displayText: TextView) {
        button1.alpha = 1F
        button2.alpha = 1F

        button1.visibility = VISIBLE
        button2.visibility = VISIBLE

        button1.startAnimation(fadeIn)
        button2.startAnimation(fadeIn)

        emphasisText.alpha = 1F
        emphasisText.textSize = 90.0F
        emphasisText.visibility = VISIBLE
        emphasisText.startAnimation(fadeIn)

        displayText.alpha = 1F
        displayText.visibility = VISIBLE
        displayText.startAnimation(fadeIn)
    }

    private fun displayButton(button: Button) {
        button.visibility = VISIBLE
        button.startAnimation(fadeIn)
        button.textSize = 90.0F
    }

    private fun showTextView(text: TextView) {
        text.visibility = VISIBLE
        text.alpha = 1F
        text.startAnimation(fadeIn)
    }

    private fun hideButtons(leftButton: Button, centerButton: Button, rightButton: Button) {
        leftButton.visibility = GONE
        centerButton.visibility = GONE
        rightButton.visibility = GONE
        leftButton.alpha = 0F
        centerButton.alpha = 0F
        rightButton.alpha = 0F
    }

    @SuppressLint("SetTextI18n")
    private fun thisIsTheLetter(duration: Long, interval: Long, leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView, randomLetter: Int) {
        leftButton.alpha = 0.0F
        centerButton.alpha = 0.0F
        rightButton.alpha = 0.0F
        leftButton.visibility = GONE
        centerButton.visibility = GONE
        rightButton.visibility = GONE

        displayText.alpha = 1F
        emphasisText.alpha = 1F

        history.startCard("title","this is the letter")
        history.add("type","language")
        history.add("time", "${Calendar.getInstance().timeInMillis}")
        history.add("letter","${randomLetter.toChar()}")

        displayText.visibility = VISIBLE
        emphasisText.visibility = VISIBLE

        displayText.startAnimation(fadeIn)
        displayText.text = "This is the letter " + randomLetter.toChar() + "."
        say("This is the letter. " + randomLetter.toChar().toUpperCase() + ".")

        emphasisText.startAnimation(fadeIn)
        emphasisText.textSize = 90.0F
        emphasisText.text = randomLetter.toChar().toUpperCase() + " " + randomLetter.toChar()

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

    private fun display(textView: TextView, stringToSpeak: String) {
        textView.visibility = VISIBLE
        textView.alpha = 1F
        textView.startAnimation(fadeIn)
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

