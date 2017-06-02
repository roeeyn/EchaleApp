package bit01.com.mx.echale.ui;

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
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import bit01.com.mx.echale.models.ApuestaActivity;
import bit01.com.mx.echale.models.Partido;
import bit01.com.mx.echale.models.User;
import bit01.com.mx.echale.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PartidosRecyclerViewActvity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Partido> mPartidos =new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    RecyclerView recyclerView;

    ImageView ivProfilePic;
    TextView username;

    User usuarioActual;
    int monedasActuales;

    boolean guiaYaMostrada = false;

    FirebaseAuth mAuth;
    String userUid;

    /*
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

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidos_recycler_view_actvity);

        // Inicializamos ButterKnife
        ButterKnife.bind(this);

        // Inicializamos la instancia de GoogleApiClienta
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(PartidosRecyclerViewActvity.this, new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                Toast.makeText(PartidosRecyclerViewActvity.this, "Hay un error!", Toast.LENGTH_SHORT).show();
                            }
                        })
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mAuth = FirebaseAuth.getInstance();
        userUid = mAuth.getCurrentUser().getUid();



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
        ivProfilePic = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageViewFotoPerfilRecyclerView);
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nombreUsuarioRecyclerView);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.TAG_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        guiaYaMostrada = sharedPreferences.getBoolean(Constants.TAG_GUIA_PARTIDOS, false);

        if(!guiaYaMostrada){

            mostrarGuia(toolbar);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.TAG_GUIA_PARTIDOS, true);
            editor.commit();


        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Hacemos la conexión de GoogleApiClient con Google Play Services
        mGoogleApiClient.connect();
    }

    // Agregar RecyclerView a la vista
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

    // Cerrar sesión del usuario.
    public void signOut(){
        // Cierra la sesión de firebase
        FirebaseAuth.getInstance().signOut();

        // Log.d(Constants.LOG_TAG, "Signed out successfully!");

        // Cierra la sesión de Google
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess())
                            Log.e(Constants.LOG_TAG, "Google Auth Closed Successfully");
                        else
                            Log.e(Constants.LOG_TAG, "Google Auth Seems To Have Errors");
                    }
                });

        // Se inicia la actividad Login
        startActivity(new Intent(PartidosRecyclerViewActvity.this, Login.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(PartidosRecyclerViewActvity.this, Perfil.class));
        } else if (id == R.id.nav_bet) {
            startActivity(new Intent(PartidosRecyclerViewActvity.this, HistorialRVActivity.class));
        } else if (id == R.id.nav_help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Recibir los partidos de Firebase
    public void traerPartidos(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/");

        final List<Partido> partidos = new ArrayList<>();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                partidos.clear();

                Iterable<DataSnapshot> children = dataSnapshot.child("partidosActuales").getChildren();
                for(DataSnapshot child : children){

                    partidos.add(child.getValue(Partido.class));

                }

                mPartidos = partidos;
                settingRecyclerView();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // Mostrar la guía de cómo apostar
    public void mostrarGuia(Toolbar toolbar){

        final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_cards_white_24dp);
        final Display display = getWindowManager().getDefaultDisplay();

        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 5, droid.getIntrinsicHeight() * 5);
        // Using deprecated methods makes you look way cool
        droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);

        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(


                        // This tap target will target the back button, we just need to pass its containing toolbar
                        TapTarget.forToolbarNavigationIcon(toolbar, "Opciones","En este botón podrás ver las opciones de tu perfil")
                                .cancelable(false)
                                .id(1),

                        // Likewise, this tap target will target the search button

                        // You can also target the overflow button in your toolbar
                        TapTarget.forToolbarOverflow(toolbar, "Sesión", "Aquí podrás ver las opciones de tu sesión")
                                .cancelable(false)
                                .id(2),
                        // This tap target will target our droid buddy at the given target rect
                        TapTarget.forBounds(droidTarget, "¡Échale!", "Aquí podrás ver los partidos en los que puedes apostar!")
                                .cancelable(false)
                                .icon(droid)
                                .id(3)
                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {

                        Toast.makeText(PartidosRecyclerViewActvity.this, "Ahora escoge un partido", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                        Toast.makeText(PartidosRecyclerViewActvity.this, "La regaste cawn!", Toast.LENGTH_SHORT).show();

                    }
                });

        sequence.start();
    }


}
