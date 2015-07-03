package com.luisgoyes.ejemplobluetooth;


import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class enlazarbt extends ListFragment {

    private String[] BT_devices_name = {"Dispositivo 1", "Dispositivo 2"};
    private ListView myLV;
    private int itemSelected = -1;

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
        return rootView;
    }

    private void funcionCancelar() {
        getActivity().getFragmentManager().beginTransaction().replace(android.R.id.content,new index()).commit();
    }

    private void funcionEnlazar() {
        switch(itemSelected){
            case -1:
                Toast.makeText(getActivity(), getResources().getString(R.string.errorSeleccion), Toast.LENGTH_SHORT).show();
                break;
            case 0:
            case 1:
                Toast.makeText(getActivity(), "Ha seleccionado " + BT_devices_name[itemSelected], Toast.LENGTH_SHORT).show();
                getActivity().getFragmentManager().beginTransaction().replace(android.R.id.content, new index()).commit();
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item,BT_devices_name));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        v.setSelected(true);
        itemSelected = position;
        super.onListItemClick(l, v, position, id);
    }


}
