package com.ilifesmart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ilifesmart.weather.R
import java.util.regex.Pattern

class ShopingActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_shoping)
		
		findViewById<Button>(R.id.tobao_product_detail).setOnClickListener {
			Intent(Intent.ACTION_VIEW, Uri.parse("https://item.taobao.com/item.htm?id=615676811592")).apply {
				this.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity")
				startActivity(this)
			}
		}
		
		findViewById<Button>(R.id.tobao_shop).setOnClickListener {
			
			val shopUrl= "https://lifesmart.tmall.com/?shop_id=111046982"
			Intent(Intent.ACTION_VIEW, Uri.parse(shopUrl)).apply {
				this.setClassName("com.taobao.taobao", "com.taobao.android.shop.activity.ShopHomePageActivity")
				startActivity(this)
			}
		}
		
		// 京东。
		findViewById<Button>(R.id.jd_product_detail).setOnClickListener {
			var detail_url = "https://item.jd.com/10574296249.html"
			var target_url:String? = null
			val m = Pattern.compile("\\D*(\\d*).*").matcher(detail_url)
			if (m.find()) {
				val id = m.group(1)
				target_url = """openapp.jdmobile://virtual?params={"category":"jump","des":"productDetail","skuId":"$id","sourceType":"JSHOP_SOURCE_TYPE","sourceValue":"JSHOP_SOURCE_VALUE"}"""
				Intent(Intent.ACTION_VIEW,Uri.parse(target_url)).apply {
					startActivity(this)
				}
			}
		}
		
		findViewById<Button>(R.id.jd_shop).setOnClickListener {
			val JDShopId = "192933"
			val url = """openApp.jdMobile://virtual?params={"category":"jump","des":"jshopMain","shopId":"$JDShopId","sourceType":"M_sourceFrom","sourceValue":"dp"}""";
			Intent(Intent.ACTION_VIEW,Uri.parse(url)).apply {
				startActivity(this)
			}
		}
		
		"https://detail.tmall.com/item.htm?id=569833217123&ali_trackid=2:mm_12238993_19794510_110896800135:1604990083_194_429147225&union_lens=lensId:OPT@1604990080@0b014c60_c023_175b0dd170c_ddf8@01;recoveryid:201_11.23.83.213_3227039_1604990073931;prepvid:201_11.186.139.24_3225585_1604990080225&spm=a2e1u.19484427.29996460.1&pvid=100_11.14.234.10_30619_7741604990080632522&scm=null&bxsign=tbkJ2PkjfAjNnd6O5nrslvYAL3Ag%207sAEKwcw0AhKGjm5dSS5AFqFv4jqm1aLDv%203qj8Zo20S3JmY4MvVHia3Lg7Ml9J%20ESsvqB3ucQ0ry3MqU="
		.apply {
			with(Uri.parse(this)) {
				val names = queryParameterNames
				
				for (key in names) {
					Log.d("UrlKey", "onCreate: key: $key, value: ${getQueryParameter(key)}")
				}
			}
		}
		
	}
}