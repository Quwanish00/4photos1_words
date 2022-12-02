package com.example.gidline

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.gidline.databinding.ActivityGameBinding
import org.w3c.dom.Text
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var questions: List<Question>
    var clickedImageId = -1
    private lateinit var prefs: SharedPreferences
    private var optionLetters = mutableListOf<TextView>()
    private var answerLetters = mutableListOf<TextView>()
    private val currentAnswers = mutableListOf<Pair<String, TextView>>()
    private val currentOptions = mutableListOf<Char>()
    private var currentQuestionId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
//        val sharedPreferences:SharedPreferences=getSharedPreferences("level",Context.MODE_PRIVATE)

        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)






        questions = Constants.setQuestions()
        binding.apply {
            optionLetters.addAll(
                listOf(
                    option1, option2, option3, option4, option5, option6,
                    option7, option8, option9, option10, option11, option12,
                )
            )
            answerLetters.addAll(
                listOf(
                    answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8
                )

            )

        }
        optionLetters.forEach { optionTV ->
            optionTV.addTextChangedListener {
                val letter = it.toString()
                optionTV.isEnabled = letter.isNotEmpty()
            }
        }

        answerLetters.forEach { answerTV ->
            answerTV.addTextChangedListener {
                val letter = it.toString()
                answerTV.isEnabled = letter.isNotEmpty()
            }
        }
        currentQuestionId++
        setQuestion()

        binding.apply {
            option1.setOnClickListener { optionClick(it as TextView) }
            option2.setOnClickListener { optionClick(it as TextView) }
            option3.setOnClickListener { optionClick(it as TextView) }
            option4.setOnClickListener { optionClick(it as TextView) }
            option5.setOnClickListener { optionClick(it as TextView) }
            option6.setOnClickListener { optionClick(it as TextView) }
            option7.setOnClickListener { optionClick(it as TextView) }
            option8.setOnClickListener { optionClick(it as TextView) }
            option9.setOnClickListener { optionClick(it as TextView) }
            option10.setOnClickListener { optionClick(it as TextView) }
            option11.setOnClickListener { optionClick(it as TextView) }
            option12.setOnClickListener { optionClick(it as TextView) }

            answer1.setOnClickListener { answerClick(it as TextView) }
            answer2.setOnClickListener { answerClick(it as TextView) }
            answer3.setOnClickListener { answerClick(it as TextView) }
            answer4.setOnClickListener { answerClick(it as TextView) }
            answer5.setOnClickListener { answerClick(it as TextView) }
            answer6.setOnClickListener { answerClick(it as TextView) }
            answer7.setOnClickListener { answerClick(it as TextView) }
            answer8.setOnClickListener { answerClick(it as TextView) }

            binding.continuee.setOnClickListener {
                baraban.isVisible= false
                currentQuestionId++
                setQuestion()

            }
            image1.setOnClickListener {
                clickedImageId = 0
                bigImage.setImageResource(questions[currentQuestionId].images[0])
                bigImage.isVisible = true
                bigImage.startAnimation(
                    AnimationUtils.loadAnimation(
                        this@GameActivity, R.anim.animation_up_one
                    )
                )
            }

            image2.setOnClickListener {
                clickedImageId = 1
                bigImage.setImageResource(questions[currentQuestionId].images[1])
                bigImage.isVisible = true
                bigImage.startAnimation(
                    AnimationUtils.loadAnimation(
                        this@GameActivity, R.anim.animation_up_two
                    )
                )
            }

            bigImage.setOnClickListener {
                when(clickedImageId){
                    0-> {
                        bigImage.startAnimation(AnimationUtils.loadAnimation(
                            this@GameActivity,R.anim.animation_down_one
                        ))
                    }
                    1 -> {
                        bigImage.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@GameActivity, R.anim.animation_down_two
                            )
                        )
                    }
                }
                Handler(Looper.myLooper()!!).postDelayed({
                    bigImage.isVisible = false
                }, 200L)
            }
        }











        }




    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        val currentQuestion = questions[currentQuestionId]
        prefs.edit().putInt(Constants.LEVEL,currentQuestionId).apply()

        binding.apply {
            currentAnswers.clear()
            updateAnswer(currentQuestion)
            showContinue(false)

            val cycle = prefs.getInt(Constants.CYCLE, 0)
            lvlText.text = (cycle * questions.size + currentQuestionId + 1).toString()

            lvlText.text = (currentQuestionId + 1).toString()

            image1.setImageResource(currentQuestion.images[0])
            image2.setImageResource(currentQuestion.images[1])
            image3.setImageResource(currentQuestion.images[2])
            image4.setImageResource(currentQuestion.images[3])

            val options = currentQuestion.answer.toMutableList()

            repeat(12 - options.size) {

                options.add(Random.nextInt(1040, 1072).toChar())
            }
            options.shuffle()
            currentOptions.clear()
            currentOptions.addAll(options)

            options.forEachIndexed { index, letter ->

                optionLetters[index].text = letter.toString()

            }
            answerLetters.forEach {
                it.isVisible = true
                it.isClickable=true
            }

            for (i in currentQuestion.answer.length until answerLetters.size) {

                answerLetters[i].isVisible = false
            }


        }


    }

    private fun showContinue(show: Boolean) {
        binding.apply {
            continuee.isVisible = show
            play.isVisible = show
            view.isVisible = show
            baraban.isVisible = show

            baraban.startAnimation(
                AnimationUtils.loadAnimation(
                    this@GameActivity,R.anim.rotate_animation
                )
            )

            answerLetters.forEach {
                it.isClickable = false
            }


        }
    }

    private fun optionClick(optionTv: TextView) {
        val currentQuestion = questions[currentQuestionId]

        val index = optionLetters.indexOf(optionTv)
        val letter = currentOptions[index]

        val pairIndex = currentAnswers.indexOfFirst { it.first == "" }
        if (pairIndex == -1) {
            currentAnswers.add(Pair(letter.toString(),optionTv))
        } else {
            currentAnswers[pairIndex] = Pair(letter.toString(),optionTv)
        }

        updateAnswer(currentQuestion)
        optionTv.text = ""


    }

    private fun answerClick(answerTv: TextView) {

        answerTv.text = ""
        val index = answerLetters.indexOf(answerTv)
        val pair = currentAnswers[index]


        pair.second.text = pair.first
        currentAnswers[index] = Pair( "",pair.second)
        updateAnswer(questions[currentQuestionId])




    }

    private fun updateAnswer(question: Question) {
        if(currentAnswers.isEmpty()){
            answerLetters.forEach {
                it.text = ""
            }
            optionLetters.forEach {
                it.isClickable = true
            }
            return
        }

        currentAnswers.forEachIndexed { index, letter ->
                answerLetters[index].text = letter.first

        }
        if (question.answer.length == currentAnswers.filter { it.first.isNotEmpty() }.size) {

            if (question.answer == currentAnswers.map { it.first }.joinToString("")) {
                showContinue(true)


            }
            optionLetters.forEach { option ->
                option.isClickable = false

            }

        } else {
            optionLetters.forEach { option ->
                option.isClickable = true
            }

        }


    }
}
