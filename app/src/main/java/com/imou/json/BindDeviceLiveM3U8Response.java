package com.imou.json;

import com.google.gson.annotations.JsonAdapter;

import java.util.List;

@JsonAdapter(BindDeviceLiveM3U8TypeAdapter.class)
public class BindDeviceLiveM3U8Response extends LeChengResponse<BindDeviceLiveM3U8Response.Data> {
    public static class Data {
        private String liveToken;
        private int liveStatus;
        private int liveType;
        private String deviceId;
        private String channelId;
        private int coverUpdate;
        private List<Stream> streams;
        private List<Job> job;

        public String getLiveToken() {
            return liveToken;
        }

        public void setLiveToken(String liveToken) {
            this.liveToken = liveToken;
        }

        public int getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(int liveStatus) {
            this.liveStatus = liveStatus;
        }

        public int getLiveType() {
            return liveType;
        }

        public void setLiveType(int liveType) {
            this.liveType = liveType;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public int getCoverUpdate() {
            return coverUpdate;
        }

        public void setCoverUpdate(int coverUpdate) {
            this.coverUpdate = coverUpdate;
        }

        public List<Stream> getStreams() {
            return streams;
        }

        public void setStreams(List<Stream> streams) {
            this.streams = streams;
        }

        public List<Job> getJob() {
            return job;
        }

        public void setJob(List<Job> job) {
            this.job = job;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "liveToken='" + liveToken + '\'' +
                    ", liveStatus=" + liveStatus +
                    ", liveType=" + liveType +
                    ", deviceId='" + deviceId + '\'' +
                    ", channelId='" + channelId + '\'' +
                    ", coverUpdate=" + coverUpdate +
                    ", streams=" + streams +
                    ", job=" + job +
                    '}';
        }

        public static class Stream {
            private String hls;
            private String coverUrl;
            private int streamId;

            public String getHls() {
                return hls;
            }

            public void setHls(String hls) {
                this.hls = hls;
            }

            public String getCoverUrl() {
                return coverUrl;
            }

            public void setCoverUrl(String coverUrl) {
                this.coverUrl = coverUrl;
            }

            public int getStreamId() {
                return streamId;
            }

            public void setStreamId(int streamId) {
                this.streamId = streamId;
            }

            @Override
            public String toString() {
                return "Stream{" +
                        "hls='" + hls + '\'' +
                        ", coverUrl='" + coverUrl + '\'' +
                        ", streamId=" + streamId +
                        '}';
            }
        }

        public static class Job {
            private boolean status;
            private String period;

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public String getPeriod() {
                return period;
            }

            public void setPeriod(String period) {
                this.period = period;
            }

            @Override
            public String toString() {
                return "Job{" +
                        "status=" + status +
                        ", period='" + period + '\'' +
                        '}';
            }
        }
    }
}
