package com.luisgoyes.ejemplobluetooth;


import android.app.Activity;
import android.app.ListFragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

public class enlazarbt extends ListFragment {

    private int itemSelected = -1;

    private ArrayAdapter<String> adapter;

    public enlazarbt() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enlazarbt, container, false);
        Button enlazar = (Button) rootView.findViewById(R.id.botonEnlazar);
        enlazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcionEnlazar();
            }
        });
        Button cancelar = (Button) rootView.findViewById(R.id.botonCancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcionCancelar();
            }
        });

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, MainActivity.getBT_devices());
        setListAdapter(adapter);

        /*myLV = (ListView) rootView.findViewById(R.id.list);
        myLV.setAdapter(new ArrayAdapter<String>());

        myLV.setAdapter(adapter);
        myLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                itemSelected = position;
            }
        });*/

        return rootView;
    }

    public void actualizarContenido(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
    }

    private void funcionCancelar() {
        getActivity().getFragmentManager().beginTransaction().replace(android.R.id.content,new index()).commit();
    }

    private void funcionEnlazar() {
        switch(itemSelected){
            case -1:
                Toast.makeText(getActivity(), getResources().getString(R.string.errorSeleccion), Toast.LENGTH_SHORT).show();
                break;
            default:
                if(connectRemoteDevice(MainActivity.getBTdevice(itemSelected))){
                    MainActivity.connected = true;
                    Toast.makeText(getActivity(), "Conectado a " + MainActivity.getBT_devices().get(itemSelected), Toast.LENGTH_SHORT).show();
                    getActivity().getFragmentManager().beginTransaction().replace(android.R.id.content, new index()).commit();
                }else{
                    MainActivity.connected = false;
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
                MainActivity.bascular.setEnabled(MainActivity.connected);
                itemSelected = -1;
                break;
        }
    }

    private boolean connectRemoteDevice(BluetoothDevice device){
        boolean connect = false;
        try {
            // SPP UUID service - this should work for most devices
            String mmUUID = "00001101-0000-1000-8000-00805F9B34FB";
            MainActivity.setsocket(device.createRfcommSocketToServiceRecord(UUID.fromString(mmUUID)));
            MainActivity.getsocket().connect();
            connect = true;
        } catch (Exception e) {
            connect = false;
        }
        return connect;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        v.setSelected(true);
        itemSelected = position;
        super.onListItemClick(l, v, position, id);
    }
}
