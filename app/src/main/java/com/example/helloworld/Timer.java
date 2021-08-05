package com.example.helloworld;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Timer extends AppCompatActivity implements TimerInterface {

    private Button startBtn;
    private RecyclerView swimmerRecview;
    private TextView txtTimer;
    private Handler handler;
    private int minutes, seconds, centiseconds = 0;
    private SwimmerRecViewAdapter adapter;

    private Stopwatch stopwatch = Stopwatch.createUnstarted(
            new Ticker() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public long read() {
                    return android.os.SystemClock.elapsedRealtimeNanos();
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ArrayList<Swimmer> swimmers = getIntent().getParcelableArrayListExtra("SWIMMERS");

        swimmerRecview = findViewById(R.id.swimmerRecView);
        adapter = new SwimmerRecViewAdapter(this, 2);
        adapter.setSwimmers(swimmers);

        swimmerRecview.setAdapter(adapter);
        swimmerRecview.setLayoutManager(new LinearLayoutManager(this));

        txtTimer =  findViewById(R.id.txtTimer);
        handler = new Handler();

        startBtn = findViewById(R.id.actionBtn);
        startBtn.setText("Start Timer");
        startBtn.setOnClickListener(v -> {
            if (startBtn.getText().toString().equals("Start Timer")) { //start
                startBtn.setText("Stop Timer");
                stopwatch.start();
                handler.post(runnable);
            }
            else if (startBtn.getText().toString().equals("Stop Timer")) { //stop
                startBtn.setText("Next");
                stopwatch.stop();
                currentCentiseconds.set(0);
                handler.removeCallbacks(runnable);
            }
            else { //next
                Intent intent = new Intent(Timer.this, SwimmerDetails.class);
                intent.putParcelableArrayListExtra("SWIMMERS", swimmers);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setCurrentCentiseconds(AtomicLong currentCentiseconds) {
    }

    @Override
    public AtomicLong getCurrentCentiseconds() {
        return currentCentiseconds;
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentCentiseconds.set(stopwatch.elapsed(TimeUnit.MILLISECONDS) / 10);

            seconds = (int) (currentCentiseconds.longValue() / 100);
            minutes = seconds / 60;
            seconds = seconds % 60;
            centiseconds =  (int) (currentCentiseconds.longValue() % 100);

            txtTimer.setText(String.format("%02d:%02d.%02d", minutes,seconds,centiseconds));
            
            handler.postDelayed(runnable, 10);
        }
    };
}
