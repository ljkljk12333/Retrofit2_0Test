package com.nightmare.jli.retrofit2_0test.Beans;

/**
 * Created by J.Li on 2016/6/12.
 */
public class WeatherBean {

    /**
     * {
     * "desc":"OK",
     * "status":1000,
     * "data":{...}
     * }
     */
    private String desc;
    private int status;
    private Data data;
    public void setDesc(String desc) {
         this.desc = desc;
     }
     public String getDesc() {
         return desc;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

}