package mobildev.iosm.com.priot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import mobildev.iosm.com.priot.Model.CarteStatistics;
import mobildev.iosm.com.priot.Model.Presence;
import mobildev.iosm.com.priot.Model.Profil;
import mobildev.iosm.com.priot.Model.Users;
import mobildev.iosm.com.priot.PreValent.PreValent;
import mobildev.iosm.com.priot.ViewHolder.ProfilViewHolder;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference ProfilRef,PresenceRef  ;
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private Query query,querypresence;
    private ArrayList<Presence> presenceEmplyer = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        ProfilRef = FirebaseDatabase.getInstance().getReference().child("Personne");
        query = ProfilRef.orderByChild("type").equalTo("0");
        Paper.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ici intialiser notre headerView
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        //recupperer le nom et le mette dans l'en-tete
        userNameTextView.setText(PreValent.CurrentOnLineUser.getUsername());

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PresenceRef = FirebaseDatabase.getInstance().getReference().child("Presence");
        querypresence= PresenceRef.orderByChild("entreSortie").equalTo("1");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.child("entreSortie").exists()){
                        if (postSnapshot.child("entreSortie").getValue().equals("1")) {
                            Presence presences = new Presence(postSnapshot.child("entreSortie").getValue().toString(), postSnapshot.child("id_Carte").getValue().toString(),postSnapshot.child("nom").getValue().toString(),postSnapshot.child("prenom").getValue().toString(),postSnapshot.child("image").getValue().toString(),postSnapshot.child("dateEntre").getValue().toString());
                            presenceEmplyer.add(presences);
                            Log.d("firebasedatPie",presences.getId_Carte());

                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
            }
        };
        PresenceRef.addValueEventListener(postListener);
    }
      /*  for (int i=0;i<PresenceRef.child("entreSortie").toString().length();i++ ) {

            if(PresenceRef.child("entreSortie").toString().equals("1")){
                Presence presences = new Presence(PresenceRef.child("entreSortie").toString(),PresenceRef.child("id_Carte").toString(),PresenceRef.child("nom").toString(),PresenceRef.child("prenom").toString(),PresenceRef.child("image").toString(),PresenceRef.child("dateEntre").toString());

                presenceEmplyer.add(presences);
                Log.d("entriesortie", presences.getEntreSortie());
            }

                    if(postSnapshot.child("id_Carte").exists()){
                        if (postSnapshot.child("id_Carte").getValue().equals(carteID)) {
                            CarteStatistics products = new CarteStatistics(postSnapshot.child("AnneMois").getValue().toString(), postSnapshot.child("dure").getValue().toString());
                            myProductsPie.add(products);
                            Log.d("firebasedatPie", products.getAnneeMois());

                        }
                    }




        }

    }*/
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Profil> options = new FirebaseRecyclerOptions.Builder<Profil>().setQuery(query, Profil.class).build();
        FirebaseRecyclerAdapter<Profil, ProfilViewHolder> Adapter = new FirebaseRecyclerAdapter<Profil, ProfilViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProfilViewHolder holder, int position, @NonNull final Profil model){
               // ProfilViewHolder holder1 ;
                holder.txtProfilName.setText(model.getNom());
                holder.txtProfilLastName.setText(model.getPrenom());
                holder.txtProfilRegistration.setText("Registration Date  = "+model.getDateInscription());
                holder.txtProfilIdCarte.setText("ID Carte : "+model.getId_Carte());
                    Log.d("presence",presenceEmplyer.toString());
                for(Presence  p : presenceEmplyer){

                    if(model.getId_Carte().equals(p.getId_Carte())) {
                        holder.active.setBackgroundResource(R.drawable.mybuttons);
                    }

                }
                 Picasso.get().load(model.getImage()).into(holder.imageView);

               holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, StatisticsActivity.class);
                        intent.putExtra("cid", model.getId_Carte());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profils_items_layout, parent, false);
                ProfilViewHolder holder = new ProfilViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(Adapter);
        Adapter.startListening();
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
         //   return true;
       // }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_orders)
        {

            startActivity(new Intent(HomeActivity.this,PresentActivity2.class));
        }
        else if (id == R.id.nav_categories)
        {

            startActivity(new Intent(HomeActivity.this,StagairesActivity.class));
        }
        else if (id == R.id.nav_settings)
        {
            startActivity(new Intent(HomeActivity.this,SittingActivity.class));
        }
        else if (id == R.id.nav_logout)
        {
            Paper.book().destroy();

            Intent intent =new Intent(HomeActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
