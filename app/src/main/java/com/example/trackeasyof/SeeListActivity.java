package com.example.trackeasyof;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class SeeListActivity extends AppCompatActivity {

    private static final String TAG = "SeeListActivity";

    private  static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_list);

        if(isServicesOK()){
            init();
        }

    }

    private void init(){
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeListActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(SeeListActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make requests
            Log.d(TAG, "isServicesOK: Google play services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            // an error occured but it's possible to resolve it
            Log.d(TAG, "isServicesOK: an error occured BUT we can fix it");
            Dialog Dialog = GoogleApiAvailability.getInstance().getErrorDialog(SeeListActivity.this, available, ERROR_DIALOG_REQUEST);
            Dialog.show();
        }
        else{
            Toast.makeText(this,"You can't make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
