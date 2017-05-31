package bit01.com.mx.echale.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.models.ApuestaActivity;
import bit01.com.mx.echale.models.User;
import bit01.com.mx.echale.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil extends AppCompatActivity {

    // Variable de Firebase
    private FirebaseAuth mAuth;

    String imageUrl = "";

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

        final String user_id = mAuth.getCurrentUser().getUid();

        myRef.child("users").child(user_id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        if(user.getPhotoUrl().compareTo("")  != 0)
                            Glide.with(Perfil.this).load(user.getPhotoUrl()).into(user_image);
                        else {
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference gsReference = storage.getReferenceFromUrl(Constants.USER_IMAGES_FOLDER + user_id + ".jpg");

                            gsReference.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                        }
                                    });

                        }




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
        AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);
        CharSequence [] items = {"Camara", "Almacenamiento local"};
        builder.setTitle("Selecciona opción")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                int permissionStatus =
                                        ActivityCompat.checkSelfPermission(Perfil.this, Manifest.permission.CAMERA);
                                if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                                    Log.e(Constants.LOG_TAG, "Switch");
                                    openCamera();
                                } else {
                                    requestCameraPermission();
                                }

                                break;
                            case 1:
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                // Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                // photoPickerIntent.setType("image/*");
                                //startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

                                startActivityForResult(pickPhoto , Constants.REQUEST_CODE_UPLOAD_PICTURE);//one can be replaced with any action code
                                break;
                        }

                    }
                });
        builder.create();
        builder.show();
    }

    private void requestCameraPermission() {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        Log.e(Constants.LOG_TAG, "Metodo requestCameraPermission");
        if (!ActivityCompat.shouldShowRequestPermissionRationale(Perfil.this, Manifest.permission.CAMERA)) {
            Log.e(Constants.LOG_TAG, "Error Metodo requestCameraPermission");
            ActivityCompat.requestPermissions(Perfil.this, permissions, Constants.REQUEST_PERMISSION_CAMERA);
        } else {
            ActivityCompat.requestPermissions(Perfil.this, permissions, Constants.REQUEST_PERMISSION_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Preguntamos si es el request code con el que mandamos a solicitar el o los permisos
        if (requestCode == Constants.REQUEST_PERMISSION_CAMERA) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Yeah!!
                openCamera();
                return;
            }

            if (!ActivityCompat.shouldShowRequestPermissionRationale(Perfil.this, Manifest.permission.CAMERA)) {
                //TODO Decirle al usuario que tiene que ir a las configuraciones y habilitar el permiso manualmente
            } else {
                ActivityCompat.requestPermissions(Perfil.this, permissions, Constants.REQUEST_PERMISSION_CAMERA);
            }

        }
    }

    public void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constants.REQUEST_CODE_TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_CODE_TAKE_PICTURE) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a storage reference from our app
            StorageReference storageRef =
                    storage.getReferenceFromUrl(Constants.USER_IMAGES_FOLDER);

            String user_id = mAuth.getCurrentUser().getUid();
            StorageReference pictureRef = storageRef.child(user_id + ".jpg");

            // Create a reference to 'images/mountains.jpg'
            // StorageReference mountainImagesRef = storageRef.child("user_images/" + pictureRef);

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            user_image.setImageBitmap(imageBitmap);
            byte[] bytes = baos.toByteArray();
            UploadTask uploadTask = pictureRef.putBytes(bytes);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.e("myLog", downloadUrl.toString());
                    imageUrl = downloadUrl.toString();
                }
            });


        }else if(requestCode == Constants.REQUEST_CODE_UPLOAD_PICTURE){
            try {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                // Create a storage reference from our app
                StorageReference storageRef =
                        storage.getReferenceFromUrl(Constants.USER_IMAGES_FOLDER);

                String user_id = mAuth.getCurrentUser().getUid();
                StorageReference pictureRef = storageRef.child(user_id + ".jpg");

                // Create a reference to 'images/mountains.jpg'
                // StorageReference mountainImagesRef = storageRef.child("user_images/" + pictureRef);

                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                user_image.setImageBitmap(selectedImage);
                byte[] bytes = baos.toByteArray();
                UploadTask uploadTask = pictureRef.putBytes(bytes);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.e("myLog", downloadUrl.toString());
                        imageUrl = downloadUrl.toString();
                    }
                });



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Perfil.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else
            Log.e(Constants.LOG_TAG, "Error culero");


    }

}
