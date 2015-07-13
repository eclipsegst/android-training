package com.example.zz.admob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class InterstitialActivity extends AppCompatActivity {
    private Button mShowButton;
    private InterstitialAd mInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        mShowButton = (Button) findViewById(R.id.showButton);
        mShowButton.setEnabled(false);
    }

    /**
     * loadInterstitial
     * - Create InterstitialAd
     * - Set Ad Unite Id
     * - Create Listener: onAdLoaded - Show, onAdFailedToLoad - Error
     * - Create AdRequest & LoadAd
     */
    public void loadInterstitial(View unusedView) {
        mShowButton.setEnabled(false);
        mShowButton.setText(getResources().getString(R.string.interstitial_loading));

        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        mInterstitial.setAdListener(new ToastAdListener(this) {
            @Override
            public void onAdLoaded() {
                super.onAdClosed();
                mShowButton.setText(getResources().getString(R.string.interstitial_show));
                mShowButton.setEnabled(true);
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                mShowButton.setText(getmErrorReason());
            }
        });

        AdRequest ar = new AdRequest.Builder().build();
        mInterstitial.loadAd(ar);
    }

    /**
     * showInterstitial
     * @param ununsedView
     * - Check that Ad is loaded & show it
     * - Reset button
     */
    public void showInterstitial(View ununsedView) {
        if (mInterstitial.isLoaded()) {
            mInterstitial.show();
        }

        mShowButton.setText(getResources().getString(R.string.interstitial_not_ready));
        mShowButton.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_interstitial, menu);
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
