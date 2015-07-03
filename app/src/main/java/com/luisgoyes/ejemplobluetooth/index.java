package com.luisgoyes.ejemplobluetooth;


import android.app.AlertDialog;
import android.content.DialogInterface;
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


public class index extends Fragment {


    public index() {}

    private TextView Estado;
    private boolean ledState = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_index, container, false);
        Button ToggleButton = (Button) rootView.findViewById(R.id.bToggle_id);
        Estado = (TextView) rootView.findViewById(R.id.tEstado);
        ToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButtonFunction();
            }
        });
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
        setEstadoText();
    }
}
