package com.ilifesmart.rxjava;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.ilifesmart.weather.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DemoActivity extends AppCompatActivity {

	public static final String TAG = "RxJava";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);

//		testDemo();
//		observeOnZipTest();
//		observableOnConcat();
//		observableOnFlatMap();
//		observableOnConcatMap();
//		observableOnDistinct();
//		ObservableOnFilter();
//		ObservableOnBuffer();
//		ObservableOnInterval();
//		observableOnWindow();
//		rxjavaDemotest();
//		observableOnScan();
//		observableOnToList();
		observableOnSubject();
	}

	private void testDemo() {
		Observable.create(new ObservableOnSubscribe<Integer>() {

			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) {
				Log.d(TAG, "subscribe: Thread-name " + Thread.currentThread().getName());
				Log.d(TAG, "subscribe: emit 1 ");
				emitter.onNext(1);
				Log.d(TAG, "subscribe: emit 2 ");
				emitter.onNext(2);
				Log.d(TAG, "subscribe: emit 3 ");
				emitter.onNext(3);
				Log.d(TAG, "subscribe: emit 4 ");
				emitter.onNext(4);
				Log.d(TAG, "subscribe: complete. ");
				emitter.onComplete();
			}
		})
		.subscribeOn(Schedulers.io()) // io线程池
		.subscribeOn(Schedulers.newThread()) // 此处不会触发.
		.observeOn(AndroidSchedulers.mainThread()) // 主线程
		.doOnNext(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				Log.d(TAG, "accept: i " + integer + "Thread-" + Thread.currentThread().getName());
			}
		})
		.observeOn(Schedulers.newThread())
		.doOnNext(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				Log.d(TAG, "accept: i " + integer + "Thread-" + Thread.currentThread().getName());
			}
		})
		.subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
			}
		});

		Log.d(TAG, "testDemo: --------------------- Map --------------------- ");
		
		Observable.create(new ObservableOnSubscribe<Integer>() {

			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				emitter.onNext(5);
				emitter.onNext(6);
				emitter.onNext(7);
				emitter.onNext(8);
			}
		}).map(new Function<Integer, String>() {

			@Override
			public String apply(Integer integer) throws Exception {
				return "Item-"+ integer.intValue();
			}
		})
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) throws Exception {
				Log.d(TAG, "accept: result " + s);
			}
		});
						
	}

	private Observable<String> getStringObservable() {
		return Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> emitter) throws Exception {
				if (!emitter.isDisposed()) {
					emitter.onNext("A");
					Log.d(TAG, "subscribe: A");
					emitter.onNext("B");
					Log.d(TAG, "subscribe: B");
					emitter.onNext("C");
					Log.d(TAG, "subscribe: C");
				}
			}
		});
	}

	private Observable<Integer> getIntegerObservable() {
		return Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				if (!emitter.isDisposed()) {
					emitter.onNext(8);
					Log.d(TAG, "subscribe: 08");
					emitter.onNext(9);
					Log.d(TAG, "subscribe: 09");
					emitter.onNext(10);
					Log.d(TAG, "subscribe: 10");
					emitter.onNext(11);
					Log.d(TAG, "subscribe: 11");
				}
			}
		});
	}

	// zip
	private void observeOnZipTest() {
		Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, String>() {
			@Override
			public String apply(String s, Integer integer) throws Exception {
				return s + " : " + integer;
			}
		}).subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) throws Exception {
				Log.d(TAG, "accept: Result " + s);
			}
		});

//		1553068418.902 4200-4200/com.ilifesmart.weather D/RxJava: accept: Result A : 8
//		1553068418.902 4200-4200/com.ilifesmart.weather D/RxJava: accept: Result B : 9
//		1553068418.903 4200-4200/com.ilifesmart.weather D/RxJava: accept: Result C : 10
	}

	// concat
	private void observableOnConcat() {
		Observable.concat(Observable.just(1,2,3), Observable.just(4,5,6))
						.subscribe(new Consumer<Integer>() {
							@Override
							public void accept(Integer integer) throws Exception {
								Log.d(TAG, "Concat_accept: " + integer.intValue());
							}
						});
//		1553068418.904 4200-4200/com.ilifesmart.weather D/RxJava: Concat_accept: 1
//		1553068418.904 4200-4200/com.ilifesmart.weather D/RxJava: Concat_accept: 2
//		1553068418.905 4200-4200/com.ilifesmart.weather D/RxJava: Concat_accept: 3
//		1553068418.905 4200-4200/com.ilifesmart.weather D/RxJava: Concat_accept: 4
//		1553068418.905 4200-4200/com.ilifesmart.weather D/RxJava: Concat_accept: 5
//		1553068418.905 4200-4200/com.ilifesmart.weather D/RxJava: Concat_accept: 6
	}

	// flatmap
	private void observableOnFlatMap() {
		Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) {
				emitter.onNext(1);
				emitter.onNext(2);
				emitter.onNext(3);
			}
		})
		.flatMap(new Function<Object, ObservableSource<String>>() {
			@Override
			public ObservableSource<String> apply(Object o) throws Exception {
				List<String> list = new ArrayList<>();
				for (int i = 0; i < 3; i++) {
					list.add("I am value " + i);
				}

				int delayTime = (int) (1 + Math.random() * 10);
				return Observable.fromIterable(list).delay(delayTime, TimeUnit.SECONDS);
			}
		})
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(new Consumer<String>() {
			@Override
			public void accept(String o) {
				Log.d(TAG, "accept: " + o);
			}
		});

	}

	// concatMap
	private void observableOnConcatMap() {
		Observable.create(new ObservableOnSubscribe<Object>() {
			@Override
			public void subscribe(ObservableEmitter<Object> emitter) {
				emitter.onNext(1);
				emitter.onNext(2);
				emitter.onNext(3);
			}
		})
		.concatMap(new Function<Object, ObservableSource<?>>() {
			@Override
			public ObservableSource<?> apply(Object o) throws Exception {
				List<String> list = new ArrayList<>();
				for (int i = 0; i < 3; i++) {
					list.add("I am value " + i);
				}

				int delayTime = (int) (1 + Math.random() * 10);
				return Observable.fromIterable(list).delay(delayTime, TimeUnit.SECONDS);
			}
		})
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(new Consumer<Object>() {
			@Override
			public void accept(Object o) {
				Log.d(TAG, "accept: " + (String)o);
			}
		});
	}

	// distinct 去重
	private void observableOnDistinct() {
		Observable.just(1, 1, 2, 3, 3, 4, 5, 6)
						.distinct()
						.subscribe(new Consumer<Integer>() {
							@Override
							public void accept(Integer integer) throws Exception {
								Log.d(TAG, "Distinct_accept: " + integer);
							}
						});
		// 1 2 3 4 5 6
	}

	// filter 过滤
	private void ObservableOnFilter() {
		Observable.just(1, 20, 30, -7, -9- 20)
						.filter(new Predicate<Integer>() {
							@Override
							public boolean test(Integer integer) throws Exception {
								return integer > 0;
							}
						}).subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				Log.d(TAG, "Filter_accept: " + integer);
			}
		});
	}

	// buffer 指定长度和步进的buffer
	private void ObservableOnBuffer() {
		Observable.range(1, 5)
						.buffer(3)
						.subscribe(new Consumer<List<Integer>>() {
							@Override
							public void accept(List<Integer> integers) throws Exception {
								Log.d(TAG, "buffer_accept: " + integers);
							}
						});
//		1553072213.650 10411-10411/com.ilifesmart.weather D/RxJava: buffer_accept: [1, 2, 3]
//		1553072213.650 10411-10411/com.ilifesmart.weather D/RxJava: buffer_accept: [3, 4, 5]
//		1553072213.650 10411-10411/com.ilifesmart.weather D/RxJava: buffer_accept: [5]
	}

	// interval 时间间隔
	private Disposable mDisposable;
	private void ObservableOnInterval() {
		mDisposable = Observable.interval(3, 2, TimeUnit.SECONDS)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Consumer<Long>() {
							@Override
							public void accept(Long aLong) throws Exception {
								Log.d(TAG, "Interval_accept: " + aLong);
							}
						});
		Log.d(TAG, "ObservableOnInterval: =========== A ");
		// 首次启动延迟为3s后续基本为2s，误差极小
	}

	// doOnNext 接收数据前干点别的事情
	private void observableOnDoOnNext() {
		Observable.just(1, 2, 3 ,4)
						.doOnNext(new Consumer<Integer>() {
							@Override
							public void accept(Integer integer) throws Exception {
								Log.d(TAG, "doOnNext_accept: " + integer);
							}
						}).subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				Log.d(TAG, "accept: " + integer);
			}
		});
	}

	// skip 跳过
	private void observableOnSkip() {
		Observable.just(1, 2, 3 ,4)
						.skip(2)
						.subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				Log.d(TAG, "Skip_accept: " + integer);
			}
		});
	}

	// take 至多接收
	private void observableOnTask() {
		Observable.just(1, 2, 3 ,4)
						.take(2)
						.subscribe(new Consumer<Integer>() {
							@Override
							public void accept(Integer integer) throws Exception {
								Log.d(TAG, "Skip_accept: " + integer);
							}
						});
	}

	// just 依次调用onNext
	private void observablesOnJust() {
		Observable.just(1,2,3,4,5,6)
						.subscribe(new Consumer<Integer>() {
							@Override
							public void accept(Integer integer) throws Exception {
								Log.d(TAG, "Just_accept: " + integer);
							}
						});
	}

	// Single 仅接收一个参数，Observer只会调用onSuccess或onError
	private void observableOnSingle() {
		Single.just(new Random().nextInt())
						.subscribe(new SingleObserver<Integer>() {
							@Override
							public void onSubscribe(Disposable d) {

							}

							@Override
							public void onSuccess(Integer integer) {
								Log.d(TAG, "Single_onSuccess: integer " + integer);
							}

							@Override
							public void onError(Throwable e) {
								Log.d(TAG, "onError: ");
							}
						});
	}

	// debounce 去除发送频率过快的问题
	private void observableOnDebounce() {
		Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				emitter.onNext(1);
				Thread.sleep(1000);
				emitter.onNext(2);
				Thread.sleep(100);
				emitter.onNext(3);
				Thread.sleep(200);
				emitter.onNext(4);
				Thread.sleep(300);
			}
		}).debounce(500, TimeUnit.MILLISECONDS)
			.subscribe(new Consumer<Integer>() {
				@Override
				public void accept(Integer integer) throws Exception {
					Log.d(TAG, "Debounce_accept: " + integer);
				}
			});
		// 过滤事件间隔少于500ms的数据.
	}

	// window
	private void observableOnWindow() {
		Observable.interval(1, TimeUnit.SECONDS)
						.take(15)
						.window(3, TimeUnit.SECONDS)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Consumer<Observable<Long>>() {
							@Override
							public void accept(Observable<Long> longObservable) throws Exception {
								Log.d(TAG, "accept: Window .. ");
								longObservable.subscribeOn(Schedulers.io())
												.observeOn(AndroidSchedulers.mainThread())
												.subscribe(new Consumer<Long>() {
													@Override
													public void accept(Long aLong) throws Exception {
														Log.d(TAG, "window_accept: receive " + aLong);
													}
												});
							}
						});
	}

	private void rxjavaDemotest() {
		Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> emitter) throws Exception {
				OkHttpClient client = new OkHttpClient();
				Request request = new Request.Builder()
								.url("http://140.143.243.75:8090/config/lastest_version.txt")
								.build();

				Call call = client.newCall(request);
				Response response = call.execute();

				emitter.onNext(response.body().string());
				emitter.onComplete();
			}
		}).subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.map(new Function<String, String>() {
				@Override
				public String apply(String s) throws Exception {
					JSONObject jsonObject = new JSONObject(s);
					return jsonObject.getString("version");
				}
			})
			.filter(new Predicate<String>() {
				@Override
				public boolean test(String s) throws Exception {
					return s.compareTo("1.0.9") > 0;
				}
			})
			.doOnNext(new Consumer<String>() {
				@Override
				public void accept(String s) throws Exception {
					Log.d(TAG, "pre_accept: Version " + s);
				}
			})
			.subscribe(new Consumer<String>() {
				@Override
				public void accept(String s) throws Exception {
					Log.d(TAG, "accept: version " + s);
				}
			});
	}

	// timer
	private void observableOnTimer() {
		Observable.timer(3, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
			@Override
			public void accept(Long aLong) throws Exception {
				Log.d(TAG, "Timer_accept: " + aLong);
			}
		});
		Log.d(TAG, "observableOnTimer: timer_start ");
	}

	// Scan
	private void observableOnScan() {
		Observable.range(1, 5)
						.scan(new BiFunction<Integer, Integer, Integer>() {
							@Override
							public Integer apply(Integer integer, Integer integer2) throws Exception {
								return integer + integer2;
							}
						})
						.subscribe(new Consumer<Integer>() {
							@Override
							public void accept(Integer integer) throws Exception {
								Log.d(TAG, "Scan_accept: feibonaqie " + integer);
							}
						});
	}

	// Map
	private void observableOnToList() {
		Observable.range(1, 5)
						.toList() // 仅转List
						.subscribe(new Consumer<List<Integer>>() {
							@Override
							public void accept(List<Integer> integers) throws Exception {
								Log.d(TAG, "accept: " + integers);
							}
						});

		Observable.range(1, 5)
						.toMap(new Function<Integer, String>() {
							@Override
							public String apply(Integer integer) {
								return "Item-" + integer; // 此处返回的是Key; Value:integer
							}
						})
						.subscribe(new Consumer<Map<String, Integer>>() {
							@Override
							public void accept(Map<String, Integer> objectIntegerMap) throws Exception {
								Log.d(TAG, "accept: " + objectIntegerMap);
							}
						});
	}

	// subject
	private void observableOnSubject() {
		Executor executor = Executors.newFixedThreadPool(5);

		PublishSubject<Integer> subject = PublishSubject.create();
		subject.subscribe(new Observer<Integer>() {
			@Override
			public void onSubscribe(Disposable d) {
				Log.d(TAG, "onSubscribe: ");
			}

			@Override
			public void onNext(Integer integer) {
				Log.d(TAG, "onNext: " + integer);
			}

			@Override
			public void onError(Throwable e) {
				Log.d(TAG, "onError: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				Log.d(TAG, "onComplete: ");
			}
		});

		Observable.range(1, 5).subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				executor.execute(()->{
					try {
						Thread.sleep(integer * 1000);
						subject.onNext(integer);
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				});
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mDisposable != null && !mDisposable.isDisposed()) {
			mDisposable.dispose();
		}
	}

}
