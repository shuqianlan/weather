package com.ilifesmart.nanohttp;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class MyNanoHttp extends NanoHTTPD {
    public static final String TAG = "NanoHttpD";
    private String rootPath;
    String file_none_miss = "<html><body><h1>文件不存在</h1></body></html>";
    String err_params = "<html><body><h1>无效的格式</h1></body></html>";

    public MyNanoHttp(int port, String _rootPath) {
        super(port);
        rootPath = _rootPath;
    }

    @Override
    public Response serve(IHTTPSession session) {

        String uri = session.getUri();
        if (uri.equals("/mgapkg/")) {

            try {
                Map<String, List<String>> params = session.getParameters();
                String filePath = rootPath + "/" +
                        params.get("agtkey").get(0) + "/" +
                        params.get("ver").get(0) + "/" +
                        params.get("file").get(0);

                File file = new File(filePath);

                Log.d(TAG, "serve: filePath " + filePath);
                if (file.exists()) {
                    FileInputStream inputStream = new FileInputStream(file);
                    return newFixedLengthResponse(Response.Status.OK, getMimeTypeForFile(filePath), inputStream, file.length());
                } else {
                    return newFixedLengthResponse(Response.Status.BAD_REQUEST, "text/html", file_none_miss);
                }

            } catch (Throwable ex) {
                return newFixedLengthResponse(Response.Status.BAD_REQUEST, "text/html", file_none_miss);
            }
        }

        return newFixedLengthResponse(Response.Status.BAD_REQUEST, "text/html", err_params);
    }
}
