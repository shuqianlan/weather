package com.imou.json;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(RecoverSDCardTypeAdapter.class)
public class RecoverSDCardResponse extends LeChengResponse<RecoverSDCardResponse.Data> {
    public static class Data {
        private String Result;

        public String getResult() {
            return Result;
        }

        public void setResult(String result) {
            Result = result;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "Result='" + Result + '\'' +
                    '}';
        }
    }

    /*
    * START: 开始初始化
    * NOSDCARD：插槽内无SD卡
    * INRECOVEr：正在初始化（有可能别的客户端已经请求初始化）
    * SDCARDERR：其他SD卡错误
    * */
    public static class RecoverSDResult {
        public static final String START = "start-recover";
        public static final String NOSDCARD = "no-sdcard";
        public static final String INRECOVEr= "in-recover";
        public static final String SDCARDERR = "sdcard-error";
    }
}
