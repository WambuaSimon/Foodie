package com.foodie.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.foodie.R;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class Acitivity_CustomerLogin extends AppCompatActivity {
    TextView code;
    EditText phone_no, name, location, location_description;
    Button done;
    String code_txt, phone_no_txt, name_txt, location_txt, location_description_txt;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity_customer_login);

        code = findViewById(R.id.code);
        phone_no = findViewById(R.id.phone_no);
        location = findViewById(R.id.location);
        name = findViewById(R.id.name);
        location_description = findViewById(R.id.location_description);
        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                code_txt = code.getText().toString();
                phone_no_txt = phone_no.getText().toString();
                name_txt = name.getText().toString();
                location_txt = location.getText().toString();
                location_description_txt = location_description.getText().toString();



                if (phone_no_txt.isEmpty() || phone_no_txt.length() < 9) {
                    phone_no.setError("Enter Valid Phone Number");
                } else if (name_txt.isEmpty()) {
                    name.setError("Enter Your Name");
                } else if (location_txt.isEmpty()) {
                    location.setError("Enter Your Location");
                }
                phoneNumber = code_txt + phone_no_txt;


                Intent intent = new Intent(Acitivity_CustomerLogin.this, Activity_CustomerVerification.class);
                intent.putExtra("phonenumber", phoneNumber);
                intent.putExtra("name", name_txt);
                intent.putExtra("location", location_txt);
                intent.putExtra("location_description", location_description_txt);
                startActivity(intent);
            }
        });




    }
    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, Activity_ViewFoods.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }
}
