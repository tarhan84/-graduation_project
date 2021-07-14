package com.example.bluetoothcontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class VoiceControl extends AppCompatActivity {

    public static String adress;
    public ImageButton konus;
    public EditText multiline_txt;
    ListView tahmin_listesi;
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter myBluetooth;
    BluetoothDevice device;
    BluetoothSocket btSocket;
    OutputStream outputStream;
    private TextToSpeech textToSpeech;
    TextView dist_txt;
    TextView temp_txt;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    TextView editText;
    int counter = 0;
    /////////////////////////
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    boolean connectionstate = false;
    byte BtAdapterSayac = 0;
    Button btnOpen, btnFind, btnAyarlar, btnSendData;
    TextView ext;
    EditText edTxt;
    String sGelenVeri;
    boolean bisChecked = false;
    ///////////////////////////////////////


    public static final int request_code_voice = 1;
    public SpeechRecognizer recognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_control);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w=dm.widthPixels;
        int h=dm.heightPixels;
        getWindow().setLayout((int)(w*.8),(int)(h*.7));
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);
        Intent I = getIntent();
        adress = I.getStringExtra(popUp.EXTRA_ADRESS);
        checkPermission();
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        device = myBluetooth.getRemoteDevice(adress);
        final Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        editText = findViewById(R.id.txt);
        Switch bt_on_off = (findViewById(R.id.con_sw));
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        ext=(TextView)findViewById(R.id.ext) ;
        ext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    btSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null) {
                   // editText.setText(matches.get(0));
                    try {
                        if (matches.get(0).equalsIgnoreCase("ileri")) {
                            editText.setText(matches.get(0));
                            send_data("i");
                        }
                        if (matches.get(0).equalsIgnoreCase("dur")) {
                            editText.setText(matches.get(0));
                            send_data("t");
                        }
                        if (matches.get(0).equalsIgnoreCase("sağ")||matches.get(0).equalsIgnoreCase("sağ ol")) {
                            editText.setText("sağ");
                            send_data("R");
                        }
                        if (matches.get(0).equalsIgnoreCase("sol")) {
                            editText.setText(matches.get(0));
                            send_data("L");
                        }
                        if (matches.get(0).equalsIgnoreCase("geri")) {
                            editText.setText(matches.get(0));
                            send_data("g");
                        }

                        if (matches.get(0).equalsIgnoreCase("mesafe")) {
                            editText.setText(matches.get(0));
                            String mesafe = readData("m");

                            String data = "Mesafe" + mesafe + "santimetre";
                            int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                        }
                        if (matches.get(0).equalsIgnoreCase("sıcaklık")) {
                            editText.setText(matches.get(0));
                            String sic = readData("s");
                            String data = "Sıcaklık" + sic + "derece";
                            int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                        }
                        if (matches.get(0).equalsIgnoreCase("ışık aç")) {
                            editText.setText(matches.get(0));
                            send_data("Z");
                        }
                        if (matches.get(0).equalsIgnoreCase("ışık kapat")) {
                            editText.setText(matches.get(0));
                            send_data("X");
                        }
                        if (matches.get(0).equalsIgnoreCase("korna çal")) {
                            editText.setText(matches.get(0));
                            send_data("C");
                        }
                        else{


                        }

                    } catch (Exception e) {

                    }
                    matches.clear();

                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


        bt_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btConnect();
                    vib.vibrate(100);
                } else {
                    try {
                        btSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        try {
            btSocket = device.createInsecureRfcommSocketToServiceRecord(mUUID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.ROOT);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }


    public void btConnect() {
        try {

            btSocket = device.createInsecureRfcommSocketToServiceRecord(mUUID);
            btSocket.connect();


        } catch (IOException e) {
            this.finish();
        }
    }

    public void send_data(String data) throws IOException {
        outputStream = btSocket.getOutputStream();
        byte[] tt = data.getBytes();
        outputStream.write(tt);
    }

    public String readData(String value) throws IOException {
        send_data(value);
        InputStream inputStream = btSocket.getInputStream();
        int ind = inputStream.available();
        byte buffer[] = new byte[ind];
        int dat = inputStream.read(buffer);
        String readMessage = new String(buffer, 0, dat);

        return readMessage;


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:

                return true;
            case KeyEvent.KEYCODE_SEARCH:

                return true;
            case KeyEvent.KEYCODE_BACK:
                Toast.makeText(this, "Press again back for exit", Toast.LENGTH_SHORT).show();
                counter++;
                if (counter > 1) {
                    counter = 0;
                    super.onBackPressed();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:

                try {
                    send_data("!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                event.startTracking();
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                editText.setText("");
                editText.setHint("Listening...");
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:

                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                mSpeechRecognizer.stopListening();
                editText.setHint("press and hold the volume down key to speak");
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    String beginListenForData() {
        try {
            final Handler handler = new Handler();
            final byte delimiter = 10; //This is the ASCII code for a newline character

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];
            workerThread = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                        try {
                            int bytesAvailable = mmInputStream.available();
                            if (bytesAvailable > 0) {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                for (int i = 0; i < bytesAvailable; i++) {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) {
                                        final byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                        final String data = new String(readBuffer, "US-ASCII");
                                        readBufferPosition = 0;

                                        handler.post(new Runnable() {
                                            public void run() {
                                                sGelenVeri = data.toString();

                                            }
                                        });
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            stopWorker = true;
                        }
                    }
                }
            });
            workerThread.start();
        } catch (Exception ignored) {
        }
        return sGelenVeri;
    }
}



