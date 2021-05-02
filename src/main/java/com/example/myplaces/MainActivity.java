package com.example.myplaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goHome();

    }
    private void goHome() {
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(),"home")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.info) {
            infoPop();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }


    private void infoPop() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("Help Screen")
                .setMessage("* Use + Button to open Google map and add new Item \n * Click on the Place name to add selected place to the list")
                .setPositiveButton("OK", null)
                .show();
        dialog.show();
    }

}