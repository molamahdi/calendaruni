package com.roshd.pedpo.calendaruni.utils;






import com.roshd.pedpo.calendaruni.vo.DateData;

import java.util.Calendar;


public class CalendarUtil {
    public static DateData date = CurrentCalendar.getCurrentDateData();

    public static int position2Year(int pos){
        int tmpYear,tmpMonth;
        Calendar calendar = Calendar.getInstance();
//        tmpYear = c.get(Calendar.YEAR);
        // TODO: 15/12/10 Maybe using current year is a mistake.
        tmpYear = date.getYear();
        tmpMonth = CalendarUtil.position2Month(pos);
        int ret;
        if(pos == 500){
            return tmpYear;
        }
        if(pos > 500){

            ret = tmpYear + ((pos - 500) + calendar.get(Calendar.MONTH))/12;
//            ret = tmpYear + ((pos - 500) + date.getMonth() - 1)/12;//oong

        }else{
            ret =  tmpYear - ((500 - pos)+tmpMonth - 1)/12;
//            ret = tmpYear - ((500-pos)/12);   //oong
        }
        return ret;
    }

    public static int position2Month(int pos){
        int tmpMonth;
        Calendar c = Calendar.getInstance();
        tmpMonth = c.get(Calendar.MONTH) + 1;
//        tmpMonth = date.getMonth();
        int ret;
        if(pos == 500){
            return tmpMonth;
        }
        if(pos > 500){
            ret = (tmpMonth + (pos - 500)%12) % 12;
        }else{
            ret = (tmpMonth - (500 - pos)%12) % 12;
            ret = ret<0?12+ret:ret;
        }
        return ret==0?12:ret;
    }

    public static int position2Day(int pos){
        int tmpDay;
        Calendar c = Calendar.getInstance();
//        tmpMonth = c.get(Calendar.MONTH) + 1;
        tmpDay = date.getDay();
        int ret;
        if(pos == 500){
            return tmpDay;
        }
//        if(pos > 500){
//            ret = (tmpMonth + (pos - 500)%12) % 12;
//        }else{
//            ret = (tmpMonth - (500 - pos)%12) % 12;
//            ret = ret<0?12+ret:ret;
//        }
        return 0;
    }

    public static int getWeekCount(int position){
        Calendar calendar = Calendar.getInstance();
        calendar.set(position2Year(position), position2Month(position) - 1, 1) ;
        int start = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int ret =  calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + start;
        int more = ret % 7 == 0 ? 0 : 1;
        ret = ret / 7 + more;
        calendar = null;
        return ret;
    }
}
