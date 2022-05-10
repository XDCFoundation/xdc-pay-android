package com.app.xdcpay.Model.Api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiCurrencyConversionResponseModel {

    @SerializedName("status")
    private apiStatusResponseModel status;

    @SerializedName("data")
    private DATA data;

    public apiStatusResponseModel getStatus() {
        return status;
    }

    public void setStatus(apiStatusResponseModel status) {
        this.status = status;
    }

    public DATA getData() {
        return data;
    }

    public void setData(DATA data) {
        this.data = data;
    }

    public class DATA {
        @SerializedName("XDC")
        private XDC xdc;

        @SerializedName("ETH")
        private XDC eth;

        public XDC getXdc() {
            return xdc;
        }

        public void setXdc(XDC xdc) {
            this.xdc = xdc;
        }

        public XDC getEth() {
            return eth;
        }

        public void setEth(XDC eth) {
            this.eth = eth;
        }
    }

    public class XDC {
        @SerializedName("quote")
        private QUOTE quote;

        public QUOTE getQuote() {
            return quote;
        }

        public void setQuote(QUOTE quote) {
            this.quote = quote;
        }
    }

    public class QUOTE {

        @SerializedName("USD")
        private USD USD;

        public ApiCurrencyConversionResponseModel.USD getUSD() {
            return USD;
        }

        public void setUSD(ApiCurrencyConversionResponseModel.USD USD) {
            this.USD = USD;
        }
    }

    public class USD {
        @SerializedName("price")
        private double price;

        @SerializedName("volume_24h")
        private double volume_24h;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getVolume_24h() {
            return volume_24h;
        }

        public void setVolume_24h(double volume_24h) {
            this.volume_24h = volume_24h;
        }
    }
}
