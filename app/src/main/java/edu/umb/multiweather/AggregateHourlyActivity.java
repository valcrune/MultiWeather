package edu.umb.multiweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;

public class AggregateHourlyActivity extends AppCompatActivity {

    DecimalFormat f = new DecimalFormat("#0.00");
    final int MEAN = 0;
    final int MAX = 1;
    final int MIN = 2;

    private int mode = 0;

    private TextView test;

    private Button meanButton;
    private Button maxButton;
    private Button minButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggregate_hourly);

        test = findViewById(R.id.test);

        meanButton = findViewById(R.id.meanButton);
        maxButton = findViewById(R.id.maxButton);
        minButton = findViewById(R.id.minButton);

        meanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = MEAN;
                updateHourly();
            }
        });
        maxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = MAX;
                updateHourly();
            }
        });
        minButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = MIN;
                updateHourly();
            }
        });

        Intent intent = getIntent();

        Date date = new Date(intent.getIntExtra("startTime", 0) * 1000L);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String formatted = format.format(date);

        String[] hours = new String[12];
        hours[0] = formatted;
        //test.setText(formatted + "\n");

        for (int i = 1; i < 12; i++) {
            Date newDate = date;
            newDate.setMinutes(date.getMinutes() + 60);
            hours[i] = format.format(newDate);
            //test.append(hours[i] + "\n");
        }

        double[][] temp = new double[3][12];
        double[][] pop = new double[3][12];
        double[][] wind = new double[3][12];


        for (int i = 0; i < 12; i++) {
            temp[0][i] = intent.getDoubleExtra("meanTemp" + i, 0);
            pop[0][i] = intent.getDoubleExtra("meanPop" + i, 0);
            wind[0][i] = intent.getDoubleExtra("meanWind" + i, 0);
        } for (int j = 0; j < 12; j++) {
            temp[1][j] = intent.getDoubleExtra("maxTemp" + j, 0);
            pop[1][j] = intent.getDoubleExtra("maxPop" + j, 0);
            wind[1][j] = intent.getDoubleExtra("maxWind" + j, 0);
        } for (int k = 0; k < 12; k++) {
            temp[2][k] = intent.getDoubleExtra("minTemp" + k, 0);
            pop[2][k] = intent.getDoubleExtra("minPop" + k, 0);
            wind[2][k] = intent.getDoubleExtra("minWind" + k, 0);
        }

        updateHourly();
        ButterKnife.bind(this);


    }

    private void updateHourly() {
        if (mode == MEAN) {
            test.setText("MEAN");
        } else if (mode == MAX) {
            test.setText("MAX");
        } else if (mode == MIN) {
            test.setText("MIN");

        }
    }
}