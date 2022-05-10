package com.app.xdcpay.Model.Api;

import android.content.Context;


public class EventModel {

    public String eventName;
    public Object requestObj;
    public Object responseObj;
    public Context context;
    public Object requestObj1;
    public Object requestObj2;
    public Object requestObj3;
    public Object requestObj4;
    public Object requestObj5;
    public Object requestObj6;
    public int pos;
    public String fResult;


    public EventModel() {
        //default constructor
    }

    public EventModel(Object requestObj, Object responseObj, String eventName, Context context) {
        this.requestObj = requestObj;
        this.responseObj = responseObj;
        this.eventName = eventName;
        this.context = context;
    }

    public EventModel(Object requestObj, Object responseObj, String eventName, Context context, String fResult) {
        this.requestObj = requestObj;
        this.responseObj = responseObj;
        this.eventName = eventName;
        this.context = context;
        this.fResult = fResult;
    }

    public EventModel(Object requestObj, Object responseObj, String eventName, Context context, int pos) {
        this.requestObj = requestObj;
        this.responseObj = responseObj;
        this.eventName = eventName;
        this.context = context;
        this.pos = pos;
    }

    public EventModel(Object requestObj, Object responseObj, String eventName, Context context,
                      int pos, boolean likeKey) {
        this.requestObj = requestObj;
        this.responseObj = responseObj;
        this.eventName = eventName;
        this.context = context;
        this.pos = pos;
    }

    public EventModel(Object requestObj, Object requestObj1, Object responseObj, String eventName, Context context) {
        this.requestObj = requestObj;
        this.responseObj = responseObj;
        this.requestObj1 = requestObj1;
        this.eventName = eventName;
        this.context = context;
    }

    public EventModel(Object requestObj, Object requestObj1, Object requestObj2,
                      Object requestObj3, Object requestObj4, Object requestObj5,
                      Object requestObj6, Object responseObj,
                      String eventName, Context context) {
        this.requestObj = requestObj;
        this.responseObj = responseObj;
        this.requestObj1 = requestObj1;
        this.requestObj2 = requestObj2;
        this.requestObj3 = requestObj3;
        this.requestObj4 = requestObj4;
        this.requestObj5 = requestObj5;
        this.requestObj6 = requestObj6;
        this.eventName = eventName;
        this.context = context;
    }

}
