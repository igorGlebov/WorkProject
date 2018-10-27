package com.example.host.databaseproject;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.ArrayList;

public class CatalogueActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Datable , CatalogueFragment.OnFragmentInteractionListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    String[] strings;

    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;


    private StorageReference storageReference;
    private DatabaseReference userReference;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private ImageButton changeAvatarButton;

    SettingsFragment settingsFragment;

    //Datable realization

    @Override
    public void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) { // не удалять, для снимка
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // Фотка сделана, извлекаем картинку
            Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
            changeAvatarInStorage(thumbnailBitmap);

            //changeAvatarButton.setImageBitmap(thumbnailBitmap);

        }
        else{
            Bitmap bitmap = null;

            switch(requestCode) {
                case GALLERY_REQUEST:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = data.getData();
                        try {
                            //ALERT CAN BE AN OSHIBKE HERE
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        changeAvatarInStorage(bitmap);
                        //changeAvatarButton.setImageBitmap(bitmap);
                    }
            }
        }
    }

    void changeAvatarInStorage(Bitmap avatar){
        storageReference.child("avatar.png").delete();
        ByteArrayOutputStream avatarBytesStream = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.PNG, 100, avatarBytesStream);
        byte[] avatarBytes = avatarBytesStream.toByteArray();
        storageReference.child(currentUser.getUid());
        StorageReference newRef = storageReference.child("avatar.png");
        UploadTask uploadTask = newRef.putBytes(avatarBytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CatalogueActivity.this, "Что-то не так с загрузкой нового изображения в базу. Проверьте соединение и повторите попытку.", Toast.LENGTH_LONG).show();

            }
        });

        settingsFragment.changeAvatar(avatar); // изменение аватарки в настройках
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);

        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mytitle);

        changeAvatarButton = findViewById(R.id.changeAvatarButton);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference(currentUser.getUid());
        mAuth = FirebaseAuth.getInstance();

        //Фрагмент по умолчанию
        CatalogueFragment fragment = new CatalogueFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (findViewById(R.id.drawer_layout));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_catalogue);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorMaroon)));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        //getMenuInflater().inflate(R.menu.catalogue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int title = R.string.title_catalogue;

        if (id == R.id.katalog) {
            CatalogueFragment fragment = new CatalogueFragment();
            replaceFragment(fragment);
            title = R.string.title_catalogue;
        } else if (id == R.id.settings) {
            SettingsFragment fragment = new SettingsFragment ();
            replaceFragment(fragment);
            settingsFragment = fragment;
            title = R.string.title_settings;
        } else if (id == R.id.logOut) {
            mAuth.signOut();
            startActivity(new Intent(CatalogueActivity.this, LoginActivity.class));
        } else if (id == R.id.basket) {
            BasketFragment basketFragment = new BasketFragment();
            replaceFragment(basketFragment);
            title = R.string.title_basket;
            Bundle bundle = new Bundle();
            bundle.putStringArray(BasketFragment.EXTRA_KEY, strings);
            basketFragment.setArguments(bundle);
        } else if (id == R.id.orderHistory) {
            OrderHistoryFragment fragment = new OrderHistoryFragment();
            replaceFragment(fragment);
            title = R.string.title_order_history;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);

        }
        return true;
    }

    @Override
    public void onFragmentInteraction(String[] strings) {
        this.strings = strings;
    }

    public void replaceFragment(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction(); // начало транзакции фрагмента
        fragmentTransaction.replace(R.id.fragment_container, fragment); // замена фрагмента, содержащегося в контейнере
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); // анимация перехода
        fragmentTransaction.addToBackStack(null); // помещение транзакции в стек возврата
        fragmentTransaction.commit(); // закрепляет изменения
    }
}

