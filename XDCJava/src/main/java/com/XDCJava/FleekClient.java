package com.XDCJava;

import com.XDCJava.Model.WalletData;
import com.XDCJava.callback.CreateAccountCallback;
import com.XDCJava.callback.EventCallback;
import com.XDCJava.contracts.src.main.java.Fleek;

import org.web3j.abi.FunctionEncoder;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.Sign;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;


public class FleekClient {
    private Web3j web3;
    public static FleekClient instance;
    private static double weiToXDC = 1000000000000000000d;
    //private static final String scAddress = "0x4bEE72D70368F3CB0625170Bdd9F1D51F9E690De";
    private static final String scAddress = "0xA522A6676A8B3fE6726F98F7906D33e97959CD44";
    private static final String GENERIC_FAILURE_MESSAGE = "Something went wrong, Please try after sometime..";

    public static FleekClient getInstance() {
        if (instance == null)
            instance = new FleekClient();

        return instance;
    }


    public Boolean isWeb3jConnected(int connectedNetwork) {
        if (connectedNetwork == 0) {
            web3 = Web3j.build(new

                    HttpService(AppConstants.BASE_TEST_URL));
        } else {
            web3 = Web3j.build(new

                    HttpService(AppConstants.BASE_MAIN_URL));
        }
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            //Connected
            //Show Error
            return !clientVersion.hasError();
        } catch (
                Exception e) {
            //Show Error
            return false;
        }
    }

    public void generateWallet(File walletDirectory, String Password, CreateAccountCallback createAccountCallback) {
        try {
            Bip39Wallet walletName = WalletUtils.generateBip39Wallet(Password, walletDirectory);
            System.out.println("wallet location: " + walletDirectory + "/" + walletName);
            Credentials credentials = WalletUtils.loadBip39Credentials(Password, walletName.getMnemonic());
            String accountAddress = credentials.getAddress();
            System.out.println("Account address: " + accountAddress);
            ECKeyPair ecKeyPair = credentials.getEcKeyPair();
            String privateKey = ecKeyPair.getPrivateKey().toString(16);
            String publickeyKey = ecKeyPair.getPublicKey().toString(16);
            System.out.println("privateKey: " + ecKeyPair.getPrivateKey());
            System.out.println("sPrivatekeyInHex: " + privateKey);
            String seedPhrase = walletName.getMnemonic();
            System.out.println("seedPhrase: " + seedPhrase);
            WalletData walletData = new WalletData();
            walletData.setAccountAddress(accountAddress/*.replace("0x", "xdc")*/);
            walletData.setPrivateKey(privateKey);
            walletData.setPublickeyKey(publickeyKey);
            walletData.setPassword(Password);
            walletData.setSeedPhrase(seedPhrase);
            createAccountCallback.success(walletData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void importWallet(String seedPhrase, String Password, File path, CreateAccountCallback createAccountCallback) {
        try {
            Credentials credentials = WalletUtils.loadBip39Credentials(Password, seedPhrase);
            String accountAddress = credentials.getAddress();
            System.out.println("Account address: " + accountAddress);
            WalletData walletData = new WalletData();
            walletData.setAccountAddress(credentials.getAddress());
            walletData.setPrivateKey(credentials.getEcKeyPair().getPrivateKey().toString(16));
            walletData.setPublickeyKey(credentials.getEcKeyPair().getPublicKey().toString(16));
            walletData.setSeedPhrase(seedPhrase);
            walletData.setPassword(Password);
            createAccountCallback.success(walletData);
        } catch (Exception e) {
            e.printStackTrace();
            createAccountCallback.failure(e.getMessage());
        }
    }

    /**
     * @param signer           the wallet address of buyer
     * @param message          the message which is sent while signature generation
     * @param signature        signature got using personalSign function
     * @param tokenId          generated from back-end and provided with video data
     * @param uri              ipfs url
     * @param nftPriceOld      price of NFT user is trying to buy
     * @param connectedNetwork is network available or not
     * @param eventCallback    to receive callback
     */
    public void mintAndTransfer(String signer,
                                String message,
                                String signature,
                                BigInteger tokenId,
                                String uri,
                                double nftPriceOld,
                                String private_key,
                                int connectedNetwork,
                                EventCallback eventCallback) {
        if (isWeb3jConnected(connectedNetwork)) {
            BigInteger nftPrice = (BigDecimal.valueOf(nftPriceOld * weiToXDC).toBigInteger());
            Credentials credentials = Credentials.create(private_key);
            Fleek fleek = Fleek.load(signer, web3, credentials, new DefaultGasProvider());
            try {
                String encodedFunction = FunctionEncoder.encode(fleek.mintAndTransfer(signer,
                        message, Numeric.hexStringToByteArray(signature), tokenId, uri, nftPrice));

                // Get nonce, the number of transactions
                BigInteger nonce;
                EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
                if (ethGetTransactionCount == null) {
                    eventCallback.failure(ethGetTransactionCount.getError().getMessage());
                }
                nonce = ethGetTransactionCount.getTransactionCount();
                //gasPrice and gasLimit can be set manually
                BigInteger gasPrice;
                EthGasPrice ethGasPrice = web3.ethGasPrice().sendAsync().get();
                if (ethGasPrice == null) {
                    eventCallback.failure(ethGasPrice.getError().getMessage());
                }
                //   gasPrice = ethGasPrice.getGasPrice();
                gasPrice = BigInteger.valueOf(400000000);
                BigInteger gasLimit = BigInteger.valueOf(300000L);

                //vault address
                RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                        scAddress, nftPrice, encodedFunction);
                //Signature Transaction
                byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                String hexValue = Numeric.toHexString(signMessage);
                //Send the transaction
                EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
                String hash = ethSendTransaction.getTransactionHash();
                Thread.sleep(10000);
                if (hash != null) {
                    EthGetTransactionReceipt transactionReceipt =
                            web3.ethGetTransactionReceipt(hash).send();
                    if (!transactionReceipt.hasError()
                            && transactionReceipt.getTransactionReceipt().isPresent()
                            && (!transactionReceipt.getResult().getStatus().equals("0x0"))) {
                        eventCallback.success(hash);
                        // return hash;
                    } else {
                        if (transactionReceipt.getError() != null) {
                            eventCallback.failure(transactionReceipt.getError().getMessage());
                        } else {
                            eventCallback.failure(GENERIC_FAILURE_MESSAGE);
                        }
                        // return "Failed";
                    }
                } else {
                    eventCallback.failure(ethSendTransaction.getError().getMessage());
                }

                /*eventCallback.success(fleek.mintAndTransfer(signer, message, signature, tokenId,
                        uri, nftPrice).sendAsync().get().getTransactionHash());*/
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                eventCallback.failure(e.getLocalizedMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            eventCallback.failure("Failed to Connect with Blockchain");
        }
    }

    /**
     * @param signer           the wallet address of buyer
     * @param message          the message which is sent while signature generation
     * @param signature        signature got using personalSign function
     * @param tokenId          token id from apis for unique token
     * @param nftPriceOld      price of NFT user is trying to buy
     * @param connectedNetwork is network available or not
     * @param eventCallback    to receive callback
     */
    public void mintedNftSale(String signer,
                              String message,
                              String signature,
                              BigInteger tokenId,
                              double nftPriceOld,
                              String private_key,
                              int connectedNetwork,
                              EventCallback eventCallback) {
        if (isWeb3jConnected(connectedNetwork)) {
            BigInteger nftPrice = (BigDecimal.valueOf(nftPriceOld * weiToXDC).toBigInteger());
            Credentials credentials = Credentials.create(private_key);
            Fleek fleek = Fleek.load(signer, web3, credentials, new DefaultGasProvider());
            try {
                String encodedFunction = FunctionEncoder.encode(fleek.mintedNftSale(signer,
                        message, Numeric.hexStringToByteArray(signature), tokenId,
                        nftPrice));

                // Get nonce, the number of transactions
                BigInteger nonce;
                EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
                if (ethGetTransactionCount == null) {
                    eventCallback.failure(ethGetTransactionCount.getError().getMessage());
                }
                nonce = ethGetTransactionCount.getTransactionCount();
                //gasPrice and gasLimit can be set manually
                BigInteger gasPrice;
                EthGasPrice ethGasPrice = web3.ethGasPrice().sendAsync().get();
                if (ethGasPrice == null) {
                    eventCallback.failure(ethGasPrice.getError().getMessage());
                }
                //   gasPrice = ethGasPrice.getGasPrice();
                gasPrice = BigInteger.valueOf(400000000);
                BigInteger gasLimit = BigInteger.valueOf(300000L);

                //vault address
                RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                        scAddress, nftPrice, encodedFunction);
                //Signature Transaction
                byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                String hexValue = Numeric.toHexString(signMessage);
                //Send the transaction
                EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
                String hash = ethSendTransaction.getTransactionHash();
                Thread.sleep(10000);
                if (hash != null) {
                    EthGetTransactionReceipt transactionReceipt =
                            web3.ethGetTransactionReceipt(hash).send();
                    if (!transactionReceipt.hasError()
                            && transactionReceipt.getTransactionReceipt().isPresent()
                            && (!transactionReceipt.getResult().getStatus().equals("0x0"))) {
                        eventCallback.success(hash);
                        // return hash;
                    } else {
                        if (transactionReceipt.getError() != null) {
                            eventCallback.failure(transactionReceipt.getError().getMessage());
                        } else {
                            eventCallback.failure(GENERIC_FAILURE_MESSAGE);
                        }
                        // return "Failed";
                    }
                } else {
                    eventCallback.failure(ethSendTransaction.getError().getMessage());
                }
                /*eventCallback.success(fleek.mintedNftSale(signer, message, signature, tokenId,
                        nftPrice).sendAsync().get().getTransactionHash());*/
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                eventCallback.failure(e.getLocalizedMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            eventCallback.failure("Failed to Connect with Blockchain");
        }
    }

    /**
     * @param owner_address The address to query the XDC balance
     * @return An uint256 representing the amount owned by the passed address.
     * @dev Gets the balance of the specified address.
     */
    public void getXdcBalance(String owner_address, int connectedNetwork, EventCallback callBack) {
        if (isWeb3jConnected(connectedNetwork)) {
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EthGetBalance balance = web3.ethGetBalance(owner_address, DefaultBlockParameterName.LATEST).send();
                            //return String.valueOf((balance.getBalance()));
                            callBack.success(String.valueOf(convertHexToDecimal(balance.getBalance())));
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.failure(e.getLocalizedMessage());
                        }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
                callBack.failure(String.valueOf(e.getMessage()));
            }
        } else {
            callBack.failure("check your connection");
        }
    }

    public BigInteger convertHexToDecimal(BigInteger hexValue) {
        BigInteger value
                = new BigInteger(AppConstants.hex_to_dec);
        return hexValue.divide(value);
    }

    //Hardik - 03-02-2022 - refrence from -> https://github.com/web3j/web3j/issues/222
    public void personalSign(String privateKey, String message, EventCallback callback) {
        try {
            Credentials credentials = Credentials.create(privateKey);
            //System.out.println("Address: " + credentials.getAddress());
            byte[] data = message.getBytes();
            //System.out.println("Sha3: " + Numeric.toHexString(Hash.sha3(data)));
            //System.out.println("Data: " + Numeric.toHexString(data));
            Sign.SignatureData signature = Sign.signMessage(data, credentials.getEcKeyPair());

            //System.out.println("R: " + Numeric.toHexString(signature.getR()));
            //System.out.println("S: " + Numeric.toHexString(signature.getS()));
            //System.out.println("V: " + Numeric.toHexString(signature.getV()));

            callback.success(Numeric.toHexString(signature.getR())
                    + Numeric.toHexString(signature.getS()).substring(2)
                    + Numeric.toHexString(signature.getV()).substring(2));
        } catch (Exception e) {
            callback.failure(e.getLocalizedMessage());
        }
    }

    //Hardik - 04-02-2022
    public Double getEstimatedGasFee(String fromAddress, String toAddress) throws IOException {

        return ((web3.ethEstimateGas(Transaction.createEthCallTransaction(
                fromAddress, toAddress, "0x2999")).send().getAmountUsed().doubleValue())
                / 1000000000000000000d);
//        return estimatedGas;
        /*EthGasPrice ethGasPrice = web3.ethGasPrice().sendAsync().get();
        return ethGasPrice.getGasPrice();*/
    }
}
