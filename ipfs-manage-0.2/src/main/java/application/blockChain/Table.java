package application.blockChain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class Table extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50600a6000819055506032600281905550601e6001819055506001600860006101000a81548160ff0219169083151502179055506001600a60006101000a81548160ff0219169083151502179055506001600660006101000a81548160ff0219169083151502179055506118c2806100896000396000f3fe608060405234801561001057600080fd5b50600436106101005760003560e01c8063785e5c0111610097578063b3a723cd11610066578063b3a723cd146107c9578063d79ab0b714610884578063dfdc1def146109d6578063ef083e0b146109f457610100565b8063785e5c011461068457806384776949146106b257806396fe4383146106e05780639abe9a911461079b57610100565b80632720228b116100d35780632720228b1461040e5780632fb55c8c146104915780636907bf7614610514578063698ec0501461053257610100565b806301a645e71461010557806310f9da29146101235780631659afc5146101a65780631992abab146102da575b600080fd5b61010d610aaf565b6040518082815260200191505060405180910390f35b61012b610ab9565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561016b578082015181840152602081019050610150565b50505050905090810190601f1680156101985780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61025f600480360360208110156101bc57600080fd5b81019080803590602001906401000000008111156101d957600080fd5b8201836020820111156101eb57600080fd5b8035906020019184600183028401116401000000008311171561020d57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610b5b565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561029f578082015181840152602081019050610284565b50505050905090810190601f1680156102cc5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610393600480360360208110156102f057600080fd5b810190808035906020019064010000000081111561030d57600080fd5b82018360208201111561031f57600080fd5b8035906020019184600183028401116401000000008311171561034157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610c69565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103d35780820151818401526020810190506103b8565b50505050905090810190601f1680156104005780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610416610d77565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561045657808201518184015260208101905061043b565b50505050905090810190601f1680156104835780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610499610e19565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156104d95780820151818401526020810190506104be565b50505050905090810190601f1680156105065780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61051c610ebb565b6040518082815260200191505060405180910390f35b6106826004803603604081101561054857600080fd5b810190808035906020019064010000000081111561056557600080fd5b82018360208201111561057757600080fd5b8035906020019184600183028401116401000000008311171561059957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156105fc57600080fd5b82018360208201111561060e57600080fd5b8035906020019184600183028401116401000000008311171561063057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610ec5565b005b6106b06004803603602081101561069a57600080fd5b8101908080359060200190929190505050611173565b005b6106de600480360360208110156106c857600080fd5b810190808035906020019092919050505061117d565b005b610799600480360360208110156106f657600080fd5b810190808035906020019064010000000081111561071357600080fd5b82018360208201111561072557600080fd5b8035906020019184600183028401116401000000008311171561074757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050611187565b005b6107c7600480360360208110156107b157600080fd5b81019080803590602001909291905050506111d1565b005b610882600480360360208110156107df57600080fd5b81019080803590602001906401000000008111156107fc57600080fd5b82018360208201111561080e57600080fd5b8035906020019184600183028401116401000000008311171561083057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506111db565b005b6109d46004803603604081101561089a57600080fd5b81019080803590602001906401000000008111156108b757600080fd5b8201836020820111156108c957600080fd5b803590602001918460018302840111640100000000831117156108eb57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561094e57600080fd5b82018360208201111561096057600080fd5b8035906020019184600183028401116401000000008311171561098257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050611225565b005b6109de6114d3565b6040518082815260200191505060405180910390f35b610aad60048036036020811015610a0a57600080fd5b8101908080359060200190640100000000811115610a2757600080fd5b820183602082011115610a3957600080fd5b80359060200191846001830284011164010000000083111715610a5b57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506114dc565b005b6000600254905090565b606060058054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610b515780601f10610b2657610100808354040283529160200191610b51565b820191906000526020600020905b815481529060010190602001808311610b3457829003601f168201915b5050505050905090565b60606003826040518082805190602001908083835b60208310610b935780518252602082019150602081019050602083039250610b70565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c5d5780601f10610c3257610100808354040283529160200191610c5d565b820191906000526020600020905b815481529060010190602001808311610c4057829003601f168201915b50505050509050919050565b6060600b826040518082805190602001908083835b60208310610ca15780518252602082019150602081019050602083039250610c7e565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d6b5780601f10610d4057610100808354040283529160200191610d6b565b820191906000526020600020905b815481529060010190602001808311610d4e57829003601f168201915b50505050509050919050565b606060078054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610e0f5780601f10610de457610100808354040283529160200191610e0f565b820191906000526020600020905b815481529060010190602001808311610df257829003601f168201915b5050505050905090565b606060098054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610eb15780601f10610e8657610100808354040283529160200191610eb1565b820191906000526020600020905b815481529060010190602001808311610e9457829003601f168201915b5050505050905090565b6000600154905090565b81600015156003826040518082805190602001908083835b60208310610f005780518252602082019150602081019050602083039250610edd565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010160009054906101000a900460ff16151514156110545760016003826040518082805190602001908083835b60208310610f845780518252602082019150602081019050602083039250610f61565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010160006101000a81548160ff02191690831515021790555060016004826040518082805190602001908083835b602083106110095780518252602082019150602081019050602083039250610fe6565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81548160ff0219169083151502179055505b600115156004826040518082805190602001908083835b6020831061108e578051825260208201915060208101905060208303925061106b565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060009054906101000a900460ff161515146110db57600080fd5b6110e481611526565b816003846040518082805190602001908083835b6020831061111b57805182526020820191506020810190506020830392506110f8565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060000190805190602001906111649291906117e8565b5061116e816115ab565b505050565b8060018190555050565b8060008190555050565b60011515600860009054906101000a900460ff161515146111a757600080fd5b6111af611630565b80600790805190602001906111c59291906117e8565b506111ce61164d565b50565b8060028190555050565b60011515600a60009054906101000a900460ff161515146111fb57600080fd5b61120361166a565b80600990805190602001906112199291906117e8565b50611222611687565b50565b8160001515600b826040518082805190602001908083835b60208310611260578051825260208201915060208101905060208303925061123d565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010160009054906101000a900460ff16151514156113b4576001600b826040518082805190602001908083835b602083106112e457805182526020820191506020810190506020830392506112c1565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010160006101000a81548160ff0219169083151502179055506001600c826040518082805190602001908083835b602083106113695780518252602082019150602081019050602083039250611346565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81548160ff0219169083151502179055505b60011515600c826040518082805190602001908083835b602083106113ee57805182526020820191506020810190506020830392506113cb565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060009054906101000a900460ff1615151461143b57600080fd5b611444816116a4565b81600b846040518082805190602001908083835b6020831061147b5780518252602082019150602081019050602083039250611458565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060000190805190602001906114c49291906117e8565b506114ce81611729565b505050565b60008054905090565b60011515600660009054906101000a900460ff161515146114fc57600080fd5b6115046117ae565b806005908051906020019061151a9291906117e8565b506115236117cb565b50565b60006004826040518082805190602001908083835b6020831061155e578051825260208201915060208101905060208303925061153b565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81548160ff02191690831515021790555050565b60016004826040518082805190602001908083835b602083106115e357805182526020820191506020810190506020830392506115c0565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81548160ff02191690831515021790555050565b6000600860006101000a81548160ff021916908315150217905550565b6001600860006101000a81548160ff021916908315150217905550565b6000600a60006101000a81548160ff021916908315150217905550565b6001600a60006101000a81548160ff021916908315150217905550565b6000600c826040518082805190602001908083835b602083106116dc57805182526020820191506020810190506020830392506116b9565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81548160ff02191690831515021790555050565b6001600c826040518082805190602001908083835b60208310611761578051825260208201915060208101905060208303925061173e565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81548160ff02191690831515021790555050565b6000600660006101000a81548160ff021916908315150217905550565b6001600660006101000a81548160ff021916908315150217905550565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061182957805160ff1916838001178555611857565b82800160010185558215611857579182015b8281111561185657825182559160200191906001019061183b565b5b5090506118649190611868565b5090565b61188a91905b8082111561188657600081600090555060010161186e565b5090565b9056fea265627a7a723058200257a4f3efd3f1b3415e0d5e8f7a1a63aa38d3c905166f219dfa5a22deca389964736f6c634300050a0032";

    public static final String FUNC_GETNODESTABLEYU = "getNodestableYu";

    public static final String FUNC_GETONLINETABLE = "getOnlinetable";

    public static final String FUNC_GETNODEFILETABLE = "getNodefiletable";

    public static final String FUNC_GETNODESBACKUPTABLE = "getNodesbackuptable";

    public static final String FUNC_GETMAINNODETABLE = "getMainnodetable";

    public static final String FUNC_GETNAMEHASHTABLE = "getNamehashtable";

    public static final String FUNC_GETNODESTABLEYU_MAIN = "getNodestableYu_main";

    public static final String FUNC_UPDATENODEFILETABLE = "updateNodefiletable";

    public static final String FUNC_UPDATENODESTABLEYU_MAIN = "updateNodestableYu_main";

    public static final String FUNC_UPDATENODESTABLELIMIT = "updateNodestableLimit";

    public static final String FUNC_UPDATEMAINNODETABLE = "updateMainnodetable";

    public static final String FUNC_UPDATENODESTABLEYU = "updateNodestableYu";

    public static final String FUNC_UPDATENAMEHASHTABLE = "updateNamehashtable";

    public static final String FUNC_UPDATENODESBACKUPTABLE = "updateNodesbackuptable";

    public static final String FUNC_GETNODESTABLELIMIT = "getNodestableLimit";

    public static final String FUNC_UPDATEONLINETABLE = "updateOnlinetable";

    @Deprecated
    protected Table(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Table(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Table(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Table(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<BigInteger> getNodestableYu() {
        final Function function = new Function(FUNC_GETNODESTABLEYU, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> getOnlinetable() {
        final Function function = new Function(FUNC_GETONLINETABLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getNodefiletable(String ip) {
        final Function function = new Function(FUNC_GETNODEFILETABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(ip)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getNodesbackuptable(String filename) {
        final Function function = new Function(FUNC_GETNODESBACKUPTABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(filename)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getMainnodetable() {
        final Function function = new Function(FUNC_GETMAINNODETABLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getNamehashtable() {
        final Function function = new Function(FUNC_GETNAMEHASHTABLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getNodestableYu_main() {
        final Function function = new Function(FUNC_GETNODESTABLEYU_MAIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> updateNodefiletable(String ip, String _hash) {
        final Function function = new Function(
                FUNC_UPDATENODEFILETABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(ip), 
                new org.web3j.abi.datatypes.Utf8String(_hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateNodestableYu_main(BigInteger _yumain) {
        final Function function = new Function(
                FUNC_UPDATENODESTABLEYU_MAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_yumain)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateNodestableLimit(BigInteger _limit) {
        final Function function = new Function(
                FUNC_UPDATENODESTABLELIMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_limit)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateMainnodetable(String hash) {
        final Function function = new Function(
                FUNC_UPDATEMAINNODETABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateNodestableYu(BigInteger _yu) {
        final Function function = new Function(
                FUNC_UPDATENODESTABLEYU, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_yu)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateNamehashtable(String hash) {
        final Function function = new Function(
                FUNC_UPDATENAMEHASHTABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateNodesbackuptable(String filename, String _hash) {
        final Function function = new Function(
                FUNC_UPDATENODESBACKUPTABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(filename), 
                new org.web3j.abi.datatypes.Utf8String(_hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getNodestableLimit() {
        final Function function = new Function(FUNC_GETNODESTABLELIMIT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> updateOnlinetable(String hash) {
        final Function function = new Function(
                FUNC_UPDATEONLINETABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Table load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Table(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Table load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Table(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Table load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Table(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Table load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Table(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Table> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Table.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Table> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Table.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Table> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Table.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Table> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Table.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}