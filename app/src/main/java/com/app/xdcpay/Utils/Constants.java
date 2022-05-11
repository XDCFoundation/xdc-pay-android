package com.app.xdcpay.Utils;

import com.app.xdcpay.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
    public static String CONNECTED_NETWORK = "1";
    public static final String WALLET_DATA = "wallet_data";
    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String STRING_FORMAT = "%.2f";
    public static final String TEXT_USD = " USD";
    public static final String OBSERVER_URL = "https://observer.xdc.org/address-details/";
    public static final String WEBSITE_URL = "https://www.xdc.org/";
    public static final String CONTACT_US_URL = "https://www.xdc.org/contact-us";

    public static final String SIMPLEX_URL = "https://simplex.bringtotheblock.net/";
    public static final String APOTHEM_NET_URL = "http://faucet.apothem.network/";

    //network Constants
    public static String NETWORK_NAME = "NETWORK_NAME";
    public static String NETWORK_RPC_URL = "NETWORK_URL";
    public static String CHAIN_ID = "CHAIN_ID";
    public static String CURRENCY_SYMBOL = "CURRENCY_SYMBOL";
    public static String BLOCK_EXPLORE_URL = "BLOCK_EXPLORER_URL";
    public static String NETWORK_ID = "NETWORK_LIST_id";
    public static String NETWORK_CAN_DELETE = "NETWORK_CAN_DELETE";
    public static String YES = "Yes";
    public static String NO = "No";
    public static String ACCOUNT_NAME = "ACCOUNT_NAME";

    //MainNet Network
    public static final String MAIN_NET_NAME = "XDC Mainnet";
    public static final String MAIN_NET_RPC_URL = "https://xdcpayrpc.blocksscan.io/";
    public static final String MAIN_NET_ID = "50";
    public static final String MAIN_NET_SYMBOL = "XDC";
    public static final String MAIN_NET_URL = "https://observer.xdc.org";

    // Apothem Network
    public static final String APOTHEM_NAME = "XDC Apothem Testnet";
    public static final String APOTHEM_RPC_URL = "https://apothemxdcpayrpc.blocksscan.io/";
    public static final String APOTHEM_ID = "51";
    public static final String APOTHEM_SYMBOL = "XDC";
    public static final String APOTHEM_URL = "https://explorer.apothem.network";

    //LocalHost_8545
    public static final String LOCALHOST_8545_NAME = "Localhost 8545";
    public static final String LOCALHOST_RPC_URL = "https://localhost:8545";
    public static final String LOCALHOST_ID = "";
    public static final String LOCALHOST_SYMBOL = "";
    public static final String LOCALHOST_URL = "";

    public static final String PRIVACY_POLICY_URL = "https://www.xdc.org";
    public static final String SUPPORT_CENTER_URL = "https://www.xdc.org/resources";

    // list
    public static final ArrayList<String> keyTypeList = new ArrayList<>(Arrays.asList("Private"));
    public static final ArrayList<String> currencyList = new ArrayList<>(Arrays.asList("USD- United State Dollar"));

    //splash
   public static Integer[] imageId = {R.drawable.ic_illustration, R.drawable.ic_illustration_1};
    public static long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    public static long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
}
