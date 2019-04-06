package com.example.pro;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class insTsk extends AppCompatActivity {

    private EditText etName, etPhone , etPass , etEmail , etCredit ;
    private MyDBHandler dbHandler;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_tsk);

        etName = (EditText) findViewById(R.id.edName);
        etPhone = (EditText) findViewById(R.id.edPhone);
        etPass = (EditText) findViewById(R.id.edPass);
        etEmail = (EditText) findViewById(R.id.edEmil);
        etCredit = (EditText) findViewById(R.id.edCredit);
        dbHandler = new MyDBHandler(getApplicationContext());
        db = dbHandler.getWritableDatabase();
    }

    public void addButtonClicked(View v){

        String nameStr = etName.getText().toString();
        String phoneStr = etPhone.getText().toString();
        String passStr = etPass.getText().toString();
        String emailStr = etEmail.getText().toString();
        String creditStr = etCredit.getText().toString();

        /*final int min = 20;
        final int max = 80;
        final int random = new Random().nextInt((max - min) + 1) + min;*/


        if ( nameStr.isEmpty() || phoneStr.isEmpty() || passStr.isEmpty() || emailStr.isEmpty()|| creditStr.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "PLease Fill missing data ", Toast.LENGTH_LONG).show();
            return;
        }


        db.execSQL("insert into "+ dbHandler.TABLE_NAME + "("+ dbHandler.COLUMN_NAME + "," +
                dbHandler.COLUMN_PHONE + "," + dbHandler.COLUMN_PASS + "," + dbHandler.COLUMN_CREDIT + "," + dbHandler.COLUMN_EMAIL + ") VALUES (?,?,?,?,?)", new String [] {nameStr, phoneStr, passStr, emailStr, creditStr});


        String tstMsg ="Your account is inserted" + "Your Locker is " ;
        Toast.makeText(getApplicationContext(), tstMsg, Toast.LENGTH_LONG).show();
        // db.close();

        etName.setText("");
        etPhone.setText("");
        etCredit.setText("");
        etPass.setText("");
        etEmail.setText("");

    }

    public void backTo(View v) {
        Intent i = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(i);
        dbHandler.close();
        finish();
    }
}