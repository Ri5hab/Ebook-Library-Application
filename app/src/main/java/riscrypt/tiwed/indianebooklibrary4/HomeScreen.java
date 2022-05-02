package riscrypt.tiwed.indianebooklibrary4;



import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class HomeScreen extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    RecyclerView rv;
    ProgressBar pb1;
    SwipeRefreshLayout srl;
    myAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        toolbar=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        pb1=(ProgressBar)findViewById(R.id.pb);
        pb1.setVisibility(View.VISIBLE);


        nav=(NavigationView)findViewById(R.id.navmenu);
        srl=(SwipeRefreshLayout)findViewById(R.id.sr);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_home:
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(HomeScreen.this,HomeScreen.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_share:
                        try {
                            Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_LONG).show();
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Application name");
                            String shareMessage = "\nDownload Free Indian eBooks Library app.(One Lakh Plus Books)\n";
                            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            startActivity(Intent.createChooser(shareIntent, "choose one"));
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        }catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"slow server",Toast.LENGTH_SHORT).show();
                        }
                    case R.id.menu_add:
                        //  Toast.makeText(getApplicationContext(),"Add",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(HomeScreen.this,Firebasesaver.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_search:
                        Toast.makeText(getApplicationContext(),"connect search",Toast.LENGTH_LONG).show();
                        MenuItem searchh=(MenuItem)findViewById(R.id.menu_search);
                        SearchView searchView=(SearchView)searchh.getActionView();
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {
                                processsearch(s);
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {
                                processsearch(s);
                                return false;
                            }
                        });

                        break;
                }

                return true;
            }
        });


        DataSnapshot dataSnapshot =null;

        rv=(RecyclerView)findViewById(R.id.rv1);
        rv.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options;
        options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Ebook"),Model.class).build();



        adapter=new myAdapter(options);
        rv.setAdapter(adapter);

        //     Toast.makeText(getApplicationContext(),"total"+td,Toast.LENGTH_SHORT).show();

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                FirebaseRecyclerOptions<Model> options;
                options = new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Ebook")
                                .orderByKey().limitToLast((int)(Math.random()*50)), Model.class).build();

                adapter = new myAdapter(options);
                adapter.startListening();
                rv.setAdapter(adapter);



                srl.setRefreshing(false);
            }

          /*  private int generateRandom() {
                int min=10;
                int max=20;
                int a=(int)(Math.random()*(max-min+1)+min);
                return 0;
            }*/

        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }

    @Override
    public boolean isFinishing() {
        pb1.setVisibility(View.INVISIBLE);
        return super.isFinishing();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.iconmenu, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                //   adapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            // Toast.makeText(getApplicationContext(),"connect add",Toast.LENGTH_LONG).show();
            Intent intent3 = new Intent(HomeScreen.this, Firebasesaver.class);
            startActivity(intent3);
        }
  /*      if(id==R.id.menu_search)
        {
            Toast.makeText(getApplicationContext(),"connect search",Toast.LENGTH_LONG).show();
            MenuItem searchh=(MenuItem)findViewById(R.id.menu_search);
            SearchView searchView=(SearchView)item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    processsearch(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    processsearch(s);
                    return false;
                }
            });


        }else
        {
            Toast.makeText(getApplicationContext()," not connect search",Toast.LENGTH_LONG).show();
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void processsearch(String s) {


        FirebaseRecyclerOptions<Model> options;
        options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Ebook").orderByChild("name")
                        .startAt(s.toUpperCase()).endAt(s.contains(toString().toLowerCase())+"\uf8ff"),Model.class).build();

        adapter=new myAdapter(options);
        adapter.startListening();
        rv.setAdapter(adapter);


    }

    }



