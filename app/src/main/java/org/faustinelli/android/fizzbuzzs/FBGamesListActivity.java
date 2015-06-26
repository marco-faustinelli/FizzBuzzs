package org.faustinelli.android.fizzbuzzs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.onebip.onedk.OneDk;


public class FBGamesListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // footer banner in the list view
        OneDk.getInstance().init(this, "7ecb968d-69ce-4a9d-9329-f90223091926");
        // interstitial
        //OneDk.getInstance().showInterstitial(this, "f8ceafff-b80f-45cc-b00e-a22891e9cb1f");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new FBGamesListFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}
