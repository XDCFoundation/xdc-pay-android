package com.app.xdcpay.DataBase.Entity;

import static com.app.xdcpay.Utils.DatabaseConstants.ACCOUNT_FIELD_1;
import static com.app.xdcpay.Utils.DatabaseConstants.ACCOUNT_FIELD_2;
import static com.app.xdcpay.Utils.DatabaseConstants.ACCOUNT_FIELD_3;
import static com.app.xdcpay.Utils.DatabaseConstants.ACCOUNT_FIELD_4;
import static com.app.xdcpay.Utils.DatabaseConstants.ACCOUNT_FIELD_5;
import static com.app.xdcpay.Utils.DatabaseConstants.ACCOUNT_FIELD_6;
import static com.app.xdcpay.Utils.DatabaseConstants.ACCOUNT_TABLE_NAME;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = ACCOUNT_TABLE_NAME)
public class AccountEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = ACCOUNT_FIELD_1)
    public String accountName;

    @ColumnInfo(name = ACCOUNT_FIELD_2)
    public String accountAddress;

    @ColumnInfo(name = ACCOUNT_FIELD_3)
    public String accountPrivateKey;

    @ColumnInfo(name = ACCOUNT_FIELD_4)
    public String accountPublicKey;

    @ColumnInfo(name = ACCOUNT_FIELD_5)
    public String seedphrase;

    @ColumnInfo(name = ACCOUNT_FIELD_6)
    public String accountType;

    public AccountEntity(String accountName, String accountAddress, String accountPrivateKey, String accountPublicKey, String seedphrase, String accountType) {
        this.accountName = accountName;
        this.accountAddress = accountAddress;
        this.accountPrivateKey = accountPrivateKey;
        this.accountPublicKey = accountPublicKey;
        this.seedphrase = seedphrase;
        this.accountType = accountType;
    }

    public void setSeedphrase(String seedphrase) {
        this.seedphrase = seedphrase;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getSeedphrase() {
        return seedphrase;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public String getAccountPrivateKey() {
        return accountPrivateKey;
    }

    public void setAccountPrivateKey(String accountPrivateKey) {
        this.accountPrivateKey = accountPrivateKey;
    }

    public String getAccountPublicKey() {
        return accountPublicKey;
    }

    public void setAccountPublicKey(String accountPublicKey) {
        this.accountPublicKey = accountPublicKey;
    }

    public String getAccountseedphrase() {
        return accountPublicKey;
    }

    public void setAccountseedphrase(String accountPublicKey) {
        this.seedphrase = accountPublicKey;
    }
}
