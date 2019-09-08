package com.example.latihanlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn;
    private EditText emailTxt;
    private EditText passTxt;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTxt = findViewById(R.id.emailTxt);
        passTxt = findViewById(R.id.passTxt);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailTxt.getText().toString().trim().matches(emailPattern)){
                    if (passTxt.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(),"Password is empty !",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        openArctivityHome();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Input email and password !",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openArctivityHome(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}