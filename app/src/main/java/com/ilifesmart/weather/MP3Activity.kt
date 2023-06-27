package com.ilifesmart.weather

import android.app.job.JobScheduler
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ilifesmart.ToolsApplication
import com.services.JobScheduleHelper
import com.services.MyJobService
import java.util.*

inline fun <reified T> loadService():ServiceLoader<T> {
	return ServiceLoader.load(T::class.java)
}

class MP3Activity : AppCompatActivity() {
	private lateinit var scheduler:JobScheduler

	private lateinit var handler:Handler
	
	companion object {
	
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_m_p3)
		
		handler = Handler(Looper.myLooper()!!) {
			true
		}
		findViewById<Button>(R.id.start).setOnClickListener {
			
			JobScheduleHelper.scheduleJobInfo(ToolsApplication.getContext(), MyJobService.CONST_JOB_ID_SOUND_PLAY, Bundle().apply {
				AlertDialog.Builder(this@MP3Activity).apply {
					val allItems = (1..6).asSequence().map { "sound/sound$it.wav" }.toList().toTypedArray()
					setSingleChoiceItems(allItems, 0) { dialog, index ->
						dialog.dismiss()
						
						JobScheduleHelper.scheduleJobInfo(ToolsApplication.getContext(), MyJobService.CONST_JOB_ID_SOUND_PLAY, Bundle().apply {
							putString(MyJobService.CONST_JOB_ID_NET_SOUND_FILE, allItems[index])
							putInt(MyJobService.CONST_JOB_ID_NET_SOUND_LOOPS, -1)
						})
					}
				}.show()
			})
		}
		
		findViewById<Button>(R.id.stop).setOnClickListener {
			JobScheduleHelper.scheduleJobInfo(ToolsApplication.getContext(), MyJobService.CONST_JOB_ID_SOUND_STOP)
		}
		
		findViewById<Button>(R.id.release).setOnClickListener {
			JobScheduleHelper.scheduleJobInfo(ToolsApplication.getContext(), MyJobService.CONST_JOB_ID_SOUND_RELEASE)
		}

		scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
		
		loadFunc()
	}
	
	override fun onBackPressed() {
		JobScheduleHelper.scheduleJobInfo(ToolsApplication.getContext(), MyJobService.CONST_JOB_ID_SOUND_RELEASE)
		super.onBackPressed()
	}
	
	fun loadFunc() {
		fun printSun(c: Collection<*>) {
			val intList = c as? List<Int> ?: throw IllegalAccessException("List is expected")
			println("list.sum:${intList.sum()}")
		}
		
		val listOfInt = listOf<Int>()
		printSun(listOf(1, 2, 3))
		try {
			printSun(setOf(1, 2, 3))
		} catch (ex: Exception) {
			Log.d("BBBB", "onCreate: ${ex.message}")
		}
		
		val items = listOf("1", 2, "3")
		println("filterItems: ${items.filterIsInstance<String>()}")
		
		loadService<MP3Activity>()
		
		listOf(1, 2, 3, 4).joinToString()
		
		mutableListOf(1, 2, 3, 4)
		
		val str:String = "1234"
		listOf("1", "2", "3", "4").sortedWith(kotlin.Comparator { o1, o2 -> o1.hashCode() - o2.hashCode() })
		
		val destination = mutableListOf<Any>().apply {
			copyData(mutableListOf(1, 2, 3, 4), this)
		}
		Log.d("ABCD", "loadFunc: copy(1,2,3,4): $destination")
		
		val print2 = ::callPrintInt
		print2.call(256)
		
		buildString {
		
		}
		
		val bean = Person("1", 1)
		val clazz = bean.javaClass.kotlin
//		clazz.primaryConstructor
		
//		name = 1234
		
		TODO("")
		
//		ArrayList(5).forea
		
		listOf(1, 2, 3, 4).fold(0) { res, e1 -> res+e1
		}
	}
	
	fun <T> copyData(source: MutableList<out T>, destination: MutableList<T>) {
//		source.add(1) // 此处即不可使用in的方法
		for (item in source) {
			destination.add(item)
		}
	}
	
	open class Animal {
		fun feed() {
			print("feed \n")
		}
	}
	
	class Cat:Animal() {
		fun cleanLitter() {
		
		}
	}
	
	class Herd<out T : Animal> {
		val size: Int = 10
		operator fun get(i: Int): T {
			return Animal() as T
		}
	}
	
	fun feedAll(animals: Herd<Animal>) {
		for (i in 0 until animals.size) {
			animals.get(i).feed()
		}
	}
	
	@Deprecated(message = "invalid method name。 use takeCareOfCATs replaced.", replaceWith = ReplaceWith("feedAll(cats)"))
	fun takeCareOfCats(cats: Herd<Cat>) {
//		for (i in 0 until cats.size) {
//			cats[i].cleanLitter()
//		}
		
		feedAll(cats)
	}
	
	class Person(val name: String, val age: Int) {
	
	}
	
	fun callPrintInt(int: Int) = print("print:$int")
}