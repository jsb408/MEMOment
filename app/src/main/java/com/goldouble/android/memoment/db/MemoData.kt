package com.goldouble.android.memoment.db

import io.realm.RealmObject
import java.util.*

open class MemoData(
    var id: Int = 0,
    var date: Date = Date(),
    var content: String = ""
) : RealmObject()