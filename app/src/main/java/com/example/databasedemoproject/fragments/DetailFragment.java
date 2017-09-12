package com.example.databasedemoproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.databasedemoproject.R;
import com.example.databasedemoproject.adapter.CustomAdapter;
import com.example.databasedemoproject.database.DatabaseHandler;
import com.example.databasedemoproject.model.DataModel;

import java.util.List;

/**
 * Created by sarabjjeet on 9/11/17.
 */

public class DetailFragment extends Fragment implements View.OnClickListener{
    private TextView txt_formal,txt_informal,txt_current_date;
    private Button btn_add;
    private DatabaseHandler db;
    private ListView listView;

    String type,date;
    List<DataModel> events;
    CustomAdapter customAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);

        initview(view);
        return view;

    }

   private void initview(View view){
       txt_formal=(TextView)view.findViewById(R.id.txt_formal);
       txt_informal=(TextView)view.findViewById(R.id.txt_informal);
       txt_current_date=(TextView)view.findViewById(R.id.txt_current_date);
       btn_add=(Button) view.findViewById(R.id.btn_add);
       listView=(ListView) view.findViewById(R.id.listView);
       btn_add.setOnClickListener(this);
       txt_formal.setOnClickListener(this);
       txt_informal.setOnClickListener(this);
       db = new DatabaseHandler(this.getActivity());

       type="formal";

       Bundle bundle=getArguments();
       date=bundle.getString("date");

       txt_current_date.setText(date);
       Log.e("date in detail frag",date);

       events = db.getAllDataWithDateType(date,type);
       customAdapter=new CustomAdapter(events,this.getActivity());
       listView.setAdapter(customAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                fragmentTransaction.setCustomAnimations(R.anim.right_in,R.anim.left_out);
                AddDataFragment addDataFragment=new AddDataFragment();
                fragmentTransaction.add(R.id.fragment_container,addDataFragment);
                fragmentTransaction.commit();
                break;
            case R.id.txt_formal:
                type="formal";
                txt_formal.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryDark,null));
                txt_informal.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.backgroundColor,null));
                events = db.getAllDataWithDateType(date,type);
                customAdapter.clear();
                customAdapter.addAll(events);
                customAdapter.notifyDataSetChanged();

                break;
            case R.id.txt_informal:
                txt_formal.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.backgroundColor,null));
                txt_informal.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryDark,null));
                type="informal";
                events = db.getAllDataWithDateType(date,type);
                customAdapter.clear();
                customAdapter.addAll(events);
                customAdapter.notifyDataSetChanged();
                break;
        }
    }


}
