package com.example.foodieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodieapp.menu.MenuDashboard;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MManage extends AppCompatActivity {
    Button addMenu,adOurMenu;
    ChipNavigationBar buttonNah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_manage);
        addMenu = findViewById(R.id.addMenu);
        adOurMenu = findViewById(R.id.adOurMenu);
        buttonNah = findViewById(R.id.bottom_nav);



        buttonNah.setItemSelected(R.id.MyP, true);


        //on-selected item listener for the bottonNav
        buttonNah.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                switch (id) {
                    case R.id.MyP:
                        startActivity(new Intent(getApplicationContext(), AManage.class));
                        //overridePendingTransition(0, 0);
                        break;
                    case R.id.Menu:
                        startActivity(new Intent(getApplicationContext(), TopEats.class));
                        //overridePendingTransition(0, 0);
                        break;
                    case R.id.Shopping:
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                        //overridePendingTransition(0, 0);
                        break;
                }
            }
        });


        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MManage.this, MenuDashboard.class);
                startActivity(i);
            }
        });
        adOurMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MManage.this, OurMenu.class);
                startActivity(i);
            }
        });
    }
}