package com.ilifesmart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ilifesmart.weather.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class TeslaOauthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tesla_oauth);

        initialize();
    }

    private void initialize() {
        WebView webView = findViewById(R.id.teslaWebview);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("BBBB", "shouldOverrideUrlLoading: URL " + url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

        });

        try {
            String random86 = usingRandom(86);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(random86.getBytes());
            String code_challenge = Base64.encodeToString(md.digest(), Base64.URL_SAFE);

            String state = usingRandom(3);
            String loadUrl = "https://account.ecouser.net/newauth/default/default/1/0/ologin.htm?redirect_uri=https://apimux.ilifesmart.com/ecouserapp/oauth/ecouser&client_id=64531ff8077ada0f6cf22115&response_type=code&state=63f9ec68-b25e-4f74-83b9-eb8e1d962f5a&time=1684222720";
            Log.d("BBBB", "initialize: loadurl " + loadUrl);
            webView.loadUrl(loadUrl);

        } catch (Exception ex) {

        }
    }

    static String usingRandom(int length) {
        String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        // create a super set of all characters
        String allCharacters = alphabetsInLowerCase + alphabetsInUpperCase + numbers;
        // initialize a string to hold result
        StringBuffer randomString = new StringBuffer();
        Random random = new Random();
        // loop for 10 times
        for (int i = 0; i < length; i++) {
            // generate a random number between 0 and length of all characters
            int randomIndex = random.nextInt(allCharacters.length());
            // retrieve character at index and add it to result
            randomString.append(allCharacters.charAt(randomIndex));
        }
        return randomString.toString();
    }

}