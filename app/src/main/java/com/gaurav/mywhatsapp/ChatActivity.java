package com.gaurav.mywhatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    TextView messageText;
    ListView listView;

    ArrayList<String> chats;
    ArrayAdapter<String> arrayAdapter = null;
    String activeUser = "";
    DatabaseReference reference;

    String currentUser;
    User user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chats = new ArrayList<>();
        user = new User();


        messageText = findViewById(R.id.messageText);
        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, chats);



        Intent intent = getIntent();
        activeUser = intent.getStringExtra("userName");

        setTitle("Chat with " + activeUser);
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        getChat();
    }

    public void getChat(){
        reference = FirebaseDatabase.getInstance().getReference().child("users");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot ds: snapshot.child("chatInfo").getChildren()){
                    Log.i("11111111",ds.getValue().toString());
                    User user = ds.getValue(User.class);

                    chats.add(user.getMessage()+"\n"+user.getTime()+"\n"+user.getSender());

                }
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendChat(View view){
        HashMap<String, String> chatInfo = new HashMap();

        //User user = new User();
        chatInfo.put("sender", currentUser);
        chatInfo.put("recipient", activeUser);
        chatInfo.put("message", messageText.getText().toString());
        chatInfo.put("time", Calendar.getInstance().getTime().toString());



        reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("chatInfo");
        reference.child(UUID.randomUUID().toString()).setValue(chatInfo);

        arrayAdapter.notifyDataSetChanged();
        //FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("chatData").push().setValue(chatInfo);
    }
}