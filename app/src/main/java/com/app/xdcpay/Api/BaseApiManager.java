package com.app.xdcpay.Api;

import static com.app.xdcpay.Utils.ApiConstants.BaseUrlApi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.app.xdcpay.Model.Api.ApiCurrencyConversionResponseModel;
import com.app.xdcpay.Model.Api.EventModel;
import com.app.xdcpay.Utils.AppUtility;
import com.app.xdcpay.Utils.EventConstants;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApiManager {

    private static BaseApiManager instance;
    private static ApiService apiService;
    private static ProgressDialog progress;
    private static Dialog customDialog;

    public BaseApiManager() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //logging.setLevel(HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true).addInterceptor(logging);

        httpClient.readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BaseUrlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        apiService = mRetrofit.create(ApiService.class);

    }

    public static BaseApiManager getInstance() {
        if (instance == null)
            instance = new BaseApiManager();
        return instance;
    }

    public ApiService getApiService() {
        if (instance == null) {
            instance = new BaseApiManager();
        }
        return apiService;
    }

    public static synchronized void init() {
        if (instance == null)
            instance = new BaseApiManager();
    }

    public void getCurrencyConversionApi(final EventModel eventModel, String symbol) {
        Call<ResponseBody> call = apiService.getValueForCurrencyConversion((Map<String, Object>) eventModel.requestObj);
//        Log.d("ApiCurrencyResponse:", "" + call.request().url() + " : " + eventModel.requestObj);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                AppUtility.hideDialog();
                try {
                    String responseModel = response.body().string();
                    JSONObject jsonObj = new JSONObject(responseModel);
                    String status = jsonObj.getString("status");
                    JSONObject jsonObject1 = new JSONObject(status);
                    int error_code = jsonObject1.getInt("error_code");

                    if (error_code == 0) {
                        String error_message = jsonObject1.getString("error_message");
                        EventBus.getDefault().post(new EventModel(null, responseModel, EventConstants.CURRENCY_CONVERSION_SUCCESS, eventModel.context));

                    } else {
                        EventBus.getDefault().post(new EventModel(null, null, EventConstants.CURRENCY_CONVERSION_FAIL, eventModel.context));
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                AppUtility.hideDialog();
                EventBus.getDefault().post(new EventModel(null, null, EventConstants.CURRENCY_CONVERSION_FAIL, eventModel.context));
            }
        });
    }
}


