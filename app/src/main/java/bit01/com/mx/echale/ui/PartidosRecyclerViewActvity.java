package bit01.com.mx.echale.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    SharedPreferences sharedPreferences;

    public void poblarPartidosDummy(){

        mPartidos.add(new Partido(1, "Coyotes", "Necaxa", "12/10/2017", "urlLocal", "urlVista", "13:00", "pendiente"));
        mPartidos.add(new Partido(2,"México", "Inglaterra", "13/10/2017", "urlLocal", "urlVista", "13:00", "pendiente"));
        mPartidos.add(new Partido(3,"América", "Chivas", "14/10/2017", "urlLocal", "urlVista", "13:00", "pendiente"));
        mPartidos.add(new Partido(4,"Toluca", "Puebla", "15/10/2017", "urlLocal", "urlVista", "13:00", "pendiente"));
        mPartidos.add(new Partido(5,"Atlante", "Atlas", "16/10/2017", "urlLocal", "urlVista", "13:00", "pendiente"));
        mPartidos.add(new Partido(6,"Pachuca", "Correcaminos", "17/10/2017", "urlLocal", "urlVista", "13:00", "pendiente"));
        mPartidos.add(new Partido(7,"Cruz Azul", "Pumas", "18/10/2017", "urlLocal", "urlVista", "13:00", "pendiente"));
        mPartidos.add(new Partido(8,"Real Madrid", "Barcelona", "19/10/2017", "urlLocal", "urlVista", "13:00", "pendiente"));
        mPartidos.add(new Partido(9,"Chelsea", "Osasuna", "20/10/2017", "urlLocal", "urlVista", "13:00", "pendiente"));
        mPartidos.add(new Partido(10,"Atlético de Madrid", "Borussia Dortmund", "21/10/2017", "urlLocal", "urlVista", "13:00", "pendiente"));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidos_recycler_view_actvity);

        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Lista de Partidos");

        traerPartidos();

        //poblarPartidosDummy();
        recyclerView = (RecyclerView) findViewById(R.id.rvPartidos);
        //settingRecyclerView();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean pruebaMostrada = sharedPreferences.getBoolean(Constants.TAG_PRUEBA_YA_HECHA, false);
        if(!pruebaMostrada){

            final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_cards_white_24dp);

            final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 25, droid.getIntrinsicHeight() * 25);


            final TapTargetSequence sequence = new TapTargetSequence(this)
                    .targets(
                            TapTarget.forToolbarNavigationIcon(toolbar, "Este es el botón de opciones", "Aquí podrás encontrar opciones como perfil, tus apuestas, tu historial, etc")
                                    .cancelable(false)
                                    .id(1),
                            TapTarget.forToolbarOverflow(toolbar, "Más opciones", "Aquí verás más opciones, como cerrar sesión")
                                    .cancelable(false)
                                    .id(2),

                            TapTarget.forBounds(droidTarget, "Échale!", "En esta sección podrás elegir el partido de tu preferencia y apostar.")
                                    .cancelable(false)
                                    .icon(droid)
                                    .id(3)


                    ).listener(new TapTargetSequence.Listener() {
                        @Override
                        public void onSequenceFinish() {

                            Toast.makeText(PartidosRecyclerViewActvity.this, "Ahora haz tu primera apuesta", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {

                            Toast.makeText(PartidosRecyclerViewActvity.this, "Algo salió mal :(", Toast.LENGTH_SHORT).show();

                        }
                    });

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.TAG_PRUEBA_YA_HECHA, true);
            editor.commit();
            sequence.start();

        }




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

    public void traerPartidos(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/");

        final List<Partido> partidos = new ArrayList<>();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.child("partidosActuales").getChildren();
                for(DataSnapshot child : children){

                    partidos.add(child.getValue(Partido.class));
                    //Partido partido = child.getValue(Partido.class);
                    //Log.e("logChido", partido.getIdPartido().toString());

                }

                mPartidos = partidos;
                settingRecyclerView();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public Activity getActivity() {
        return this;
    }
}
