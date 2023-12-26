package com.n1akai.gamesstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    ConstraintLayout homeHeader;
    EditText searchBarEt;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    NavController navController;

    private void initView() {
        homeHeader = findViewById(R.id.home_header);
        searchBarEt = findViewById(R.id.edit_text_search);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        searchBar();
        setSupportActionBar(toolbar);
        setupNavController();
        setupToolbar();
        setupBottomNav();
        hideHomeHeaderInOtherFragments();
        navigateToSearchFrag();
        // Testing
        // new feature
    }

    private void setupNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment != null ? navHostFragment.getNavController() : null;
    }

    private void searchBar() {
        searchBarEt.setOnClickListener(v -> startActivity(new Intent(this, Search.class)));
    }

    private void setupToolbar() {
        NavigationUI.setupWithNavController(toolbar, navController);
    }

    private void setupBottomNav() {
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    private void hideHomeHeaderInOtherFragments() {
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.homeFragment) {
                homeHeader.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.GONE);
            } else if (navDestination.getId() == R.id.searchFragment) {
                homeHeader.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                toolbar.findViewById(R.id.edit_text_main_search).setVisibility(View.VISIBLE);
            } else {
                homeHeader.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                toolbar.findViewById(R.id.edit_text_main_search).setVisibility(View.GONE);
            }
        });
    }

    private void navigateToSearchFrag() {
        searchBarEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                NavDirections action = NavGraphDirections.actionGlobalSearchFragment();
                navController.navigate(action);
            }
        });
    }

}