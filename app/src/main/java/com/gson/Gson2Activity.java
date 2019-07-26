package com.gson;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.gson_demo.DevicesResponse;
import com.gson_demo.OauthResponse;
import com.gson_demo.UserResponse;
import com.ilifesmart.weather.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
//        Result resultBean = fromJsonObject(reader, Person.class);
//        Log.d(TAG, "initialize: Result<Person> " + resultBean);

        // https://www.jianshu.com/p/e740196225a4

        // 流式反序列化
        // 手动方式
        Person person4 = new Person();
        person4.setName("tesla");
        person4.setAge(26);
        String streamJson = gson.toJson(person4);
        Log.d(TAG, "initialize: person4Json " + streamJson);
        Person person3 = new Person();
        JsonReader jsonReader = new JsonReader(new StringReader(streamJson));

        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                final String name = jsonReader.nextName();
                switch (name) {
                    case "name":
                        person3.setName(jsonReader.nextString());
                        break;
                    case "age":
                        person3.setAge(jsonReader.nextInt());
                        break;
                    case "address":
                        person3.setAddress(jsonReader.nextString());
                        break;
                }
            }
            jsonReader.endObject();

            Log.d(TAG, "initialize: person3 " + person3);
        } catch(Exception ex) {
        	ex.printStackTrace();
        }

        // 流式序列化
        // 手动方式

        try {
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(System.out));
            writer.beginObject()
                    .name("name").value(person3.getName())
                    .name("age").value(person3.getAge())
                    .name("address").value(person3.getAddress())
                    .endObject();
            writer.flush(); // I/System.out: {"name":"Tesila","age":24,"address":null}
        } catch(Exception ex) {
        	ex.printStackTrace();
        }

        // GsonBuilder (自定义规则)
        Gson gson2 = new GsonBuilder()
                .serializeNulls() // 序列化null值
                .setDateFormat("yyyy-MM-dd")
                .setVersion(1.5) // 设置版本号|对注解Since和Until有效
                .disableInnerClassSerialization() // 禁止序列化内部类
//                .excludeFieldsWithoutExposeAnnotation() // 过滤掉没有Expose的字段
//                .generateNonExecutableJson()      // 生成不可执行的Json(多了 )]}' 这四个字符)
//                .setPrettyPrinting()              // 格式化输出
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        Log.d(TAG, "shouldSkipField: f.name: " + f.getName());
                        if (f.getName().equals("county")) {
                            Log.d(TAG, "shouldSkipField: filterCountry!");
                            return true;
                        }

                        Expose expose = f.getAnnotation(Expose.class);
                        if (expose != null && expose.serialize() == false) {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        Log.d(TAG, "addDeserializationExclusionStrategy shouldSkipField f.name: " + f.getName());
                        if (f.getName().equals("unionCode")) {
                            Log.d(TAG, "shouldSkipField: filterUnionCode ----->");
                            return true;
                        }

                        Expose expose = f.getAnnotation(Expose.class);
                        if (expose != null && expose.deserialize() == false) {
                            Log.d(TAG, "shouldSkipField: =========================");
                            return true;
                        }

                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        // Expose字段过滤
        String gson2Str = gson2.toJson(person4);

        Log.d(TAG, "initialize: gson2Str " + gson2Str);
        // ver:1.2 initialize: gson2Str {"unionCode":"2456"}
        // ver:1.5 initialize: gson2Str {"county":"China"}
        Person person5 = new Person();
        person5.setName("wuzh");
        person5.setAge(26);
        person5.setAddress("hangzhou");
        person5.setCounty("China");
        person5.setUnionCode("310052");
        String json5 = gson2.toJson(person5);
        Person person5Result = gson2.fromJson(json5, Person.class);

        Log.d(TAG, "initialize: json5: " + json5);
        Log.d(TAG, "initialize: person5Result: " + person5Result);

        Gson gson3 = new GsonBuilder()
//                .registerTypeAdapter(Person.class, new TypeAdapter<Person>() {
//                    @Override
//                    public void write(JsonWriter out, Person value) throws IOException {
//                        out.beginObject()
//                                .name("name").value(value.name)
//                                .name("age").value(value.age)
//                                .name("address").value(value.address)
//                                .name("unionCode").value(value.unionCode)
//                                .name("county").value(value.county)
//                                .endObject();
//                    }
//
//                    @Override
//                    public Person read(JsonReader in) throws IOException {
//                        Person person = new Person();
//                        in.beginObject();
//                        while (in.hasNext()) {
//                            switch (in.nextName()) {
//                                case "name":
//                                    person.setName(in.nextString());
//                                    break;
//                                case "age":
//                                    person.setAge(in.nextInt());
//                                    break;
//                                case "address":
//                                    person.setAddress(in.nextString());
//                                    break;
//                                case "unionCode":
//                                    person.setUnionCode(in.nextString());
//                                    break;
//                                case "county":
//                                    person.setCounty(in.nextString());
//                                    break;
//                            }
//                        }
//                        in.endObject();
//                        return person;
//                    }
//                }.nullSafe())
                // 此处类型必须是包装类型!
                // 支持泛型，不支持继承
                // 如果一个序列化对象本身带有泛型，且注册了typeAdapter。则toJson必须指定类型.
                .registerTypeAdapter(Person.class, new JsonSerializer<Person>() {
                    @Override
                    public JsonElement serialize(Person src, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.age); // 仅序列化age
                    }
                })
                // 支持继承，不支持泛型
                .registerTypeHierarchyAdapter(Number.class, new TypeAdapter<Integer>() {
                    @Override
                    public void write(JsonWriter out, Integer value) throws IOException {

                    }

                    @Override
                    public Integer read(JsonReader in) throws IOException {
                        return null;
                    }
                })
                .create(); // 26

        String gson3Str = gson3.toJson(person5);
        Log.d(TAG, "initialize: gson3Str " + gson3Str);

        // ------ 泛型类型测试(已验证OK)
        OauthResponse oauth = new OauthResponse();
        oauth.setId("1.0");
        oauth.setMsg("success");
        OauthResponse.UserToken token = new OauthResponse.UserToken();
        token.setExpiredTime(System.currentTimeMillis()/1000);
        token.setUserToken(UUID.randomUUID().toString());
        oauth.setData(token);

        UserResponse response = new UserResponse();
        response.setData("");
        response.setId("1.0");
        response.setMsg("success");

        DevicesResponse devices = new DevicesResponse();
        devices.setId("1.0");
        devices.setMsg("success");
        List<DevicesResponse.DeviceBean> beans = new ArrayList<>();
        for (int j = 0; j < 6; j++) {
            DevicesResponse.DeviceBean deviceBean = new DevicesResponse.DeviceBean();
            deviceBean.setClsType("Item-"+(j+1));
            deviceBean.setHeatTs(System.currentTimeMillis() - 1000*j);
            deviceBean.setName("Device-"+(j+1));
            deviceBean.setUuid(UUID.randomUUID().toString());
            beans.add(deviceBean);
        }
        devices.setData(beans);

        String oauthJson = gson.toJson(oauth);
        String responseJson = gson.toJson(response);
        String devicesJson = gson.toJson(devices);

        OauthResponse oauth_result = gson.fromJson(oauthJson, OauthResponse.class);
        UserResponse  response_result = gson.fromJson(responseJson, UserResponse.class);
        DevicesResponse devices_result = gson.fromJson(devicesJson, DevicesResponse.class);

        Log.d(TAG, "initialize: oauth_result " + oauth_result);
        Log.d(TAG, "initialize: response_result " + response_result);
        Log.d(TAG, "initialize: devices_result " + devices_result);

    }

     // @JsonAdapter(xxx.class): 指定此类对应的序列化与反序列化对象. 不需要再注册registerTypeAdapter, 并内部默认调用nullSafe() || 注解的好处
    public static class Person {
        private String name;
        @SerializedName(value = "address", alternate = {"Address", "country"})
        private String address;
        private int age;

        @Expose
        @Until(1.3)
        private String unionCode;

        @Since(1.4)
        @Expose(serialize = true, deserialize = false)
        private String county = "China";

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

        public String getUnionCode() {
            return unionCode;
        }

        public void setUnionCode(String unionCode) {
            this.unionCode = unionCode;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", age=" + age +
                    ", unionCode=" + unionCode +
                    ", county=" + county +
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
