package com.gm.zodiak

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.gm.mvies.feature.helper.setHidden
import com.gm.mvies.feature.helper.setVisible
import com.gm.zodiak.databinding.ActivityMainBinding
import com.gm.zodiak.db.Zodiak
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by lazy { ViewModelProvider(this, ViewModelFactory(application)).get(MainVM::class.java)}
    private lateinit  var zodiaks : List<Zodiak>


    val ddmmyyyy = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val ddmmmyyyy = SimpleDateFormat("dd MMM", Locale.getDefault())


    var years= 0
    var months= 0
    var days= 0
    var tDate= ""

    var cal = Calendar.getInstance()
    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)


            val diff: Long = Calendar.getInstance().time.time - cal.time.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60

            days = (hours / 24).toInt()
            years= (days / 365)
            days -= (years * 365)
            months = (days / 30)
            days -= (months * 30)


            tDate= ddmmmyyyy.format(cal.time)
            binding.tgl.setText(ddmmyyyy.format(cal.time))

            binding.lHasil.setHidden()
        }
    }

//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onClick()
        onChange()
        listenData()
    }

    @SuppressLint("SetTextI18n")
    fun onClick(){
        binding.pick.setOnClickListener {
            DatePickerDialog(this@MainActivity,
                dateSetListener,

                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.submit.setOnClickListener {
            binding.message.setHidden()
            if(binding.name.text.toString().equals("")){
                binding.message.setVisible()
                binding.message.text= "Nama tidak bisa kosong"
            } else{
                if(binding.tgl.text.toString().equals("")){
                    binding.message.setVisible()
                    binding.message.text= "Tanggal lahir tidak bisa kosong"
                } else{
                    binding.lHasil.setVisible()

                    binding.nama.text= "Hallo "+binding.name.text

                    binding.tahun.text= "$years Tahun"
                    binding.bulan.text= "$months Bulan"
                    binding.hari.text= "$days Hari"

                    getZodiak()
                }
            }
        }
    }

    fun onChange(){
        binding.name.addTextChangedListener {
            binding.lHasil.setHidden()
        }
    }

    fun getZodiak(){
        for (temp in zodiaks){

            val start= getDate("${temp.start} 2023", "dd MMM yyyy")?.time!!
            val end= getDate("${temp.end} 2023", "dd MMM yyyy")?.time!!
            val date= getDate("$tDate 2023", "dd MMM yyyy")?.time!!

            if(date in start..end){
                binding.zodiak.text= temp.name
                break
            }
        }
    }

    fun getDate(date: String, parse:String): Date? {
        val parser = SimpleDateFormat(parse)

        return parser.parse(date)
    }

    fun listenData(){
        viewModel.getAllZodiak().observe(this,{ listData ->
            listData?.let { data ->
                zodiaks= data
            }
        })
    }
}