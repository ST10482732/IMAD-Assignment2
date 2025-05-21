package vcmsa.ci.flashcardquiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {

    // Variable declarations first
    var question: TextView? = null
    var output: TextView? = null
    var trueBtn: Button? = null
    var falseBtn: Button? = null
    var nextBtn: Button? = null

    // Array of questions and answers (Rao,2020)
    val questions = arrayOf(
        "Nelson Mandela was the president in 1994.",
        "World War II ended in 1943.",
        "The American Declaration of Independence was signed in 1776.",
        "The Industrial Revolution began in Germany.",
        "The French Revolution began in 1789."
    )
    val answers = arrayOf(
        "true",   // for questions[0]
        "false",  // for questions[1]
        "true",   // for questions[2]
        "false",  // for questions[3]
        "true"   // for questions[4]
    )
    var  currentIndex = 0
    var score = 0
    var answered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        // Link Ul components (Syal,2021)
        question = findViewById<View>(R.id.questionTxt) as TextView
        output = findViewById<View>(R.id.output) as TextView
        trueBtn = findViewById<View>(R.id.trueBtn) as Button
        falseBtn = findViewById<View>(R.id.falseBtn) as Button
        nextBtn = findViewById<View>(R.id.nextBtn) as Button

        displayQuestion()

        trueBtn!!.setOnClickListener {
            if (!answered) checkAnswer("true")
        }
        falseBtn!!.setOnClickListener {
            if (!answered) checkAnswer("false")
          }
        nextBtn!!.setOnClickListener {
            moveToNextQuestion()
          }
        }

        // Display current question
        fun displayQuestion() {
            question!!.text = questions[currentIndex]
            output!!.text = ""
            nextBtn!!.visibility = View.GONE
            answered = false

        }

        // Check answer and show feedback (Martin,2008)
        fun checkAnswer(userAnswer : String) {
            answered = true
            val correctAnswer = answers[currentIndex]

            if (userAnswer == correctAnswer) {
                output!!.text = "Correct!" // Provide feedback (Martin,2008)
                score++
            } else {
                output!!.text = "Incorrect"
            }

            // Show next button after answering
            nextBtn!!.visibility = View.VISIBLE
        }
            // Move to the next question (Android Developers,2023)
             fun moveToNextQuestion() {
                 currentIndex++

            if (currentIndex < questions.size) {
                displayQuestion()
            }else {
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("questions", questions)
                intent.putExtra("answers", answers)
                startActivity(intent)
                finish()
            }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}