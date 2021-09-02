package com.example.project4_2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button button1;
   ImageButton button2;
    //Button button3;
    Button button4;
    Button vv;
    Button btnBlt;
    private BluetoothSPP bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bt = new BluetoothSPP(this);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (ImageButton) findViewById(R.id.button2);
        button4 = (Button) findViewById(R.id.button4);
        vv = findViewById(R.id.vv);
        btnBlt = (Button) findViewById(R.id.btnBlt);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.119.go.kr/Center119/main.do"));
                startActivity((mIntent));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/119"));
                startActivity((mIntent));
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.safetv.go.kr/GenCMS/gencms/cmsMng.d" +
                                "o?sub_num=244&BBS_STATE=view&BOARD_IDX=1642&gcode=" +
                                "CG0000014&pageNo=1"));
                startActivity((mIntent));
            }
        });
        if(!bt.isBluetoothAvailable()){
            Toast.makeText(getApplicationContext(),"Bluetooth is not available",
                    Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            TextView txtEm = findViewById(R.id.txtEmergency);
            @Override
            public void onDataReceived(byte[] data, String message) {
                if((Integer.parseInt(message) == 1)) {
                    txtEm.setText("위험해요!");
                }else if((Integer.parseInt(message) == 0)) {
                    txtEm.setText("안전해요!");
                }
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext(),"Connect to "+
                        name + "\n" + address, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeviceDisconnected() {
                Toast.makeText(getApplicationContext(),"Connect Lost",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeviceConnectionFailed() {
                Toast.makeText(getApplicationContext(), "Unable to connect",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnBlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bt.getServiceState() == BluetoothState.STATE_CONNECTED){
                    bt.disconnect();
                }else{
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }
    public void onDestroy(){
        super.onDestroy();
        bt.stopService();
    }
    public void onStart(){
        super.onStart();
        if(bt.isBluetoothEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        }else{
            if(!bt.isServiceAvailable()){
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            }
        }
    }
    public void setup(){
        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt.send("Text", true);
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE){
            if(resultCode == Activity.RESULT_OK){
                bt.connect(data);
            }
        }else if(requestCode == BluetoothState.REQUEST_ENABLE_BT){
            if(resultCode == Activity.RESULT_OK){
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            }else{
                Toast.makeText(getApplicationContext(),"Bluetooth was not enabled",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}