package com.luisgoyes.ejemplobluetooth;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class index extends Fragment {


    public index() {}

    private TextView Estado;
    private boolean ledState = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_index, container, false);
        MainActivity.bascular = (Button) rootView.findViewById(R.id.bToggle_id);
        Estado = (TextView) rootView.findViewById(R.id.tEstado);
        MainActivity.bascular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButtonFunction();
            }
        });
        MainActivity.bascular.setEnabled(MainActivity.connected);
        return rootView;
    }

    private void setEstadoText() {
        if(ledState==true) {
            Estado.setText(getResources().getString(R.string.sEstadoOn));
        }else{
            Estado.setText(getResources().getString(R.string.sEstadooff));
        }
    }

    private void ToggleButtonFunction() {
        ledState = !ledState;
        if (MainActivity.getsocket().isConnected()){
            try{
                //MainActivity.getsocket().getOutputStream().write("L".toString().getBytes());
                if(ledState){
                    MainActivity.getsocket().getOutputStream().write("*|1|13|1|#".getBytes());
                }else{
                    MainActivity.getsocket().getOutputStream().write("*|1|13|0|#".getBytes());
                }
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
        setEstadoText();
    }

    private void msg(String s){
        Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
