package com.nightmare.jli.retrofit2_0test.Beans;

/**
 * Created by J.Li on 2016/6/12.
 */
public class Forecast {

    /**
     * {
     * "fengxiang":"北风",
     * "fengli":"5-6级",
     * "high":"高温 24℃",
     * "type":"晴",
     * "low":"低温 11℃",
     * "date":"3日星期六"
     * }
     */
    private String fengxiang;
    private String fengli;
    private String high;
    private String type;
    private String low;
    private String date;
    public void setFengxiang(String fengxiang) {
         this.fengxiang = fengxiang;
     }
     public String getFengxiang() {
         return fengxiang;
     }

    public void setFengli(String fengli) {
         this.fengli = fengli;
     }
     public String getFengli() {
         return fengli;
     }

    public void setHigh(String high) {
         this.high = high;
     }
     public String getHigh() {
         return high;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setLow(String low) {
         this.low = low;
     }
     public String getLow() {
         return low;
     }

    public void setDate(String date) {
         this.date = date;
     }
     public String getDate() {
         return date;
     }

    @Override
    public String toString() {
        return getDate()+"，天气："+getType()+"，最"+getHigh()+"~最"+getLow()+"，"+getFengxiang()+" "+getFengli();
    }
}