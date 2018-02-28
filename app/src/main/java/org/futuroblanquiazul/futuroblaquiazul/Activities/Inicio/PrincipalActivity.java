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
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Fragments.CaptacionFragment;
import org.futuroblanquiazul.futuroblaquiazul.Fragments.MainFragment;
import org.futuroblanquiazul.futuroblaquiazul.Fragments.MetodologiaFragment;
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
    Menu captacion,estadistico,mantenimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        captacion=(Menu)findViewById(R.id.menu_modulo_captacion);
        estadistico=(Menu)findViewById(R.id.menu_modulo_estadisticos);
        //mantenimiento=(Menu)findViewById(R.id.menu_modulo_mantenimiento);
        mostrar_vistas() ;
        Toolbar_iniz();
        MetodoDrawer();
        navigation_init();
    }
    private void Setear_usuario() {

        usuario.setText(Usuario.SESION_ACTUAL.getUsuario());
        tipo.setText("Administrador");
        //String ruta="http://alianza2.esy.es/alianza/imagenes/"+ Usuario_Sesion.SESION.getFoto();
        //Glide.with(this).load(ruta).into(foto);
    }
    private void navigation_init() {

        navigationView=(NavigationView) findViewById(R.id.nav_view);
        //How to change elements in the header programatically
        View headerView = navigationView.getHeaderView(0);
        usuario = (TextView) headerView.findViewById(R.id.username_principal);
        tipo = (TextView) headerView.findViewById(R.id.tipo_principal);
        foto=(ImageView)headerView.findViewById(R.id.profile_image);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void mostrar_vistas() {
        if(getIntent().getStringExtra("o")==null){
            displayView(0);
        }else if(getIntent().getStringExtra("o").equalsIgnoreCase("o1")){
            displayView(1);
        }else if(getIntent().getStringExtra("o").equalsIgnoreCase("o2")){
            displayView(2);
        }else if(getIntent().getStringExtra("o").equalsIgnoreCase("o5")){
            displayView(5);
        }else if(getIntent().getStringExtra("o").equalsIgnoreCase("o8")){
            displayView(8);
        }else if(getIntent().getStringExtra("o").equalsIgnoreCase("o9")){
            displayView(11);
        }else if(getIntent().getStringExtra("o").equalsIgnoreCase("o3")){
            displayView(3);
        }else if(getIntent().getStringExtra("o").equalsIgnoreCase("o20")){
            displayView(20);
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
        // Handle navigation view item clicks here.
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


       /* else if (id == R.id.mant_usuario) {
            displayView(5);
            toolbar.setTitle("Usuarios");
        }
        else if (id == R.id.mant_perfiles) {
            displayView(6);
            toolbar.setTitle("Perfiles");
        }*/

       /* else if (id == R.id.mant_categorias) {
            displayView(7);
            toolbar.setTitle("Categorias");
        }
        else if (id == R.id.mant_jugadores) {
            displayView(8);
            toolbar.setTitle("Jugadores");
        }
        else if (id == R.id.mant_eventos_barrio) {
            displayView(9);
            toolbar.setTitle("Campeonatos");
        }
        else if (id == R.id.mant_barrio) {
            displayView(21);
            toolbar.setTitle("Barrio Intimo -Mant");
        }
        else if (id == R.id.mant_mis_equipos) {
            displayView(10);
            toolbar.setTitle("Mis Equipos");

        }*/

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
           /* case 3:
                fragment = new FragmentEstadistico();
                break;
            case 4:
                Intent intent=new Intent(this, ListaCategoriasPlantel.class);
                startActivity(intent);
                break;
            case 5:
                fragment = new FragmentUsuarios();
                break;
            case 6:
                fragment = new FragmentPerfiles();
                break;
            case 7:
                fragment = new FragmentCategorias();
                break;
            case 8:
                fragment = new FragmentJugadores();
                break;
            case 9:
                fragment = new FragmentEventosEstadistico();
                break;
            case 10:
                fragment = new FragmentMisEquipos();
                break;
            case 11:
                fragment = new FragmentSeguimiento();
                break;

            case 20:
                fragment = new FragmentBarrio();
                break;
            case 21:
                fragment = new FragmentGestionBarrio();
                break;
            case 23:
                fragment = new FragmentReporteCaptacion();
                break;
             */
            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }

}
