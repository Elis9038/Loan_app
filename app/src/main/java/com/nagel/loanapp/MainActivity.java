package com.nagel.loanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText txtCost, txtLoan, txtRate, txtPaym, txtYear, txtTerm;
    private Button btnAmortisation, btnCalculate, btnClear;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAmortisation = findViewById(R.id.btnAmortisation);

        btnAmortisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Loan.getInstance().getPeriods() > 0) {
                    startActivity(new Intent(MainActivity.this, PlanActivity.class));
                }
            }
        });
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        txtPaym = findViewById(R.id.txtPaym);
        txtPaym.setEnabled(false);
        txtCost = findViewById(R.id.txtCost);
        txtLoan = findViewById(R.id.txtLoan);
        txtRate= findViewById(R.id.txtRate);
        txtYear = findViewById(R.id.txtYear);
        txtTerm = findViewById(R.id.txtTerm);
    }

    public void onClear(View view) {
        txtCost.setText("");
        txtLoan.setText("");
        txtRate.setText("");
        txtPaym.setText("");
        txtYear.setText("");
        txtTerm.setText("");
        Loan.getInstance().setPrincipal(1);
        Loan.getInstance().setInterestRate(1);
        Loan.getInstance().setPeriods(1);
        txtCost.requestFocus();
    }

    public void onCalculate(View view) {
        double cost = 0;
        double loan;
        double rate;
        year = 0;
        int term = 0;
        try {
            String text = txtCost.getText().toString().trim();
            if (text.length() > 0) {
                cost = Double.parseDouble(text);
                if (cost <= 0) throw new Exception();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Exception accured: " + ex, Toast.LENGTH_SHORT).show();
            txtCost.requestFocus();
            return;
        }
        try {
            loan = Double.parseDouble(txtLoan.getText().toString().trim());
            if (loan < 0) throw new Exception();
        } catch (Exception ex) {
            Toast.makeText(this, "Exeption accured: " + ex, Toast.LENGTH_SHORT).show();
            txtLoan.requestFocus();
            return;
        }
        try {
            rate = Double.parseDouble(txtRate.getText().toString().trim());
            if (rate < 0 && rate < 50) throw new Exception();
        } catch (Exception ex) {
            Toast.makeText(this, "Exception accured: " + ex, Toast.LENGTH_SHORT).show();
            txtRate.requestFocus();
            return;
        }
        try {
            year = Integer.parseInt(txtYear.getText().toString());
            if (year <= 0 || year > 60) throw new Exception();
        } catch (Exception ex) {
            Toast.makeText(this, "Exception accured: " + ex, Toast.LENGTH_SHORT).show();
            txtYear.requestFocus();
            return;
        }
        try {
            term = Integer.parseInt(txtTerm.getText().toString());
            Loan loanL = new Loan();
            loanL.setPeriods(term);
            if (term <= 0 || term > 12) throw new Exception();
        } catch (Exception ex) {
            Toast.makeText(this, "Exception accured: " + ex, Toast.LENGTH_SHORT).show();
            txtTerm.requestFocus();
            return;
        }
        Loan.getInstance().setPrincipal(loan + cost);
        Loan.getInstance().setInterestRate(rate / 100 / term);
        Loan.getInstance().setPeriods(year * term);
        txtPaym.setText(String.format(Locale.getDefault(),"%.2f", Loan.getInstance().payment()));
    }

    public void onAmort(View view) {
        if (Loan.getInstance().getPeriods() > 0) {
            Intent intent = new Intent(this, PlanActivity.class);
            startActivity(intent);
        }
    }

    public String getString(EditText t) {
        return t.getText().toString();
    }

    public int getYear() {
        return year;
    }

}

