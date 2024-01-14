package com.unoth.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final String EXTRA_CURRENT_USER_ID = "current_id";
    private static final String EXTRA_OTHER_USER_ID = "other_id";

    private TextView textViewTitle;
    private View onlineStatus;
    private RecyclerView recyclerViewMessage;
    private EditText editTextMessage;
    private ImageView imageViewSendMessage;
    private MessageAdapter messageAdapter;
    private String currentUserId;
    private String otherUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();

        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        messageAdapter = new MessageAdapter(currentUserId);
        recyclerViewMessage.setAdapter(messageAdapter);

        //testing
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message(
                    currentUserId,
                    otherUserId,
                    "text " + i
            );
            messageList.add(message);
        }
        for (int i = 0; i < 10; i++) {
            Message message = new Message(
                    otherUserId,
                    currentUserId,
                    "text " + i
            );
            messageList.add(message);
        }
        messageAdapter.setMessages(messageList);
    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        onlineStatus = findViewById(R.id.onlineStatus);
        recyclerViewMessage = findViewById(R.id.recyclerViewMessage);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }
}