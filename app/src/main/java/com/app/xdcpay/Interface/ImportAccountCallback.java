package com.app.xdcpay.Interface;

import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;

import java.util.List;

public interface ImportAccountCallback {
    void AccountListOnClickListener(int pos, List<AccountEntity> networkLists);
    void AccountDeleteOnClickListener(String strPrivateKey);

}
