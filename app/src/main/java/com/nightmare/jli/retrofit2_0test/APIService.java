package com.nightmare.jli.retrofit2_0test;


import com.nightmare.jli.retrofit2_0test.Beans.WeatherBean;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by J.Li on 2016/6/12.
 */
public interface APIService {

    @GET("weather_mini")
    Call<WeatherBean> loadWeather(@Query("city") String city);

    /**
     * retrofit 支持 rxjava 整合
     * 这种方法适用于新接口
     */
    @GET("weather_mini")
    Observable<WeatherBean> getWeatherData(@Query("city") String city);


}
