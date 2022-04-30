package com.example.helloworld;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DataFragment extends Fragment {
    TextView rollno;
    EditText branch,name,deptartment;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_data, container, false);


        deptartment = (EditText)view.findViewById(R.id.dept);
        rollno = (TextView) view.findViewById(R.id.rollno);
        name = (EditText) view.findViewById(R.id.name);
        branch = (EditText)view.findViewById(R.id.branch);
        String[] data = this.getArguments().getStringArray("objects");
        int position = this.getArguments().getInt("position");

        name.setText(data[0]);
        rollno.setText(data[1]);
        deptartment.setText(data[2]);
        branch.setText(data[3]);

        button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Your Details Updated!!", Toast.LENGTH_SHORT).show();
                Student.name[position] = name.getText().toString();
                Student.dept = deptartment.getText().toString();
                Student.branch[position] = branch.getText().toString();
            }
        });




        return view;
    }
}