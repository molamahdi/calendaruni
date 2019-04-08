package com.roshd.pedpo.calendaruni;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.roshd.pedpo.calendaruni.adapter.AdapterCalendarView;


public class CalendarActivity extends AppCompatActivity {


    private ViewPager viewPager ;
    private AdapterCalendarView adapterCalendarView ;
    public static SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        sharedPreferences = getSharedPreferences("rang1",MODE_PRIVATE);

        viewPager = findViewById(R.id.viewPagerCalendar);

        adapterCalendarView = new AdapterCalendarView(getSupportFragmentManager(),this);

        viewPager.setAdapter(adapterCalendarView);
        viewPager.setCurrentItem(500);


    }
}
