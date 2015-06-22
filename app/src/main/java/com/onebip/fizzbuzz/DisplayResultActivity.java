package com.onebip.fizzbuzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.faustinelli.android.fizzbuzzs.R;


public class DisplayResultActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_result);
        mTextView = (TextView)findViewById(R.id.fizzbuzz_result);

        Intent intent = getIntent();
        String result = intent.getStringExtra(InsertNumberActivity.EXTRA_RESULT);

        mTextView.setText(result);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.fizzbuzz_result_item) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
