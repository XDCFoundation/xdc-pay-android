package com.app.xdcpay.DataBase.Entity;

import static com.app.xdcpay.Utils.DatabaseConstants.NETWORK_FIELD_1;
import static com.app.xdcpay.Utils.DatabaseConstants.NETWORK_FIELD_2;
import static com.app.xdcpay.Utils.DatabaseConstants.NETWORK_FIELD_3;
import static com.app.xdcpay.Utils.DatabaseConstants.NETWORK_FIELD_4;
import static com.app.xdcpay.Utils.DatabaseConstants.NETWORK_FIELD_5;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.app.xdcpay.Utils.DatabaseConstants;

@Entity(tableName = DatabaseConstants.NETWORK_TABLE_NAME)
public class NetworkEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = NETWORK_FIELD_1)
    public String networkName;

    @ColumnInfo(name = NETWORK_FIELD_2)
    public String rpcUrl;

    @ColumnInfo(name = NETWORK_FIELD_3)
    public String chainId;

    @ColumnInfo(name = NETWORK_FIELD_4)
    public String currencySymbol;

    @ColumnInfo(name = NETWORK_FIELD_5)
    public String blockExplorerUrl;

    // below line we are creating constructor class.
    // inside constructor class we are not passing id because it is incrementing automatically

    public NetworkEntity(String networkName, String rpcUrl, String chainId, String currencySymbol, String blockExplorerUrl) {
        this.networkName = networkName;
        this.rpcUrl = rpcUrl;
        this.chainId = chainId;
        this.currencySymbol = currencySymbol;
        this.blockExplorerUrl = blockExplorerUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getRpcUrl() {
        return rpcUrl;
    }

    public void setRpcUrl(String rpcUrl) {
        this.rpcUrl = rpcUrl;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getBlockExplorerUrl() {
        return blockExplorerUrl;
    }

    public void setBlockExplorerUrl(String blockExplorerUrl) {
        this.blockExplorerUrl = blockExplorerUrl;
    }
}
