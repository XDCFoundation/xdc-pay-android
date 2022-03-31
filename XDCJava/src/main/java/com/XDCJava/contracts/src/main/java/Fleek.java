package com.XDCJava.contracts.src.main.java;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Fleek extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162002157380380620021578339810160408190526200003491620001b0565b604080518082018252600e81526d4e46544d61726b6574506c61636560901b60208083019182528351808501909452600384526213919560ea1b90840152815191929162000085916000916200010a565b5080516200009b9060019060208401906200010a565b5050600780546001600160a01b031916339081179091556040519091506000907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a3600880546001600160a01b0319166001600160a01b03939093169290921790915560095562000229565b8280546200011890620001ec565b90600052602060002090601f0160209004810192826200013c576000855562000187565b82601f106200015757805160ff191683800117855562000187565b8280016001018555821562000187579182015b82811115620001875782518255916020019190600101906200016a565b506200019592915062000199565b5090565b5b808211156200019557600081556001016200019a565b60008060408385031215620001c457600080fd5b82516001600160a01b0381168114620001dc57600080fd5b6020939093015192949293505050565b600181811c908216806200020157607f821691505b602082108114156200022357634e487b7160e01b600052602260045260246000fd5b50919050565b611f1e80620002396000396000f3fe6080604052600436106101355760003560e01c8063715018a6116100ab57806395d89b411161006f57806395d89b4114610348578063a22cb4651461035d578063b88d4fde1461037d578063c87b56dd1461039d578063e985e9c5146103bd578063f2fde38b146103dd57600080fd5b8063715018a6146102c25780637828968d146102d75780638da5cb5b146102f75780638f32d59b1461031557806394b470601461033557600080fd5b806342842e0e116100fd57806342842e0e1461020b578063574aa2561461022b57806359939b2e1461024b5780636352211e1461026f578063683453bf1461028f57806370a08231146102a257600080fd5b806301ffc9a71461013a57806306fdde031461016f578063081812fc14610191578063095ea7b3146101c957806323b872dd146101eb575b600080fd5b34801561014657600080fd5b5061015a610155366004611807565b6103fd565b60405190151581526020015b60405180910390f35b34801561017b57600080fd5b5061018461044f565b604051610166919061187c565b34801561019d57600080fd5b506101b16101ac36600461188f565b6104e1565b6040516001600160a01b039091168152602001610166565b3480156101d557600080fd5b506101e96101e43660046118bd565b61056e565b005b3480156101f757600080fd5b506101e96102063660046118e9565b610684565b34801561021757600080fd5b506101e96102263660046118e9565b6106b5565b34801561023757600080fd5b506101e961024636600461188f565b6106d0565b34801561025757600080fd5b5061026160095481565b604051908152602001610166565b34801561027b57600080fd5b506101b161028a36600461188f565b6106ff565b6101e961029d3660046119cd565b610776565b3480156102ae57600080fd5b506102616102bd366004611a7a565b61084a565b3480156102ce57600080fd5b506101e96108d1565b3480156102e357600080fd5b506101e96102f2366004611a7a565b610945565b34801561030357600080fd5b506007546001600160a01b03166101b1565b34801561032157600080fd5b506007546001600160a01b0316331461015a565b6101e9610343366004611a97565b610991565b34801561035457600080fd5b50610184610a57565b34801561036957600080fd5b506101e9610378366004611b1f565b610a66565b34801561038957600080fd5b506101e9610398366004611b5d565b610a75565b3480156103a957600080fd5b506101846103b836600461188f565b610aad565b3480156103c957600080fd5b5061015a6103d8366004611bc9565b610c24565b3480156103e957600080fd5b506101e96103f8366004611a7a565b610c52565b60006001600160e01b031982166380ac58cd60e01b148061042e57506001600160e01b03198216635b5e139f60e01b145b8061044957506301ffc9a760e01b6001600160e01b03198316145b92915050565b60606000805461045e90611bf7565b80601f016020809104026020016040519081016040528092919081815260200182805461048a90611bf7565b80156104d75780601f106104ac576101008083540402835291602001916104d7565b820191906000526020600020905b8154815290600101906020018083116104ba57829003601f168201915b5050505050905090565b60006104ec82610c88565b6105525760405162461bcd60e51b815260206004820152602c60248201527f4552433732313a20617070726f76656420717565727920666f72206e6f6e657860448201526b34b9ba32b73a103a37b5b2b760a11b60648201526084015b60405180910390fd5b506000908152600460205260409020546001600160a01b031690565b6000610579826106ff565b9050806001600160a01b0316836001600160a01b031614156105e75760405162461bcd60e51b815260206004820152602160248201527f4552433732313a20617070726f76616c20746f2063757272656e74206f776e656044820152603960f91b6064820152608401610549565b336001600160a01b038216148061060357506106038133610c24565b6106755760405162461bcd60e51b815260206004820152603860248201527f4552433732313a20617070726f76652063616c6c6572206973206e6f74206f7760448201527f6e6572206e6f7220617070726f76656420666f7220616c6c00000000000000006064820152608401610549565b61067f8383610ca5565b505050565b61068e3382610d13565b6106aa5760405162461bcd60e51b815260040161054990611c32565b61067f838383610dd5565b61067f83838360405180602001604052806000815250610a75565b6007546001600160a01b031633146106fa5760405162461bcd60e51b815260040161054990611c83565b600955565b6000818152600260205260408120546001600160a01b0316806104495760405162461bcd60e51b815260206004820152602960248201527f4552433732313a206f776e657220717565727920666f72206e6f6e657869737460448201526832b73a103a37b5b2b760b91b6064820152608401610549565b600061078186610f71565b9050600061078f8287610fa1565b9050336001600160a01b03898116908316146107bd5760405162461bcd60e51b815260040161054990611cb8565b83816001600160a01b031631116108165760405162461bcd60e51b815260206004820152601c60248201527f496e73756666696369656e742066756e647320746f2072656465656d000000006044820152606401610549565b6108208287611078565b61082a86866111ab565b610835828288610dd5565b61083f8285611236565b505050505050505050565b60006001600160a01b0382166108b55760405162461bcd60e51b815260206004820152602a60248201527f4552433732313a2062616c616e636520717565727920666f7220746865207a65604482015269726f206164647265737360b01b6064820152608401610549565b506001600160a01b031660009081526003602052604090205490565b6007546001600160a01b031633146108fb5760405162461bcd60e51b815260040161054990611c83565b6007546040516000916001600160a01b0316907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600780546001600160a01b0319169055565b6007546001600160a01b0316331461096f5760405162461bcd60e51b815260040161054990611c83565b600880546001600160a01b0319166001600160a01b0392909216919091179055565b600061099c85610f71565b905060006109aa8286610fa1565b9050336001600160a01b03888116908316146109d85760405162461bcd60e51b815260040161054990611cb8565b33318410610a285760405162461bcd60e51b815260206004820152601c60248201527f496e73756666696369656e742066756e647320746f2072656465656d000000006044820152606401610549565b610a43828287604051806020016040528060008152506112c1565b610a4d8285611236565b5050505050505050565b60606001805461045e90611bf7565b610a713383836112f4565b5050565b610a7f3383610d13565b610a9b5760405162461bcd60e51b815260040161054990611c32565b610aa7848484846112c1565b50505050565b6060610ab882610c88565b610b1e5760405162461bcd60e51b815260206004820152603160248201527f45524337323155524953746f726167653a2055524920717565727920666f72206044820152703737b732bc34b9ba32b73a103a37b5b2b760791b6064820152608401610549565b60008281526006602052604081208054610b3790611bf7565b80601f0160208091040260200160405190810160405280929190818152602001828054610b6390611bf7565b8015610bb05780601f10610b8557610100808354040283529160200191610bb0565b820191906000526020600020905b815481529060010190602001808311610b9357829003601f168201915b505050505090506000610bce60408051602081019091526000815290565b9050805160001415610be1575092915050565b815115610c13578082604051602001610bfb929190611cf9565b60405160208183030381529060405292505050919050565b610c1c846113c3565b949350505050565b6001600160a01b03918216600090815260056020908152604080832093909416825291909152205460ff1690565b6007546001600160a01b03163314610c7c5760405162461bcd60e51b815260040161054990611c83565b610c858161149b565b50565b6000908152600260205260409020546001600160a01b0316151590565b600081815260046020526040902080546001600160a01b0319166001600160a01b0384169081179091558190610cda826106ff565b6001600160a01b03167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92560405160405180910390a45050565b6000610d1e82610c88565b610d7f5760405162461bcd60e51b815260206004820152602c60248201527f4552433732313a206f70657261746f7220717565727920666f72206e6f6e657860448201526b34b9ba32b73a103a37b5b2b760a11b6064820152608401610549565b6000610d8a836106ff565b9050806001600160a01b0316846001600160a01b03161480610dc55750836001600160a01b0316610dba846104e1565b6001600160a01b0316145b80610c1c5750610c1c8185610c24565b826001600160a01b0316610de8826106ff565b6001600160a01b031614610e4c5760405162461bcd60e51b815260206004820152602560248201527f4552433732313a207472616e736665722066726f6d20696e636f72726563742060448201526437bbb732b960d91b6064820152608401610549565b6001600160a01b038216610eae5760405162461bcd60e51b8152602060048201526024808201527f4552433732313a207472616e7366657220746f20746865207a65726f206164646044820152637265737360e01b6064820152608401610549565b610eb9600082610ca5565b6001600160a01b0383166000908152600360205260408120805460019290610ee2908490611d3e565b90915550506001600160a01b0382166000908152600360205260408120805460019290610f10908490611d55565b909155505060008181526002602052604080822080546001600160a01b0319166001600160a01b0386811691821790925591518493918716917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef91a4505050565b600081604051602001610f849190611d6d565b604051602081830303815290604052805190602001209050919050565b6000806000808451604114610fbc5760009350505050610449565b50505060208201516040830151606084015160001a601b811015610fe857610fe5601b82611d89565b90505b8060ff16601b1415801561100057508060ff16601c14155b156110115760009350505050610449565b60408051600081526020810180835288905260ff831691810191909152606081018490526080810183905260019060a0016020604051602081039080840390855afa158015611064573d6000803e3d6000fd5b505050602060405103519350505050610449565b6001600160a01b0382166110ce5760405162461bcd60e51b815260206004820181905260248201527f4552433732313a206d696e7420746f20746865207a65726f20616464726573736044820152606401610549565b6110d781610c88565b156111245760405162461bcd60e51b815260206004820152601c60248201527f4552433732313a20746f6b656e20616c7265616479206d696e746564000000006044820152606401610549565b6001600160a01b038216600090815260036020526040812080546001929061114d908490611d55565b909155505060008181526002602052604080822080546001600160a01b0319166001600160a01b03861690811790915590518392907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908290a45050565b6111b482610c88565b6112175760405162461bcd60e51b815260206004820152602e60248201527f45524337323155524953746f726167653a2055524920736574206f66206e6f6e60448201526d32bc34b9ba32b73a103a37b5b2b760911b6064820152608401610549565b6000828152600660209081526040909120825161067f92840190611758565b60006064600954836112489190611dae565b6112529190611de3565b905060006112608284611d3e565b6008546040519192506001600160a01b03169083156108fc029084906000818181858888f1935050505061129357600080fd5b6040516001600160a01b0385169082156108fc029083906000818181858888f19350505050610aa757600080fd5b6112cc848484610dd5565b6112d88484848461155c565b610aa75760405162461bcd60e51b815260040161054990611df7565b816001600160a01b0316836001600160a01b031614156113565760405162461bcd60e51b815260206004820152601960248201527f4552433732313a20617070726f766520746f2063616c6c6572000000000000006044820152606401610549565b6001600160a01b03838116600081815260056020908152604080832094871680845294825291829020805460ff191686151590811790915591519182527f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31910160405180910390a3505050565b60606113ce82610c88565b6114325760405162461bcd60e51b815260206004820152602f60248201527f4552433732314d657461646174613a2055524920717565727920666f72206e6f60448201526e3732bc34b9ba32b73a103a37b5b2b760891b6064820152608401610549565b600061144960408051602081019091526000815290565b905060008151116114695760405180602001604052806000815250611494565b806114738461165a565b604051602001611484929190611cf9565b6040516020818303038152906040525b9392505050565b6001600160a01b0381166115005760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b6064820152608401610549565b6007546040516001600160a01b038084169216907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a3600780546001600160a01b0319166001600160a01b0392909216919091179055565b60006001600160a01b0384163b1561164f57604051630a85bd0160e11b81526001600160a01b0385169063150b7a02906115a0903390899088908890600401611e49565b6020604051808303816000875af19250505080156115db575060408051601f3d908101601f191682019092526115d891810190611e86565b60015b611635573d808015611609576040519150601f19603f3d011682016040523d82523d6000602084013e61160e565b606091505b50805161162d5760405162461bcd60e51b815260040161054990611df7565b805181602001fd5b6001600160e01b031916630a85bd0160e11b149050610c1c565b506001949350505050565b60608161167e5750506040805180820190915260018152600360fc1b602082015290565b8160005b81156116a8578061169281611ea3565b91506116a19050600a83611de3565b9150611682565b60008167ffffffffffffffff8111156116c3576116c361192a565b6040519080825280601f01601f1916602001820160405280156116ed576020820181803683370190505b5090505b8415610c1c57611702600183611d3e565b915061170f600a86611ebe565b61171a906030611d55565b60f81b81838151811061172f5761172f611ed2565b60200101906001600160f81b031916908160001a905350611751600a86611de3565b94506116f1565b82805461176490611bf7565b90600052602060002090601f01602090048101928261178657600085556117cc565b82601f1061179f57805160ff19168380011785556117cc565b828001600101855582156117cc579182015b828111156117cc5782518255916020019190600101906117b1565b506117d89291506117dc565b5090565b5b808211156117d857600081556001016117dd565b6001600160e01b031981168114610c8557600080fd5b60006020828403121561181957600080fd5b8135611494816117f1565b60005b8381101561183f578181015183820152602001611827565b83811115610aa75750506000910152565b60008151808452611868816020860160208601611824565b601f01601f19169290920160200192915050565b6020815260006114946020830184611850565b6000602082840312156118a157600080fd5b5035919050565b6001600160a01b0381168114610c8557600080fd5b600080604083850312156118d057600080fd5b82356118db816118a8565b946020939093013593505050565b6000806000606084860312156118fe57600080fd5b8335611909816118a8565b92506020840135611919816118a8565b929592945050506040919091013590565b634e487b7160e01b600052604160045260246000fd5b600082601f83011261195157600080fd5b813567ffffffffffffffff8082111561196c5761196c61192a565b604051601f8301601f19908116603f011681019082821181831017156119945761199461192a565b816040528381528660208588010111156119ad57600080fd5b836020870160208301376000602085830101528094505050505092915050565b60008060008060008060c087890312156119e657600080fd5b86356119f1816118a8565b9550602087013567ffffffffffffffff80821115611a0e57600080fd5b611a1a8a838b01611940565b96506040890135915080821115611a3057600080fd5b611a3c8a838b01611940565b9550606089013594506080890135915080821115611a5957600080fd5b50611a6689828a01611940565b92505060a087013590509295509295509295565b600060208284031215611a8c57600080fd5b8135611494816118a8565b600080600080600060a08688031215611aaf57600080fd5b8535611aba816118a8565b9450602086013567ffffffffffffffff80821115611ad757600080fd5b611ae389838a01611940565b95506040880135915080821115611af957600080fd5b50611b0688828901611940565b9598949750949560608101359550608001359392505050565b60008060408385031215611b3257600080fd5b8235611b3d816118a8565b915060208301358015158114611b5257600080fd5b809150509250929050565b60008060008060808587031215611b7357600080fd5b8435611b7e816118a8565b93506020850135611b8e816118a8565b925060408501359150606085013567ffffffffffffffff811115611bb157600080fd5b611bbd87828801611940565b91505092959194509250565b60008060408385031215611bdc57600080fd5b8235611be7816118a8565b91506020830135611b52816118a8565b600181811c90821680611c0b57607f821691505b60208210811415611c2c57634e487b7160e01b600052602260045260246000fd5b50919050565b60208082526031908201527f4552433732313a207472616e736665722063616c6c6572206973206e6f74206f6040820152701ddb995c881b9bdc88185c1c1c9bdd9959607a1b606082015260800190565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b60208082526021908201527f496e76616c6964206f7220756e617574686f72697a6564207369676e617475726040820152606560f81b606082015260800190565b60008351611d0b818460208801611824565b835190830190611d1f818360208801611824565b01949350505050565b634e487b7160e01b600052601160045260246000fd5b600082821015611d5057611d50611d28565b500390565b60008219821115611d6857611d68611d28565b500190565b60008251611d7f818460208701611824565b9190910192915050565b600060ff821660ff84168060ff03821115611da657611da6611d28565b019392505050565b6000816000190483118215151615611dc857611dc8611d28565b500290565b634e487b7160e01b600052601260045260246000fd5b600082611df257611df2611dcd565b500490565b60208082526032908201527f4552433732313a207472616e7366657220746f206e6f6e20455243373231526560408201527131b2b4bb32b91034b6b83632b6b2b73a32b960711b606082015260800190565b6001600160a01b0385811682528416602082015260408101839052608060608201819052600090611e7c90830184611850565b9695505050505050565b600060208284031215611e9857600080fd5b8151611494816117f1565b6000600019821415611eb757611eb7611d28565b5060010190565b600082611ecd57611ecd611dcd565b500690565b634e487b7160e01b600052603260045260246000fdfea26469706673582212204cfad44d9274e0e9821e47362dcff9d2f11e581be8486c0032b744faf02b08fd64736f6c634300080b0033";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_CHANGECOMISSIONPERCENTAGE = "changeComissionPercentage";

    public static final String FUNC_CHANGEPORTALFEERECEIVER = "changePortalFeeReceiver";

    public static final String FUNC_COMISSIONPERCENTAGE = "comissionPercentage";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_MINTANDTRANSFER = "mintAndTransfer";

    public static final String FUNC_MINTEDNFTSALE = "mintedNftSale";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_safeTransferFrom = "safeTransferFrom";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOKENURI = "tokenURI";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>(true) {
            }));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Bool>() {
            }));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>(true) {
            }));
    ;

    @Deprecated
    protected Fleek(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Fleek(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Fleek(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Fleek(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalForAllEventResponse>() {
            @Override
            public ApprovalForAllEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVALFORALL_EVENT, log);
                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVE,
                Arrays.<Type>asList(new Address(160, to),
                        new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String owner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new Address(160, owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> changeComissionPercentage(BigInteger value) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGECOMISSIONPERCENTAGE,
                Arrays.<Type>asList(new Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changePortalFeeReceiver(String addr) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGEPORTALFEERECEIVER,
                Arrays.<Type>asList(new Address(160, addr)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> comissionPercentage() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COMISSIONPERCENTAGE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getApproved(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETAPPROVED,
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String owner, String operator) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISAPPROVEDFORALL,
                Arrays.<Type>asList(new Address(160, owner),
                        new Address(160, operator)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isOwner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISOWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public org.web3j.abi.datatypes.Function mintAndTransfer(String signer,
                                    String message,
                                    byte[] signature,
                                    BigInteger tokenId,
                                    String uri, BigInteger nftPrice) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MINTANDTRANSFER,
                Arrays.<Type>asList(new Address(160, signer),
                        new Utf8String(message),
                        new org.web3j.abi.datatypes.DynamicBytes(signature),
                        new Uint256(tokenId),
                        new Utf8String(uri),
                        new Uint256(nftPrice)),
                Collections.<TypeReference<?>>emptyList());
        //return executeRemoteCallTransaction(function);
        return function;
    }

    public org.web3j.abi.datatypes.Function mintedNftSale(
            String signer,
            String message,
            byte[] signature,
            BigInteger tokenId,
            BigInteger nftPrice) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MINTEDNFTSALE,
                Arrays.<Type>asList(new Address(160, signer),
                        new Utf8String(message),
                        new org.web3j.abi.datatypes.DynamicBytes(signature),
                        new Uint256(tokenId),
                        new Uint256(nftPrice)),
                Collections.<TypeReference<?>>emptyList());
        //return executeRemoteCallTransaction(function);
        return function;
    }

    public RemoteFunctionCall<String> name() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ownerOf(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNEROF,
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_safeTransferFrom,
                Arrays.<Type>asList(new Address(160, from),
                        new Address(160, to),
                        new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, byte[] _data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_safeTransferFrom,
                Arrays.<Type>asList(new Address(160, from),
                        new Address(160, to),
                        new Uint256(tokenId),
                        new org.web3j.abi.datatypes.DynamicBytes(_data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator, Boolean approved) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETAPPROVALFORALL,
                Arrays.<Type>asList(new Address(160, operator),
                        new Bool(approved)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SYMBOL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> tokenURI(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENURI,
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new Address(160, from),
                        new Address(160, to),
                        new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new Address(160, newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Fleek load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Fleek(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Fleek load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Fleek(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Fleek load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Fleek(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Fleek load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Fleek(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Fleek> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String adminAccount, BigInteger percentage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, adminAccount),
                new Uint256(percentage)));
        return deployRemoteCall(Fleek.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Fleek> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String adminAccount, BigInteger percentage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, adminAccount),
                new Uint256(percentage)));
        return deployRemoteCall(Fleek.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Fleek> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String adminAccount, BigInteger percentage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, adminAccount),
                new Uint256(percentage)));
        return deployRemoteCall(Fleek.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Fleek> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String adminAccount, BigInteger percentage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, adminAccount),
                new Uint256(percentage)));
        return deployRemoteCall(Fleek.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String owner;

        public String operator;

        public Boolean approved;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger tokenId;
    }
}
