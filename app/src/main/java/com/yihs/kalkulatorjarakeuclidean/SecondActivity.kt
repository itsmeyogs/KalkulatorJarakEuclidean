package com.yihs.kalkulatorjarakeuclidean

import android.os.Bundle
import android.text.InputType.*
import android.view.Gravity.*
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yihs.kalkulatorjarakeuclidean.databinding.ActivitySecondBinding
import java.math.BigDecimal
import kotlin.math.sqrt

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val jumlahTitik = intent.getIntExtra("jumlah_titik", 0)
        initEditText(jumlahTitik)

        binding.btnHitung.setOnClickListener {
            val titikValues = getAllTitikValues()
            val titikBaruValues = getAllTitikBaruValues()
            if (titikValues.isNotEmpty() && titikBaruValues.isNotEmpty()) {
                if (jumlahTitik == 1) {
                    hitung1Titik(titikValues[0], titikBaruValues[0])

                } else if (jumlahTitik == 2) {
                    hitung2Titik(
                        titikValues[0],
                        titikValues[1],
                        titikBaruValues[0],
                        titikBaruValues[1]
                    )
                } else if (jumlahTitik == 3) {
                    hitung3Titik(
                        titikValues[0],
                        titikValues[1],
                        titikValues[2],
                        titikBaruValues[0],
                        titikBaruValues[1],
                        titikBaruValues[2]
                    )
                } else if (jumlahTitik == 4) {
                    hitung4Titik(
                        titikValues[0],
                        titikValues[1],
                        titikValues[2],
                        titikValues[3],
                        titikBaruValues[0],
                        titikBaruValues[1],
                        titikBaruValues[2],
                        titikBaruValues[3]
                    )
                }
            }
        }
    }

    private fun initEditText(jumlahTitik: Int) {
        binding.apply {

            for (i in 1..jumlahTitik) {
                // TextView untuk layoutTitik
                val labelTitik = TextView(this@SecondActivity).apply {
                    text = "Titik X$i:"
                    layoutParams = LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(8, 0, 8, 0)
                    }
                }
                layoutTitik.addView(labelTitik)
                val editTextTitik = EditText(this@SecondActivity).apply {
                    hint = "X$i"
                    tag = "titikX$i"
                    gravity = CENTER_HORIZONTAL
                    layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT).apply {
                        weight = 1f
                        inputType = TYPE_CLASS_NUMBER or TYPE_NUMBER_FLAG_DECIMAL
                        setMargins(8, 0, 8, 0)
                    }
                }
                layoutTitik.addView(editTextTitik)

                // TextView untuk layoutTitikBaru
                val labelTitikBaru = TextView(this@SecondActivity).apply {
                    text = "Titik X$i:"
                    layoutParams = LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(8, 0, 8, 0)
                    }
                }
                val editTextTitikBaru = EditText(this@SecondActivity).apply {
                    hint = "X$i"
                    tag = "titikX$i"
                    gravity = CENTER_HORIZONTAL
                    layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT).apply {
                        weight = 1f
                        inputType = TYPE_CLASS_NUMBER or TYPE_NUMBER_FLAG_DECIMAL
                        setMargins(8, 0, 8, 0)
                    }
                }
                layoutTitikBaru.addView(labelTitikBaru)
                layoutTitikBaru.addView(editTextTitikBaru)
            }
        }
    }

    private fun getAllTitikValues(): List<String> {
        val values = mutableListOf<String>()
        val layoutTitik = binding.layoutTitik
        for (i in 0 until layoutTitik.childCount) {
            val child = layoutTitik.getChildAt(i)
            if (child is EditText && child.tag?.toString()?.startsWith("titikX") == true) {
                if (child.text.toString().isNotEmpty()) {
                    values.add(child.text.toString().trim())
                } else {
                    Toast.makeText(
                        this@SecondActivity,
                        "Semua titik harus diisi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return values
    }

    fun getAllTitikBaruValues(): List<String> {
        val values = mutableListOf<String>()
        val layoutTitikBaru = binding.layoutTitikBaru
        for (i in 0 until layoutTitikBaru.childCount) {
            val child = layoutTitikBaru.getChildAt(i)
            if (child is EditText && child.tag?.toString()?.startsWith("titikX") == true) {
                if (child.text.toString().isNotEmpty()) {
                    values.add(child.text.toString().trim())
                } else {
                    Toast.makeText(
                        this@SecondActivity,
                        "Semua titik baru harus diisi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return values
    }

    private fun delAngkaSetelahKoma(input: BigDecimal): BigDecimal {
        if (input.toString().contains(".")) {
            val bagian = input.toString().split(".")
            if (bagian[1].length > 3) {
                val potong = bagian[1].substring(0, 3)
                val hasil = "${bagian[0]}.${potong}"
                return hasil.toBigDecimal()
            } else {
                val hasil = "${bagian[0]}.${bagian[1]}"
                return hasil.toBigDecimal()
            }
        } else {
            return input
        }
    }


    private fun hitung1Titik(titik: String, titikBaru: String) {
        val hitungFirst = titik.toBigDecimal() - titikBaru.toBigDecimal()
        val hitungSecond = hitungFirst.pow(2)
        val hitungThird = sqrt(hitungSecond.toDouble())

        binding.apply {
            hasilFirst.text = "$$\\sqrt{(${titik})^2-(${titikBaru})^2} = \\sqrt{${hitungFirst}^2}$$"
            hasilSecond.text =
                "$$\\sqrt{${delAngkaSetelahKoma(hitungSecond)}} = ${delAngkaSetelahKoma(hitungThird.toBigDecimal())}$$"
            hasilFirst.visibility = View.VISIBLE
            hasilSecond.visibility = View.VISIBLE
        }
    }

    private fun hitung2Titik(
        titik1: String,
        titik2: String,
        titikBaru1: String,
        titikBaru2: String
    ) {
        val hitung1First = titik1.toBigDecimal() - titikBaru1.toBigDecimal()
        val hitung2First = titik2.toBigDecimal() - titikBaru2.toBigDecimal()

        val hitung1Second = hitung1First.pow(2)
        val hitung2Second = hitung2First.pow(2)

        val hitung1Third = delAngkaSetelahKoma(hitung1Second) + delAngkaSetelahKoma(hitung2Second)
        val hitung2Third = sqrt(hitung1Third.toDouble())

        binding.apply {
            hasilFirst.text =
                "$$\\sqrt{(${titik1}-${titikBaru1})^2+(${titik2}-${titikBaru2})^2}$$"
            hasilSecond.text = "$$\\sqrt{(${hitung1First})^2+(${hitung2First})^2} = \\sqrt{${
                delAngkaSetelahKoma(hitung1Second)
            }+${delAngkaSetelahKoma(hitung2Second)}}$$"
            hasilThird.text =
                "$$\\sqrt{${hitung1Third}} = ${delAngkaSetelahKoma(hitung2Third.toBigDecimal())}$$"
            hasilFirst.visibility = View.VISIBLE
            hasilSecond.visibility = View.VISIBLE
            hasilThird.visibility = View.VISIBLE
        }
    }

    private fun hitung3Titik(
        titik1: String,
        titik2: String,
        titik3: String,
        titikBaru1: String,
        titikBaru2: String,
        titikBaru3: String
    ) {
        val hitung1First = titik1.toBigDecimal() - titikBaru1.toBigDecimal()
        val hitung2First = titik2.toBigDecimal() - titikBaru2.toBigDecimal()
        val hitung3First = titik3.toBigDecimal() - titikBaru3.toBigDecimal()

        val hitung1Second = hitung1First.pow(2)
        val hitung2Second = hitung2First.pow(2)
        val hitung3Second = hitung3First.pow(2)

        val hitung1Third =
            delAngkaSetelahKoma(hitung1Second) + delAngkaSetelahKoma(hitung2Second) + delAngkaSetelahKoma(
                hitung3Second
            )

        val hitung1Fourth = sqrt(hitung1Third.toDouble())

        binding.apply {
            hasilFirst.text =
                "$$\\sqrt{(${titik1}-${titikBaru1})^2+(${titik2}-${titikBaru2})^2+(${titik3}-${titikBaru3})^2}$$"
            hasilSecond.text =
                "$$\\sqrt{(${hitung1First})^2+(${hitung2First})^2+(${hitung3First})^2}$$"
            hasilThird.text =
                "$$\\sqrt{${delAngkaSetelahKoma(hitung1Second)}+${delAngkaSetelahKoma(hitung2Second)}+${
                    delAngkaSetelahKoma(hitung3Second)
                }}$$"
            hasilFourth.text =
                "$$\\sqrt{${hitung1Third}} = ${delAngkaSetelahKoma(hitung1Fourth.toBigDecimal())}$$"

            hasilFirst.visibility = View.VISIBLE
            hasilSecond.visibility = View.VISIBLE
            hasilThird.visibility = View.VISIBLE
            hasilFourth.visibility = View.VISIBLE
        }
    }

    private fun hitung4Titik(
        titik1: String,
        titik2: String,
        titik3: String,
        titik4: String,
        titikBaru1: String,
        titikBaru2: String,
        titikBaru3: String,
        titikBaru4: String
    ) {
        val hitung1First = titik1.toBigDecimal() - titikBaru1.toBigDecimal()
        val hitung2First = titik2.toBigDecimal() - titikBaru2.toBigDecimal()
        val hitung3First = titik3.toBigDecimal() - titikBaru3.toBigDecimal()
        val hitung4First = titik4.toBigDecimal() - titikBaru4.toBigDecimal()

        val hitung1Second = hitung1First.pow(2)
        val hitung2Second = hitung2First.pow(2)
        val hitung3Second = hitung3First.pow(2)
        val hitung4Second = hitung4First.pow(2)

        val hitung1Third =
            delAngkaSetelahKoma(hitung1Second) + delAngkaSetelahKoma(hitung2Second) + delAngkaSetelahKoma(
                hitung3Second
            ) + delAngkaSetelahKoma(hitung4Second)

        val hitung1Fourth = sqrt(hitung1Third.toDouble())

        binding.apply {
            hasilFirst.text =
                "$$\\sqrt{(${titik1}-${titikBaru1})^2+(${titik2}-${titikBaru2})^2+(${titik3}-${titikBaru3})^2+(${titik4}-${titikBaru4})^2}$$"
            hasilSecond.text =
                "$$\\sqrt{(${hitung1First})^2+(${hitung2First})^2+(${hitung3First})^2+(${hitung4First})^2}$$"
            hasilThird.text =
                "$$\\sqrt{${delAngkaSetelahKoma(hitung1Second)}+${delAngkaSetelahKoma(hitung2Second)}+${
                    delAngkaSetelahKoma(hitung3Second)
                }+${delAngkaSetelahKoma(hitung4Second)}}$$"
            hasilFourth.text =
                "$$\\sqrt{${hitung1Third}} = ${delAngkaSetelahKoma(hitung1Fourth.toBigDecimal())}$$"

            hasilFirst.visibility = View.VISIBLE
            hasilSecond.visibility = View.VISIBLE
            hasilThird.visibility = View.VISIBLE
            hasilFourth.visibility = View.VISIBLE
        }

    }
}