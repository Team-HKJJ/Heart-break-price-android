package com.hkjj.heartbreakprice.core.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtil {

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateTime(): String{
        val current = LocalDateTime.now()

        // 패턴 설정: yyyy(년)-MM(월)-dd(일)'T'(구분자)HH(시):mm(분):ss(초)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        return current.format(formatter)
    }
}