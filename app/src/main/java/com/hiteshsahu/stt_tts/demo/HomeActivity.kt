package com.hiteshsahu.stt_tts.demo
import android.app.Activity
import android.content.Intent
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


class HomeActivity : BasePermissionActivity() {

    private var fadeIn = AlphaAnimation(0.0f, 1.0f)
    private var fadeOut = AlphaAnimation(1.0f, 0.0f)

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
            initText.startAnimation(fadeOut)
            helloButton.startAnimation(fadeOut)
            leftButton.visibility = View.VISIBLE
            centerButton.visibility = View.VISIBLE
            rightButton.visibility = View.VISIBLE

            say("Hey! Thanks for clicking on me.")
            say("It makes me feel special")
        }
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
                    .initialize(stringToSpeak, this)}
    }

    fun findString(listOfPossibleMatches: ArrayList<String>?, stringToMatch: String): Boolean {
        if (null != listOfPossibleMatches) {
            for (transaltion in listOfPossibleMatches) {
                if (transaltion.contains(stringToMatch)) {
                    return true
                }
            }
        }
        return false
    }

    fun share(messageToShare: String, activity: Activity) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, messageToShare)
        activity.startActivity(Intent.createChooser(shareIntent, "Share using"))
    }



}


