package com.luisgoyes.ejemplobluetooth;

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


public class MainActivity extends ActionBarActivity {
//https://wingoodharry.wordpress.com/2014/04/15/android-sendreceive-data-with-arduino-using-bluetooth-part-2/

    public static boolean connected = false;

    private enlazarbt fragEnlazar = new enlazarbt();
    // Fragmento para emparejarse con un bluetooth

    public static Button bascular = null;
    // Botón del fragmento Index para cambiar el estado del LED.

    private static ArrayList<String> BT_devices_names = new ArrayList<>();
    // Esta lista contendrá los nombres de los dispositivos disponibles para ser mostrados en el ListView
    public static ArrayList<String> getBT_devices_names(){
        return BT_devices_names;
    }

    private static ArrayList<BluetoothDevice> BT_devices = new ArrayList<BluetoothDevice>();
    // Esta lista contendrá los dispositivos bluetooth disponibles para crear un enlace
    public static BluetoothDevice getBTdevice( int index ){
        return BT_devices.get(index);
    }

    private BluetoothAdapter myBT = null;

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ( BluetoothDevice.ACTION_FOUND.equals(intent.getAction() ) ){
                BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                BT_devices.add(remoteDevice);
                BT_devices_names.add(remoteDevice.getName()+" ("+remoteDevice.getAddress()+")");
                fragEnlazar.actualizarContenido();
            }
        }
    };

    private static BluetoothSocket BTsocket;
    // En el lado del servidor, se crea un socket de escucha.
    // El BluetoothSocket maneja la conexión tipo RFCOMM (orientada a conexión)
    public static BluetoothSocket getsocket(){
        return BTsocket;
    }
    public static void setsocket(BluetoothSocket ext){
        BTsocket = ext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Al crear la aplicación debe cargarse el fragmento principal y nada más (no se debe establecer ninguna conexión ni nada)
        getFragmentManager().beginTransaction().add(android.R.id.content, new index()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myBroadcastReceiver!=null){
            registerReceiver(myBroadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
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
        if(BTsocket!=null){
            try {
                BTsocket.close();
                connected = false;
                if(bascular!=null) {
                    bascular.setEnabled(false);
                }
                msgToast("Dispositivo desconectado exitosamente");
            } catch (IOException e) {
                msgToast("Error al desconectar el Bluetooth");
            }
        }
    }

    private void EnlazarMenuItem() {
        myBT = BluetoothAdapter.getDefaultAdapter();
        if( myBT == null ){
            msgToast("Este dispositivo NO tiene módulo Bluetooth");
        }else{
            if( myBT.isEnabled() == false ){
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon, 1);
            }else{
                desconectarBluetooth();
                discoverBTDevices();
                fragEnlazar.itemSelected = -1;
                getFragmentManager().beginTransaction().replace(android.R.id.content, fragEnlazar).commit();
            }
        }
    }

    private void discoverBTDevices(){
        BT_devices.clear();
        BT_devices_names.clear();
        myBT.startDiscovery();
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

    private void msgToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}