package com.hkjj.heartbreakprice.core.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object TimeUtil {

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateTime(): String{
        val current = LocalDateTime.now()

        // 패턴 설정: yyyy(년)-MM(월)-dd(일)'T'(구분자)HH(시):mm(분):ss(초)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        return current.format(formatter)
    }

    fun formatTimeAgo(date: Date): String {
        val now = Date()
        val diff = now.time - date.time
        val minutes = diff / 60000
        val hours = diff / 3600000
        val days = diff / 86400000
        return when {
            minutes < 1 -> "방금 전"
            minutes < 60 -> "${minutes}분 전"
            hours < 24 -> "${hours}시간 전"
            days < 7 -> "${days}일 전"
            else -> SimpleDateFormat("MM.dd", Locale.getDefault()).format(date)
        }
    }
}