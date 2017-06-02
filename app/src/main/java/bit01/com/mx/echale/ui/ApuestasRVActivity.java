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
import bit01.com.mx.echale.models.ApuestaActivity;
import bit01.com.mx.echale.models.ApuestaPendiente;
import bit01.com.mx.echale.models.Historial;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ApuestasRVActivity extends AppCompatActivity {

    List<ApuestaPendiente> mListApuestas = new ArrayList<>();

    @BindView(R.id.rvApuestasPendientes)
    RecyclerView recyclerView;

    FirebaseAuth mAuth;

    String userUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apuestas_rv);

        // Inicializamos ButterKnife
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Apuestas por Jugar");
        }


        mAuth = FirebaseAuth.getInstance();
        userUid = mAuth.getCurrentUser().getUid();

        traerApuestasPendientes();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                startActivity(new Intent(ApuestasRVActivity.this, PartidosRecyclerViewActvity.class));
                break;

        }

        return true;

    }

    private void traerApuestasPendientes() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/users/" + userUid + "/apuestasPendientes/");

        final List<ApuestaPendiente> partidosApostados = new ArrayList<>();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                partidosApostados.clear();

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                if(!children.equals(null)) {
                    for (DataSnapshot child : children) {
                        partidosApostados.add(child.getValue(ApuestaPendiente.class));
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

    private void settingRecyclerView() {

        ApuestasAdapter apuestasAdapter = new ApuestasAdapter(mListApuestas);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApuestasRVActivity.this));
        recyclerView.setAdapter(apuestasAdapter);
    }

}
