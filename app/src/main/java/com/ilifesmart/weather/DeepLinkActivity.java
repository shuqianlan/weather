package com.ilifesmart.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class DeepLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);

        ((TextView)findViewById(R.id.deeplink_to_lsapp)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(R.id.deeplink_to_lsapp)).setText(Html.fromHtml(getString(R.string.deeplink)));
    }
}