package com.luisgoyes.ejemplobluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends ActionBarActivity {

    public static boolean connected = false;
    public static Button bascular;

    private enlazarbt fragEnlazar = new enlazarbt();

    private static ArrayList<String> BT_devices = new ArrayList<>();
    public static ArrayList<String> getBT_devices(){
        return BT_devices;
    }

    private BluetoothAdapter myBluetooth = null;
    private static ArrayList<BluetoothDevice> btDeviceList = new ArrayList<BluetoothDevice>();
    public static BluetoothDevice getBTdevice( int index ){
        return btDeviceList.get(index);
    }
    private static BluetoothSocket clientSocket;
    public static BluetoothSocket getsocket(){
        return clientSocket;
    }
    public static void setsocket(BluetoothSocket ext){
        clientSocket = ext;
    }
    private BroadcastReceiver discoveryMonitor = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Añadimos el dispositivo encontrado al adaptador del ListView.
            String remoteName = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
            BT_devices.add(remoteName);
            // Recuperamos el dispositivo detectado y lo guardamos en el array de dispositivos.
            BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            btDeviceList.add(remoteDevice);
            fragEnlazar.actualizarContenido();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(android.R.id.content, new index()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(discoveryMonitor, new IntentFilter(BluetoothDevice.ACTION_FOUND));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.iEnlazar:
                EnlazarMenuItem();
                break;
            case R.id.iDisconnect:
                desconectarBluetooth();
                break;
            case R.id.iHelp:
                HelpMenuItem();
                break;
            case R.id.iAbout:
                AboutMenuItem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void desconectarBluetooth() {
        if (clientSocket!=null) //If the btSocket is busy
        {
            try{
                clientSocket.close(); //close connection
            }catch (IOException e){
                msg("Error");
            }
        }
    }

    private void EnlazarMenuItem() {
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if( myBluetooth == null){
            Toast.makeText(this, "Este dispositivo Android NO tiene Bluetooth", Toast.LENGTH_LONG).show();
        }else{
            if( !myBluetooth.isEnabled() ){
                //Ask to the user turn the bluetooth on
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon, 1);
            }else {
                desconectarBluetooth();
                discoverBTDevices();
                getFragmentManager().beginTransaction().replace(android.R.id.content, fragEnlazar).commit();
            }
        }
    }

    private void discoverBTDevices(){
        BT_devices.clear();
        btDeviceList.clear();
        myBluetooth.startDiscovery();
    }

    private void HelpMenuItem() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.sHelp))
                .setMessage(getResources().getString(R.string.sHelpInfo))
                .setNeutralButton(getResources().getString(R.string.sOk),new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void AboutMenuItem() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.sAbout))
                .setMessage(getResources().getString(R.string.sAboutInfo))
                .setNeutralButton(getResources().getString(R.string.sOk),new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void msg(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}