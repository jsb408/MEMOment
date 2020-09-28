package com.goldouble.android.memoment.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.goldouble.android.memoment.MainActivity
import com.goldouble.android.memoment.R
import com.goldouble.android.memoment.WriteActivity
import com.goldouble.android.memoment.db.MemoData
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main_calendar.view.*
import kotlinx.android.synthetic.main.list_item_moment.view.*
import java.text.SimpleDateFormat
import java.util.*

class MomentAdapter(val writeDate: Date) : RecyclerView.Adapter<MomentAdapter.ItemViewHolder>() {
    val realm = Realm.getDefaultInstance()
    val data : List<MemoData>
    get() {
        return realm.where(MemoData::class.java).findAll().filter {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.date) ==
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(writeDate)
        }.sortedBy { it.date }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentAdapter.ItemViewHolder {
        val adapterView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_moment, parent, false)
        return ItemViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: MomentAdapter.ItemViewHolder, position: Int) {
        holder.bindData(data[position])
    }


    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(moment: MemoData?) {
            moment?.let {memoData ->
                view.apply {
                    dateText.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(memoData.date)
                    contentText.text = memoData.content

//                    setOnClickListener { _ ->
//                        val intent = Intent(context, WriteActivity::class.java)
//                            .putExtra("id", memoData.id)
//                        context.startActivity(intent)
//                    }

                    setOnLongClickListener { _ ->
                        AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog)
                            .setMessage(R.string.delete_moment)
                            .setPositiveButton("확인") { _, _ ->
                                realm.executeTransaction {
                                    it.where(MemoData::class.java).equalTo("id", memoData.id).findAll().deleteAllFromRealm()
                                }
                                notifyItemRemoved(adapterPosition)
                                notifyItemRangeChanged(adapterPosition, data.size)
                            }
                            .setNegativeButton("취소") { _, _ -> }.show()
                        true
                    }
                }
            }
        }
    }
}