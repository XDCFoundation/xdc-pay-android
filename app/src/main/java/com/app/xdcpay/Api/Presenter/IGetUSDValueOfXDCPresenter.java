package com.app.xdcpay.Api.Presenter;

import android.content.Context;

import java.util.Map;

public interface IGetUSDValueOfXDCPresenter {
    void onGetUSDValueOfXDC(Map<String, Object> currencyData, Context context);

    void unregister(Context context);

}
