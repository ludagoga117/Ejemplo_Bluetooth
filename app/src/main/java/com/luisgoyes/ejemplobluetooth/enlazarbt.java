package com.luisgoyes.ejemplobluetooth;


import android.app.ListFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.UUID;

public class enlazarbt extends ListFragment {

    public enlazarbt() {}

    public int itemSelected = -1;

    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enlazarbt, container, false);

        // Con el botón de enlazar se enlazará al dispositivo seleccionado
        Button enlazar = (Button) rootView.findViewById(R.id.botonEnlazar);
        enlazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcionEnlazar();
            }
        });

        // Con el botón de cancelar volvemos al fragmento principal
        Button cancelar = (Button) rootView.findViewById(R.id.botonCancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcionCancelar();
            }
        });

        //El adaptador del ListView lo llenamos con el arreglo de nombres de dispositivos descubiertos
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, MainActivity.getBT_devices_names());
        setListAdapter(adapter);

        /* En vez de heredar de ListFragment podriamos haber heredado de Fragment y creariamos el ListView a partir de:
         * ListView myLV;
         * myLV = (ListView) rootView.findViewById(R.id.list);
         * myLV.setAdapter(new ArrayAdapter<String>());
         * myLV.setAdapter(adapter);
         * myLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         *      @Override
         *      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         *          view.setSelected(true);
         *          itemSelected = position;
         *      }
         * });
         */
        return rootView;
    }

    public void actualizarContenido(){
        adapter.notifyDataSetChanged();
    }

    private void funcionCancelar() {
        getActivity().getFragmentManager().beginTransaction().replace(android.R.id.content,new index()).commit();
    }

    private void funcionEnlazar() {
        if(itemSelected==-1){
            msgToast(getResources().getString(R.string.errorSeleccion));
        }else if(itemSelected>=0){
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
            if(connectRemoteDevice(MainActivity.getBTdevice(itemSelected))){
                MainActivity.connected = true;
                msgToast("Conectado a " + MainActivity.getBT_devices_names().get(itemSelected));
                getActivity().getFragmentManager().beginTransaction().replace(android.R.id.content, new index()).commit();
            }else{
                MainActivity.connected = false;
                msgToast("Error de conexión");
            }
            MainActivity.bascular.setEnabled(MainActivity.connected);
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
        /* ToDo: Mantener seleccionado un elemento del ListView
         * Cuando se de click a un elemento del ListView, este debe mantenerse seleccionado
         */

        super.onListItemClick(l, v, position, id);
    }

    private void msgToast(String s){
        Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
