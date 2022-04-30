package com.example.helloworld;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class ListAdapter extends RecyclerView.Adapter {

    Context context;
    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item ,parent,false);
        return new ListViewHolder(view);

    }

    public ListAdapter(Context context){
        this.context = context;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        ((ListViewHolder)holder).bindView(position);
        ((ListViewHolder) holder).linearLayoutUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] objects = new String[6];
                objects[0] = Student.name[position];
                objects[1] = Student.rollno[position];
                objects[2] = Student.dept;
                objects[3] = Student.branch[position];

                Bundle bundleObject = new Bundle();
                bundleObject.putStringArray("objects",objects);
                bundleObject.putInt("position",position);
                DataFragment dataFragment = new DataFragment();
                dataFragment.setArguments(bundleObject);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.placeholder,dataFragment).addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {

        return 30;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayoutUI;
        TextView studentNameID ;
        TextView studentRollnoID;


        public ListViewHolder(@NonNull View view) {
            super(view);
            studentRollnoID = view.findViewById(R.id.studentRollnoID);
            linearLayoutUI = view.findViewById(R.id.student_item_linearID);
            studentNameID = view.findViewById(R.id.studentNameID);
        }

        public void bindView(int position)
        {
            studentRollnoID.setText(Student.rollno[position]);
            studentNameID.setText(Student.name[position]);

        }





    }
}
