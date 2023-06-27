package com.echarts

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import com.google.gson.Gson
import com.ilifesmart.weather.R
import org.json.JSONArray
import org.json.JSONObject

class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_echarts)

        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically=true
            setSupportZoom(false)
            displayZoomControls = false
        }
        webView.webViewClient = object : WebViewClient() {
            private var mWebView: WebView?=null
            init {
                mWebView = webView
            }
//            override fun onRenderProcessGone(
//                view: WebView?,
//                detail: RenderProcessGoneDetail
//            ): Boolean {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    if (!detail.didCrash()) {
//                        mWebView?.also { webView ->
//                            mWebView.removeView(webView)
//                            webView.destroy()
//                            mWebView = null
//                        }
//                        return true
//                    }
//                }
//
//                return false
//            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                println("shouldOverrideUrlLoading ${request?.url.toString()}")
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                println("onPageFinished")
                onFinishLoadUrl()
            }

        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

        }

        webView.loadUrl("file:///android_asset/web/index.html")
    }

    private fun onFinishLoadUrl() {
        findViewById<WebView>(R.id.webView).postDelayed({
            val json = creatEChartsOption()
            loadEChartsOption(json)
        }, 2000)

    }

    private fun loadEChartsOption(option:String) {
        println("onLoadEChartOption: $option")
        findViewById<WebView>(R.id.webView).loadUrl("javascript:loadEcharts('" + option + "')")
    }

    private fun creatEChartsOption():String {
        val option = EChartsDemoOption(
            title = mapOf("title" to  "ECharts Demo"),
            tooltip = mapOf(),
            legend = mapOf("data" to "销量"),
            xAxis = mapOf("data" to listOf("衬衫", "羊毛衫","雪纺衫","裤子","高跟鞋","袜子")),
            yAxis = mapOf(),
            series = listOf(EChartsDemoOption.Serie("销量", "bar", listOf(5, 20, 36, 10, 10, 20))),
            dataZoom = listOf(EChartsDemoOption.DataZoom("slider", 0, 100))
        )

        return Gson().toJson(option)
    }

    private data class EChartsDemoOption(
        val title:Map<String,String>,
        val tooltip:Map<Any, Any>,
        val legend:Map<String,String>,
        val xAxis:Map<String,List<String>>,
        val yAxis:Map<Any, Any>,
        val series:List<Serie>,
        val dataZoom:List<DataZoom>
    ) {
        data class Serie(
            val name:String,
            val type:String,
            val data:List<Int>
        )

        data class DataZoom(
            val type:String,
            val start:Int,
            val end:Int
        )
    }
}
