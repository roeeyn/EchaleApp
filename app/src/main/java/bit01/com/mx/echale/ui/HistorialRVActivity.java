package bit01.com.mx.echale.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.models.Historial;
import bit01.com.mx.echale.models.Partido;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HistorialRVActivity extends AppCompatActivity {

    List<Historial> mListApuestas = new ArrayList<>();

    @BindView(R.id.rvHistorial)
    RecyclerView recyclerView;

    FirebaseAuth mAuth;
    String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_rv);

        // Inicializamos ButterKnife
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Historial de Apuestas");
        }


        mAuth = FirebaseAuth.getInstance();
        userUid = mAuth.getCurrentUser().getUid();

        traerHistorial();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                startActivity(new Intent(HistorialRVActivity.this, PartidosRecyclerViewActvity.class));
                break;

        }

        return true;

    }
    
  // Obtener información de apuestas de Firebase

    private void traerHistorial() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/users/" + userUid + "/historial/");

        final List<Historial> partidosApostados = new ArrayList<>();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                partidosApostados.clear();

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                if(!children.equals(null)) {
                    for (DataSnapshot child : children) {
                        partidosApostados.add(child.getValue(Historial.class));
                    }

                    mListApuestas = partidosApostados;
                    settingRecyclerView();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // Agregar RecyclerView a la vista
    private void settingRecyclerView() {
        HistorialAdapter historialAdapter = new HistorialAdapter(mListApuestas);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistorialRVActivity.this));
        recyclerView.setAdapter(historialAdapter);

    }


}


