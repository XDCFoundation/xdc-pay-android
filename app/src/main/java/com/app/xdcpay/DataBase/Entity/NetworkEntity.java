package com.app.xdcpay.DataBase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "addNetwork_table")
public class NetworkEntity {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "network_name")
    public String networkName;

    @ColumnInfo(name = "rpc_url")
    public String rpcUrl;

    @ColumnInfo(name = "chain_id")
    public String chainId;

    @ColumnInfo(name = "currency_symbol")
    public String currencySymbol;

    @ColumnInfo(name = "block_explorer_url")
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
