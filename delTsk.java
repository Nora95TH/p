package com.example.pro;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class delTsk extends AppCompatActivity {

    private EditText etID;
    private TextView naTxt, paTxt, emTxt, crTxt,phTxt;
    private MyDBHandler dbHandler;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_tsk);

        etID = (EditText) findViewById(R.id.idInp);
        naTxt = (TextView) findViewById(R.id.naTxtView);
        phTxt = (TextView) findViewById(R.id.phTxtView);
        paTxt = (TextView) findViewById(R.id.paTxtView);
        emTxt = (TextView) findViewById(R.id.emTxtView);
        crTxt = (TextView) findViewById(R.id.crTxtView);
        dbHandler = new MyDBHandler(getApplicationContext());
        db = dbHandler.getWritableDatabase();
    }



    public void shwIDInfo(View v){
        String id = etID.getText().toString();

        if ( id.isEmpty()  )
        {
            Toast.makeText(getApplicationContext(), "PLease Fill missing data ", Toast.LENGTH_LONG).show();
            return;
        }

        String sqlStmt = "SELECT * FROM "+ dbHandler.TABLE_NAME
                + " where " + dbHandler.COLUMN_RECID + " = ?";

        Cursor c = db.rawQuery(sqlStmt, new String[] {id});

        if(!c.moveToFirst())
        {
            Toast.makeText(getApplicationContext(), "No ID has matched ", Toast.LENGTH_LONG).show();
            return;
        }
        naTxt.setText(c.getString(1));
        phTxt.setText(c.getString(2));
        paTxt.setText(c.getString(3));
        emTxt.setText(c.getString(4));
        crTxt.setText(c.getString(5));
        c.close();

    }

    public void delTsk(View v){

        String id = etID.getText().toString();

        if ( id.isEmpty()  )
        {
            Toast.makeText(getApplicationContext(), "PLease Fill missing data ", Toast.LENGTH_LONG).show();
            return;
        }

        Cursor c = db.rawQuery(" SELECT * FROM " + dbHandler.TABLE_NAME + " where " + dbHandler.COLUMN_RECID + " = ? ", new String[]{id});
        if (c.moveToFirst()) {
            db.execSQL(" delete     from " + dbHandler.TABLE_NAME + " where " + dbHandler.COLUMN_RECID + " = ? ", new String[]{id});
            String tstMsg;
            tstMsg = "Id: " + id + "is deleted";

            Toast.makeText(getApplicationContext(), tstMsg, Toast.LENGTH_LONG).show();
            etID.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), " Invalid ID ", Toast.LENGTH_LONG).show();}

    }

    public void backTo(View v){

        Intent i = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(i);
        dbHandler.close();
        finish();

    }
}