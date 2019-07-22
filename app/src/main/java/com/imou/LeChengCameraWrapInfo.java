package com.imou;

public class LeChengCameraWrapInfo {
    public static final String APPID = "lc80cfa4d4f8b54c49";
    public static final String AppSecret = "998f9f9e24c94f3e97814e4b1d2a2f";
    public static final String APPCNUrl = "openapi.lechange.cn:443";
    public static final String APPOverSeaUrl = "openapi.easy4ip.com:443";
    public static final int RESULT_SOURCE_OPENAPI = 99; // 在播放过程中rest请求回调类型

    public static final String EXTRA_USER_ID = "CURR_USER_ID";
    public static final String EXTRA_USERTOKEN = "_USERTOKEN";
    public static final String EXTRA_EXPIRETIME= "_EXPIRETIME";

    public static class CloudStorageCode {
        public static final String HLS_DOWNLOAD_FAILD = "0"; // 下载失败
        public static final String HLS_DOWNLOAD_BEGIN = "1"; // 开始下载
        public static final String HLS_DOWNLOAD_END = "2"; // 下载结束
        public static final String HLS_SEEK_SUCCESS = "3"; // 定位成功
        public static final String HLS_SEEK_FAILD = "4"; // 定位失败
        // public static final String HLS_ABORT_DONE = "5";
        // public static final String HLS_RESUME_DONE = "6";
        public static final String HLS_KEY_ERROR = "11"; // 秘钥错误
    }

    public static class LocalDownloadCode{
        public static final String RTSP_DOWNLOAD_FRAME_ERROR = "0";
        public static final String RTSP_DOWNLOAD_TEARDOWN = "1";
        public static final String RTSP_DOWNLOAD_AUTHORIZATION_FAIL = "3";
        public static final String RTSP_DOWNLOAD_BEGIN = "4";
        public static final String RTSP_DOWNLOAD_OVER = "5";
        public static final String RTSP_DOWNLOAD_PAUSE = "6";
        public static final String RTSP_DOWNLOAD_KEY_MISMATH = "7";
    }

    public static class PlayerResultCode {
        public static final String STATE_PACKET_FRAME_ERROR = "0"; // 组帧失败
        public static final String STATE_RTSP_TEARDOWN_ERROR = "1"; // 内部要求关闭,如连接断开等
        public static final String STATE_RTSP_DESCRIBE_READY = "2"; // 会话已经收到Describe响应
        public static final String STATE_RTSP_AUTHORIZATION_FAIL = "3"; // RTSP鉴权失败
        public static final String STATE_RTSP_PLAY_READY = "4"; // 收到PLAY响应
        // public static final String STATE_RTSP_FILE_PLAY_OVER = "5"; //
        // 录像文件回放正常结束
        public static final String STATE_RTSP_KEY_MISMATCH = "7";
    }


}
