package com.hiteshsahu.stt_tts.levels

import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.hiteshsahu.stt_tts.data_structures.History
import java.util.*

// https://kotlinlang.org/docs/reference/classes.html

class arithmetic19 constructor(history: History) {

}

private fun arithmetic1_9(leftButton: Button, centerButton: Button, rightButton: Button, emphasisText: TextView, displayText: TextView, supportText: TextView) {
    history.startCard("title","addition 1..9")
    history.add("type","arithmetic1_9")

    val startTime = Calendar.getInstance().timeInMillis
    history.add("start time", "$startTime")

    val firstRandomDigit = Random().nextInt(4) + 1
    val secondRandomDigit = Random().nextInt(4) + 1

    val questionPhrase = "$firstRandomDigit + $secondRandomDigit ="
    val answerPhrase = "$firstRandomDigit + $secondRandomDigit = ${(firstRandomDigit + secondRandomDigit)}"

    emphasisText.text = questionPhrase

    if (!history.contain("title","addition 1..9")) {
        say("Can you evaluate this expression? $firstRandomDigit + $secondRandomDigit")
        displayText.text = "Can you evaluate this expression?"
        displayText.startAnimation(fadeIn)
        displayText.visibility = View.VISIBLE
    }
    else {
        say("$firstRandomDigit + $secondRandomDigit")
        displayText.text = ""
        displayText.visibility = View.GONE
    }

    leftButton.alpha = 1F
    centerButton.alpha = 1F
    rightButton.alpha = 1F
    emphasisText.alpha = 1F
    displayText.alpha = 1F

    emphasisText.visibility = View.VISIBLE
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
                            val endTime = Calendar.getInstance().timeInMillis
                            history.add("end time", "$endTime")
                            history.add("time", "${endTime - startTime}")
                            history.endCard()
                            controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                        }
                    }.start()
                } else {
                    val endTime = Calendar.getInstance().timeInMillis
                    history.add("end time", "$endTime")
                    history.add("time", "${endTime - startTime}")
                    history.endCard()
                    controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
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
                            leftButton.visibility = View.VISIBLE
                            leftButton.alpha = 1F
                            leftButton.startAnimation(fadeOut)
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
                        val endTime = Calendar.getInstance().timeInMillis
                        history.add("end time", "$endTime")
                        history.add("time", "${endTime - startTime}")
                        history.endCard()
                        controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
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
                                    if (controller.shouldExplain(history, "addition 1..9")) {
                                        emphasisText.text = ""
                                        val sb = StringBuilder()
                                        for (i in 1..firstRandomDigit) {
                                            sb.append("\u25CF")
                                        }
                                        sb.append(" + ")
                                        for (i in 1..secondRandomDigit) {
                                            sb.append("\u25CF")
                                        }
                                        emphasisText.text = sb.toString()
                                        display(displayText,"The addition of the quantity " + firstRandomDigit + " and " + secondRandomDigit)
                                        object : CountDownTimer(5000, 1000) {
                                            override fun onTick(millisUntilFinished: Long) {}
                                            override fun onFinish() {
                                                display(displayText,"is the sum of " + (firstRandomDigit + secondRandomDigit) + " individual 1's")
                                                emphasisText.startAnimation(fadeIn)
                                                supportText.visibility = View.VISIBLE
                                                supportText.alpha = 1F
                                                supportText.startAnimation(fadeIn)
                                                sb.append(" =")
                                                emphasisText.text = sb.toString()
                                                val s = StringBuilder()
                                                for (i in 1..(firstRandomDigit + secondRandomDigit)) {
                                                    s.append("\u25CF")
                                                }
                                                supportText.text = s.toString()
                                                object : CountDownTimer(9000, 1000) {
                                                    override fun onTick(millisUntilFinished: Long) {}
                                                    override fun onFinish() {
                                                        supportText.visibility = View.GONE
                                                        supportText.alpha = 0F
                                                        val endTime = Calendar.getInstance().timeInMillis
                                                        history.add("end time", "$endTime")
                                                        history.add("time", "${endTime - startTime}")
                                                        history.endCard()
                                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                                                    }
                                                }.start()
                                            }
                                        }.start()
                                    } else {
                                        supportText.visibility = View.GONE
                                        supportText.alpha = 0F
                                        val endTime = Calendar.getInstance().timeInMillis
                                        history.add("end time", "$endTime")
                                        history.add("time", "${endTime - startTime}")
                                        history.endCard()
                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                                    }
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
                            if (controller.shouldExplain(history, "addition 1..9")) {
                                emphasisText.text = ""
                                val sb = StringBuilder()
                                for (i in 1..firstRandomDigit) {
                                    sb.append("\u25CF")
                                }
                                sb.append(" + ")
                                for (i in 1..secondRandomDigit) {
                                    sb.append("\u25CF")
                                }
                                emphasisText.text = sb.toString()
                                display(displayText,"The addition of the quantity " + firstRandomDigit + " and " + secondRandomDigit)
                                object : CountDownTimer(5000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {}
                                    override fun onFinish() {
                                        display(displayText,"is the sum of " + (firstRandomDigit + secondRandomDigit) + " individual 1's")
                                        emphasisText.startAnimation(fadeIn)
                                        supportText.visibility = View.VISIBLE
                                        supportText.alpha = 1F
                                        supportText.startAnimation(fadeIn)
                                        sb.append(" =")
                                        emphasisText.text = sb.toString()
                                        val s = StringBuilder()
                                        for (i in 1..(firstRandomDigit + secondRandomDigit)) {
                                            s.append("\u25CF")
                                        }
                                        supportText.text = s.toString()
                                        object : CountDownTimer(9000, 1000) {
                                            override fun onTick(millisUntilFinished: Long) {}
                                            override fun onFinish() {
                                                supportText.visibility = View.GONE
                                                supportText.alpha = 0F
                                                val endTime = Calendar.getInstance().timeInMillis
                                                history.add("end time", "$endTime")
                                                history.add("time", "${endTime - startTime}")
                                                history.endCard()
                                                controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                                            }
                                        }.start()
                                    }
                                }.start()
                            } else {
                                supportText.visibility = View.GONE
                                supportText.alpha = 0F
                                val endTime = Calendar.getInstance().timeInMillis
                                history.add("end time", "$endTime")
                                history.add("time", "${endTime - startTime}")
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                            }
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
                            val endTime = Calendar.getInstance().timeInMillis
                            history.add("end time", "$endTime")
                            history.add("time", "${endTime - startTime}")
                            history.endCard()
                            controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                        }
                    }.start()
                } else {
                    val endTime = Calendar.getInstance().timeInMillis
                    history.add("end time", "$endTime")
                    history.add("time", "${endTime - startTime}")
                    history.endCard()
                    controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
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
                            centerButton.visibility = View.VISIBLE
                            centerButton.alpha = 1F
                            centerButton.startAnimation(fadeOut)
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
                        val endTime = Calendar.getInstance().timeInMillis
                        history.add("end time", "$endTime")
                        history.add("time", "${endTime - startTime}")
                        history.endCard()
                        controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
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
                                    if (controller.shouldExplain(history, "addition 1..9")) {
                                        emphasisText.text = ""
                                        val sb = StringBuilder()
                                        for (i in 1..firstRandomDigit) {
                                            sb.append("\u25CF")
                                        }
                                        sb.append(" + ")
                                        for (i in 1..secondRandomDigit) {
                                            sb.append("\u25CF")
                                        }
                                        emphasisText.text = sb.toString()
                                        display(displayText,"The addition of the quantity " + firstRandomDigit + " and " + secondRandomDigit)
                                        object : CountDownTimer(5000, 1000) {
                                            override fun onTick(millisUntilFinished: Long) {}
                                            override fun onFinish() {
                                                display(displayText,"is the sum of " + (firstRandomDigit + secondRandomDigit) + " individual 1's")
                                                emphasisText.startAnimation(fadeIn)
                                                supportText.visibility = View.VISIBLE
                                                supportText.alpha = 1F
                                                supportText.startAnimation(fadeIn)
                                                sb.append(" =")
                                                emphasisText.text = sb.toString()
                                                val s = StringBuilder()
                                                for (i in 1..(firstRandomDigit + secondRandomDigit)) {
                                                    s.append("\u25CF")
                                                }
                                                supportText.text = s.toString()
                                                object : CountDownTimer(9000, 1000) {
                                                    override fun onTick(millisUntilFinished: Long) {}
                                                    override fun onFinish() {
                                                        supportText.visibility = View.GONE
                                                        supportText.alpha = 0F
                                                        val endTime = Calendar.getInstance().timeInMillis
                                                        history.add("end time", "$endTime")
                                                        history.add("time", "${endTime - startTime}")
                                                        history.endCard()
                                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                                                    }
                                                }.start()
                                            }
                                        }.start()
                                    } else {
                                        supportText.visibility = View.GONE
                                        supportText.alpha = 0F
                                        val endTime = Calendar.getInstance().timeInMillis
                                        history.add("end time", "$endTime")
                                        history.add("time", "${endTime - startTime}")
                                        history.endCard()
                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                                    }
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
                            if (controller.shouldExplain(history, "addition 1..9")) {
                                emphasisText.text = ""
                                val sb = StringBuilder()
                                for (i in 1..firstRandomDigit) {
                                    sb.append("\u25CF")
                                }
                                sb.append(" + ")
                                for (i in 1..secondRandomDigit) {
                                    sb.append("\u25CF")
                                }
                                emphasisText.text = sb.toString()
                                display(displayText,"The addition of the quantity " + firstRandomDigit + " and " + secondRandomDigit)
                                object : CountDownTimer(5000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {}
                                    override fun onFinish() {
                                        display(displayText,"is the sum of " + (firstRandomDigit + secondRandomDigit) + " individual 1's")
                                        emphasisText.startAnimation(fadeIn)
                                        supportText.visibility = View.VISIBLE
                                        supportText.alpha = 1F
                                        supportText.startAnimation(fadeIn)
                                        sb.append(" =")
                                        emphasisText.text = sb.toString()
                                        val s = StringBuilder()
                                        for (i in 1..(firstRandomDigit + secondRandomDigit)) {
                                            s.append("\u25CF")
                                        }
                                        supportText.text = s.toString()
                                        object : CountDownTimer(9000, 1000) {
                                            override fun onTick(millisUntilFinished: Long) {}
                                            override fun onFinish() {
                                                supportText.visibility = View.GONE
                                                supportText.alpha = 0F
                                                val endTime = Calendar.getInstance().timeInMillis
                                                history.add("end time", "$endTime")
                                                history.add("time", "${endTime - startTime}")
                                                history.endCard()
                                                controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                                            }
                                        }.start()
                                    }
                                }.start()
                            } else {
                                supportText.visibility = View.GONE
                                supportText.alpha = 0F
                                val endTime = Calendar.getInstance().timeInMillis
                                history.add("end time", "$endTime")
                                history.add("time", "${endTime - startTime}")
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                            }
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
                            val endTime = Calendar.getInstance().timeInMillis
                            history.add("end time", "$endTime")
                            history.add("time", "${endTime - startTime}")
                            history.endCard()
                            controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                        }
                    }.start()
                } else {
                    val endTime = Calendar.getInstance().timeInMillis
                    history.add("end time", "$endTime")
                    history.add("time", "${endTime - startTime}")
                    history.endCard()
                    controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
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
                            rightButton.visibility = View.VISIBLE
                            rightButton.alpha = 1F
                            rightButton.startAnimation(fadeOut)
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
                        val endTime = Calendar.getInstance().timeInMillis
                        history.add("end time", "$endTime")
                        history.add("time", "${endTime - startTime}")
                        history.endCard()
                        controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
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
                                    if (controller.shouldExplain(history, "addition 1..9")) {
                                        emphasisText.text = ""
                                        val sb = StringBuilder()
                                        for (i in 1..firstRandomDigit) {
                                            sb.append("\u25CF")
                                        }
                                        sb.append(" + ")
                                        for (i in 1..secondRandomDigit) {
                                            sb.append("\u25CF")
                                        }
                                        emphasisText.text = sb.toString()
                                        display(displayText,"The addition of the quantity " + firstRandomDigit + " and " + secondRandomDigit)
                                        object : CountDownTimer(5000, 1000) {
                                            override fun onTick(millisUntilFinished: Long) {}
                                            override fun onFinish() {
                                                display(displayText,"is the sum of " + (firstRandomDigit + secondRandomDigit) + " individual 1's")
                                                emphasisText.startAnimation(fadeIn)
                                                supportText.visibility = View.VISIBLE
                                                supportText.alpha = 1F
                                                supportText.startAnimation(fadeIn)
                                                sb.append(" =")
                                                emphasisText.text = sb.toString()
                                                val s = StringBuilder()
                                                for (i in 1..(firstRandomDigit + secondRandomDigit)) {
                                                    s.append("\u25CF")
                                                }
                                                supportText.text = s.toString()

                                                object : CountDownTimer(9000, 1000) {
                                                    override fun onTick(millisUntilFinished: Long) {}
                                                    override fun onFinish() {
                                                        supportText.visibility = View.GONE
                                                        supportText.alpha = 0F
                                                        val endTime = Calendar.getInstance().timeInMillis
                                                        history.add("end time", "$endTime")
                                                        history.add("time", "${endTime - startTime}")
                                                        history.endCard()
                                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                                                    }
                                                }.start()
                                            }
                                        }.start()
                                    } else {
                                        supportText.visibility = View.GONE
                                        supportText.alpha = 0F
                                        val endTime = Calendar.getInstance().timeInMillis
                                        history.add("end time", "$endTime")
                                        history.add("time", "${endTime - startTime}")
                                        history.endCard()
                                        controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                                    }
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
                            if (controller.shouldExplain(history, "addition 1..9")) {
                                emphasisText.text = ""
                                val sb = StringBuilder()
                                for (i in 1..firstRandomDigit) {
                                    sb.append("\u25CF")
                                }
                                sb.append(" + ")
                                for (i in 1..secondRandomDigit) {
                                    sb.append("\u25CF")
                                }
                                emphasisText.text = sb.toString()
                                display(displayText,"The addition of the quantity " + firstRandomDigit + " and " + secondRandomDigit)
                                object : CountDownTimer(5000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {}
                                    override fun onFinish() {
                                        display(displayText,"is the sum of " + (firstRandomDigit + secondRandomDigit) + " individual 1's")
                                        emphasisText.startAnimation(fadeIn)
                                        supportText.visibility = View.VISIBLE
                                        supportText.alpha = 1F
                                        supportText.startAnimation(fadeIn)
                                        sb.append(" =")
                                        emphasisText.text = sb.toString()
                                        val s = StringBuilder()
                                        for (i in 1..(firstRandomDigit + secondRandomDigit)) {
                                            s.append("\u25CF")
                                        }
                                        supportText.text = s.toString()
                                        object : CountDownTimer(9000, 1000) {
                                            override fun onTick(millisUntilFinished: Long) {}
                                            override fun onFinish() {
                                                supportText.visibility = View.GONE
                                                supportText.alpha = 0F
                                                val endTime = Calendar.getInstance().timeInMillis
                                                history.add("end time", "$endTime")
                                                history.add("time", "${endTime - startTime}")
                                                history.endCard()
                                                controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                                            }
                                        }.start()
                                    }
                                }.start()
                            } else {
                                supportText.visibility = View.GONE
                                supportText.alpha = 0F
                                val endTime = Calendar.getInstance().timeInMillis
                                history.add("end time", "$endTime")
                                history.add("time", "${endTime - startTime}")
                                history.endCard()
                                controller(leftButton, centerButton, rightButton, emphasisText, displayText, supportText)
                            }
                        }
                    }.start()
                }
            }
        }
    }
}
