package com.unoth.messenger;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("pezduk@gamil.com", "qwerty")
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            Log.d(TAG, "User authorized: " + user.getEmail());
                        } else {
                            Log.d(TAG, "User not authorized");
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.toString());
                    }
                });


    }
}