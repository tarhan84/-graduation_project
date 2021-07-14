package com.example.bluetoothcontrol;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Vibrator;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class ScrollingActivity extends AppCompatActivity {


    FloatingActionButton fab;

    private Set<BluetoothDevice> pairedDevices;
    private Set<BluetoothDevice> availableDevices;


   ListView paired_list;
    ListView available_list;
    BluetoothAdapter myBluetooth;
    Vibrator vib;
    FloatingActionButton bt_onOff;
    public static String EXTRA_ADRESS;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        myBluetooth=BluetoothAdapter.getDefaultAdapter();
        paired_list=(ListView)findViewById(R.id.bt_paired_devices);
        vib=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.mipmap.bt);
        //paired_list.setDivider(null);
        listPairDevices();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                On_Off();
            }
        });

        if(myBluetooth.isEnabled()){
            fab.setImageResource(R.mipmap.asdf);
        }
        else
            fab.setImageResource(R.mipmap.ghjk);

    }

    private void listPairDevices() {
        pairedDevices=myBluetooth.getBondedDevices();

        ArrayList list=new ArrayList();
        for(BluetoothDevice bt:pairedDevices){
            list.add(bt.getName()+"\n"+bt.getAddress());
        }
        final ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        paired_list.setAdapter(adapter);
        paired_list.setOnItemClickListener(selectDevice);

    }


    public void On_Off() {
        if(!myBluetooth.isEnabled()){

            myBluetooth.enable();
            while (!myBluetooth.isEnabled()){

            }
            fab.setImageResource(R.mipmap.asdf);
            vib.vibrate(600);
            listPairDevices();
            Toast.makeText(ScrollingActivity.this, "Turning on Bluetooth...", Toast.LENGTH_SHORT).show();

        }

        else{
            myBluetooth.disable();
            fab.setImageResource(R.mipmap.ghjk);
            vib.vibrate(600);
            Toast.makeText(ScrollingActivity.this, "Turning off Bluetooth..", Toast.LENGTH_SHORT).show();

        }
    }
    public AdapterView.OnItemClickListener selectDevice=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent I=new Intent(getApplicationContext(),popUp.class);
            String info=((TextView)view).getText().toString();
            String adress=info.substring(info.length()-17);
            I.putExtra(EXTRA_ADRESS,adress);
            startActivity(I);
        }
    };
}
