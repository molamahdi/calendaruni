package com.roshd.pedpo.calendaruni.utils;

import android.graphics.Color;
import android.widget.TextView;

import com.roshd.pedpo.calendaruni.vo.DayData;

import java.util.Calendar;
import java.util.Date;

public class GridUtil {


    private static GridUtil gridUtil = new GridUtil();
    public static GridUtil newInstance()
    {
        return gridUtil ;
    }

    public void toDay(DayData dayData, TextView textView , TextView textViewTitle) {
        if (dayData.getDate().equals(CurrentCalendar.getCurrentDateData())) {

            textViewTitle.setText(dayData.getDate().getYear() + "/" + dayData.getDate().getMonth() + "/" + dayData.getDate().getDay());
//            ShapeDrawable circleDrawable = new ShapeDrawable(new OvalShape());
//            circleDrawable.getPaint().setColor(Color.parseColor("#EF6C00"));
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundColor(Color.parseColor("#EF6C00"));

        }
    }

    public boolean kmmToday(DayData dayData ,TextView textView) {
        Date date1 = new Date(dayData.getDate().getYear(), dayData.getDate().getMonth(), dayData.getDate().getDay());

        Date date2 = new Date(CurrentCalendar.getCurrentDateData().getYear(), CurrentCalendar.getCurrentDateData().getMonth(), CurrentCalendar.getCurrentDateData().getDay());

        if (date1.getTime() < date2.getTime()) {

            textView.setTextColor(Color.GRAY);
            textView.setText(dayData.getText());
            return true;
        }
        return false;
    }

    public void sunDay(DayData dayData, TextView textViewCell)
    {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(dayData.getDate().getYear(),dayData.getDate().getMonth()-1,dayData.getDate().getDay());
        calendar.setTime(date);

        if (calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY+1 || calendar.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY )
        {
            textViewCell.setTextColor(Color.RED);
            GridUtil.newInstance().kmmToday(dayData,textViewCell);
        }


    }

    public void dayIsZero(DayData dayData, TextView textViewCell) {


        if (dayData.getDate().getDay() == 0) {
            textViewCell.setText("");
            textViewCell.setBackgroundColor(Color.WHITE);
        }
    }

    public void rangCalendar(DayData dayData, TextView textView, Date rang1, Date rang2) {


        Date rangBase = new Date(dayData.getDate().getYear(), (dayData.getDate().getMonth()) - 1, dayData.getDate().getDay());

        if (rang2.getTime()==0 || rang2.getTime() < rang1.getTime()){
            if (rangBase.getTime()>= rang1.getTime() && rangBase.getTime() <= rang1.getTime())
            {
                textView.setBackgroundColor(Color.GREEN);
                textView.setText(dayData.getText());
            }
        }


        if (rangBase.getTime() >= rang1.getTime() && rangBase.getTime() <= rang2.getTime()) {

            textView.setBackgroundColor(Color.GREEN);
            textView.setText(dayData.getText());
//            Toast.makeText(getContext(), dayData.getDate().getYear() + "/" + dayData.getDate().getMonth() + "/" + dayData.getDate().getDay(), Toast.LENGTH_SHORT).show();
        }

    }

}
