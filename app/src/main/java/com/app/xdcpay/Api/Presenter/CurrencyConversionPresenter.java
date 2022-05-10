package com.app.xdcpay.Api.Presenter;

import android.content.Context;


import com.app.xdcpay.Api.BaseApiManager;
import com.app.xdcpay.Api.View.IGetUSDValueOfXDCView;

import com.app.xdcpay.Model.Api.EventModel;
import com.app.xdcpay.Utils.EventConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class CurrencyConversionPresenter implements IGetUSDValueOfXDCPresenter {
    private final IGetUSDValueOfXDCView view;
    String currencySymbol;

    public CurrencyConversionPresenter(IGetUSDValueOfXDCView view) {
        this.view = view;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onGetUSDValueOfXDC(Map<String, Object> currencyData, Context context, String currencySymbol) {
        this.currencySymbol = currencySymbol;
        BaseApiManager.getInstance().getCurrencyConversionApi(new EventModel(currencyData,
                null, null, null, context), currencySymbol);
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
        String apiResponse = String.valueOf(eventModel.responseObj);
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(apiResponse);
            String data = jsonObj.getString("data");
            JSONObject jsonObject1 = new JSONObject(data);
            String currency = jsonObject1.getString(currencySymbol);
            JSONObject jsonObject2 = new JSONObject(currency);
            String quote = jsonObject2.getString("quote");
            JSONObject jsonObject3 = new JSONObject(quote);
            String USD = jsonObject3.getString("USD");
            JSONObject jsonObject4 = new JSONObject(USD);
            double USDValue = jsonObject4.getDouble("price");
            view.onGetUSDValueOfXDCSuccess(USDValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        JSONObject jsonObj = new JSONObject(apiResponse);
//        String status = jsonObj.getString("status");
//        JSONObject jsonObject1 = new JSONObject(status);
//        int error_code = jsonObject1.getInt("error_code");
//        Double USDValue = null;
//        if (apiResponse.getData().getXdc() != null) {
//            USDValue = apiResponse.getData().getXdc().getQuote().getUSD().getPrice();
//        }
//        if (apiResponse.getData().getEth() != null) {
//            USDValue = apiResponse.getData().getEth().getQuote().getUSD().getPrice();
//        }

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

//

    }
}