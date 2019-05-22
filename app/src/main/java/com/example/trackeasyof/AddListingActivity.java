package com.example.trackeasyof;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class AddListingActivity extends AppCompatActivity {
    private EditText mNameField;
    private Button mAddItem;
    private FirebaseAuth mAuth;
    private DatabaseReference mProductDatabase;
    private String name,Userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);
        mNameField = (EditText) findViewById(R.id.NameItem);
        mAddItem = (Button) findViewById(R.id.AddItem);
        mAuth = FirebaseAuth.getInstance();
        Userid = mAuth.getCurrentUser().getUid();
        mProductDatabase= FirebaseDatabase.getInstance().getReference().child("Products").child(Userid).child("Object");
        getUserInfo();

        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveUserInformation();
                Intent intent = new Intent(AddListingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getUserInfo(){
        mProductDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name")!=null)
                    {
                        name = map.get("name").toString();
                        mNameField.setText(name);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void SaveUserInformation() {
        name = mNameField.getText().toString();
        Map UserInfo = new HashMap();
        UserInfo.put("Product Name",name);
        mProductDatabase.push().setValue(UserInfo);

    }
}

