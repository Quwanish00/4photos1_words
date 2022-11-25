package com.example.gidline

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.gidline.databinding.ActivityGameBinding
import org.w3c.dom.Text
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var questions: List<Question>
    private var optionLetters = mutableListOf<TextView>()
    private var answerLetters = mutableListOf<TextView>()
    private val currentAnswers = mutableListOf<Pair<TextView,String>>()
    private val currentOptions = mutableListOf<Char>()
    private var currentQuestionId = -1
    private var lvl = 0
    override fun onCreate(savedInstanceState: Bundle?) {
//        val sharedPreferences:SharedPreferences=getSharedPreferences("level",Context.MODE_PRIVATE)

        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentQuestionId = 0
        binding.back.setOnClickListener {

            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }






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
                answerLetters.clear()
                currentQuestionId++
                setQuestion()
                answerLetters.forEach {
                    it.isClickable=true
                    it.isEnabled=true
                }
                optionLetters.forEach {
                    it.isClickable=true
                    it.isEnabled=true
                }
            }

            option1.addTextChangedListener {
                val letter =it.toString()
                if(letter.isNotEmpty()){
                    option1.isEnabled=true
                }
            }


        }



    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        val currentQuestion = questions[currentQuestionId]

        binding.apply {
            currentAnswers.clear()
            updateAnswer(currentQuestion)
            showContinue(false)

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

            answerLetters.forEach {
                it.isClickable =false
            }



        }
    }

    private fun optionClick(optionTv: TextView) {
        val currentQuestion = questions[currentQuestionId]

        val index = optionLetters.indexOf(optionTv)
        val letter = currentOptions[index]

        val pairIndex =currentAnswers.indexOfFirst { it.second == "" }
        if(pairIndex == -1){
            currentAnswers.add(Pair(optionTv,letter.toString()))
        }
        else{
            currentAnswers[pairIndex]=Pair(optionTv,letter.toString())
        }

        updateAnswer(currentQuestion)
        optionTv.text = ""

        if (currentQuestion.answer.length == currentAnswers.size) {

            if (currentQuestion.answer == currentAnswers.map { it.second }. joinToString("")) {
                showContinue(true)




            }
                binding.apply {
                    answer1.setTextColor(Color.RED)
                    answer2.setTextColor(Color.RED)
                    answer3.setTextColor(Color.RED)
                    answer4.setTextColor(Color.RED)
                    answer5.setTextColor(Color.RED)
                    answer6.setTextColor(Color.RED)
                    answer7.setTextColor(Color.RED)
                    answer8.setTextColor(Color.RED)
                }
                optionLetters.forEach { option ->
                    option.isClickable = false

                }

        }
    }
    private fun answerClick(answerTv:TextView){
        binding.apply {
            answer1.setTextColor(Color.WHITE)
            answer2.setTextColor(Color.WHITE)
            answer3.setTextColor(Color.WHITE)
            answer4.setTextColor(Color.WHITE)
            answer5.setTextColor(Color.WHITE)
            answer6.setTextColor(Color.WHITE)
            answer7.setTextColor(Color.WHITE)
            answer8.setTextColor(Color.WHITE)
        }


        answerTv.text=""
        val index=answerLetters.indexOf(answerTv)
        val pair= currentAnswers[index]


        pair.first.text = pair.second
        currentAnswers[index] = Pair(pair.first,"")


        answerLetters.forEach { answer->

            answer.addTextChangedListener {
                val answeR=answer.toString()
                answer.isEnabled=answeR.isNotEmpty()
            }

        }
        
    }

    private fun updateAnswer(question: Question) {
        currentAnswers.forEachIndexed { index, letter ->
            if (letter.second !=""){
                answerLetters[index].text = letter.second
            }
        }
        if (question.answer.length == currentAnswers.size) {

            if (question.answer == currentAnswers.map { it.second }. joinToString("")) {
                showContinue(true)




            }
            binding.apply {
                answer1.setTextColor(Color.RED)
                answer2.setTextColor(Color.RED)
                answer3.setTextColor(Color.RED)
                answer4.setTextColor(Color.RED)
                answer5.setTextColor(Color.RED)
                answer6.setTextColor(Color.RED)
                answer7.setTextColor(Color.RED)
                answer8.setTextColor(Color.RED)
            }
            optionLetters.forEach { option ->
                option.isClickable = false

            }

        }

    }


}
