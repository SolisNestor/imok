package com.fox.imok.reporte;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fox.imok.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReporteActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.FavTabs)
    TabLayout favTabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        ButterKnife.bind(this);
        initToolbar();
        initTabs();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return false;
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.reporteempleados));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initTabs(){
        viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        favTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        favTabs.setupWithViewPager(viewpager);
    }
}
