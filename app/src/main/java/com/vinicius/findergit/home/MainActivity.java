package com.vinicius.findergit.home;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vinicius.findergit.Utils;
import com.vinicius.findergit.about.AboutActivity;
import com.vinicius.findergit.account.AccountFragment;
import com.vinicius.findergit.base.BaseActivity;
import com.vinicius.findergit.R;
import com.vinicius.findergit.data.api.GitHub;
import com.vinicius.findergit.data.api.Repositories;
import com.vinicius.findergit.data.api.Repository;
import com.vinicius.findergit.data.dao.OwnerDao;
import com.vinicius.findergit.data.dao.RepositoryDao;
import com.vinicius.findergit.data.model.RealmOwner;
import com.vinicius.findergit.data.model.RealmRepository;
import com.vinicius.findergit.favorites.FavoriteFragment;
import com.vinicius.findergit.ConstUtils;
import com.vinicius.findergit.history.HistoryFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    SearchListDialogFragment.Listener,
                    SensorEventListener{

    private SensorManager mSensorManager;

    private boolean close = false;
    private boolean inTransaction = false;

    private RepositorySearchListener mRepositorySearchListener;

    HomeFragment fragmentHome;
    HistoryFragment fragmentHistory;
    FavoriteFragment fragmentFavorites;
    AccountFragment fragmentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearch();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        fragmentAccount = AccountFragment.newInstance();
        fragmentFavorites = FavoriteFragment.newInstance();
        fragmentHistory = HistoryFragment.newInstance();
        fragmentHome = HomeFragment.newInstance();
    }

    private void openSearch() {
        SearchListDialogFragment.newInstance().show(getSupportFragmentManager(), "dialog");
    }


    @Override
    protected void onStart() {
        super.onStart();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        replaceFragment(fragmentHome);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment instanceof RepositorySearchListener) {
            mRepositorySearchListener = fragmentHome;
        } else {
            mRepositorySearchListener = null;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (close) {
                super.onBackPressed();
            } else {
                close = true;
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       close = false;
                    }
                }, 1500);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFragment(fragmentHome);
        } else if (id == R.id.nav_history) {
            replaceFragment(fragmentHistory);
        } else if (id == R.id.nav_fav) {
            replaceFragment(fragmentFavorites);
        } else if (id == R.id.nav_settings) {
            replaceFragment(fragmentAccount);
        } else if (id == R.id.nav_logout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchDefined(String search) {
        final RepositoryDao repositoryDao = new RepositoryDao();
        final OwnerDao ownerDao = new OwnerDao();
        Utils.connect(ConstUtils.ENDPOINT_GITHUB)
                .create(GitHub.class)
                .searchRepositories(search)
                .enqueue(new Callback<Repositories>() {
                    @Override
                    public void onResponse(@NonNull Call<Repositories> call, @NonNull Response<Repositories> response) {
                        if (response.body() != null) {
                            // clear
                            repositoryDao.deleteAll();
                            fragmentHome.notifyAdapterChanged();
                            ownerDao.deleteAll();

                            @SuppressWarnings("ConstantConditions")
                            ArrayList<Repository> repoResponse = response.body().getRepositories();
                            // lists to save in db
                            ArrayList<RealmRepository> repositories = new ArrayList<>();
                            ArrayList<RealmOwner> owners = new ArrayList<>();
                            // items to extract
                            RealmRepository itemRepository;
                            RealmOwner itemOwner;

                            for (Repository r : repoResponse) {
                                // Repository save
                                itemRepository = new RealmRepository(
                                        r.getId(),
                                        r.getName(),
                                        r.getLanguage(),
                                        r.getUrl(),
                                        r.getOwner().getName()
                                );
                                repositories.add(itemRepository);
                                // Owner save
                                itemOwner = new RealmOwner(
                                        r.getOwner().getName(),
                                        r.getOwner().getAvatarUrl()
                                        );
                                owners.add(itemOwner);
                            }
                            repositoryDao.add(repositories);
                            fragmentHome.notifyAdapterChanged();
                            ownerDao.add(owners);
                            mRepositorySearchListener.onSearchResult();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Repositories> call, @NonNull Throwable t) {
                        Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onSearchClosed() {
        inTransaction = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float values[] = sensorEvent.values;
        if (!inTransaction && Math.abs(values[0])+Math.abs(values[1])+Math.abs(values[2]) > 70) {
            inTransaction = true;
            openSearch();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    interface RepositorySearchListener {
        void onSearchResult();
    }
}
