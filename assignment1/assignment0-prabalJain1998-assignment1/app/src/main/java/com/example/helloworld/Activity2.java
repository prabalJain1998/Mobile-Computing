package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "State of Activity2 changed from Create to Start", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of Activity2 changed from Create to Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "State of Activity2 changed from Pause to Stop", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of Activity2 changed from Pause to Stop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "State of Activity2 changed from Pause to Resume", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of Activity2 changed from Pause to Resume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Toast.makeText(this, "State of Activity2 changed to Pause", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of Activity2 changed to Pause");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Toast.makeText(this, "State of Activity2 changed from Stop to Restart", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of Activity2 changed from Stop to Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "State of Activity2 changed from Stop to Destroy", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of Activity2 changed from Stop to Destroy");
    }



    public int value = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_2);


        Toast.makeText(this, "State of Activity2 changed from Stop to Destroy", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of Activity2 changed from Stop to Destroy");




        Intent intent = getIntent();
        String name = intent.getStringExtra("personName");
        Log.i("INFO", "fetched name from Main Activity Successfully");
        TextView text = (TextView) findViewById(R.id.textView);

        boolean isChecked1 = this.getIntent().getBooleanExtra("checkBoxValue1", false);
        boolean isChecked2 = this.getIntent().getBooleanExtra("checkBoxValue2", false);
        boolean isChecked3 = this.getIntent().getBooleanExtra("checkBoxValue3", false);
        boolean isChecked4 = this.getIntent().getBooleanExtra("checkBoxValue4", false);
        boolean isChecked5 = this.getIntent().getBooleanExtra("checkBoxValue5", false);


        String temp = "";
        if(isChecked1){
            temp += "\n     Wearing a mask when outside\n";
        }
        if(isChecked2){
            temp += "\n     Washing hands frequently\n";
        }
        if(isChecked3){
            temp += "\n     Maintaining 2 feet distance\n";
        }
        if(isChecked4){
            temp += "\n     Covering nose and mouth while sneezing and coughing\n";
        }
        if(isChecked5){
            temp += "\n     Taking health diets\n";
        }
        text.setText("  Hi "+name+"\n   you have selected\n"+temp);

        Button check = findViewById(R.id.checkButton);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("INFO", "check Button is working");
                if( isChecked1 && isChecked2 && isChecked3 && isChecked4 && isChecked5){
                    Toast.makeText(Activity2.this, "Congo! You are Safe", Toast.LENGTH_SHORT).show();
                    value = 1;
                }else{
                    Toast.makeText(Activity2.this, "Please! Take Precautions!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("INFO", "back Button is working");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", value);
                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });



    }
}