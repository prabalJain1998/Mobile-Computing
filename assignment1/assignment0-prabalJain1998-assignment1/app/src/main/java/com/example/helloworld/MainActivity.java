package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView returnTextView;
    private EditText personName;

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "State of MainActivity changed from Create to Start", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of MainActivity changed from Create to Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "State of MainActivity changed from Pause to Stop", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of MainActivity changed from Pause to Stop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "State of MainActivity changed from Pause to Resume", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of MainActivity changed from Pause to Resume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Toast.makeText(this, "State of MainActivity changed to Pause", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of MainActivity changed to Pause");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Toast.makeText(this, "State of MainActivity changed from Stop to Restart", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of MainActivity changed from Stop to Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "State ofMainActivity changed from Stop to Destroy", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of MainActivity changed from Stop to Destroy");
    }

    public void onCheckboxClicked(View view) {

        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        Log.i("INFO", "CheckBox is Clicked");
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox1:
                if (checked){
                    Toast.makeText(this, "Great", Toast.LENGTH_SHORT).show();
                }
                else{
                    break;
                }
            case R.id.checkBox2:
                if (checked){
                    Toast.makeText(this, "Great", Toast.LENGTH_SHORT).show();
                }
                else {
                    break;
                }
            case R.id.checkBox3:
                if (checked){
                    Toast.makeText(this, "Great", Toast.LENGTH_SHORT).show();
                }
                else {
                    break;
                }
            case R.id.checkBox4:
                if (checked){
                    Toast.makeText(this, "Great", Toast.LENGTH_SHORT).show();
                }
                else {
                    break;
                }
            case R.id.checkBox5:
                if (checked){
                    Toast.makeText(this, "Great", Toast.LENGTH_SHORT).show();
                }
                else {
                    break;
                }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_main);
        Toast.makeText(this, "State of MainActivity changed from Create to Start", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "State of MainActivity changed from Create to Start");


        personName = (EditText) findViewById(R.id.nameTextView);
        Log.i("INFO", "personName is Fetched!");
        returnTextView = (TextView) findViewById(R.id.returnTextView);

        Button submit = findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("INFO", "Submit Button is working");
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                String name = personName.getText().toString();
                intent.putExtra("personName", name);
                CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
                boolean isChecked1 = checkBox1.isChecked();
                CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
                boolean isChecked2 = checkBox2.isChecked();
                CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
                boolean isChecked3 = checkBox3.isChecked();
                CheckBox checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
                boolean isChecked4 = checkBox4.isChecked();
                CheckBox checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
                boolean isChecked5 = checkBox5.isChecked();
                intent.putExtra("checkBoxValue1", isChecked1);
                intent.putExtra("checkBoxValue2", isChecked2);
                intent.putExtra("checkBoxValue3", isChecked3);
                intent.putExtra("checkBoxValue4", isChecked4);
                intent.putExtra("checkBoxValue5", isChecked5);
                startActivityForResult(intent, 1);
            }
        });

        Button clear = findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("INFO", "Clear Button is working");
                returnTextView = findViewById(R.id.returnTextView);
                returnTextView.setText("");
                personName.setText("");
                CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
                checkBox1.setChecked(false);

                CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
                checkBox2.setChecked(false);

                CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
                checkBox3.setChecked(false);

                CheckBox checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
                checkBox4.setChecked(false);

                CheckBox checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
                checkBox5.setChecked(false);
            }
        });
        if(savedInstanceState != null) {
            String val = savedInstanceState.getString("value");
            returnTextView = findViewById(R.id.returnTextView);
            returnTextView.setText(val);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int result = data.getIntExtra("result",0);
                if(result == 0){
                    returnTextView = findViewById(R.id.returnTextView);
                    returnTextView.setText("UNSAFE");
                    returnTextView.setTextColor(Color.GREEN);
                }else{
                    returnTextView = findViewById(R.id.returnTextView);
                    returnTextView.setText("SAFE");
                    returnTextView.setTextColor(Color.GREEN);
                }

            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        returnTextView = findViewById(R.id.returnTextView);
        String val = returnTextView.getText().toString();
        outState.putString("value",val);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}