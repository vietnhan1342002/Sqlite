package com.example.tiptime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioButton amazing , good , okay;
    EditText edt_cost;
    TextView txt_monney;
    Button caculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        caculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_monney.setText("Tip of monney: $" + caculator_tips());
                Toast.makeText(MainActivity.this, "Caculator completed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhxa() {
        amazing = findViewById(R.id.btn_amazing);
        good = findViewById(R.id.btn_good);
        okay = findViewById(R.id.btn_okay);
        caculator = findViewById(R.id.btn_caculator);
        edt_cost = findViewById(R.id.edit_cost);
        txt_monney = findViewById(R.id.txt_tips);
    }
    private double caculator_tips(){
        Double cost = 0.0;
        if(amazing.isChecked()){
            cost =  (Double.parseDouble(edt_cost.getText().toString())) * 0.20;
        }else if(good.isChecked()){
            cost =  (Double.parseDouble(edt_cost.getText().toString())) * 0.18;
        }else if(okay.isChecked()){
            cost =  (Double.parseDouble(edt_cost.getText().toString())) * 0.15;
        }
        return cost;
    }
}