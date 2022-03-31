package com.XDCJava.callback;

import com.XDCJava.Model.Token721DetailsResponse;

public interface Token721DetailCallback {
    void success(Token721DetailsResponse tokenApiModel);

    void success(String message) throws Exception;
    void success(String token,String transactionHash) throws Exception;
    void failure(Throwable t);

    void failure(String message);
}
