package com.example.mylocationlab06;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;
    private TrackGPS gps;

    private static final int REQUEST_CODE_PERMISSION = 1;
    String[] nPermission = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onDestroy(){
        super.onDestroy();
        gps.stopGPS();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 23){

                    if(checkSelfPermission((nPermission[0])) !=PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this, nPermission, REQUEST_CODE_PERMISSION);
                        return;
                    }
                }


                gps = new TrackGPS(MainActivity.this);
                if(gps.canGetLocation()){
                    editText = (EditText) findViewById((R.id.editText));
                    editText.setText("Latitude: " + gps.getLatitude() + ":Longitude: " + gps.getLongitude() + ":Altitude: " + gps.getAltitude());
                } else{
                    gps.showAlert();
                }


            }
        });
    }
}