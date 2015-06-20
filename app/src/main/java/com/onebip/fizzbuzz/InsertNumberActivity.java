package com.onebip.fizzbuzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.faustinelli.android.fizzbuzzs.R;

// TODO: rename to NumberInputActivity
public class InsertNumberActivity extends Activity {

    public static final String EXTRA_RESULT = "com.onebip.fizzbuzz.MESSAGE";
    public static final String EXTRA_INPUT = "FIBU_INPUT";

    private TextView mTextView;
    private String sInput = "9999";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_number);
        mTextView = (TextView)findViewById(R.id.number);
        String sNumber = getIntent().getStringExtra(EXTRA_INPUT);
        if (sNumber != null) {
            sInput = sNumber;
        }
        mTextView.setText(sInput);
    }

    public void calculateFizzBuzz(View view) {
        Intent intent = new Intent(this, DisplayResultActivity.class);
        EditText numberInput = (EditText) findViewById(R.id.number);
        Number number = new Number(Integer.parseInt(numberInput.getText().toString()));
        String message = number.fizzBuzz();
        intent.putExtra(EXTRA_RESULT, message);
        startActivity(intent);
    }
}
