package com.imou;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class CustomGsonConverterFactory extends Converter.Factory {

    private final Gson gson;
    public static CustomGsonConverterFactory create() {
        return create(new Gson());
    }

    public static CustomGsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new CustomGsonConverterFactory(gson);
    }

    private CustomGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @TargetApi(value = Build.VERSION_CODES.P)
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Log.d("BBBB", "responseBodyConverter: type " + type.getTypeName());
        return new CustomResponseBodyConverter(type, annotations, gson);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonRequestBodyConverter<>(gson, adapter);

    }
}
