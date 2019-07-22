package com.imou;

import android.util.Log;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RetrofitHelper {

	public static final String TAG = "RetrofitHelper";
	private static volatile RetrofitHelper sRetrofitHelper = null;
	private Retrofit mRetrofit;
	private OkHttpClient mOkHttpClient;

	public RetrofitHelper() {
		mOkHttpClient = new OkHttpClient.Builder()
						.readTimeout(10, TimeUnit.SECONDS)
						.connectTimeout(10, TimeUnit.SECONDS)
						.addNetworkInterceptor(new Interceptor() {
							@Override
							public Response intercept(Chain chain) throws IOException {
								Request request = chain.request();
								Log.d(TAG, "intercept: content " + request.toString());
								return chain.proceed(request);
							}
						})
						.build();


		mRetrofit = new Retrofit.Builder()
						.client(mOkHttpClient)
						.baseUrl(SignHelper.getApiBaseUrl())
						.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
						.addConverterFactory(GsonConverterFactory.create())
						.build();
	}

	public static RetrofitHelper getInstance() {
		if (sRetrofitHelper == null) {
			synchronized (RetrofitHelper.class) {
				if (sRetrofitHelper == null) {
					sRetrofitHelper = new RetrofitHelper();
				}
			}
		}
		return sRetrofitHelper;
	}

	public Retrofit getRetrofit() {
		return mRetrofit;
	}

}
