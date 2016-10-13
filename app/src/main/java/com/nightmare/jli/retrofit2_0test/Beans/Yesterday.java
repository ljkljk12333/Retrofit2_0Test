package com.nightmare.jli.retrofit2_0test.Beans;

/**
 * Created by J.Li on 2016/6/12.
 */
public class Yesterday {

    /**
     * {
     * "fl":"微风",
     * "fx":"无持续风向",
     * "high":"高温 23℃",
     * "type":"晴",
     * "low":"低温 12℃",
     * "date":"2日星期五"
     * }
     */
    private String fl;
    private String fx;
    private String high;
    private String type;
    private String low;
    private String date;
    public void setFl(String fl) {
         this.fl = fl;
     }
     public String getFl() {
         return fl;
     }

    public void setFx(String fx) {
         this.fx = fx;
     }
     public String getFx() {
         return fx;
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
        return getDate()+"，天气："+getType()+"，最"+getHigh()+"~最"+getLow()+"，"+getFx()+" "+getFl();
    }
}