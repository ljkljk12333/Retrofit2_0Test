package com.nightmare.jli.retrofit2_0test.Beans;
import java.util.List;

/**
 * Created by J.Li on 2016/6/12.
 */
public class Data {

    /**
     * {
     * "wendu":"22",
     * "ganmao":"风较大，较易发生感冒，注意防护。",
     * "forecast":[{...}...],
     * "yesterday":{...},
     * "aqi":"59",
     * "city":"北京"
     * }
     */
    private String wendu;
    private String ganmao;
    private List<Forecast> forecast;
    private Yesterday yesterday;
    private String aqi;
    private String city;
    public void setWendu(String wendu) {
         this.wendu = wendu;
     }
     public String getWendu() {
         return wendu;
     }

    public void setGanmao(String ganmao) {
         this.ganmao = ganmao;
     }
     public String getGanmao() {
         return ganmao;
     }

    public void setForecast(List<Forecast> forecast) {
         this.forecast = forecast;
     }
     public List<Forecast> getForecast() {
         return forecast;
     }

    public void setYesterday(Yesterday yesterday) {
         this.yesterday = yesterday;
     }
     public Yesterday getYesterday() {
         return yesterday;
     }

    public void setAqi(String aqi) {
         this.aqi = aqi;
     }
     public String getAqi() {
         return aqi;
     }

    public void setCity(String city) {
         this.city = city;
     }
     public String getCity() {
         return city;
     }

    @Override
    public String toString() {
        String returnValue="城市："+getCity()+"，当前温度："+getWendu()+"，当前污染指数："+getAqi();
        List<Forecast> forecasts=getForecast();
        if(forecasts!=null&&forecasts.size()>0){
            for(int i=0;i<forecasts.size();i++){
                returnValue+="\r\n"+forecasts.get(i).toString();
            }
//            returnValue+="\r\n"+
        }
        return returnValue;
    }
}