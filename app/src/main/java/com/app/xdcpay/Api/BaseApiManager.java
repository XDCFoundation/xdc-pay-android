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

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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

    public void getCurrencyConversionApi(final EventModel eventModel) {
        Call<ApiCurrencyConversionResponseModel> call = apiService.getValueForCurrencyConversion((Map<String, Object>) eventModel.requestObj);
        Log.d("ApiCurrencyResponse:", "" + call.request().url() + " : " + eventModel.requestObj);
        call.enqueue(new Callback<ApiCurrencyConversionResponseModel>() {
            @Override
            public void onResponse(Call<ApiCurrencyConversionResponseModel> call, Response<ApiCurrencyConversionResponseModel> response) {
                AppUtility.hideDialog();
                ApiCurrencyConversionResponseModel responseModel = response.body();

                if (responseModel == null || responseModel.getStatus().getErrorCode() != 0) {
                    EventBus.getDefault().post(new EventModel(null, null, EventConstants.CURRENCY_CONVERSION_FAIL, eventModel.context));
                } else {
                    EventBus.getDefault().post(new EventModel(null, responseModel, EventConstants.CURRENCY_CONVERSION_SUCCESS, eventModel.context));
                }
            }

            @Override
            public void onFailure(Call<ApiCurrencyConversionResponseModel> call, Throwable t) {
                AppUtility.hideDialog();
                EventBus.getDefault().post(new EventModel(null, null, EventConstants.CURRENCY_CONVERSION_FAIL, eventModel.context));
            }
        });
    }
}


