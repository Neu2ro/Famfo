package com.example.famfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {

    private EditText loanAmount;
    private EditText interest;
    private EditText year;
    private EditText month;
    private TextView lEmi;
    private TextView lTenure;
    private TextView lLoanAmount;
    private TextView lInterestPayble;
    private TextView lTotalPayment;
    private Button summaryBtn;
    private Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        loanAmount = (EditText)findViewById(R.id.loanamt);
        interest = (EditText)findViewById(R.id.interestpersent);
        year = (EditText)findViewById(R.id.year);
        month = (EditText)findViewById(R.id.month);

        lEmi = (TextView)findViewById(R.id.setEmi);
        lTenure = (TextView)findViewById(R.id.setTenure);
        lLoanAmount = (TextView)findViewById(R.id.setLoanAmt);
        lInterestPayble = (TextView)findViewById(R.id.setIP);
        lTotalPayment = (TextView)findViewById(R.id.setTotAmt);

        summaryBtn = (Button)findViewById(R.id.btnSummary);
        clearBtn = (Button)findViewById(R.id.btnClear);

        summaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double loanAmount1 = Integer.parseInt(loanAmount.getText().toString());
                double interest1 = Double.parseDouble(interest.getText().toString());
                int month1 = Integer.parseInt(month.getText().toString());
                int year1 = Integer.parseInt(year.getText().toString());

                int yearExcange = (year1 * 12) + month1;
                double principle = loanAmount1 * (interest1 / 100);
                double power = Math.pow(interest1 / 100 + 1, yearExcange);
                double sum = principle / (1 - (1 / power));
                double TotalInterest = sum * yearExcange - loanAmount1;
                double totalPayment2 = loanAmount1 + TotalInterest;

                sum = Math.round(sum * 100.0) / 100.0;
                TotalInterest = Math.round(TotalInterest * 100.0) / 100.0;
                totalPayment2 = Math.round(totalPayment2 * 100.0) / 100.0;

                lEmi.setText(String.valueOf(sum));
                lTenure.setText(String.valueOf(yearExcange) + " Month");
                lLoanAmount.setText(String.valueOf(loanAmount1));
                lInterestPayble.setText(String.valueOf(TotalInterest));
                lTotalPayment.setText(String.valueOf(totalPayment2));
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loanAmount.setText("");
                interest.setText("");
                year.setText("");
                month.setText("");

                lEmi.setText("");
                lTotalPayment.setText("");
                lTenure.setText("");
                lLoanAmount.setText("");
                lInterestPayble.setText("");
            }
        });
    }
}