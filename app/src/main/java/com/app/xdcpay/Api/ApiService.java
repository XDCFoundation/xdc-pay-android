package com.app.xdcpay.Api;

import static com.app.xdcpay.Utils.ApiConstants.CURRENCY_CONVERSION;

import com.app.xdcpay.Model.Api.ApiCurrencyConversionResponseModel;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET(CURRENCY_CONVERSION)
    Call<ResponseBody> getValueForCurrencyConversion(@QueryMap Map<String, Object> parameters);
}
