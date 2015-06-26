package com.onebip.fizzbuzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onebip.onedk.OneDk;
import com.onebip.onedk.PurchasableItem;

import org.faustinelli.android.fizzbuzzs.R;
import org.faustinelli.android.fizzbuzzs.model.FBGame;
import org.faustinelli.android.fizzbuzzs.model.FBGames;

import java.util.UUID;

// TODO: rename to NumberInputActivity
public class InsertNumberActivity extends Activity {

    public static final String EXTRA_RESULT = "com.onebip.fizzbuzz.MESSAGE";
    private static final int REQUEST_CODE_PURCHASE_FIZZ = 101;
    private static final int REQUEST_CODE_PURCHASE_BUZZ = 102;
    private static final int REQUEST_CODE_PURCHASE_FIZZBUZZ = 103;
    private Handler mHandler = new Handler();

    private TextView mTextView;

    private FBGame sGame;
    private String sInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_insert_number);
        mTextView = (TextView) findViewById(R.id.number);

        sGame = new FBGame();

        Intent intent = getIntent();

        UUID gameId = (UUID) intent.getSerializableExtra(FBGame.EXTRA_GAME_ID);

        if (gameId != null) {
            Log.d(FBGames.TAG, "received input from intent!!!");
            sGame = FBGames.get(getApplicationContext()).getGame(gameId);
            sInput = sGame.getNumber();
        }

        mTextView.setText(sInput);
    }


    public void calculateFizzBuzz(View view) {
        Intent intent = new Intent(this, DisplayResultActivity.class);
        EditText numberInput = (EditText) findViewById(R.id.number);
        String sNumber = numberInput.getText().toString();
        sGame.setNumber(sNumber);

        Log.d(FBGames.TAG, "=====> setting number " + sNumber);

        Number number = new Number(Integer.parseInt(sNumber));

        String message = number.fizzBuzz();
        intent.putExtra(EXTRA_RESULT, message);
        startActivity(intent);
    }

    public void buyFizz(View view) {
        purchaseItem("1d60d09a-832d-40f1-8be4-9870003c0767", REQUEST_CODE_PURCHASE_FIZZ);
    }

    public void buyBuzz(View view) {
        purchaseItem("d7e3e07a-4621-46f2-b60d-0006a63b9496", REQUEST_CODE_PURCHASE_BUZZ);
    }

    public void buyFizzBuzz(View view) {
        purchaseItem("f052d5c3-7e86-4e6e-a692-be587ad05f2b", REQUEST_CODE_PURCHASE_FIZZBUZZ);
    }

    private void purchaseItem(String purchasableItemId, final int requestId) {
        OneDk.getInstance().loadPurchasableItem(
                purchasableItemId,
                new OneDk.PurchasableItemCallback() {
                    @Override
                    public void onCompleted(PurchasableItem item) {
                        OneDk.getInstance().startPurchase(
                                InsertNumberActivity.this,
                                item,
                                requestId);
                    }

                    @Override
                    public void onError() {
                        Log.e("INA", "Purchase failed or cancelled");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PURCHASE_FIZZ) {
            startChildActivity(resultCode, "FIZZ", "3");
        }
        if (requestCode == REQUEST_CODE_PURCHASE_BUZZ) {
            startChildActivity(resultCode, "BUZZ", "5");
        }
        if (requestCode == REQUEST_CODE_PURCHASE_FIZZBUZZ) {
            startChildActivity(resultCode, "FIZZBUZZ", "15");
        }
    }

    private void startChildActivity(int resultCode, String item, final String sNumber) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Purchase " + item + " completed", Toast.LENGTH_LONG).show();
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    runNumber(sNumber);
                }
            }, 2000);
        } else {
            Toast.makeText(this, "Purchase " + item + " failed or cancelled", Toast.LENGTH_LONG).show();
        }
    }

    private void runNumber(String sNumber) {
        Intent intent = new Intent(this, DisplayResultActivity.class);
        sGame.setNumber(sNumber);
        Log.d(FBGames.TAG, "=====> setting number " + sNumber);
        Number number = new Number(Integer.parseInt(sNumber));
        String message = number.fizzBuzz();
        intent.putExtra(EXTRA_RESULT, message);
        startActivity(intent);
    }

    /**
     * Added by Marco!!
     */
    @Override
    public void onPause() {
        super.onPause();
        FBGames.get(getApplicationContext()).saveGames();

        // interstitial
        if (Math.random() < 0.5) {
            OneDk.getInstance().showInterstitial(this, "f8ceafff-b80f-45cc-b00e-a22891e9cb1f");
        }
    }
}
