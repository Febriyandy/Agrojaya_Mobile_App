package com.febriandi.agrojaya.data.dao

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//Data converter untuk merubah format tanggal sebelum di simpan
class DateConverter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, formatter) }
    }
}