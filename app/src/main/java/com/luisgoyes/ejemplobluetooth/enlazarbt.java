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

import java.io.IOException;
import java.util.ArrayList;
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
        /* ToDo: Actualizar el contenido del adaptador del ListView
         * Cada vez que se añade un nuevo string a BT_devices_names se debe actualizar el ListView
         */

    }

    private void funcionCancelar() {
        getActivity().getFragmentManager().beginTransaction().replace(android.R.id.content,new index()).commit();
    }

    private void funcionEnlazar() {
        /* ToDo: Conectarse con uno de los dispositivos descubiertos, que se muestra en el ListView
         * Si no se selecciona ningún ítem, saldrá un mensaje de error.
         * En caso contrario, se trata de conectar con el dispositivo seleccionado
         * Si se logra conexión, saldrá un mensaje indicando conexión exitosa y se retorna al fragment principal
         * Si no se logra conexión, saldrá un mensaje indicando error en conexión
         */

    }

    private boolean connectRemoteDevice(BluetoothDevice device){
        boolean connect = false;
        /* ToDo: Conectar el dispositivo mediante una comunicación por Socket
         * SPP UUID service - this should work for most devices
         * "00001101-0000-1000-8000-00805F9B34FB"
         * The UUID class defines universally unique identifiers
         * 00001101: Serial Port Protocol
         * Base UUID Value (Used in promoting 16-bit and 32-bit UUIDs to 128-bit UUIDs)	0x0000000000001000800000805F9B34FB
         *
         * 1. Se crea la conexión con el dispositivo seleccionado "device"
         * y para poder manipularla se guarda en la variable socket
         * 2. Se conecta con dicho dispositivo
         * 3. Esa conexión requiere un manejo de excepción
         */

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
