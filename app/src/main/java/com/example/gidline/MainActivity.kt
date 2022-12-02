package com.example.gidline

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gidline.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var questions: List<Question>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.play.setOnClickListener {
            val intent=Intent(this,GameActivity::class.java)
            startActivity(intent)

        }
        questions = Constants.setQuestions()
        prefs = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)


    }

    override fun onStart() {
        super.onStart()
        val questionId = prefs.getInt(Constants.LEVEL, 0)
        setQuestion(questionId)
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion(questionId: Int) {
        val currentQuestion = questions[questionId]

        binding.apply {
            val cycle = prefs.getInt(Constants.CYCLE, 0)
            textLvl.text = (cycle * questions.size + questionId + 1).toString()

            image1.setImageResource(currentQuestion.images[0])
            image2.setImageResource(currentQuestion.images[1])
            image3.setImageResource(currentQuestion.images[2])
            image4.setImageResource(currentQuestion.images[3])
        }
    }




    }
