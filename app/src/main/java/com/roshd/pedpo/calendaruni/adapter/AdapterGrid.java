package com.roshd.pedpo.calendaruni.adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roshd.pedpo.calendaruni.CalendarActivity;
import com.roshd.pedpo.calendaruni.R;
import com.roshd.pedpo.calendaruni.to.MarkStyle;
import com.roshd.pedpo.calendaruni.utils.CurrentCalendar;
import com.roshd.pedpo.calendaruni.utils.GridUtil;
import com.roshd.pedpo.calendaruni.vo.DateData;
import com.roshd.pedpo.calendaruni.vo.DayData;
import com.roshd.pedpo.calendaruni.vo.MarkedDates;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;


public class AdapterGrid extends ArrayAdapter implements Observer, View.OnClickListener {

    private ArrayList arrayListDate;
    private LayoutInflater layoutInflater;
    private TextView textViewTitle;
    private EditText editTextStartDate, editTextEndDate;
    private DatePickerDialog datePickerDialog;
    private Button btn_show;
    private AppCompatActivity appCompatActivity;
    private boolean check = true;
    private Date rang1, rang2;
    private Calendar calendarRang1;
    private Calendar calendarRang2;

    private boolean rangeBoolean ;


//    private DataBaseManagerCalendar dataBaseManagerCalendar ;


    public AdapterGrid(Context context, AppCompatActivity appCompatActivity, int resource, ArrayList arrayListDate, TextView textViewTitle, EditText editTextStartDate, EditText editTextEndDate, Button btn_show) {
        super(context, resource);

        this.arrayListDate = arrayListDate;
        this.layoutInflater = LayoutInflater.from(context);
        this.textViewTitle = textViewTitle;

        this.editTextEndDate = editTextEndDate;
        this.editTextStartDate = editTextStartDate;
        this.btn_show = btn_show;
        this.appCompatActivity = appCompatActivity;
//        dataBaseManagerCalendar = new DataBaseManagerCalendar(context);

        MarkedDates.getInstance().addObserver(this);

    }

    @Override
    public void notifyDataSetChanged() {
//        calendarRang1 = new Ca(CalendarActivity.sharedPreferences.getLong("rang1", 0));
//        calendarRang2 = new Date(CalendarActivity.sharedPreferences.getLong("rang2", 0));
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View covertView, ViewGroup parent) {

        final DayData dayData = (DayData) arrayListDate.get(position);

        covertView = this.layoutInflater.inflate(R.layout.item_cell_day_calendar, parent, false);
        final TextView textViewCell = covertView.findViewById(R.id.textview_item);


        textViewCell.setText(dayData.getText());
//        textViewCell.setBackgroundColor(Color.WHITE);


        chengeCalendarDay(dayData, textViewCell);

//      Set Range
        rang1 = new Date(CalendarActivity.sharedPreferences.getLong("rang1", 0));
        rang2 = new Date(CalendarActivity.sharedPreferences.getLong("rang2", 0));


        calendarRang1 = Calendar.getInstance();
        calendarRang1.setTimeInMillis(CalendarActivity.sharedPreferences.getLong("rang1dialog", 0));

        editTextStartDate.setText(CalendarActivity.sharedPreferences.getString("dateStringFrom", ""));
        editTextEndDate.setText(CalendarActivity.sharedPreferences.getString("dateStringTo", ""));
        rangeBoolean = CalendarActivity.sharedPreferences.getBoolean("rangeBoolean",true);

        calendarRang2 = Calendar.getInstance();
//        calendarRang2.setTimeInMillis(CalendarActivity.sharedPreferences.getLong("rang2", 0));


        if (rang1 != null && rang2 != null) {
            GridUtil.newInstance().rangCalendar(dayData, textViewCell, rang1, rang2);
        }


        GridUtil.newInstance().dayIsZero(dayData, textViewCell);


        MarkStyle style = MarkedDates.getInstance().check(dayData.getDate());
        boolean marked = style != null;
        if (marked) {
            dayData.getDate().setMarkStyle(style);

//            ShapeDrawable circleDrawable = new ShapeDrawable(new OvalShape());
//            circleDrawable.getPaint().setColor(style.getColor());

            textViewCell.setText(dayData.getText());
            textViewCell.setTextColor(Color.GRAY);
            textViewCell.setBackgroundColor(style.getColor());

        }

        covertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                myClickCell(dayData, textViewCell);

                notifyDataSetChanged();
                return false;
            }
        });
        covertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rangeBoolean) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.set(dayData.getDate().getYear(), dayData.getDate().getMonth() - 1, dayData.getDate().getDay());
                    rang1 = new Date(
                            calendarStart.get(Calendar.YEAR),
                            calendarStart.get(Calendar.MONTH),
                            calendarStart.get(Calendar.DAY_OF_MONTH)

                    );
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = sdf.format(calendarStart.getTime());
                    CalendarActivity.sharedPreferences.edit().putString("dateStringFrom", dateString).apply();
                    editTextStartDate.setText(dateString);


                    Toast.makeText(appCompatActivity, "Rang1 : "+rang1.getYear() + " : " + rang1.getMonth() + " : " + rang1.getDate(), Toast.LENGTH_SHORT).show();
                    calendarRang1.setTimeInMillis(calendarStart.getTimeInMillis());
//                Toast.makeText(appCompatActivity, dayData.getDate().getYear()+" : "+dayData.getDate().getMonth()+"  : "+dayData.getDate().getDay()+"", Toast.LENGTH_SHORT).show();
                    CalendarActivity.sharedPreferences.edit().putLong("rang1dialog", calendarRang1.getTimeInMillis()).apply();
                    CalendarActivity.sharedPreferences.edit().putLong("rang1", rang1.getTime()).apply();
                    CalendarActivity.sharedPreferences.edit().putLong("rang2", 0).apply();
                    CalendarActivity.sharedPreferences.edit().putString("dateStringTo", "").apply();

//                editTextStartDate.setText(dateString);
//                    rangeBoolean = false;
                    CalendarActivity.sharedPreferences.edit().putBoolean("rangeBoolean", false).apply();
                    notifyDataSetChanged();
                } else if (rangeBoolean == false) {

                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.set(dayData.getDate().getYear(), dayData.getDate().getMonth() - 1, dayData.getDate().getDay());
                    rang2 = new Date(
                            calendarEnd.get(Calendar.YEAR),
                            calendarEnd.get(Calendar.MONTH),
                            calendarEnd.get(Calendar.DAY_OF_MONTH)

                    );
                    if (rang1.getTime()>rang2.getTime())
                    {
                        Toast.makeText(appCompatActivity, "long", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = sdf.format(calendarEnd.getTime());
                    CalendarActivity.sharedPreferences.edit().putString("dateStringTo", dateString).apply();
                    editTextEndDate.setText(dateString);
                    Toast.makeText(appCompatActivity, "Rang2 : "+rang2.getYear() + " : " + rang2.getMonth() + " : " + rang2.getDate(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(appCompatActivity, dayData.getDate().getYear()+" : "+dayData.getDate().getMonth()+"  : "+dayData.getDate().getDay()+"", Toast.LENGTH_SHORT).show();
//                CalendarActivity.sharedPreferences.edit().putLong("rang1dialog", calendarRang1.getTimeInMillis()).apply();

                    CalendarActivity.sharedPreferences.edit().putLong("rang2", rang2.getTime()).apply();

//                    rangeBoolean = true;
                    CalendarActivity.sharedPreferences.edit().putBoolean("rangeBoolean", true).apply();
                    notifyDataSetChanged();
                }

            }
        });

        editTextStartDate.setOnClickListener(this);
        editTextEndDate.setOnClickListener(this);

        return covertView;
    }

    public void chengeCalendarDay(DayData dayData, TextView textView) {
//        toDay(dayData,textView , textViewTitle);
        GridUtil.newInstance().toDay(dayData, textView, textViewTitle);
        GridUtil.newInstance().sunDay(dayData, textView);
        GridUtil.newInstance().kmmToday(dayData, textView);
    }


    @Override
    public int getCount() {
        return arrayListDate.size();
    }


    public void myClickCell(DayData dayData, TextView textView) {
        if (dayData.getDate().getDay() == 0) {
            return;
        }
        if (GridUtil.newInstance().kmmToday(dayData, textView)) {
            return;
        }

        if (MarkedDates.getInstance().remove(dayData.getDate())) {
            return;
        }

        //save color in mark style and daydate
        dayData.getDate().setMarkStyle(Color.WHITE);

        //save day in markdates
        MarkedDates.getInstance().add(dayData.getDate());

        Toast.makeText(getContext(), dayData.getDate().getYear() + "/" + dayData.getDate().getMonth() + "/" + dayData.getDate().getDay(), Toast.LENGTH_SHORT).show();


//        ShapeDrawable circleDrawable = new ShapeDrawable(new OvalShape());
//        circleDrawable.getPaint().setColor(dayData.getDate().getMarkStyle().getColor());
//
//        textView.setText(dayData.getText());
//        textView.setBackground(circleDrawable);
//        textView.setTextColor(Color.WHITE);


    }


    @Override
    public void update(Observable o, Object arg) {
        notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editText_StartDate:

                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker =
                        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(final DatePicker view, final int year, final int month,
                                                  final int dayOfMonth) {

                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                calendar.set(year, month, dayOfMonth);
                                String dateString = sdf.format(calendar.getTime());
                                //for editText
                                CalendarActivity.sharedPreferences.edit().putString("dateStringFrom", dateString).apply();

                                //for dialog
                                calendarRang1.setTimeInMillis(calendar.getTimeInMillis());
                                //for rang
                                rang1 = new Date(year, month, dayOfMonth);

                                //for dialog
                                CalendarActivity.sharedPreferences.edit().putLong("rang1dialog", calendarRang1.getTimeInMillis()).apply();
                                //for rang
                                CalendarActivity.sharedPreferences.edit().putLong("rang1", rang1.getTime()).apply();

                                CalendarActivity.sharedPreferences.edit().putBoolean("rangeBoolean", false).apply();
                                editTextStartDate.setText(dateString);
                                notifyDataSetChanged();
                            }
                        }, year, month, day); // set date picker to current date

                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePicker.show();
                datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.editText_EndDate:

                if (calendarRang1.getTimeInMillis() == 0) {
                    Toast.makeText(appCompatActivity, "please enter a start date", Toast.LENGTH_SHORT).show();
                    return;
                }

                final Calendar calendarEnd = Calendar.getInstance();
                //for dialog End
                calendarEnd.setTimeInMillis(calendarRang1.getTime().getTime());
                final int year2 = calendarEnd.get(Calendar.YEAR);
                final int month2 = calendarEnd.get(Calendar.MONTH);
                final int day2 = calendarEnd.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerEnd =
                        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(final DatePicker view, final int year, final int month,
                                                  final int dayOfMonth) {

                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                calendarEnd.set(year, month, dayOfMonth);
                                String dateString = sdf.format(calendarEnd.getTime());
                                CalendarActivity.sharedPreferences.edit().putString("dateStringTo", dateString).apply();

//                                calendarRang2.setTimeInMillis(calendarEnd.getTimeInMillis());

                                // for rang
                                rang2 = new Date(year, month, dayOfMonth);
                                CalendarActivity.sharedPreferences.edit().putLong("rang2", rang2.getTime()).apply();

                                editTextEndDate.setText(dateString);
                                notifyDataSetChanged();
                            }
                        }, year2, month2, day2); // set date picker to current date

                if (calendarEnd != null) {
                    datePickerEnd.getDatePicker().setMinDate(calendarEnd.getTimeInMillis());
                }
                datePickerEnd.show();
                datePickerEnd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });

                break;
        }
    }


    public void calendarRangToday2(DayData dayData, TextView textView, Calendar rang1, Calendar rang2) {


//        Date rangBase = new Date(dayData.getDate().getYear(), (dayData.getDate().getMonth()) - 1, dayData.getDate().getDay());

        Calendar calendarBase = Calendar.getInstance();
        calendarBase.set(dayData.getDate().getYear(), (dayData.getDate().getMonth()) - 1, dayData.getDate().getDay());

        if (calendarBase.getTimeInMillis() >= rang1.getTimeInMillis() && calendarBase.getTimeInMillis() <= rang2.getTimeInMillis()) {

            textView.setBackgroundColor(Color.GREEN);
            textView.setText(dayData.getText());

//            Toast.makeText(getContext(), dayData.getDate().getYear() + "/" + dayData.getDate().getMonth() + "/" + dayData.getDate().getDay(), Toast.LENGTH_SHORT).show();
        }


    }

    public void rangToday(DayData dayData, TextView textView) {

        Calendar calendar = new GregorianCalendar();

        calendar.clear(Calendar.HOUR_OF_DAY);
        calendar.clear(Calendar.HOUR);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);


        DateData dateDataRange1 = new DateData(2019, 01, 28);
//                DateData dateDataRange2 = new DateData(2019, 01, 31);
        Date rang1 = new Date(dateDataRange1.getYear(), dateDataRange1.getMonth(), dateDataRange1.getDay());
//                Date rang2 = new Date(dayData.getDate().getYear(), dayData.getDate().getMonth(), dayData.getDate().getDay());
        calendar.set(rang1.getYear(), (rang1.getMonth()) - 1, rang1.getDay());


        Long time1 = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 4);


        Long time2 = calendar.getTimeInMillis();
        Long temp = time1;
        String str = "";
        while (temp < time2) {
            calendar.setTimeInMillis(temp);
            textView.setBackgroundColor(Color.BLUE);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            temp = calendar.getTimeInMillis();

            str += calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DATE) + "\r\n ";

        }
    }
}
