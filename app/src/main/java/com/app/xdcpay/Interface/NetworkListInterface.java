package com.app.xdcpay.Interface;

import com.app.xdcpay.DataBase.Entity.NetworkEntity;

import java.util.List;

public interface NetworkListInterface {

    void networkListOnClickListener(int pos, List<NetworkEntity> networkLists);
}
