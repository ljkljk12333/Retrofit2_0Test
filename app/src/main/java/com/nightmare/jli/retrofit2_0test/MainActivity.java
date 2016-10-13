package com.nightmare.jli.retrofit2_0test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nightmare.jli.retrofit2_0test.Beans.Data;
import com.nightmare.jli.retrofit2_0test.Beans.WeatherBean;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.IOException;
import java.io.InputStream;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by J.Li on 2016/6/12.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG="J.Lee-Retrofit2.0Test";

    /**
     * 天气预报host
     */
    String weatherBaseUrl = "http://wthrcdn.etouch.cn/";

    /**
     * 天气图标
     */
    String weatherIconBaseUrl = "http://api.map.baidu.com/";

    /**
     * 信息显示TextView实例
     */
    TextView textView;

    /**
     * 天气图标
     */
    ImageView imageView;

    /**
     * 下拉刷新Layout实例
     */
    SwipeRefreshLayout swipeRefreshLayout;
    /**
     * 下拉刷新Layout.OnRefreshListener实例
     */
    SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeatherByRxJava();
            }
        };

        textView=(TextView)findViewById(R.id.text);
        imageView=(ImageView)findViewById(R.id.image);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);

        //设置刷新动画变换颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        /**
         * 由于SwipeRefreshLayout刷新动画的关键变量mOriginalOffsetTop是在其onMeasure方法内获取正确值，
         * 在没有设置正确值前下拉效果动画是没有显示到正确的坐标，所以导致用户看不到，
         * 如果要加入启动SwipeRefreshLayout自动刷新功能的话，
         * 我们需要让mOriginalOffsetTop在调用setRefreshing方法前正确赋值，
         * 所以推荐在onMeasure方法触发后再调用setRefreshing及OnRefreshListener.onRefresh方法，
         * 这里我们设置一个GlobalLayoutListener，在onGlobalLayout方法里实现onMeasure方法触发后的刷新操作。
         */
//        swipeRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                //因为只需要在启动时执行一次自动刷新，所以这里就移除了Listener
//                swipeRefreshLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                //调用setRefreshing方法
//                swipeRefreshLayout.setRefreshing(true);
//                //调用OnRefreshListener.onRefresh方法
//                onRefreshListener.onRefresh();
//            }
//        });

        /**
         * 我们还可以直接post一个Runnable在run方法里调用setRefreshing及OnRefreshListener.onRefresh方法进行数据刷新，
         * post的Runnable会在SwipeRefreshLayout控件初始化之后执行run方法，
         * 所以解决了让mOriginalOffsetTop在调用setRefreshing方法前正确赋值的问题。
         */
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                //调用setRefreshing方法
                swipeRefreshLayout.setRefreshing(true);
                //调用OnRefreshListener.onRefresh方法
                onRefreshListener.onRefresh();
            }
        });


//        getByRxJava();
    }

    @Override
    protected void onResume() {
        super.onResume();

//
    }

    private void getWeatherByRxJava() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Observable<WeatherBean> observable = service.getWeatherData("北京");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherBean>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: ");
                        Toast.makeText(getApplicationContext(),
                                "天气刷新成功",
                                Toast.LENGTH_SHORT)
                                .show();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        Toast.makeText(getApplicationContext(),
                                "天气刷新失败",
                                Toast.LENGTH_SHORT)
                                .show();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(WeatherBean weatherBean) {
                        Data dataBean = weatherBean.getData();
                        if(dataBean !=null){
                            getWeatherIcon(dataBean.getForecast().get(0).getType());
                            textView.setText(dataBean.toString());
//                                textView.setText(textView.getText()+"\r\n" +
//                                        "时间:" + dataBean.get(i).getTime() + "，内容:" + dataBean.get(i).getContext());
                        }
                    }
                });
    }


    private void getWeatherIcon(final String tqType) {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String tqTypePingYin = toPinYin(tqType);
                subscriber.onNext(tqTypePingYin);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String pinyin) {
                        if(!pinyin.isEmpty()) {

                            OkHttpClient client = new OkHttpClient();

                            final Request request = new Request.Builder()
                                    .url(weatherIconBaseUrl + "images/weather/day/" + pinyin + ".png")
                                    .build();

                            Call call = client.newCall(request);

                            call.enqueue(new com.squareup.okhttp.Callback() {
                                @Override
                                public void onFailure(Request request, IOException e) {

                                }

                                @Override
                                public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                                    if (response.body() != null) {
                                        InputStream is = response.body().byteStream();
                                        final Bitmap tempBitmap = BitmapFactory.decodeStream(is);
                                        is.close();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageView.setImageBitmap(tempBitmap);
                                            }
                                        });
                                    } else {
                                        Log.e(TAG, "onResponse: body==null");
                                    }
                                }
                            });
                        }
                    }
                });
//
//        if(!tqType.isEmpty()) {
//            String tqTypePingYin = "";
//            for (int i = 0; i < tqType.toCharArray().length; i++) {
//                String[] tempStrings = PinyinHelper.toHanyuPinyinStringArray(tqType.toCharArray()[i]);
//                if (tempStrings != null && tempStrings.length > 0)
//                    for (int j = 0; j < tempStrings.length; j++) {
//                        tqTypePingYin += tempStrings[j];
//                    }
//            }
//
//
//        }
    }


    private String toPinYin(String hanzi) {
        String pinyin = "";
        if (!hanzi.isEmpty()) {

            try {
                //拼音输出格式
                HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
                //拼音小写
                format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
                //无音标模式
                format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
                //用V代替ü
                format.setVCharType(HanyuPinyinVCharType.WITH_V);

                for (int i = 0; i < hanzi.toCharArray().length; i++) {
                    String[] tempStrings = PinyinHelper.toHanyuPinyinStringArray(hanzi.toCharArray()[i], format);
                    if (tempStrings != null && tempStrings.length > 0)
                        for (int j = 0; j < tempStrings.length; j++) {
                            pinyin += tempStrings[j];
                        }
                }
            }catch (BadHanyuPinyinOutputFormatCombination pyex){
                Log.e(TAG, "toPinYin: "+pyex.getMessage() );
                pinyin="";
            }
        }
        return pinyin;
    }

}
