package com.ilifesmart

import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.ilifesmart.weather.R

class TaoBaoShopActivity : AppCompatActivity() {
    companion object {
        const val TAG = "TaoBao"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tao_bao_shop)

        findViewById<WebView>(R.id.innner_webview).loadUrl("https://account.ecouser.net/newauth/default/default/1/0/ologin.htm?redirect_uri=https://apimux.ilifesmart.com/ecouserapp/oauth/ecouser&client_id=64531ff8077ada0f6cf22115&response_type=code&state=0b4bdbdd-8fcd-4193-a357-11a1af95d0cd&time=1684219900&lang=zh")
        findViewById<WebView>(R.id.innner_webview).settings.apply {
//            builtInZoomControls = true
//            setSupportZoom(true)
            javaScriptEnabled = true
            setSupportZoom(true)
            javaScriptCanOpenWindowsAutomatically = true
            displayZoomControls=true
//            blockNetworkLoads = true


            // `searchBoxJavaBridge_` has big security risk. http://jvn.jp/en/jp/JVN53768697
            try {
                val method = this.javaClass.getMethod(
                    "removeJavascriptInterface", *arrayOf<Class<*>>(
                        String::class.java
                    )
                )
                method.invoke(this, "searchBoxJavaBridge_")
            } catch (e: Exception) {
                Log.d(
                    "BBBB",
                    "This API level do not support `removeJavascriptInterface`"
                )
            }
        }

        findViewById<WebView>(R.id.innner_webview).webViewClient = object :WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Log.d(TAG, "shouldOverrideUrlLoading: url ${request?.url}")
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d(TAG, "onPageFinished: url $url")
                super.onPageFinished(view, url)
            }

        }

        findViewById<WebView>(R.id.innner_webview).webChromeClient = object: WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                Log.d(TAG, "onProgressChanged: progress $newProgress")
                super.onProgressChanged(view, newProgress)
            }
        }
    }
}