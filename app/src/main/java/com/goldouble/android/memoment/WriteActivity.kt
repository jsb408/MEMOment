package com.goldouble.android.memoment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.goldouble.android.memoment.db.MemoData
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_write.*
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity : AppCompatActivity() {
    val realm = Realm.getDefaultInstance()
    val calendar: Calendar
        get() {
            return Calendar.getInstance().apply { time = writeDate }
        }

    lateinit var writeDate: Date
    var seq = -1
    var original: MemoData? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        seq = intent.getIntExtra("id", -1)
        original = realm.where(MemoData::class.java).equalTo("id", seq).findFirst()

        writeDate = original?.date ?: Date(intent.getLongExtra("writeDate", 0))

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.write_moment)
        }

        dateText.text = dateText(writeDate)
        dateText.setOnClickListener {
            DatePickerDialog(this,
                { _, year, month, day ->
                    writeDate.time = Calendar.getInstance().apply {
                        time = writeDate
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, day)
                    }.timeInMillis
                    (it as TextView).text = dateText(writeDate)
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        timeText.text = timeText(writeDate)
        timeText.setOnClickListener {
            TimePickerDialog(this,
                { _, hour, min ->
                    writeDate.time = Calendar.getInstance().apply {
                        time = writeDate
                        set(Calendar.HOUR_OF_DAY, hour)
                        set(Calendar.MINUTE, min)
                    }.timeInMillis
                    timeText.text = timeText(writeDate)
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        original?.let { contentEditText.setText(it.content) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_write, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.write_moment -> {
                writeMemo(contentEditText.text.toString())
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun writeMemo(cont: String) {
        val moments = realm.where(MemoData::class.java).findAll()
        val seq = if(moments.isEmpty()) 0 else moments.last()!!.id + 1

        realm.apply {
            beginTransaction()
            original?.let {
                realm.where(MemoData::class.java).equalTo("id", it.id).findFirst()!!.apply {
                    date = writeDate
                    content = cont
                }
            } ?: run {
                createObject<MemoData>().apply {
                    id = original?.id ?: seq
                    date = writeDate
                    content = cont
                }
            }
            commitTransaction()
        }
    }

    private fun dateText(date: Date) : String = SimpleDateFormat("yyyy. MM. dd. ", Locale.getDefault()).format(date)
    private fun timeText(date: Date) : String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
}