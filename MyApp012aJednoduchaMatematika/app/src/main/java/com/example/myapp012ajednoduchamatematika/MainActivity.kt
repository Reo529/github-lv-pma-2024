package com.example.myapp012ajednoduchamatematika

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var TimeTextView: TextView? = null
    var QuestionTextText: TextView? = null
    var ScoreTextView: TextView? = null
    var AlertTextView: TextView? = null
    var FinalScoreTextView: TextView? = null
    var btn0: Button? = null
    var btn1: Button? = null
    var btn2: Button? = null
    var btn3: Button? = null
    var countDownTimer: CountDownTimer? = null
    var random: Random = Random
    var a = 0
    var b = 0
    var indexOfCorrectAnswer = 0
    var answers = ArrayList<Int>()
    var points = 0
    var totalQuestions = 0
    var cals = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val calInt = intent.getStringExtra("cals")
        cals = calInt ?: "" // Pokud je calInt null, nastav prázdný řetězec
        TimeTextView = findViewById(R.id.TimeTextView)
        QuestionTextText = findViewById(R.id.QuestionTextText)
        ScoreTextView = findViewById(R.id.ScoreTextView)
        AlertTextView = findViewById(R.id.AlertTextView)
        btn0 = findViewById(R.id.button0)
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)

        start()
    }

    fun NextQuestion(cal: String) {
        a = random.nextInt(10)
        b = random.nextInt(10)

        // Pokud je operace dělení, zaručíme, že b nebude nula
        if (cal == "/" && b == 0) {
            b = random.nextInt(1, 10) // Pokud je dělení, nastavíme b na nenulovou hodnotu
        }

        QuestionTextText?.text = "$a $cal $b"
        indexOfCorrectAnswer = random.nextInt(4)
        answers.clear()

        for (i in 0..3) {
            if (indexOfCorrectAnswer == i) {
                when (cal) {
                    "+" -> answers.add(a + b)
                    "-" -> answers.add(a - b)
                    "*" -> answers.add(a * b)
                    "/" -> answers.add(a / b)
                }
            } else {
                var wrongAnswer = random.nextInt(20)
                // Zajišťujeme, že špatné odpovědi nebudou stejné jako správná
                while (
                    wrongAnswer == a + b
                    || wrongAnswer == a - b
                    || wrongAnswer == a * b
                    || (cal == "/" && wrongAnswer == a / b)
                ) {
                    wrongAnswer = random.nextInt(20)
                }
                answers.add(wrongAnswer)
            }
        }

        btn0?.text = answers[0].toString()
        btn1?.text = answers[1].toString()
        btn2?.text = answers[2].toString()
        btn3?.text = answers[3].toString()

        // Přiřazení tagů pro porovnání správné odpovědi
        btn0?.tag = 0
        btn1?.tag = 1
        btn2?.tag = 2
        btn3?.tag = 3
    }

    fun optionSelect(view: View?) {
        totalQuestions++
        if (indexOfCorrectAnswer == view?.tag as Int) {
            points++
            AlertTextView?.text = "Correct"
        } else {
            AlertTextView?.text = "Wrong"
        }
        ScoreTextView?.text = "$points/$totalQuestions"
        NextQuestion(cals)
    }

    fun PlayAgain(view: View?) {
        points = 0
        totalQuestions = 0
        ScoreTextView?.text = "$points/$totalQuestions"
        countDownTimer?.start()
    }

    private fun start() {
        NextQuestion(cals)
        countDownTimer = object : CountDownTimer(10000, 500) {
            override fun onTick(p0: Long) {
                TimeTextView?.text = (p0 / 1000).toString() + "s"
            }

            override fun onFinish() {
                TimeTextView?.text = "Konec času"
                showDialog()
            }
        }.start()
    }

    private fun showDialog() {
        val inflate = LayoutInflater.from(this)
        val winDialog = inflate.inflate(R.layout.win_layout, null)
        FinalScoreTextView = winDialog.findViewById(R.id.FinalScoreTextView)
        val btnPlayAgain = winDialog.findViewById<Button>(R.id.buttonPlayAgain)
        val btnBack = winDialog.findViewById<Button>(R.id.buttonBack)

        val dialog = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setView(winDialog)

        FinalScoreTextView?.text = "$points/$totalQuestions"

// Vytvoření dialogového okna
        val showDialog = dialog.create()
        showDialog.show()

        btnPlayAgain.setOnClickListener {
            // Zavření dialogového okna
            showDialog.dismiss()
            // Logika pro restart hry
            PlayAgain(it)
        }

        btnBack.setOnClickListener {
            // Zavření dialogového okna
            showDialog.dismiss()
            // Návrat na předchozí obrazovku
            onBackPressed()
        }
    }
}