package com.hirenj.convertor.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Filter;

import com.hirenj.convertor.R;
import com.hirenj.convertor.fragement.CommonEngineeringFragment;
import com.hirenj.convertor.fragement.CommonFinFragment;
import com.hirenj.convertor.fragement.CommonFragment;
import com.hirenj.convertor.fragement.DigitalConverter;
import com.hirenj.convertor.fragement.FavouriteFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CommonFinFragment.OnFragmentInteractionListener, DigitalConverter.OnFragmentInteractionListener,
        CommonFragment.OnFragmentInteractionListener, FavouriteFragment.OnFragmentInteractionListener, CommonEngineeringFragment.OnFragmentInteractionListener {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SimpleCursorAdapter myAdapter;

    SearchView searchView = null;
    private ArrayList<String> strArrData = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String[] from = new String[] {"fishName"};
        final int[] to = new int[] {android.R.id.text1};

        // setup SimpleCursorAdapter
        myAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        strArrData.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.common_converter_array))));
        strArrData.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.fin_converter_array))));
        strArrData.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.engg_converter_array))));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FavouriteFragment.newInstance("Favourite", ""), "Favourite");
        adapter.addFragment(CommonFragment.newInstance("Common", ""), "Common");
        adapter.addFragment(CommonFinFragment.newInstance("Finance", ""), "Finance");
        adapter.addFragment(CommonEngineeringFragment.newInstance("Engineering", ""), "Engineering");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        // Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.searchMain);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setIconified(false);
            searchView.setSuggestionsAdapter(myAdapter);
            // Getting selected (clicked) item suggestion
            searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionClick(int position) {

                    // Add clicked text to search box
                    CursorAdapter ca = searchView.getSuggestionsAdapter();
                    Cursor cursor = ca.getCursor();
                    cursor.moveToPosition(position);

                    String selectedConverter = cursor.getString(cursor.getColumnIndex("fishName"));

                    Intent intent = new Intent(getApplicationContext(), CommonConverterActivity.class);
                    intent.putExtra("type", selectedConverter);
                    startActivity(intent);

                    //searchView.setQuery(cursor.getString(cursor.getColumnIndex("fishName")),false);
                    return true;
                }

                @Override
                public boolean onSuggestionSelect(int position) {
                    return true;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    // Filter data
                    final MatrixCursor mc = new MatrixCursor(new String[]{ BaseColumns._ID, "fishName" });
                    for (int i=0; i<strArrData.size(); i++) {
                        if (strArrData.get(i).toLowerCase().contains(s.toLowerCase()))
                            mc.addRow(new Object[] {i, strArrData.get(i)});
                    }
                    myAdapter.changeCursor(mc);
                    return false;
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Check out this app: https://play.google.com/store/apps/details?id=com.hirenj.convertor";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Unit Converter");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Choose Sharing Option"));
        } else if (id == R.id.nav_send) {
            Intent Email = new Intent(Intent.ACTION_SEND);
            Email.setType("text/email");
            Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"hirenj0009@gmail.com"});
            Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback on Unit Converter (Beta)");
            Email.putExtra(Intent.EXTRA_TEXT, "Dear Dev," + "");
            startActivity(Intent.createChooser(Email, "Send Feedback via:"));
        }else if (id == R.id.nav_pref) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        //doTransaction(fragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    @Override
    protected void onNewIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
super.onNewIntent(intent);
            // User entered text and pressed search button. Perform task ex: fetching data from database and display

        }
    }

}
