package com.yihs.kalkulatorjarakeuclidean

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yihs.kalkulatorjarakeuclidean.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val JUMLAH_TITIK = "jumlah_titik"
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            btnSubmit.setOnClickListener {
                if (etJumlahTitik.text.toString().isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Jumlah titik tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (etJumlahTitik.text.toString().toInt() < 1) {
                    Toast.makeText(this@MainActivity, "Jumlah titik minimal 1", Toast.LENGTH_SHORT)
                        .show()
                } else if (etJumlahTitik.text.toString().toInt() > 4) {
                    Toast.makeText(this@MainActivity, "Jumlah titik maksimal 4", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    val jumlahTitik = etJumlahTitik.text.toString().toInt()
                    val intent = Intent(this@MainActivity, SecondActivity::class.java)
                    intent.putExtra(JUMLAH_TITIK, jumlahTitik)
                    startActivity(intent)
                }

            }
        }
    }
}