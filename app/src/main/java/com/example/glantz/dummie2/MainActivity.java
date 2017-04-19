package com.example.glantz.dummie2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {
    private Context ctx = this;
    private TextView name;
    private TextView password;
    private String DB_pathway = "/data/data/com.example.glantz.dummie2/databases/dbtest";
    private TextView HelloWorld;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button Creation
        Button fetchButton = (Button) findViewById(R.id.FetchButton);
        Button readbutton = (Button) findViewById(R.id.ReadButton);
        Button writebutton = (Button) findViewById(R.id.WriteButton);
        Button updateButton = (Button) findViewById(R.id.UpdateButton);


        //Initialize SQLiteDatabase
        final SQLiteDatabase db = new databasehandler(ctx).getWritableDatabase();

        //Add listeners and methods to all the buttons
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatabaseInfo(db);
            }
        });
        readbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFileAsString();
            }
        });
        writebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeXML();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoDB(db);
            }
        });
    }

    /**
     * Displays name and password column in the textfield name and password.
     *
     * @param SQLiteDatabase
     */
    private void getDatabaseInfo(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT name, password FROM user;", null);
        String names = "Name:" + "\n";
        String passwords = "Password:" + "\n";
        while (cursor.moveToNext()) {
            names += cursor.getString(cursor.getColumnIndex("name")) + "\n";
            passwords += cursor.getString(cursor.getColumnIndex("password")) + "\n";
        }
        name = (TextView) findViewById(R.id.name);
        name.setText(names);
        password = (TextView) findViewById(R.id.password);
        password.setText(passwords);
        HelloWorld = (TextView) findViewById(R.id.helloworld);
        HelloWorld.setText("Content from SQLite DB");
    }

    /**
     * Drops the user table if it exist and recreates it with some sample data.
     *
     * @param SQLiteDatabase
     */
    private void insertIntoDB(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("CREATE TABLE user( userName_email TEXT ,name TEXT ,password TEXT);");
        db.execSQL("INSERT INTO user(userName_email,name,password) VALUES('hglantz@kth.se','Hampus','test');");
        db.execSQL("INSERT INTO user(userName_email,name,password) VALUES('matkag@kth.se','Mattias','flumm');");
        db.execSQL("INSERT INTO user(userName_email,name,password) VALUES('johnmagn@kth.se','John','dumm');");
        db.execSQL("INSERT INTO user(userName_email,name,password) VALUES('eliasax@kth.se','Elias','knas');");
    }

    /**
     * Reads in tmp.xml file from external storage and displays it in the name textfield.
     */
    private void readFileAsString() {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new FileReader(Environment.getExternalStorageDirectory().toString() + "/tmp.xml"));
            char[] buf = new char[1024];
            int numRead = 0;

            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HelloWorld = (TextView) findViewById(R.id.helloworld);
        HelloWorld.setText("Content from XML File");
        name = (TextView) findViewById(R.id.name);
        name.setText(fileData.toString());
        password = (TextView) findViewById(R.id.password);
        password.setText("");
    }

    /**
     * Writes a preset String to a tmp.xml file
     */
    private void writeXML() {
        FileOutputStream stream = null;
        String tmp = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><company>\n <staff id=\"2\">\n\t\n\t<lastname>Mattias</lastname>\n\t<nickname>Halloj</nickname>\n\t<salary>2000000</salary>\n   <age>28</age></staff>\n</company>";
        try {
            stream = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/tmp.xml");
            stream.write(tmp.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * For future use possible to show the DB_pathway in the Hello World textfield.
     * Don't forget to add a button for this.
     */
    private void showDB_pathway() {
        HelloWorld = (TextView) findViewById(R.id.helloworld);
        HelloWorld.setText(DB_pathway);
        name = (TextView) findViewById(R.id.name);
        name.setText("");
        password = (TextView) findViewById(R.id.password);
        password.setText("");
    }
}
