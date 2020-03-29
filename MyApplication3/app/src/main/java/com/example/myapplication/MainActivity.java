package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {

    TextView balance;
    Button add;
    Button minus;
    EditText mdy;
    EditText amount;
    EditText demo;
    TextView history;

    double curBalance;
    String mBalance;
    String transaction;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdy = (EditText) findViewById(R.id.mdy);
        amount = (EditText) findViewById(R.id.amount);
        demo = (EditText) findViewById(R.id.demo);
        add = (Button) findViewById(R.id.add);
        minus = (Button) findViewById(R.id.minus);
        history = (TextView) findViewById(R.id.listhistory);
        balance = (TextView) findViewById(R.id.balance);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor  = pref.edit();
        balance.setText(pref.getString("curBalance", "Current Balance: $0"));
        curBalance = pref.getFloat("balance", 0);
        history.setText(pref.getString("history", ""));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input1 = mdy.getText().toString();
                String input2 = amount.getText().toString();
                String input3 = demo.getText().toString();

                double added = Double.parseDouble(input2);
                curBalance += added;
                editor.putFloat("balance", (float) curBalance);
                editor.commit();
                mBalance = "Current Balance: $" + (double) pref.getFloat("balance", 0);
                editor.putString("curBalance", mBalance);
                editor.commit();
                balance.setText(pref.getString("curBalance", ""));

                transaction = "Added $" + input2 + " on " + input1 + " from " + input3 + ".\n" + pref.getString("history","");
                editor.putString("history", transaction);
                editor.commit();
                history.setText(pref.getString("history", ""));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input1 = mdy.getText().toString();
                String input2 = amount.getText().toString();
                String input3 = demo.getText().toString();

                double spend = Double.parseDouble(input2);
                curBalance -= spend;
                editor.putFloat("balance", (float) curBalance);
                editor.commit();

                mBalance = "Current Balance: $" + (double) pref.getFloat("balance", 0);
                editor.putString("curBalance", mBalance);
                editor.commit();
                balance.setText(pref.getString("curBalance", ""));

                transaction = "Spend $" + input2 + " on " + input1 + " from " + input3 + ".\n" +  pref.getString("history","");

                editor.putString("history", transaction);
                editor.commit();

                history.setText(pref.getString("history", ""));
            }
        });
    }
}
