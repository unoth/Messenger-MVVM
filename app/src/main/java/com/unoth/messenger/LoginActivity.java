package com.unoth.messenger;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgotPassword;
    private TextView textViewRegister;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel();
        setupClickListener();
    }

    private void setupClickListener() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                viewModel.login(email, password);
            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ResetPasswordActivity.newIntent(
                        LoginActivity.this,
                        editTextEmail.getText().toString().trim()
                );
                startActivity(intent);
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegistrationActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(LoginActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
    }
}