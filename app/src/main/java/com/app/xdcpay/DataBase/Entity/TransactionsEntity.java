package com.app.xdcpay.DataBase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.app.xdcpay.Utils.DatabaseConstants.TRANSACTIONS_FIELD_1;
import static com.app.xdcpay.Utils.DatabaseConstants.TRANSACTIONS_FIELD_2;
import static com.app.xdcpay.Utils.DatabaseConstants.TRANSACTIONS_FIELD_3;
import static com.app.xdcpay.Utils.DatabaseConstants.TRANSACTIONS_FIELD_4;
import static com.app.xdcpay.Utils.DatabaseConstants.TRANSACTIONS_FIELD_5;
import static com.app.xdcpay.Utils.DatabaseConstants.TRANSACTIONS_FIELD_6;
import static com.app.xdcpay.Utils.DatabaseConstants.TRANSACTIONS_TABLE_NAME;

@Entity(tableName = TRANSACTIONS_TABLE_NAME)
public
class TransactionsEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = TRANSACTIONS_FIELD_1)
    public String sender;

    @ColumnInfo(name = TRANSACTIONS_FIELD_2)
    public String receiver;

    @ColumnInfo(name = TRANSACTIONS_FIELD_3)
    public String amount;

    @ColumnInfo(name = TRANSACTIONS_FIELD_4)
    public String gas_limit;

    @ColumnInfo(name = TRANSACTIONS_FIELD_5)
    public String gas_price;

    @ColumnInfo(name = TRANSACTIONS_FIELD_6)
    public String transaction_hash;

    public TransactionsEntity(String sender, String receiver, String amount, String gas_limit, String gas_price, String transaction_hash) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.gas_limit = gas_limit;
        this.gas_price = gas_price;
        this.transaction_hash = transaction_hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGas_limit() {
        return gas_limit;
    }

    public void setGas_limit(String gas_limit) {
        this.gas_limit = gas_limit;
    }

    public String getGas_price() {
        return gas_price;
    }

    public void setGas_price(String gas_price) {
        this.gas_price = gas_price;
    }

    public String getTransaction_hash() {
        return transaction_hash;
    }

    public void setTransaction_hash(String transaction_hash) {
        this.transaction_hash = transaction_hash;
    }
}
