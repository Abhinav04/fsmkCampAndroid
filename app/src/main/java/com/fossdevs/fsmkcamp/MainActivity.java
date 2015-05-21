package com.fossdevs.fsmkcamp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStarted();
    }

    @Override
    protected void onResume() {
        getStarted();
        GcmKeepAlive gcm=new GcmKeepAlive(getApplicationContext());
        gcm.broadcastIntents();
        super.onResume();
    }

    private void getStarted() {
        DbHelper dbHelper=new DbHelper(getApplicationContext());
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        final Cursor c=db.rawQuery("SELECT rowid as _id,senderName,sentBy FROM notices GROUP BY sentBy",null);
        if(c.getCount()==0){
            setContentView(R.layout.empty_main);
        }else{
            setContentView(R.layout.activity_main);
            c.moveToFirst();
            ListView lv=(ListView) findViewById(R.id.list_notices);
            lv.setDividerHeight(5);

            int to[]={R.id.list_user_full_name,R.id.list_user_name};
            String []from={"senderName","sentBy"};
            SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(getApplicationContext(),R.layout.list_notices_view,c,from,to,1);
            lv.setAdapter(simpleCursorAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    c.moveToPosition(pos);
                    String user=c.getString(c.getColumnIndex("sentBy"));
                    Intent intent=new Intent(getApplicationContext(),DisplayNotices.class);
                    intent.putExtra("username",user);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.signup){
            DbHelper dbHelper=new DbHelper(getApplicationContext());
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            Cursor c=db.rawQuery("SELECT * FROM device_id;",null);
            if(c.getCount()==0){
                Intent intent=new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),"You already initialized the app",Toast.LENGTH_LONG).show();
            }
        }
        if (id == R.id.subscribe){
            Intent intent=new Intent(getApplicationContext(),Subscribe.class);
            startActivity(intent);
        }
        if(id==R.id.unsubscribe){
            Intent intent=new Intent(getApplicationContext(),Unsubscribe.class);
            startActivity(intent);
        }
        if(id==R.id.home){
            Intent intent=new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
        }
        if(id==R.id.about){
            Intent intent=new Intent(getApplicationContext(),About.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
