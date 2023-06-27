package com.wuzh.kotlin.tools

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

fun <T: AppCompatActivity> AppCompatActivity.startActivity(clazz:T) {
	startActivity(Intent().apply {
		setClass(this@startActivity, clazz::class.java)
	})
}
class Tools {
	fun main() {
		val list = listOf(1,2,3).sum()
	}
}

class StringList:List<String> {
	override val size: Int
		get() = TODO("Not yet implemented")
	
	override fun contains(element: String): Boolean {
		TODO("Not yet implemented")
	}
	
	override fun containsAll(elements: Collection<String>): Boolean {
		TODO("Not yet implemented")
	}
	
	override fun get(index: Int): String {
		TODO("Not yet implemented")
	}
	
	override fun indexOf(element: String): Int {
		TODO("Not yet implemented")
	}
	
	override fun isEmpty(): Boolean {
		TODO("Not yet implemented")
	}
	
	override fun iterator(): Iterator<String> {
		TODO("Not yet implemented")
	}
	
	override fun lastIndexOf(element: String): Int {
		TODO("Not yet implemented")
	}
	
	override fun listIterator(): ListIterator<String> {
		TODO("Not yet implemented")
	}
	
	override fun listIterator(index: Int): ListIterator<String> {
		TODO("Not yet implemented")
	}
	
	override fun subList(fromIndex: Int, toIndex: Int): List<String> {
		TODO("Not yet implemented")
	}
}