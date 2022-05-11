package com.app.xdcpay.Utils;

public class DatabaseConstants {

    public static final String DATABASE_NAME = "network_database";
    public static final int DATABASE_VERSION = 1;

    public static final String NETWORK_TABLE_NAME = "addNetwork_table";
    public static final String NETWORK_FIELD_1 = "network_name";
    public static final String NETWORK_FIELD_2 = "rpc_url";
    public static final String NETWORK_FIELD_3 = "chain_id";
    public static final String NETWORK_FIELD_4 = "currency_symbol";
    public static final String NETWORK_FIELD_5 = "block_explorer_url";
    public static final String NETWORK_FIELD_6 = "id";

    public static final String ACCOUNT_TABLE_NAME = "importAccount_table";
    public static final String ACCOUNT_FIELD_1 = "accountName";
    public static final String ACCOUNT_FIELD_2 = "accountAddress";
    public static final String ACCOUNT_FIELD_3 = "accountPrivateKey";
    public static final String ACCOUNT_FIELD_4 = "accountPublicKey";

    public static final String CONTACT_TABLE_NAME = "AddContact_table";
    public static final String CONTACT_FIELD_1 = "contactName";
    public static final String CONTACT_FIELD_2 = "contactWalletAddress";
//    public static final String CONTACT_FIELD_3 = "accountPrivateKey";
//    public static final String CONTACT_FIELD_4 = "accountPublicKey";
}
