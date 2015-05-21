package com.fossdevs.fsmkcamp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TabHost;


public class Home extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        TabHost mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec("SCHEDULE").setIndicator("SCHEDULE").setContent(new Intent(this  ,Schedule.class )));
        mTabHost.addTab(mTabHost.newTabSpec("VOLUNTEERS").setIndicator("VOLUNTEERS").setContent(new Intent(this , Volunteers.class )));
        mTabHost.addTab(mTabHost.newTabSpec("CONTACTS").setIndicator("CONTACTS").setContent(new Intent(this , ContactUs.class )));
        mTabHost.setCurrentTab(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
