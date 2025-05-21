package vcmsa.ci.flashcardquiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScoreActivity : AppCompatActivity() {

    var scoreTxt: TextView? = null
    var feedback: TextView? = null
    var reviewAns: TextView? = null
    var exit: Button? = null
    var review: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score)

        scoreTxt = findViewById<View>(R.id.scoreTxt) as TextView
        feedback = findViewById<View>(R.id.feedbacktxt) as TextView
        reviewAns = findViewById<View>(R.id.reviewAns) as TextView
        exit = findViewById<View>(R.id.exitBtn) as Button
        review = findViewById<View>(R.id.reviewBtn) as Button

        val score = intent.getIntExtra("score", 0)
        val questions = intent.getStringArrayExtra("questions")
        val answers = intent.getStringArrayExtra("answers")

        scoreTxt!!.text = "Your Score: $score / ${questions!!.size}"

        // Provide feedback based on score (Deitel,2020)
        if (score < 3) {
            feedback!!.text = "Keep practicing!"
        } else if (score < 5) {
            feedback!!.text = "Good job!"
        } else {
            feedback!!.text = "Excellent!"
        }
       // Show review in a scrollable TextView (Hardy,2021)
        review!!.setOnClickListener {
            val reviewBuilder = StringBuilder()
            if (questions != null && answers != null) {
                for (i in questions.indices) {
                    reviewBuilder.append("Q${i + 1}: ${questions[i]}\n")
                    reviewBuilder.append("Answer : ${answers[i]}\n\n")
                }
            // Setting the built string to the textview (Hardy,2021)
            reviewAns!!.text = reviewBuilder.toString()
            reviewAns!!.visibility = View.VISIBLE
        }
        // Exit button closes the app (Android Developers,2023)
        exit!!.setOnClickListener {
            finishAffinity()      // Close the entire app
        }

    }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}