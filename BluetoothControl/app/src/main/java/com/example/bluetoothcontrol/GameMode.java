package com.example.bluetoothcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class GameMode extends AppCompatActivity {
    public static String adress;
    static final UUID mUUID=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    TextView temp;
    TextView disc;
    TextView powerLevel;
    TextView position;
    BluetoothAdapter myBluetooth;
    BluetoothDevice device;
    BluetoothSocket btSocket;
    ImageButton up_bt;
    ImageButton down_bt;
    ImageButton left_bt;
    ImageButton right_bt;
    ImageButton servo_l;
    ImageButton servo_r;
    OutputStream outputStream;
    ImageButton bt_connect;
    ImageButton far_on_off;
    ImageButton sellector;
    ImageButton hiz_arttir;
    ImageButton hiz_azalt;
    ImageButton korna;
    String sicaklik;
    String mesafe;


    InputStream inputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode2);
        Intent I=getIntent();
        adress=I.getStringExtra(popUp.EXTRA_ADRESS);
        temp=(TextView)findViewById(R.id.temp_txt);
        disc=(TextView)findViewById(R.id.distance_txt);
        myBluetooth=BluetoothAdapter.getDefaultAdapter();
        device=myBluetooth.getRemoteDevice(adress);

        up_bt=(ImageButton)findViewById(R.id.up);
        down_bt=(ImageButton)findViewById(R.id.down);
        left_bt=(ImageButton)findViewById(R.id.left);
        right_bt=(ImageButton)findViewById(R.id.right);

        servo_l=(ImageButton)findViewById(R.id.s_left);
        servo_r=(ImageButton)findViewById(R.id.s_right);


        sellector=(ImageButton)findViewById(R.id.sell);
        far_on_off=(ImageButton)findViewById(R.id.far);

        hiz_arttir=(ImageButton)findViewById(R.id.hiz_art);
        hiz_azalt=(ImageButton) findViewById(R.id.hiz_az);

        bt_connect=(ImageButton)findViewById(R.id.bt_con) ;
        korna=(ImageButton)findViewById(R.id.krn);

        powerLevel=(TextView)findViewById(R.id.power_txt);

        position=(TextView)findViewById(R.id.pos_txt);






        try {
            btSocket=device.createInsecureRfcommSocketToServiceRecord(mUUID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        hiz_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    send_data("(");
                    String tmp="%"+readData("5");
                    powerLevel.setText(tmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        hiz_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    send_data(")");
                    String tmp="%"+readData("5");
                    powerLevel.setText(tmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        up_bt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_UP&&btSocket.isConnected()){//kaldırıldığında
                    try {
                        send_data("t");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&btSocket.isConnected()){//basıldığında
                    try {
                        send_data("i");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                return false;
            }

        });

        up_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!btSocket.isConnected()){
                    Toast.makeText(GameMode.this, "Not Connected to a Device!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        down_bt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_UP&&btSocket.isConnected()){//kaldırıldığında
                    try {
                        send_data("t");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&btSocket.isConnected()){//basıldığında
                    try {
                        send_data("g");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                return false;
            }
        });

        down_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!btSocket.isConnected()){
                    Toast.makeText(GameMode.this, "Not Connected to a Device!", Toast.LENGTH_SHORT).show();

                }
            }
        });


        left_bt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_UP&&btSocket.isConnected()){//kaldırıldığında
                    try {
                        send_data("t");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&btSocket.isConnected()){//basıldığında
                    try {
                        send_data("l");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });


        left_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!btSocket.isConnected()){
                    Toast.makeText(GameMode.this, "Not Connected to a Device!", Toast.LENGTH_SHORT).show();

                }
            }
        });


        right_bt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_UP&&btSocket.isConnected()){//kaldırıldığında
                    try {
                        send_data("t");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&btSocket.isConnected()){//basıldığında
                    try {
                        send_data("r");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });


        right_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!btSocket.isConnected()){
                    Toast.makeText(GameMode.this, "Not Connected to a Device!", Toast.LENGTH_SHORT).show();

                }
            }
        });


        servo_l.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_UP&&btSocket.isConnected()){//kaldırıldığında
                    try {
                        send_data("z");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&btSocket.isConnected()){//basıldığında
                    try {
                        send_data("y");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });


        servo_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!btSocket.isConnected()){
                    Toast.makeText(GameMode.this, "Not Connected to a Device!", Toast.LENGTH_SHORT).show();

                }
            }
        });


        servo_r.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_UP&&btSocket.isConnected()){//kaldırıldığında
                    try {
                        send_data("z");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&btSocket.isConnected()){//basıldığında
                    try {
                        send_data("x");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });


        sellector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_UP&&btSocket.isConnected()){//kaldırıldığında
                    try {
                        send_data("-");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&btSocket.isConnected()){//basıldığında
                    try {
                        send_data("p");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });

        far_on_off.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {



                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&btSocket.isConnected()){//basıldığında
                    try {
                        send_data("q");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });


        korna.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_UP&&btSocket.isConnected()){//kaldırıldığında
                    try {
                        send_data(".");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&btSocket.isConnected()){//basıldığında
                    try {
                        send_data("*");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String tmp=readData("s");
                    temp.setText(tmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        disc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String tmp=readData("m");
                    disc.setText(tmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });






        servo_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!btSocket.isConnected()){
                    Toast.makeText(GameMode.this, "Not Connected to a Device!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!btSocket.isConnected()){

                    btConnect();

                    if(btSocket.isConnected()){

                        bt_connect.setImageResource(R.mipmap.bt_con);
                        String tmp= null;
                        try {
                            tmp="%"+readData("5");
                            powerLevel.setText(tmp);
                            tmp = readData("s");
                            temp.setText(tmp);
                            tmp = readData("m");
                            disc.setText(tmp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                else{
                    try {
                        btSocket.close();
                        bt_connect.setImageResource(R.mipmap.bt_dis);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });





    }



    public void send_data(String data) throws IOException {
        outputStream=btSocket.getOutputStream();
        byte[] tt=data.getBytes();
        outputStream.write(tt);
    }
    public void btConnect(){
        try {

            btSocket=device.createInsecureRfcommSocketToServiceRecord(mUUID);
            btSocket.connect();


        } catch (IOException e) {
            this.finish();
        }
    }

   public String readData(String value) throws IOException {
        InputStream inputStream=btSocket.getInputStream();
        byte buffer[]=new byte[20];
        send_data(value);
        int dat=inputStream.read(buffer);
       String readMessage = new String(buffer, 0, dat);
        return readMessage;
   }

}
