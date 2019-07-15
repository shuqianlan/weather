package com.imou;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RxBus {

	private static volatile RxBus _instance;

	private static Object mLock = new Object();
	private final Subject<Object> subject = PublishSubject.create().toSerialized();
	private Map<String, CompositeDisposable> mDisposableMap = new ConcurrentHashMap<>();

	public static RxBus getInstance() {
		if (_instance == null) {
			synchronized (mLock) {
				if (_instance == null) {
					_instance = new RxBus();
				}
			}
		}

		return _instance;
	}

	public <T> Flowable<T> getObservable(Class<T> clazz) {
		/*
		 * BUFFER: 缓存所有的onNext事件直至被消耗
 		 * MISSING: onNext没有做缓存或丢弃最新值
		 * ERROR: 标记异常
		 * LASTEST: 保持最新的onNext() value
		 *
	 	 * */
		return subject.toFlowable(BackpressureStrategy.LATEST).ofType(clazz);
	}

	public void addSubscription(Object o, Disposable disposable) {
		addSubscription(o.getClass(), disposable);
	}

	public void addSubscription(Class clz, Disposable disposable) {
		String key = clz.getName();

		// 基于类型名的多观察者缓存，用于生命周期内有效.
		if (mDisposableMap.containsKey(key)) {
			mDisposableMap.get(key).add(disposable);
		} else {
			CompositeDisposable disposable1 = new CompositeDisposable(disposable); // resources = OpenHashSet<Disposable>
			mDisposableMap.put(key, disposable1);
		}
	}

	public void unSubscribe(Object o) {
		unSubscribe(o.getClass());
	}

	public void unSubscribe(Class clazz) {
		String key = clazz.getName();
		if (!mDisposableMap.containsKey(key)) {
			return;
		}

		if (mDisposableMap.get(key) != null) {
			mDisposableMap.get(key).dispose();
		}

		mDisposableMap.remove(key);
	}

	public void post(Object o) {
		// 消息通知.
		subject.onNext(o);
	}

	public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error) {
		return getObservable(type)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(next, error);
	}

	public <T> Disposable doSubscribe(Flowable<T> flowable, Consumer<T> next, Consumer<Throwable> error) {
		return flowable
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(next, error);
	}

	public <T> void doSubscribe(Flowable<T> flowable, Subscriber<T> subscriber) {
		flowable
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(subscriber);
	}

	public <T> Flowable<T> doSubscribe(Flowable<T> flowable, Consumer<Subscription> onSubscribe, Action action) {
		return flowable
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.doOnSubscribe(onSubscribe)
						.doAfterTerminate(action);
	}

	public <T> Flowable<T> doSubscribe(Flowable<T> flowable) {
		return flowable
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread());
	}
}
