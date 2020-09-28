package com.goldouble.android.memoment

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import com.goldouble.android.memoment.adapter.MomentAdapter
import com.goldouble.android.memoment.adapter.VerticalSpaceItemDecoration
import com.goldouble.android.memoment.db.MemoData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_calendar.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val calendar : Calendar
    get() {
        return Calendar.getInstance().apply {time = calendarView.selectedDate.date}
    }

    var isLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
        supportActionBar?.hide()

        calendarView.apply {
            val titleMonth = resources.getStringArray(R.array.months)
            setTitleFormatter { "${it.year} ${titleMonth[it.month]}" }
            setWeekDayLabels(R.array.weekdays)
            updateCalendar()
            setOnDateChangedListener { _, date, _ -> bindData(date.date) }
            setOnTitleClickListener {
                DatePickerDialog(context,
                    { _, year, month, day ->
                        selectedDate = CalendarDay.from(year, month, day)
                        currentDate = selectedDate
                        bindData(selectedDate.date)
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            selectedDate = CalendarDay.today()
        }

        BottomSheetBehavior.from(bottomSheet).addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("SLIDE", slideOffset.toString())
                momentRecyclerView.apply {
                    if(itemDecorationCount > 0) removeItemDecorationAt(0)
                    addItemDecoration(VerticalSpaceItemDecoration(context, slideOffset))
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Log.d("STATE", newState.toString())
            }
        })

        floatingWriteButton.setOnClickListener {
            val cal = Calendar.getInstance().apply {
                time = Date()
                set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            }

            val intent = Intent(this, WriteActivity::class.java)
                .putExtra("writeDate", cal.timeInMillis)
            startActivity(intent)
        }

        bindData(Date())
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        Log.d("HEIGHT", "hasFocus: $hasFocus, root : ${rootLayout.measuredHeight}, calendar: ${calendarView.measuredHeight}")
        if(!isLoaded) {
            BottomSheetBehavior.from(bottomSheet).peekHeight = rootLayout.measuredHeight - (calendarView.measuredHeight + 36)
            isLoaded = true
        }
    }

    override fun onResume() {
        super.onResume()
        updateCalendar()
        bindData(calendarView.selectedDate.date)
    }

    private fun bindData(date: Date) {
        momentRecyclerView.adapter = MomentAdapter(date)
        momentRecyclerView.layoutManager = LinearLayoutManager(this)

        noMomentLbl.visibility = if(momentRecyclerView.adapter?.itemCount == 0) View.VISIBLE else View.GONE
    }

    private fun updateCalendar() {
        calendarView.apply {
            removeDecorators()
            addDecorators(SelectedDayDecorator(context), HaveMomentDayDecorator(context))
        }
    }

    class SelectedDayDecorator(val context: Context): DayViewDecorator {
        override fun decorate(view: DayViewFacade?) {
            view?.setSelectionDrawable(ContextCompat.getDrawable(context, R.drawable.selected_rectangle)!!)
        }

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return true
        }
    }

    class HaveMomentDayDecorator(val context: Context): DayViewDecorator {
        override fun decorate(view: DayViewFacade?) {
            view?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.have_moment_indicator)!!)
        }

        override fun shouldDecorate(day: CalendarDay): Boolean {
            val moments = Realm.getDefaultInstance().where(MemoData::class.java).findAll().filter {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.date) ==
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(day.date)
            }
            return moments.isNotEmpty()
        }
    }
}