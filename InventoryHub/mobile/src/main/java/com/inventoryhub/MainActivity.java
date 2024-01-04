package com.inventoryhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText pin_number;
    Button continue_button;

    public static String pin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pin_number = (EditText) findViewById(R.id.pin_number);
        continue_button = (Button) findViewById(R.id.continue_button);

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin_number.getText().toString().length()<8){
                    Toast.makeText(MainActivity.this, "Please write complete pin", Toast.LENGTH_SHORT).show();
                }else{
                    pin = pin_number.getText().toString();
                    startActivity(new Intent(MainActivity.this, MobileLanding.class));
                }
            }
        });


    }
}