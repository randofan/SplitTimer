package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class SwimmerDetails extends AppCompatActivity {

    private RecyclerView swimmerRecview;
    private SwimmerRecViewAdapter adapter;
    private Button resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swimmer_details);
        ArrayList<Swimmer> swimmers = getIntent().getParcelableArrayListExtra("SWIMMERS");

        swimmerRecview = findViewById(R.id.swimmerRecView);
        adapter = new SwimmerRecViewAdapter(this, 3);
        adapter.setSwimmers(swimmers);

        swimmerRecview.setAdapter(adapter);
        swimmerRecview.setLayoutManager(new LinearLayoutManager(this));

        resetBtn = findViewById(R.id.actionBtn);
        resetBtn.setOnClickListener(v -> {
            // TODO go back to MainActivity
        });

    }
}