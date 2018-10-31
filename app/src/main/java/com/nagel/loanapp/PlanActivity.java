package com.nagel.loanapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    GridView grid;
    private List<String> items = new ArrayList();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        MainActivity main = new MainActivity();

        items.add("Period");
        items.add("Interest");
        items.add("Repayment");
        items.add("Outstanding");

        for (int n = 1; n <= Loan.getInstance().getPeriods(); ++n)
        {
            items.add("" + n);
            items.add(String.format("%1.2f", Loan.getInstance().interest(n)));
            items.add(String.format("%1.2f", Loan.getInstance().repayment(n)));
            items.add(String.format("%1.2f", Math.abs(Loan.getInstance().outstanding(n))));
        }

        GridView grid = findViewById(R.id.grid);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
            }

        });


    }
}
