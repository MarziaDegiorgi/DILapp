package com.polimi.dilapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button registrationButton;
    private EditText editTextEmail;
    private  EditText editTextPassword;
    private TextView textViewSignUp;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog= new ProgressDialog(this);

        firebaseAuth= FirebaseAuth.getInstance();

        registrationButton= (Button) findViewById(R.id.buttonRegister);

        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextPassword= (EditText)findViewById(R.id.editTextPassword);

        textViewSignUp= (TextView) findViewById(R.id.textViewSignUp);

        registrationButton.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    private void registerUser(){
       String email = editTextEmail.getText().toString().trim();
       String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
        //email is empty
            Toast.makeText(this, "Inserire l'email", Toast.LENGTH_SHORT).show();
            return;
        }
       if(TextUtils.isEmpty(password)){
       //password is empty
           Toast.makeText(this, "Inserire la password", Toast.LENGTH_SHORT).show();
           return;
       }

       firebaseAuth.createUserWithEmailAndPassword(email,password);
    }

    private void loginUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Inserire l'email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Inserire la password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Benvenuto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

     @Override
    public  void onClick(View view){
        if (view == registrationButton){
            registerUser();
        }
        if(view == textViewSignUp){
            loginUser();
        }
     }

}

