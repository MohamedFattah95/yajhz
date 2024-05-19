package com.yajhz.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import com.yajhz.R
import com.yajhz.data.model.api.TimeModel
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("LogNotTimber")
object DateUtility {
    @SuppressLint("SimpleDateFormat")
    fun stringToDate(aDate: String?, aFormat: String?): Date? {
        if (aDate == null) return null
        val pos = ParsePosition(0)
        val simpledateformat = SimpleDateFormat(aFormat)
        return simpledateformat.parse(aDate, pos)
    }

    fun getDateOnly(mDateTime: String?): String {
        var dateOnly = ""
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //pattern
        try {
            val date = dateFormatter.parse(mDateTime) //2019-08-28 20:31:24 to parser of pattern
            //Get date only
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            dateOnly = dateFormat.format(date) //2019-08-28
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateOnly
    }

    fun getDateArabicFormat(mDateTime: String?): String {
        var dateOnly = ""
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") //pattern
        dateOnly = SimpleDateFormat(
            "dd " +
                    SimpleDateFormat("MM", Locale("ar"))
                        .format(Date()) + " yyyy", Locale.US
        )
            .format(Date())
        return dateOnly
    }

    @get:SuppressLint("SimpleDateFormat")
    val currentTimeTFormat: String
        get() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Date())

    fun getDateFormatted(mDateTime: String?): String {
        var dateOnly = ""
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd") //pattern
        try {
            val date = dateFormatter.parse(mDateTime) //2019-08-28 20:31:24 to parser of pattern
            //Get date only
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
            dateOnly = dateFormat.format(date) //2019-08-28
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateOnly
    }

    fun getDateOnlyTFormat(mDateTime: String?): String {
        var dateOnly = ""
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") //pattern
        try {
            val date = dateFormatter.parse(mDateTime) //2019-08-28 20:31:24 to parser of pattern
            //Get date only
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            dateOnly = dateFormat.format(date) //2019-08-28
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dateOnly
    }

    fun getDateMonthNameWithYearTFormat(mDateTime: String?): String {
        var mDateMonthNameWithYear = ""
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale("ar")) //pattern
        try {
            val date = dateFormatter.parse(mDateTime) //2019-08-28 20:31:24 to parser of pattern
            val dayOfTheWeek = DateFormat.format("EEEE", date) as String // Thursday
            val day = DateFormat.format("dd", date) as String // 20
            val monthString = DateFormat.format("MMM", date) as String // Jun
            val monthNumber = DateFormat.format("MM", date) as String // 06
            val year = DateFormat.format("yyyy", date) as String // 2013
            mDateMonthNameWithYear = "$monthString $year"
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return mDateMonthNameWithYear
    }

    fun getDateTimeWithDayMonthNamesTFormat(mDateTime: String?, context: Context): String {
        var mDateMonthNameWithYear = ""
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale("ar")) //pattern
        try {
            val date = dateFormatter.parse(mDateTime) //2019-08-28 20:31:24 to parser of pattern
            val dayOfTheWeek = DateFormat.format("EEEE", date) as String // Thursday
            val day = DateFormat.format("dd", date) as String // 20
            val monthString = DateFormat.format("MMM", date) as String // Jun
            val monthNumber = DateFormat.format("MM", date) as String // 06
            val year = DateFormat.format("yyyy", date) as String // 2013
            val timeModel: TimeModel = getTimeOnlyTFormat(mDateTime, context)
            val time: String = timeModel.time.toString() + " " + timeModel.amOrpm
            mDateMonthNameWithYear = "$dayOfTheWeek $day $monthString $year - $time"
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return mDateMonthNameWithYear
    }

    fun getDateTimeWithMonthNameTFormat(mDateTime: String?, context: Context): String {
        var mDateMonthNameWithYear = ""
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale("ar")) //pattern
        try {
            val date = dateFormatter.parse(mDateTime) //2019-08-28 20:31:24 to parser of pattern
            val dayOfTheWeek = DateFormat.format("EEEE", date) as String // Thursday
            val day = DateFormat.format("dd", date) as String // 20
            val monthString = DateFormat.format("MMM", date) as String // Jun
            val monthNumber = DateFormat.format("MM", date) as String // 06
            val year = DateFormat.format("yyyy", date) as String // 2013
            val timeModel: TimeModel = getTimeOnlyTFormat(mDateTime, context)
            val time: String = timeModel.time.toString() + " " + timeModel.amOrpm
            mDateMonthNameWithYear = "$day $monthString $year - $time"
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return mDateMonthNameWithYear
    }

    fun getDateTimeWithMonthNameSpaceFormat(mDateTime: String?, context: Context): String {
        var mDateMonthNameWithYear = ""
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ar")) //pattern
        try {
            val date = dateFormatter.parse(mDateTime) //2019-08-28 20:31:24 to parser of pattern
            val dayOfTheWeek = DateFormat.format("EEEE", date) as String // Thursday
            val day = DateFormat.format("dd", date) as String // 20
            val monthString = DateFormat.format("MMM", date) as String // Jun
            val monthNumber = DateFormat.format("MM", date) as String // 06
            val year = DateFormat.format("yyyy", date) as String // 2013
            val timeModel: TimeModel = getTimeOnly(mDateTime, context)
            val time: String = timeModel.time.toString() + " " + timeModel.amOrpm
            mDateMonthNameWithYear = "$day $monthString $year - $time"
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return mDateMonthNameWithYear
    }

    fun getDateTimeTFormat(mDateTime: String?, context: Context): String {
        var dateOnly = ""
        var dateTime = ""
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") //pattern
        try {
            val date = dateFormatter.parse(mDateTime) //2019-08-28 20:31:24 to parser of pattern
            //Get date only
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            dateOnly = dateFormat.format(date) //2019-08-28
            val timeModel: TimeModel = getTimeOnlyTFormat(mDateTime, context)
            dateTime = dateOnly + " - " + timeModel.time + " " + timeModel.amOrpm
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateTime
    }

    fun getDateTimeSpaceFormat(mDateTime: String?, context: Context): String {
        var dateOnly = ""
        var dateTime = ""
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //pattern
        try {
            val date = dateFormatter.parse(mDateTime) //2019-08-28 20:31:24 to parser of pattern
            //Get date only
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            dateOnly = dateFormat.format(date) //2019-08-28
            val timeModel: TimeModel = getTimeOnly(mDateTime, context)
            dateTime = dateOnly + " - " + timeModel.time + " " + timeModel.amOrpm
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateTime
    }

    @SuppressLint("LogNotTimber")
    fun getTimeOnly(mDateTime: String?, context: Context): TimeModel {
        var timeOnly = ""
        val time = TimeModel()
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //pattern
        try {
            val date = dateFormatter.parse(mDateTime) //2019-08-28 20:31:24 to parser of pattern
            //Get time only
            val dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
            timeOnly = dateFormat.format(date) //05:30
            if (timeOnly.contains("AM")) {
                val arr = timeOnly.split(" ").toTypedArray()
                time.time = arr[0]
                time.amOrpm = context.getText(R.string.am_text) as String
            } else if (timeOnly.contains("PM")) {
                val arr = timeOnly.split(" ").toTypedArray()
                time.time = arr[0]
                time.amOrpm = context.getText(R.string.pm_text) as String
            }
            Log.e("Time", "getTimeOnly: $timeOnly")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }

    @SuppressLint("LogNotTimber")
    fun getTimeOnlyTFormat(mDateTime: String?, context: Context): TimeModel {
        var timeOnly = ""
        val time = TimeModel()
        @SuppressLint("SimpleDateFormat") val dateFormatter =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") //pattern
        try {
            val date = dateFormatter.parse(mDateTime) //2019-08-28 20:31:24 to parser of pattern
            //Get time only
            val dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
            timeOnly = dateFormat.format(date) //05:30
            if (timeOnly.contains("AM")) {
                val arr = timeOnly.split(" ").toTypedArray()
                time.time = arr[0]
                time.amOrpm = context.getText(R.string.am_text) as String
            } else if (timeOnly.contains("PM")) {
                val arr = timeOnly.split(" ").toTypedArray()
                time.time = arr[0]
                time.amOrpm = context.getText(R.string.pm_text) as String
            }
            Log.e("Time", "getTimeOnly: $timeOnly")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }

    //This method returns date and time : 25-01-2020 - 02:10 AM
    fun getDateTime(dateTime: String?, context: Context): String {
        val time: TimeModel = getTimeOnlyTFormat(dateTime, context)
        return getDateOnlyTFormat(dateTime) + " - " + time.time + " " + time.amOrpm
    }

    fun getTimeFormatHS(mTime: String?): String {
        var timeOnly = ""
        @SuppressLint("SimpleDateFormat") val dateFormatter = SimpleDateFormat("HH:mm:ss") //pattern
        try {
            val date = dateFormatter.parse(mTime) //20:31:24 to parser of pattern
            //Get time only
            val dateFormat = SimpleDateFormat("hh:mm", Locale.US)
            timeOnly = dateFormat.format(date) //05:30
            Log.e("Time", "getTimeOnly: $timeOnly")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeOnly
    }

    fun getDayFromDateString(stringDate: String?, dateTimeFormat: String?): String {
        val daysArray =
            arrayOf("saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday")
        var day = ""
        var dayOfWeek = 0
        //dateTimeFormat = yyyy-MM-dd HH:mm:ss
        @SuppressLint("SimpleDateFormat") val formatter = SimpleDateFormat(dateTimeFormat)
        val date: Date?
        try {
            date = formatter.parse(stringDate)
            val c = Calendar.getInstance()
            if (date != null) {
                c.time = date
            }
            dayOfWeek = c[Calendar.DAY_OF_WEEK] - 1
            if (dayOfWeek < 0) {
                dayOfWeek += 7
            }
            day = daysArray[dayOfWeek]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return day
    }

    val currentTimeStamp: String?
        get() = try {
            @SuppressLint("SimpleDateFormat") val dateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
            dateFormat.format(Date())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
}