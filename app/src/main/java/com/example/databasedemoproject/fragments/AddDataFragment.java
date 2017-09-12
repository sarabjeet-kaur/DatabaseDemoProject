package com.example.databasedemoproject.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.databasedemoproject.R;
import com.example.databasedemoproject.database.DatabaseHandler;
import com.example.databasedemoproject.model.DataModel;
import com.example.databasedemoproject.utility.utility;

import java.util.Calendar;
import java.util.Date;

;
/**
 * Created by sarabjjeet on 9/8/17.
 */

public class AddDataFragment extends Fragment implements View.OnClickListener{

    private EditText et_title,et_description;
    private TextView txt_date_time;
    private Button btn_submit;
    private boolean validate=false;
    private DatabaseHandler db;
    private RadioGroup radioGroup;
    private RadioButton rb_formal,rb_informal;

    String date1,time1,title,description,type;
    private Calendar myCalendar = Calendar.getInstance();
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_data_fragment, container, false);

        initview(view);
        return view;
    }

    private void initview(View view){
        et_title=(EditText) view.findViewById(R.id.et_title);
        et_description=(EditText) view.findViewById(R.id.et_description);
        txt_date_time=(TextView) view.findViewById(R.id.txt_date_time);

        btn_submit=(Button) view.findViewById(R.id.btn_submit);
        radioGroup=(RadioGroup)view.findViewById(R.id.radioGroup);
        rb_formal=(RadioButton) view.findViewById(R.id.rb_formal);
        rb_informal=(RadioButton) view.findViewById(R.id.rb_informal);
        btn_submit.setOnClickListener(this);
        txt_date_time.setOnClickListener(this);

        db = new DatabaseHandler(this.getActivity());


    }

    private void validateData(){
        title=et_title.getText().toString();
        description=et_description.getText().toString();

        if(TextUtils.isEmpty(et_title.getText().toString())){
            et_title.setError("Please set Title");
            et_title.requestFocus();
        }else if(TextUtils.isEmpty(et_description.getText().toString())){
            et_description.setError("Pleas set Description");
            et_description.requestFocus();
        } else if(TextUtils.isEmpty(txt_date_time.getText().toString())){
            txt_date_time.setError("Please Select Date & Time");
            txt_date_time.requestFocus();
        }else{
            validate=true;
            if(validate){
                insert();
                Toast.makeText(this.getActivity(), "Event Recorded Successfully!", Toast.LENGTH_SHORT).show();
                clear();
            }
            else{
                validateData();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                validateData();
                break;
            case R.id.txt_date_time:
                picDate();
                break;
        }
    }

    public void insert(){
        Log.e("date1",date1);
        Log.e("time1",time1);
        Log.e("title",title);
        Log.e("description",description);
        if(rb_formal.isChecked()){
            type="formal";
        }
        else if(rb_informal.isChecked()){
            type="informal";
        }
       Log.e("type",type);
        db.addData(new DataModel(date1,time1,title,description,type));
    }

    private void picDate(){

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                date1=  utility.convertDateToString(myCalendar.getTime());
                picTime();
            }

        };
        DatePickerDialog datePickerDialog=new DatePickerDialog(this.getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.show();

    }

    private void picTime(){
        final TimePickerDialog.OnTimeSetListener time=new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                myCalendar.set(Calendar.MINUTE,minute);
                String timeSet = "";
                if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    timeSet = "PM";
                } else if (hourOfDay == 0) {
                    hourOfDay += 12;
                    timeSet = "AM";
                } else if (hourOfDay == 12)
                    timeSet = "PM";
                else
                    timeSet = "AM";


                String minutes = "";
                if (minute < 10)
                    minutes = "0" + minute;
                else
                    minutes = String.valueOf(minute);

                String myTimeFormat =hourOfDay+":"+minutes+" "+timeSet.toString();
                time1=myTimeFormat;
                txt_date_time.setText(date1+" "+time1);
            }
        };
        new TimePickerDialog(this.getActivity(),time, myCalendar.get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE),false).show();
        txt_date_time.setText(date1+" "+time1);

    }
    private void clear(){
        et_title.setText("");
        et_description.setText("");
        txt_date_time.setText("");
    }

}
