package com.example.databasedemoproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.databasedemoproject.R;
import com.example.databasedemoproject.database.DatabaseHandler;
import com.example.databasedemoproject.model.DataModel;
import com.example.databasedemoproject.utility.utility;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.databasedemoproject.R.id.custom_calendar;

/**
 * Created by sarabjjeet on 9/8/17.
 */

public class CalendarFragment extends Fragment implements View.OnClickListener, CalendarListener {

    private Button btn_add;
    private CustomCalendarView customCalendarView;
    private DatabaseHandler db;
    private TextView txt_current_date;
    private String date1;
    private List<DataModel> events;
    private List<String> list_dates;
    private Calendar myCalendar = Calendar.getInstance(Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_add = (Button) view.findViewById(R.id.btn_add);
        customCalendarView = (CustomCalendarView) view.findViewById(custom_calendar);
        txt_current_date = (TextView) view.findViewById(R.id.txt_current_date);
        btn_add.setOnClickListener(this);
        ImageView img_next = (ImageView) view.findViewById(R.id.rightButton);
        ImageView img_prev = (ImageView) view.findViewById(R.id.leftButton);

        img_next.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.forward_month));
        img_prev.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.back_month));
        customCalendarView.setCalendarListener(this);
        db = new DatabaseHandler(this.getActivity());

        List<String> dates = db.getDate();
        for (String cn : dates) {
            Log.e("Contacts: ", cn);
        }

      //  String myDateFormat = "dd MMM yyyy";
       // SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat);
      date1=  utility.convertDateToString(myCalendar.getTime());
       // date1 = sdf.format(myCalendar.getTime());

        txt_current_date.setText("Today," + date1);

        events = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                fragmentTransaction.setCustomAnimations(R.anim.right_in,R.anim.left_out);
                AddDataFragment addDataFragment = new AddDataFragment();
                fragmentTransaction.add(R.id.fragment_container, addDataFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onDateSelected(Date date) {
        date1=utility.convertDateToString(date);
        txt_current_date.setText(date1);

        Bundle bundle = new Bundle();
        bundle.putString("date", date1);
        Log.e("date in calendar frag", date1);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.anim.right_in,R.anim.left_out);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_container, detailFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onMonthChanged(Date time) {
    }

    private void markEventsOnCalender(){
        list_dates = db.getDate();
        //adding calendar day decorators
        List<DayDecorator> decorators = new ArrayList<>();
        decorators.add(new DayDecorator() {
            @Override
            public void decorate(DayView dayView) {
                if (utility.convertDateToString(dayView.getDate())
                        .equalsIgnoreCase(utility.convertDateToString(myCalendar.getTime()))){
                    dayView.setBackgroundColor(ContextCompat.getColor(
                            getContext(), R.color.colorPrimaryDark
                    ));

                    dayView.setTextColor(ContextCompat.getColor(
                            getContext(), R.color.white));
                }
                for(String saved_date: list_dates){
                    if (saved_date.equalsIgnoreCase(utility.convertDateToString(dayView.getDate()))){
                        dayView.setBackgroundColor(ContextCompat.getColor(
                                getContext(), R.color.backgroundColor
                        ));
                    }
                    else if (utility.convertDateToString(dayView.getDate())
                            .equalsIgnoreCase(utility.convertDateToString(myCalendar.getTime()))){
                        dayView.setBackgroundColor(ContextCompat.getColor(
                                getContext(), R.color.colorPrimaryDark
                        ));
                        dayView.setTextColor(ContextCompat.getColor(
                                getContext(), R.color.white));
                    }
                }
            }
        });
        customCalendarView.setDecorators(decorators);
        customCalendarView.refreshCalendar(myCalendar);
    }

    @Override
    public void onResume() {
        super.onResume();
        markEventsOnCalender();
        Log.e("on","resume");

    }
}
