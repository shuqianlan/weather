package com.gson

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.ilifesmart.weather.R
import java.lang.reflect.Modifier

class GsonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gson)

        val books = listOf(Book("tom", "android-1", 100.0), Book("tom2", "android-2", 100.0), Book("tom3", "android-3", 80.0))
        val jsonStr = Gson().toJson(books)
        println("jsonStr:$jsonStr")

        val books2 = listOf(SanTiBook("刘慈欣", "三体-黑暗纪元", 100.0), SanTiBook("刘慈欣", "三体-黑暗森林", 100.0), SanTiBook("刘慈欣", "三体-死神永生", 80.0))

        // 过滤所有不加Expose注解的字段
        val santi = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(books2)
        println("santi:$santi")

        GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.ABSTRACT).create()
//        Gson().fromJson<Book>(jsonStr)

        // (基于策略)自定义规则
        GsonBuilder().addSerializationExclusionStrategy(object :ExclusionStrategy {
            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                return (clazz as Int ?: (clazz as Double)) != null
            }

            override fun shouldSkipField(f: FieldAttributes?): Boolean {
                if ("discount".equals(f?.name)) {
                    return true
                }

                val expose = f?.getAnnotation(Expose::class.java)
                if (expose != null && expose.serialize != true) {
                    return true
                }
                return false
            }
        }).addDeserializationExclusionStrategy(object :ExclusionStrategy {
            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun shouldSkipField(f: FieldAttributes?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }).create()

        // POJO与JSON的字段映射规则

        // 泛型
        val strs = "[\"Android\", \"Swift\", \"Kotlin\"]"
//        val array = Gson().fromJson(strs, String::class)
//        println("array:${array}")
    }
}
