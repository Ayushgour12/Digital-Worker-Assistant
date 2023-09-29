package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText inputEmail,inputPassword;
    Button btnlogin;
    String emailPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private Button btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView btn=findViewById(R.id.textViewSignUp);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnlogin = findViewById(R.id.btnlogin);
        progressDialog = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        btnGoogle=findViewById(R.id.btnGoogle);








        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });


        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();


        if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter correct Email");
        } else if (password.isEmpty() || password.length() < 8) {
            inputPassword.setError("Enter proper password");
        } else {
            progressDialog.setMessage("Please wait while login...");
            progressDialog.setTitle("login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

              mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()) {
                         if(task.isSuccessful()){
                             progressDialog.dismiss();
                             sendUserToNextActivity();
                             Toast.makeText(LoginActivity.this ,"login Successfully" ,Toast.LENGTH_SHORT);
                         }
                         else{
                             progressDialog.dismiss();
                             Toast.makeText(LoginActivity.this , ""+task.getException(),Toast.LENGTH_SHORT).show();
                         }
                     }
                  }
              });
        }

        }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(LoginActivity.this,selection.class);
        startActivity(intent);

    }
}
