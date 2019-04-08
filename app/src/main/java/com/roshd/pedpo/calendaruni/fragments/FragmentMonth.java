package com.roshd.pedpo.calendaruni.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;


import com.roshd.pedpo.calendaruni.R;
import com.roshd.pedpo.calendaruni.adapter.AdapterGrid;
import com.roshd.pedpo.calendaruni.adapter.AdapterGridTitle;
import com.roshd.pedpo.calendaruni.vo.MarkedDates;
import com.roshd.pedpo.calendaruni.vo.MonthData;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentMonth extends Fragment  {

    private MonthData monthData;
    private GridView gridView;
    private GridView gridViewTitle;
    private AdapterGrid adapterGrid;
    private AdapterGridTitle adapterGridTitle;
    private TextView textViewTitle;
    private EditText editTextStartDate , editTextEndDate ;
    private Button btn_show ;

    public static int colorDate = Color.parseColor("#ffff4444");

    public void setData(MonthData monthData) {
        this.monthData = monthData;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_month, container, false);

        gridView = view.findViewById(R.id.gridview);
        gridViewTitle = view.findViewById(R.id.gridviewTitle);
        textViewTitle = view.findViewById(R.id.textTitleDate);
        editTextEndDate = view.findViewById(R.id.editText_EndDate);
        editTextStartDate = view.findViewById(R.id.editText_StartDate);
        btn_show = view.findViewById(R.id.btn_show);


        String[] week = getActivity().getResources().getStringArray(R.array.week);
        ArrayList arrayListTitle = new ArrayList(Arrays.asList(week));



        adapterGridTitle = new AdapterGridTitle(getContext(), 1, arrayListTitle);
        gridViewTitle.setAdapter(adapterGridTitle);

        textViewTitle.setText(monthData.getDate().getYear() + "/" + monthData.getDate().getMonthString() + "/" + monthData.getDate().getDayString());

//        Toast.makeText(getContext(), monthData.getLastMonthTotalDay()+"", Toast.LENGTH_SHORT).show();

        adapterGrid = new AdapterGrid(getContext(), (AppCompatActivity) getActivity(), 1, monthData.getData() , textViewTitle ,editTextStartDate , editTextEndDate , btn_show);
        gridView.setAdapter(adapterGrid);
//        adapterGrid.notifyDataSetChanged();
//
//        adapterGrid.notifyDataSetInvalidated();


        return view;
    }

    @Override
    public void onResume() {
        adapterGrid.notifyDataSetChanged();
        MarkedDates.getInstance().refresh();
        super.onResume();
    }
}
