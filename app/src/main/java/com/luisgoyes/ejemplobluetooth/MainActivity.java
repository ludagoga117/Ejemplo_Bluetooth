package com.luisgoyes.ejemplobluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(android.R.id.content, new index()).commit();
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
            case R.id.iHelp:
                HelpMenuItem();
                break;
            case R.id.iAbout:
                AboutMenuItem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void EnlazarMenuItem() {
        getFragmentManager().beginTransaction().replace(android.R.id.content, new enlazarbt()).commit();
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
}
