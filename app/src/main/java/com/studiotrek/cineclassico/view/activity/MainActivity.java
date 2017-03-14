package com.studiotrek.cineclassico.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.studiotrek.cineclassico.controller.adapter.MainAdapter;
import com.studiotrek.cineclassico.R;
import com.studiotrek.cineclassico.model.Filme;
import com.studiotrek.cineclassico.model.ListaFilmes;
import com.studiotrek.cineclassico.util.CinePreference;
import com.studiotrek.cineclassico.util.Constantes;
import com.studiotrek.cineclassico.util.FirebaseUtil;
import com.studiotrek.cineclassico.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private DatabaseReference mDatabase;
    private RecyclerView rvMainLista;
    private LinearLayoutManager mLayoutManager;
    private List<ListaFilmes> listaFilmesList;
    private MainAdapter mainAdapter;

    //
    private List<Filme> filmesDrama = new ArrayList<>();
    private List<Filme> filmesTerror = new ArrayList<>();
    private List<Filme> filmesComedia = new ArrayList<>();
    private List<Filme> filmesInfantil = new ArrayList<>();
    private List<Filme> filmesRomance = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        rvMainLista = (RecyclerView) findViewById(R.id.rv_main_lista);

        mDatabase = FirebaseUtil.getDatabase().getReference();
        mDatabase.keepSynced(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null || CinePreference.getStatusLogin(getApplicationContext()) == Constantes.DESLOGADO) {
            realizarLogin();
        }

        if (!Util.isOnline(MainActivity.this)) {
            SnackbarOffline(drawerLayout);
        }

    }

    private void teste (DataSnapshot dataSnapshot) {

        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
            Filme filme = postSnapshot.getValue(Filme.class);
            switch (filme.getGenero()) {
                case "Drama":
                    filmesDrama.add(filme);
                    break;
                case "Terror":
                    filmesTerror.add(filme);
                    break;
                case "Comédia":
                    filmesComedia.add(filme);
                    break;
                case "Infantil":
                    filmesInfantil.add(filme);
                    break;
                case "Romance":
                    filmesRomance.add(filme);
                    break;
            }
        }

        ListaFilmes listaFilmesDrama = new ListaFilmes();
        listaFilmesDrama.setGenero("Drama");
        listaFilmesDrama.setFilmes(filmesDrama);

        ListaFilmes listaFilmesTerror = new ListaFilmes();
        listaFilmesTerror.setGenero("Terror");
        listaFilmesTerror.setFilmes(filmesTerror);

        ListaFilmes listaFilmesComedia = new ListaFilmes();
        listaFilmesComedia.setGenero("Comédia");
        listaFilmesComedia.setFilmes(filmesComedia);

        ListaFilmes listaFilmesInfantil = new ListaFilmes();
        listaFilmesInfantil.setGenero("Infantil");
        listaFilmesInfantil.setFilmes(filmesInfantil);

        ListaFilmes listaFilmesRomance = new ListaFilmes();
        listaFilmesRomance.setGenero("Romance");
        listaFilmesRomance.setFilmes(filmesRomance);

        listaFilmesList = new ArrayList<>();
        listaFilmesList.add(listaFilmesDrama);
        listaFilmesList.add(listaFilmesTerror);
        listaFilmesList.add(listaFilmesComedia);
        listaFilmesList.add(listaFilmesInfantil);
        listaFilmesList.add(listaFilmesRomance);
    }


    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.child("filme").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teste(dataSnapshot);

                rvMainLista.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                rvMainLista.setLayoutManager(mLayoutManager);
                mainAdapter = new MainAdapter(getApplicationContext(), listaFilmesList);
                rvMainLista.setAdapter(mainAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_desconectar) {
            CinePreference.setLogout(MainActivity.this);
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            realizarLogin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void realizarLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
