package com.roshd.pedpo.calendaruni.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.roshd.pedpo.calendaruni.fragments.FragmentMonth;
import com.roshd.pedpo.calendaruni.utils.CalendarUtil;
import com.roshd.pedpo.calendaruni.vo.DateData;
import com.roshd.pedpo.calendaruni.vo.MarkedDates;
import com.roshd.pedpo.calendaruni.vo.MonthData;

;import java.util.Observable;
import java.util.Observer;

public class AdapterCalendarView extends FragmentPagerAdapter  {

    private AppCompatActivity appCompatActivity ;

    public AdapterCalendarView(FragmentManager fm , AppCompatActivity appCompatActivity) {
        super(fm);
        this.appCompatActivity = appCompatActivity ;

    }

    @Override
    public Fragment getItem(int position) {

        int year = CalendarUtil.position2Year(position);
        int month =(int) CalendarUtil.position2Month(position);
        int day = CalendarUtil.position2Day(position);

        FragmentMonth fragment = new FragmentMonth();

        MonthData monthData = new MonthData(new DateData(year, month, month / 2));
        fragment.setData(monthData);

//        MarkedDates.getInstance().refresh();

        return fragment;
    }

    @Override
    public int getCount() {
        return 1000;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
