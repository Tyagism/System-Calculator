package cal.system.systemcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AlignmentSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.systemcalculator.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayHistory()
        addtwonum()
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun addtwonum() {
        val num1 = findViewById<EditText>(R.id.Num1)
        val num2 = findViewById<EditText>(R.id.Num2)
        val additbtn = findViewById<Button>(R.id.add)
        val subbtn = findViewById<Button>(R.id.sub)
        val multiplybtn = findViewById<Button>(R.id.multiply)
        val dividebtn = findViewById<Button>(R.id.divide)
        val clsbtn = findViewById<Button>(R.id.Clsbtn)
        val exitbtn = findViewById<Button>(R.id.Exitbtn)
        val textView = findViewById<TextView>(R.id.textView)
        val txtview2 = findViewById<TextView>(R.id.txtView2)


        txtview2.setOnClickListener {
            val url = "https://github.com/Tyagism/System-Calculator/blob/main/Privacy-Policy.md"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }


        additbtn.setOnClickListener {
            val addnum1 = num1.text.toString()
            val addnum2 = num2.text.toString()

            if (addnum1.isNotEmpty() && addnum2.isNotEmpty()) {
                val result = addnum1.toFloat() + addnum2.toFloat()
                val calculation = "$addnum1 + $addnum2"
                textView.text = "Answer : $result"
                saveResults(calculation, result)
            } else {
                Toast.makeText(
                    this,
                    "Both numbers should not be empty" + textView.text,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        subbtn.setOnClickListener {
            val addnum1 = num1.text.toString()
            val addnum2 = num2.text.toString()

            if (addnum1.isNotEmpty() && addnum2.isNotEmpty()) {
                val result = addnum1.toFloat() - addnum2.toFloat()
                val calculation = "$addnum1 - $addnum2"
                textView.text = "Answer : $result"
                saveResults(calculation, result)
            } else {
                Toast.makeText(
                    this,
                    "Both numbers should not be empty" + textView.text,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        multiplybtn.setOnClickListener {
            val addnum1 = num1.text.toString()
            val addnum2 = num2.text.toString()

            if (addnum1.isNotEmpty() && addnum2.isNotEmpty()) {
                val result = addnum1.toFloat() * addnum2.toFloat()
                val calculation = "$addnum1 * $addnum2"
                textView.text = "Answer : $result"
                saveResults(calculation, result)

            } else {
                Toast.makeText(
                    this,
                    "Both numbers should not be empty" + textView.text,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }



        dividebtn.setOnClickListener {
            val addnum1 = num1.text.toString()
            val addnum2 = num2.text.toString()

            if (addnum1.isNotEmpty() && addnum2.isNotEmpty()) {
                val result = addnum1.toFloat() / addnum2.toFloat()
                val calculation = "$addnum1 / $addnum2"
                textView.text = "Answer : $result"
                saveResults(calculation, result)
            } else {
                Toast.makeText(
                    this,
                    "Both numbers should not be empty" + textView.text,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        clsbtn.setOnClickListener {
            num1.setText("")
            num2.setText("")
            textView.text = "Answer :"

        }
        exitbtn.setOnClickListener { finishAffinity() }

    }


    @SuppressLint("SuspiciousIndentation","SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveResults(calculation:String,result:Float){
        val sharedPreferences=getSharedPreferences("Results",Context.MODE_PRIVATE)
        val datePreferences=getSharedPreferences("Dates",Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        val dateEditor=datePreferences.edit()
        val oldResults=sharedPreferences.getString("results","")
        val currentDateTime=LocalDateTime.now()
        val formatter=DateTimeFormatter.ofPattern("dd/MM/yy              ")
        val formattedDateTime:String=currentDateTime.format(formatter)
        val newResult="$oldResults\n\n\n\n $formattedDateTime  \n\n$calculation=$result"
        editor.putString("results",newResult)
        dateEditor.putString("date",formattedDateTime)
        editor.apply()
        dateEditor.apply()


        val hisView=findViewById<TextView>(R.id.txtview1)
        var isHistoryDisplayed=false

        hisView.setOnClickListener{
            if(isHistoryDisplayed){
                hisView.text="History"
                isHistoryDisplayed=false
            }else{
                val formattedDateTimeSpannable = SpannableString(formattedDateTime)
                formattedDateTimeSpannable.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, formattedDateTime.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                val newResultSpannable = SpannableString(newResult)
                newResultSpannable.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0, newResult.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                val combinedSpannable = SpannableStringBuilder().append(formattedDateTimeSpannable).append("\n").append(newResultSpannable)

                hisView.text = combinedSpannable
                isHistoryDisplayed=true
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun displayHistory(){
        val hisView=findViewById<TextView>(R.id.txtview1)
        var isHistoryDisplayed=false


        hisView.setOnClickListener{
            if(isHistoryDisplayed){
                hisView.text="History"
                isHistoryDisplayed=false
            }else{
                val sharedPreferences=getSharedPreferences("Results",Context.MODE_PRIVATE)
                val results=sharedPreferences.getString("results","")
                hisView.text=results
                isHistoryDisplayed=true
            }
        }
    }

}

