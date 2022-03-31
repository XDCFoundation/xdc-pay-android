package com.XDCJava.callback;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface EventCallback
{
    void success(String hash) throws Exception;

    void failure(Throwable t);

    void failure(String message);
}
