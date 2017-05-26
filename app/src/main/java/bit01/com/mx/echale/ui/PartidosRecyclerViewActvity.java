package bit01.com.mx.echale.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.models.Partido;
import bit01.com.mx.echale.utils.Constants;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PartidosRecyclerViewActvity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Partido> mPartidos =new ArrayList<>();

    RecyclerView recyclerView;

    public void poblarPartidosDummy(){

        mPartidos.add(new Partido("Coyotes", "Necaxa", "12/10/2017", "urlLocal", "urlVista", "13:00"));
        mPartidos.add(new Partido("México", "Inglaterra", "13/10/2017", "urlLocal", "urlVista", "13:00"));
        mPartidos.add(new Partido("América", "Chivas", "14/10/2017", "urlLocal", "urlVista", "13:00"));
        mPartidos.add(new Partido("Toluca", "Puebla", "15/10/2017", "urlLocal", "urlVista", "13:00"));
        mPartidos.add(new Partido("Atlante", "Atlas", "16/10/2017", "urlLocal", "urlVista", "13:00"));
        mPartidos.add(new Partido("Pachuca", "Correcaminos", "17/10/2017", "urlLocal", "urlVista", "13:00"));
        mPartidos.add(new Partido("Cruz Azul", "Pumas", "18/10/2017", "urlLocal", "urlVista", "13:00"));
        mPartidos.add(new Partido("Real Madrid", "Barcelona", "19/10/2017", "urlLocal", "urlVista", "13:00"));
        mPartidos.add(new Partido("Chelsea", "Osasuna", "20/10/2017", "urlLocal", "urlVista", "13:00"));
        mPartidos.add(new Partido("Atlético de Madrid", "Borussia Dortmund", "21/10/2017", "urlLocal", "urlVista", "13:00"));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidos_recycler_view_actvity);

        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        poblarPartidosDummy();
        recyclerView = (RecyclerView) findViewById(R.id.rvPartidos);
        settingRecyclerView();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    public void settingRecyclerView(){

        PartidoAdapter partidoAdapter = new PartidoAdapter(mPartidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(PartidosRecyclerViewActvity.this));
        recyclerView.setAdapter(partidoAdapter);

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
        getMenuInflater().inflate(R.menu.partidos_recycler_view_actvity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out_button) {
            //FirebaseAuth.getInstance().signOut();
            signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Log.d(Constants.LOG_TAG, "Signed out successfully!");
        startActivity(new Intent(PartidosRecyclerViewActvity.this, Login.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
