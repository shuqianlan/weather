package com.gson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.ilifesmart.weather.R;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class Gson2Activity extends AppCompatActivity {

    public static final String TAG = "Gson2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson2);

        initialize();
    }

    private void initialize() {
        Gson gson = new Gson();

        String json = "[\"Java\", \"Kotlin\", \"Swift\"]";
        String[] array = gson.fromJson(json, String[].class);
        List<String> list = gson.fromJson(json, new TypeToken<List<String>>() {}.getType());

        Log.d(TAG, "initialize: array " + Arrays.toString(array));
        Log.d(TAG, "initialize: lists " + Arrays.toString(list.toArray()));

        // 使用指南-1
        // JSON解析
        int i = gson.fromJson("10", int.class);
        double d= gson.fromJson("\"99.99\"", double.class);
        boolean b = gson.fromJson("false", boolean.class);
        String s = gson.fromJson("string", String.class);
        Log.d(TAG, "initialize: i " + i + " d " + d + " b " + b + " s " + s );

        // 生成JSON
        Log.d(TAG, "initialize: (1) " + gson.toJson(1));
        Log.d(TAG, "initialize: (99.99) " + gson.toJson(99.99));
        Log.d(TAG, "initialize: (false) " + gson.toJson(false));
        Log.d(TAG, "initialize: (Hello) " + gson.toJson("Hello"));

        Person bean = new Person();
        bean.setAddress("杭州");
        bean.setAge(26);
        bean.setName("下雨啦");

        String personJSON = gson.toJson(bean);
        Log.d(TAG, "initialize: personJSON: " + personJSON);
        Person beanFromJSON = gson.fromJson(personJSON, Person.class);
        Log.d(TAG, "initialize: personFromJSON: " + beanFromJSON);

        // SerializedName 注解的使用(针对某一字段给予不同字段名的适配)
        String personJson = "{\"name\":\"下雨啦\",\"age\":26,\"Address\":\"杭州\"}";
        Person person1 = gson.fromJson(personJson, Person.class);
        Person person2 = gson.fromJson(personJson, new TypeToken<Person>() {}.getType()); // (){} 调用不同包下的仅protected的构造函数
        Log.d(TAG, "initialize: person1 " + person1); // Person{name='下雨啦', address='杭州', age=26}
        Log.d(TAG, "initialize: person2 " + person2); // Person{name='下雨啦', address='杭州', age=26}

        // 泛型使用
        String stringArray = "[\"Java\", \"Swift\", \"Kotlin\"]";
        String[] arrays = gson.fromJson(stringArray, String[].class);

        // new TypeToken<List<String>>() {}.getType(): 返回运行时的类型，此处即List<String>
        List<String> lists = gson.fromJson(stringArray, new TypeToken<List<String>>() {}.getType());

        Log.d(TAG, "initialize: arrays " + Arrays.toString(arrays));
        Log.d(TAG, "initialize: lists " + Arrays.toString(lists.toArray()));

        // 避免上述写法的不易阅读性。gson.fromJson(Reader reader, Class<T> clazz);由于泛型在运行时已檫除类型，故此处无法实现！
        // 解决方案:自定义Type
        Result result = new Result();
        result.id = "1.1";
        result.msg = "Success";
        result.data = person1;
        String resultJSON = gson.toJson(result);
        StringReader reader = new StringReader(resultJSON);
        Log.d(TAG, "initialize: resultJSON: " + resultJSON);
        Result resultBean = fromJsonObject(reader, Person.class);
        Log.d(TAG, "initialize: Result<Person> " + resultBean);

    }

    public static class Person {
        private String name;
        @SerializedName(value = "address", alternate = {"Address", "country"})
        private String address;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static class ParameteredTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;

        public ParameteredTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = (args != null) ? args : new Type[0];
        }

        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    public static <T> Result<T> fromJsonObject(Reader json, Class<T> clazz) {
        Type type = new ParameteredTypeImpl(Result.class, new Class[]{clazz});
        return new Gson().fromJson(json, type);
    }

    public static <T> Result<List<T>> fromJsonArray(Reader json, Class<T> clazz) {
        // 转为List<T>
        Type listType = new ParameteredTypeImpl(List.class, new Class[]{clazz});
        // 转为List<List<T>>
        Type type = new ParameteredTypeImpl(Result.class, new Type[]{listType});
        return new Gson().fromJson(json, type);
    }

    public class Result<T> {
        private String id;
        private String msg;
        private T data;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "id='" + id + '\'' +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}
