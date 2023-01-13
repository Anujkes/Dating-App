package com.example.datingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.datingapp.databinding.ActivityLoginBinding;
import com.example.datingapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
   private  NavController navController;
   private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //------------------------- bottom navigation------------------//

        navController= Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController);

        //---------------------------------------------------------------//


        // ------------Drawer navigation setup--------------------//
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.drawerNavigation.setNavigationItemSelectedListener(MainActivity.this);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.rateus:
                Toast.makeText(this, "Developers", Toast.LENGTH_SHORT).show();
                break;
            case R.id.favourite:
                Toast.makeText(this, "Video", Toast.LENGTH_SHORT).show();
                break;
            case R.id.privacy:
                Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.developer:
                break;
            case R.id.share:
                Toast.makeText(this, "Theme", Toast.LENGTH_SHORT).show();
                break;
            case R.id.terms:
                Toast.makeText(this, "Websites", Toast.LENGTH_SHORT).show();
                break;



        }


        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return true;

    }

    @Override
    public void onBackPressed() {

        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.close();
        else
            super.onBackPressed();

    }
}