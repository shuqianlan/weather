package com.ilifesmart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ilifesmart.weather.R
import kotlinx.android.synthetic.main.activity_shoping.*
import java.util.regex.Pattern

class ShopingActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_shoping)
		
		tobao_product_detail.setOnClickListener {
			Intent(Intent.ACTION_VIEW, Uri.parse("https://item.taobao.com/item.htm?id=615676811592")).apply {
				this.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity")
				startActivity(this)
			}
		}
		
		tobao_shop.setOnClickListener {
			
			val shopUrl= "https://lifesmart.tmall.com/?shop_id=111046982"
			Intent(Intent.ACTION_VIEW, Uri.parse(shopUrl)).apply {
				this.setClassName("com.taobao.taobao", "com.taobao.android.shop.activity.ShopHomePageActivity")
				startActivity(this)
			}
		}
		
		// 京东。
		jd_product_detail.setOnClickListener {
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
		
		jd_shop.setOnClickListener {
			val JDShopId = "192933"
			val url = """openApp.jdMobile://virtual?params={"category":"jump","des":"jshopMain","shopId":"$JDShopId","sourceType":"M_sourceFrom","sourceValue":"dp"}""";
			Intent(Intent.ACTION_VIEW,Uri.parse(url)).apply {
				startActivity(this)
			}
		}
	}
}