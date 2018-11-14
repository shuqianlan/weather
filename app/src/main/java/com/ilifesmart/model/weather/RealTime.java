package com.ilifesmart.model.weather;

import com.ilifesmart.weather.WeatherActivity;

import java.util.List;

public class RealTime {
    /**
     * status : ok
     * lang : zh_CN
     * server_time : 1542189548
     * tzshift : 28800
     * location : [30.3,120.2]
     * unit : metric
     * result : {"status":"ok","o3":54,"co":0.6,"temperature":16,"pm10":65,"skycon":"PARTLY_CLOUDY_NIGHT","cloudrate":0.3,"aqi":59,"dswrf":185,"visibility":12.2,"humidity":0.69,"so2":11,"ultraviolet":{"index":0,"desc":"无"},"pres":101843.67,"pm25":42,"no2":59,"precipitation":{"nearest":{"status":"ok","distance":14.72,"intensity":0.1875},"local":{"status":"ok","intensity":0,"datasource":"radar"}},"comfort":{"index":6,"desc":"凉爽"},"wind":{"direction":94.49,"speed":12.3}}
     */

    private String status;
    private String lang;
    private int server_time;
    private int tzshift;
    private String unit;
    private ResultBean result;
    private List<Double> location;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getServer_time() {
        return server_time;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }

    public int getTzshift() {
        return tzshift;
    }

    public void setTzshift(int tzshift) {
        this.tzshift = tzshift;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public static class ResultBean {
        /**
         * status : ok
         * o3 : 54
         * co : 0.6
         * temperature : 16
         * pm10 : 65
         * skycon : PARTLY_CLOUDY_NIGHT
         * cloudrate : 0.3
         * aqi : 59
         * dswrf : 185
         * visibility : 12.2
         * humidity : 0.69
         * so2 : 11
         * ultraviolet : {"index":0,"desc":"无"}
         * pres : 101843.67
         * pm25 : 42
         * no2 : 59
         * precipitation : {"nearest":{"status":"ok","distance":14.72,"intensity":0.1875},"local":{"status":"ok","intensity":0,"datasource":"radar"}}
         * comfort : {"index":6,"desc":"凉爽"}
         * wind : {"direction":94.49,"speed":12.3}
         */

        private String status;
        private int o3;
        private double co;
        private int temperature;
        private int pm10;
        private String skycon;
        private double cloudrate;
        private int aqi;
        private int dswrf;
        private double visibility;
        private double humidity;
        private int so2;
        private UltravioletBean ultraviolet;
        private double pres;
        private int pm25;
        private int no2;
        private PrecipitationBean precipitation;
        private ComfortBean comfort;
        private WindBean wind;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getO3() {
            return o3;
        }

        public void setO3(int o3) {
            this.o3 = o3;
        }

        public double getCo() {
            return co;
        }

        public void setCo(double co) {
            this.co = co;
        }

        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }

        public int getPm10() {
            return pm10;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public String getSkycon() {
            return skycon;
        }

        public void setSkycon(String skycon) {
            this.skycon = skycon;
        }

        public double getCloudrate() {
            return cloudrate;
        }

        public void setCloudrate(double cloudrate) {
            this.cloudrate = cloudrate;
        }

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public int getDswrf() {
            return dswrf;
        }

        public void setDswrf(int dswrf) {
            this.dswrf = dswrf;
        }

        public double getVisibility() {
            return visibility;
        }

        public void setVisibility(double visibility) {
            this.visibility = visibility;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public int getSo2() {
            return so2;
        }

        public void setSo2(int so2) {
            this.so2 = so2;
        }

        public UltravioletBean getUltraviolet() {
            return ultraviolet;
        }

        public void setUltraviolet(UltravioletBean ultraviolet) {
            this.ultraviolet = ultraviolet;
        }

        public double getPres() {
            return pres;
        }

        public void setPres(double pres) {
            this.pres = pres;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public int getNo2() {
            return no2;
        }

        public void setNo2(int no2) {
            this.no2 = no2;
        }

        public PrecipitationBean getPrecipitation() {
            return precipitation;
        }

        public void setPrecipitation(PrecipitationBean precipitation) {
            this.precipitation = precipitation;
        }

        public ComfortBean getComfort() {
            return comfort;
        }

        public void setComfort(ComfortBean comfort) {
            this.comfort = comfort;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class UltravioletBean {
            /**
             * index : 0
             * desc : 无
             */

            private int index;
            private String desc;

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class PrecipitationBean {
            /**
             * nearest : {"status":"ok","distance":14.72,"intensity":0.1875}
             * local : {"status":"ok","intensity":0,"datasource":"radar"}
             */

            private NearestBean nearest;
            private LocalBean local;

            public NearestBean getNearest() {
                return nearest;
            }

            public void setNearest(NearestBean nearest) {
                this.nearest = nearest;
            }

            public LocalBean getLocal() {
                return local;
            }

            public void setLocal(LocalBean local) {
                this.local = local;
            }

            public static class NearestBean {
                /**
                 * status : ok
                 * distance : 14.72
                 * intensity : 0.1875
                 */

                private String status;
                private double distance;
                private double intensity;

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public double getDistance() {
                    return distance;
                }

                public void setDistance(double distance) {
                    this.distance = distance;
                }

                public double getIntensity() {
                    return intensity;
                }

                public void setIntensity(double intensity) {
                    this.intensity = intensity;
                }
            }

            public static class LocalBean {
                /**
                 * status : ok
                 * intensity : 0
                 * datasource : radar
                 */

                private String status;
                private int intensity;
                private String datasource;

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public int getIntensity() {
                    return intensity;
                }

                public void setIntensity(int intensity) {
                    this.intensity = intensity;
                }

                public String getDatasource() {
                    return datasource;
                }

                public void setDatasource(String datasource) {
                    this.datasource = datasource;
                }
            }
        }

        public static class ComfortBean {
            /**
             * index : 6
             * desc : 凉爽
             */

            private int index;
            private String desc;

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class WindBean {
            /**
             * direction : 94.49
             * speed : 12.3
             */

            private double direction;
            private double speed;

            public double getDirection() {
                return direction;
            }

            public void setDirection(double direction) {
                this.direction = direction;
            }

            public double getSpeed() {
                return speed;
            }

            public void setSpeed(double speed) {
                this.speed = speed;
            }
        }
    }
}
