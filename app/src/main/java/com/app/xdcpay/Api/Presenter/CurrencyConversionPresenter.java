package com.app.xdcpay.Api.Presenter;

import android.content.Context;
import android.util.Log;

import com.app.xdcpay.Api.BaseApiManager;
import com.app.xdcpay.Api.View.IGetUSDValueOfXDCView;
import com.app.xdcpay.Model.Api.ApiCurrencyConversionResponseModel;
import com.app.xdcpay.Model.Api.EventModel;
import com.app.xdcpay.Utils.EventConstants;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class CurrencyConversionPresenter implements IGetUSDValueOfXDCPresenter {
    private final IGetUSDValueOfXDCView view;

    public CurrencyConversionPresenter(IGetUSDValueOfXDCView view) {
        this.view = view;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onGetUSDValueOfXDC(Map<String, Object> currencyData, Context context) {
        BaseApiManager.getInstance().getCurrencyConversionApi(new EventModel(currencyData, null, null, null, context));
    }

    @Override
    public void unregister(Context context) {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(EventModel eventModel) {
        if (eventModel == null)
            return;
        switch (eventModel.eventName) {
            case EventConstants.CURRENCY_CONVERSION_FAIL:
                onGetUSDValueOfXDCFailure();
                break;
            case EventConstants.CURRENCY_CONVERSION_SUCCESS:
                onGetUSDValueOfXDCSuccess(eventModel);
                break;
        }
    }

    private void onGetUSDValueOfXDCFailure() {
        view.onGetUSDValueOfXDCFailure(EventConstants.FAILURE);
    }

    private void onGetUSDValueOfXDCSuccess(EventModel eventModel) {

        if (eventModel.responseObj == null) {
            return;
        }

        ApiCurrencyConversionResponseModel apiResponse = (ApiCurrencyConversionResponseModel) eventModel.responseObj;
        Double USDValue = null;
        if (apiResponse.getData().getXdc() != null) {
            USDValue = apiResponse.getData().getXdc().getQuote().getUSD().getPrice();
        }
        if (apiResponse.getData().getXdc() != null) {
            USDValue = apiResponse.getData().getEth().getQuote().getUSD().getPrice();
        }

       /* ApiCurrencyConversionResponseModel.DATA data = apiResponse.getData();
        Gson gson = new Gson();
        String jsonString = gson.toJson(data);
        try {
            JSONObject request = new JSONObject(jsonString);
//            String
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.d("CurrencyData:", "" + jsonString);*/

        view.onGetUSDValueOfXDCSuccess(USDValue);

    }
}