package com.unoth.messenger;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends ViewModel {
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceUser;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<List<User>> users = new MutableLiveData<>();

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public void setUserOnline(Boolean isOnline) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) {
            return;
        }
        databaseReferenceUser.child(firebaseUser.getUid()).child("online").setValue(isOnline);
    }

    public UsersViewModel() {
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUser = firebaseDatabase.getReference("user");
        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser == null) {
                    return;
                }
                List<User> userFromDb = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user == null) {
                        return;
                    }
                    if (!user.getId().equals(currentUser.getUid())) {
                        userFromDb.add(user);
                    }
                }
                users.setValue(userFromDb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("UsersViewModel", error.getMessage());
            }
        });
    }

    public void logout() {
        auth.signOut();
    }

}
