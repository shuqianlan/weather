package com.imou;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.imou.json.LeChengResponse;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

public class CustomResponseBodyConverter<T extends LeChengResponse> implements Converter<ResponseBody, T> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson gson;
    private Annotation[] annotations;
    private Type type;

    public CustomResponseBodyConverter(Type type, Annotation[] annotations, Gson gson) {
        this.gson = gson;
        this.annotations = annotations;
        this.type = type;
    }

    @Nullable
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();

        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonObject data = jsonObject.getAsJsonObject("result").getAsJsonObject("data");

        T t = null;
        try {
            // 通过反射获取泛型的实例对象
            Class<T> clazz = (Class<T>) type;
            Log.d("BBBB", "convert: -------------------1 ");
            t = clazz.newInstance();
            Log.d("BBBB", "convert: -------------------2 ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (t instanceof LeChengResponse) {
            Log.d("BBBB", "convert: className " + t.getClass().getName());
//            Class resultData = t.getResult().getData().getClass();
//            Annotation[] annotations = resultData.getAnnotations();
//            for(Annotation annotation:annotations) {
//                Log.d("BBBB", "convert: " + annotation.annotationType());
//                if (annotation != null && annotation instanceof JsonAdapter) {
//                    Object instance = new ConstructorConstructor(Collections.<Type, InstanceCreator<?>>emptyMap()).get(TypeToken.get(((JsonAdapter) annotation).value())).construct();
//                    if (instance instanceof TypeAdapter) {
//                        return (T)((TypeAdapter) instance).read(new JsonReader(new StringReader(response)));
//                    }
//                }
//            }
        }
        return gson.fromJson(response, type);
    }
}
