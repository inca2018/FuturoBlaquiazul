package org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Fragments.CaptacionFragment;
import org.futuroblanquiazul.futuroblaquiazul.Fragments.EstadisticoFragment;
import org.futuroblanquiazul.futuroblaquiazul.Fragments.InformacionFragment;
import org.futuroblanquiazul.futuroblaquiazul.Fragments.MainFragment;
import org.futuroblanquiazul.futuroblaquiazul.Fragments.MantenimientoFragment;
import org.futuroblanquiazul.futuroblaquiazul.Fragments.MetodologiaFragment;
import org.futuroblanquiazul.futuroblaquiazul.Fragments.SolicitudesFragment;
import org.futuroblanquiazul.futuroblaquiazul.R;

public class PrincipalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    NavigationView navigationView = null;
    Toolbar toolbar = null;
   // List<Usuarios_Perfiles> TEMP;
    String Perfil;
    TextView usuario,tipo;
    ImageView foto;
    Menu captacion,estadistico,mantenimiento,metodologia,informacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mostrar_vistas() ;
        Toolbar_iniz();
        MetodoDrawer();
        navigation_init();
    }
    private void Setear_usuario() {

        if(Usuario.SESION_ACTUAL.getUsuario()!=null){
            usuario.setText(Usuario.SESION_ACTUAL.getNombres()+" "+Usuario.SESION_ACTUAL.getApellidos());
            tipo.setText("Administrador");
        }else{
            usuario.setText("No Disponible");
            tipo.setText("No Disponible");
        }

        if(Usuario.SESION_ACTUAL.getPerfil().getId()==1){
            navigationView.getMenu().findItem(R.id.menu_modulo_solicitud).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_captacion).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_metodologia).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_informacion).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_estadisticos).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_mantenimiento).setVisible(true);
        }else if(Usuario.SESION_ACTUAL.getPerfil().getId()==2){
            navigationView.getMenu().findItem(R.id.menu_modulo_solicitud).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_captacion).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_metodologia).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_informacion).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_estadisticos).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_mantenimiento).setVisible(false);
        }else if(Usuario.SESION_ACTUAL.getPerfil().getId()==3){
            navigationView.getMenu().findItem(R.id.menu_modulo_solicitud).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_captacion).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_metodologia).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_informacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_estadisticos).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_mantenimiento).setVisible(false);
        }else if(Usuario.SESION_ACTUAL.getPerfil().getId()==4){
            navigationView.getMenu().findItem(R.id.menu_modulo_solicitud).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_captacion).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_metodologia).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_informacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_estadisticos).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_mantenimiento).setVisible(false);

        }else if(Usuario.SESION_ACTUAL.getPerfil().getId()==5){
            navigationView.getMenu().findItem(R.id.menu_modulo_solicitud).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_captacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_metodologia).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_informacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_estadisticos).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_mantenimiento).setVisible(false);
        }else if(Usuario.SESION_ACTUAL.getPerfil().getId()==6){
            navigationView.getMenu().findItem(R.id.menu_modulo_solicitud).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_captacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_metodologia).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_modulo_informacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_estadisticos).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_modulo_mantenimiento).setVisible(false);
        }
        tipo.setText(Usuario.SESION_ACTUAL.getPerfil().getNombre_Perfil());
        String ruta=Usuario.SESION_ACTUAL.getFoto();


        Glide.with(this).load(ruta).error(R.drawable.user_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(foto);
    }
    private void navigation_init() {

        navigationView=(NavigationView) findViewById(R.id.nav_view);
        //How to change elements in the header programatically
        View headerView = navigationView.getHeaderView(0);
        usuario = headerView.findViewById(R.id.username_principal);
        tipo = headerView.findViewById(R.id.tipo_principal);
        foto=headerView.findViewById(R.id.profile_image);
        navigationView.setNavigationItemSelectedListener(this);
        Setear_usuario();
    }
    private void mostrar_vistas() {
        if(getIntent().getStringExtra("o")==null){
            displayView(0);
        }else if(getIntent().getStringExtra("o").equalsIgnoreCase("o1")){
            displayView(1);
        }else if(getIntent().getStringExtra("o").equalsIgnoreCase("o2")){
            displayView(3);

        }else if(getIntent().getStringExtra("o").equalsIgnoreCase("o3")){
            displayView(2);
        }else if(getIntent().getStringExtra("o").equalsIgnoreCase("o4")){
            displayView(4);
        }

        else if(getIntent().getStringExtra("o").equalsIgnoreCase("o5")){
            displayView(5);
        }

        else if(getIntent().getStringExtra("o").equalsIgnoreCase("o6")){
            displayView(6);
        }


    }
    private void Toolbar_iniz() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void MetodoDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        ;
        toggle.syncState();
    }
    public void onBackPressed() {
        /**DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         if (drawer.isDrawerOpen(GravityCompat.START)) {
         drawer.closeDrawer(GravityCompat.START);
         } else {
         super.onBackPressed();
         }*/
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_1) {
            displayView(0);
            toolbar.setTitle("Inicio");
        }
        else if (id == R.id.nav_2) {
            displayView(1);
            toolbar.setTitle("Captación");
        }
        else if (id == R.id.nav_6) {
            displayView(2);
            toolbar.setTitle("Metodologia");

        }

        else if (id == R.id.menu_modulo_solicitud) {
            displayView(3);
            toolbar.setTitle("Solicitudes de Usuarios");
        }

        else if (id == R.id.nav_8) {
            displayView(4);
            toolbar.setTitle("Estadisticos");

        }
       else if (id == R.id.mant_generales) {
            displayView(5);
            toolbar.setTitle("Mantenimiento General");
        }
        else if (id == R.id.info_general) {
            displayView(6);
            toolbar.setTitle("Información General");
        }




        if (id == R.id.nav_salir) {

            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(PrincipalActivity.this);
            builder.setTitle("SALIR")
                    .setMessage("¿Desea Cerrar Sesión?")
                    .setPositiveButton("SI",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(PrincipalActivity.this,LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
            builder.show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void displayView(int position) {
        fragment = null;
        //String fragmentTags = "";
        switch (position) {
            case 0:
                fragment = new MainFragment();
                break;
            case 1:
                fragment = new CaptacionFragment();
                break;
            case 2:
                fragment = new MetodologiaFragment();
                break;
            case 3:
                fragment = new SolicitudesFragment();
                break;

            case 4:
                fragment = new EstadisticoFragment();
                break;

            case 5:
                fragment = new MantenimientoFragment();
                break;

            case 6:
                fragment = new InformacionFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }

}
