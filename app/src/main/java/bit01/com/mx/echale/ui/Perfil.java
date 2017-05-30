package bit01.com.mx.echale.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.models.User;
import bit01.com.mx.echale.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil extends AppCompatActivity {

    // Variable de Firebase
    private FirebaseAuth mAuth;

    @BindView(R.id.profile_name) TextView user_name;
    @BindView(R.id.profile_email) TextView user_email;
    @BindView(R.id.profile_birth_date) TextView user_birth_date;
    @BindView(R.id.profile_coins) TextView user_coins;
    @BindView(R.id.profile_image) CircleImageView user_image;
    @BindView(R.id.upload_image) Button user_upload_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicialización d eFirebase
        ButterKnife.bind(Perfil.this);

        // Inicialización de la instancia de FireBase
        mAuth = FirebaseAuth.getInstance();

        showData();
    }


    public void showData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        String user_id = mAuth.getCurrentUser().getUid();

        myRef.child("users").child(user_id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        if(user.getPhotoUrl().compareTo("")!= 0)
                            Glide.with(Perfil.this).load(user.getPhotoUrl()).into(user_image);
                        else
                            user_upload_image.setVisibility(View.VISIBLE);

                        user_name.setText(user.getNombre());
                        user_coins.setText(user.getMonedas() + "");
                        user_email.setText(user.getMail());
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(Constants.LOG_TAG, "getUser:onCancelled", databaseError.toException());
                        // ...
                    }
                });
    }


    @OnClick(R.id.upload_image)
    public void onClickUploadImage(){

    }

}
