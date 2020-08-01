package com.example.personaldev.policeonduty.dashboard.view_criminal_report;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.personaldev.policeonduty.R;
import com.example.personaldev.policeonduty.dashboard.main.Dashboard;
import com.example.personaldev.policeonduty.dashboard.view_criminal_report.criminal_profile.Inspect_Criminal;
import com.example.personaldev.policeonduty.dashboard.view_criminal_report.criminal_profile.selectedCriminalActivty;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Criminal_detailed_view extends AppCompatActivity implements com.example.personaldev.policeonduty.dashboard.view_criminal_report.criminalAdapter.SelectedCriminal {

    Toolbar toolbar;
    RecyclerView recyclerView;

    ImageView backActivity;

    List<CriminalModel> criminalModelList = new ArrayList<>();
    private FirebaseDatabase mdataBase;
    private DatabaseReference mdataReference;
    List<Names> criminalNames = new ArrayList<Names>();
    String[] names = new String[]{"Abhay Choudhary", "Abhishek Agrawal", "Anchal Gupta", "Aayushi Singhal", "Kuldeep Patel", "Nayan Sori", "Chaitali Choudhary", "Ultimate Criminal", "Mein", "Tum", "Hum Sab"};

    criminalAdapter criminalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_criminal_detailed_view);

        backActivity = findViewById(R.id.general_back_activity);
        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Criminal_detailed_view.this, Dashboard.class));
            }
        });

        //Getting Content From Firebase
        mdataBase = FirebaseDatabase.getInstance();
        mdataReference = mdataBase.getReference();
        Log.i("DRET","Works");
        mdataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("DRET","3rd Base");
                try {
                    for (DataSnapshot ds : snapshot.child("criminals").getChildren()) {
                        Log.i("DRET",ds.getKey());
                        Names name = ds.getValue(Names.class);
                        name.setId(ds.getKey());

                        criminalNames.add(name);

                        System.out.println("s");
                    }
                    initCriminalNames(criminalNames);
                }
                catch (Exception e){
                    Log.i("DRET",e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        ///
        recyclerView = findViewById(R.id.Criminal_RecyclerView);
        toolbar = findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));



    }
    public void initCriminalNames(List<Names> ciminalNames){
        for (Names s : criminalNames) {
            CriminalModel criminalModel = new CriminalModel(s.name,s.getId());

            criminalModelList.add(criminalModel);
        }

        criminalAdapter = new criminalAdapter(criminalModelList, this);

        recyclerView.setAdapter(criminalAdapter);
    }

    @Override
    public void selectedCriminal(CriminalModel criminalModel) {
        startActivity(new Intent(Criminal_detailed_view.this, selectedCriminalActivty.class).putExtra("data",criminalModel));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_view);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                criminalAdapter.getFilter().filter(newText);

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.search_view)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
