package com.example.climphmusic.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.climphmusic.Fragments.HomeFragment;
import com.example.climphmusic.Fragments.LibraryFragment;
import com.example.climphmusic.Services.MusicService;
import com.example.climphmusic.R;
import com.example.climphmusic.Fragments.TrendingFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    ChipNavigationBar bottomNavBar;
    public static FragmentContainerView fragmentContainerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();
        bottomNavBar = findViewById(R.id.bottom_nav_bar);
        fragmentContainerView = findViewById(R.id.activity_main_fragment_container_view);

        if(MusicService.mediaPlayer == null){
            fragmentContainerView.setVisibility(View.GONE);
        }
        bottomNavBar.setItemSelected(R.id.nav_home,true);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,new HomeFragment());
        fragmentTransaction.commit();


        bottomNavBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (i) {

                    case R.id.nav_home:
                        fragmentTransaction.replace(R.id.content,new HomeFragment());
                        break;

                    case R.id.nav_trending:
                        fragmentTransaction.replace(R.id.content,new TrendingFragment());
                        break;

                    case R.id.nav_library:
                        fragmentTransaction.replace(R.id.content,new LibraryFragment());
                        break;

                }
                fragmentTransaction.commit();

            }
        });

    }
}
