package bit01.com.mx.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                Log.e("logChidoFinal", partidos.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.e("logChidoFinal", partidos.toString());


    }
}
