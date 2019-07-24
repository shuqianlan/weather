package com.imou.json;

import com.google.gson.stream.JsonReader;
import com.imou.CustomTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class BindDeviceLiveM3U8TypeAdapter extends CustomTypeAdapter<BindDeviceLiveM3U8Response,BindDeviceLiveM3U8Response.Data> {
    @Override
    protected BindDeviceLiveM3U8Response createResponse() {
        return new BindDeviceLiveM3U8Response();
    }

    @Override
    protected BindDeviceLiveM3U8Response.Data getResultData(JsonReader in) {
        BindDeviceLiveM3U8Response.Data data = new BindDeviceLiveM3U8Response.Data();
        List<BindDeviceLiveM3U8Response.Data.Stream> streams = new ArrayList<>();
        List<BindDeviceLiveM3U8Response.Data.Job> jobs = new ArrayList<>();
        try {
        	while (in.hasNext()) {
        	    switch (in.nextName()) {
                    case "liveToken":
                        data.setLiveToken(in.nextString());
                        break;
                    case "liveStatus":
                        data.setLiveStatus(in.nextInt());
                        break;
                    case "liveType":
                        data.setLiveType(in.nextInt());
                        break;
                    case "coverUpdate":
                        data.setCoverUpdate(in.nextInt());
                        break;
                    case "streams":
                        in.beginArray();
                        while (in.hasNext()) {
                            streams.add(getStream(in));
                        }
                        data.setStreams(streams);
                        in.endArray();
                        break;
                    case "job":
                        in.beginArray();
                        while (in.hasNext()) {
                            jobs.add(getJob(in));
                        }
                        data.setJob(jobs);
                        in.endArray();
                        break;
                    default:
                        in.skipValue();
                }
            }
        } catch(Exception ex) {
        	ex.printStackTrace();
        }

        return data;
    }

    private BindDeviceLiveM3U8Response.Data.Stream getStream(JsonReader in) {
        BindDeviceLiveM3U8Response.Data.Stream stream = new BindDeviceLiveM3U8Response.Data.Stream();

        try {
        	while (in.hasNext()) {
        	    switch (in.nextName()) {
                    case "hls":
                        stream.setHls(in.nextString());
                        break;
                    case "coverUrl":
                        stream.setCoverUrl(in.nextString());
                        break;
                    case "streamId":
                        stream.setStreamId(in.nextInt());
                        break;
                }
            }
        } catch(Exception ex) {
        	ex.printStackTrace();
        }

        return stream;
    }

    private BindDeviceLiveM3U8Response.Data.Job getJob(JsonReader in) {
        BindDeviceLiveM3U8Response.Data.Job job = new BindDeviceLiveM3U8Response.Data.Job();

        try {
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "status":
                        job.setStatus(in.nextBoolean());
                        break;
                    case "period":
                        job.setPeriod(in.nextString());
                        break;
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return job;
    }

}
