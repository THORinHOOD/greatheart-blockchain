package com.hakaton.blockchain.services;

import com.hakaton.blockchain.Response;
import com.hakaton.blockchain.controllers.models.FundWallet;
import com.hakaton.blockchain.controllers.models.Operation;
import org.hyperledger.fabric.gateway.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

@Service
public class FabricApi {

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    // helper function for getting connected to the gateway
    private Gateway connect() throws Exception{
        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a CCP
        Path networkConfigPath = Paths.get("..", "..", "test-network", "organizations",
                "peerOrganizations", "org1.example.com", "connection-org1.yaml");

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
        return builder.connect();
    }

    public Response<Operation> readDonation(Long userId, String timestamp) {
        return readOperation(userId, timestamp, "DONATION");
    }

    public Response<Operation> readConsumption(Long userId, String timestamp) {
        return readOperation(userId, timestamp, "CONSUMPTION");
    }

    private Response<Operation> readOperation(Long userId, String timestamp, String operationType) {
        return executeTransaction(contract -> contract.evaluateTransaction("readOperation",
                         String.valueOf(userId), timestamp, operationType),
                this::parseOperation);
    }

    public Response<Operation> addDonation(Long userId, Long amount, String timestamp, String description) {
        return addOperation(userId, amount, timestamp, "DONATION", description);
    }

    public Response<Operation> addConsumption(Long userId, Long amount, String timestamp, String description) {
        return addOperation(userId, amount, timestamp, "CONSUMPTION", description);
    }

    private Response<Operation> addOperation(Long userId, Long amount, String timestamp, String operationType,
                                             String description) {
        return executeTransaction(contract -> contract.submitTransaction(getOperationMethod(operationType),
                        String.valueOf(userId),
                        String.valueOf(amount),
                        timestamp,
                        description),
                this::parseOperation);
    }

    public Response<FundWallet> readBalance() {
        return executeTransaction(contract -> contract.evaluateTransaction("readFundBalance"),
                this::parseFundWallet);
    }

    private String getOperationMethod(String operationType) {
        return "add" + operationType.substring(0, 1) + operationType.substring(1).toLowerCase();
    }

    private Response<FundWallet> parseFundWallet(byte[] bytes) {
        return parse("fund wallet", bytes, obj -> new FundWallet(obj.getLong("balance")));
    }

    private Response<Operation> parseOperation(byte[] bytes) {
        return parse("operation", bytes, obj -> new Operation(
                obj.getLong("userId"),
                obj.getLong("amount"),
                obj.getString("timestamp"),
                obj.getString("id"),
                obj.getString("operationType"),
                obj.getString("description")
        ));
    }

    private <RESULT> Response<RESULT> parse(String entity, byte[] bytes, Function<JSONObject, RESULT> map) {
        try {
            JSONObject obj = new JSONObject(new String(bytes, StandardCharsets.UTF_8));
            return Response.EXECUTE_RAW(() -> map.apply(obj));
        } catch (Exception exception) {
            return Response.BAD("Can't parse " + entity + " response");
        }
    }

    private <RESULT> Response<RESULT> executeTransaction(Transaction toExec,
                                                         Function<byte[], Response<RESULT>> parser) {
        try (Gateway gateway = connect()) {
            byte[] result = toExec.exec(gateway
                    .getNetwork("mychannel")
                    .getContract("payments"));
            return parser.apply(result);
        } catch (Exception exception) {
            return Response.BAD(exception.getMessage());
        }
    }

    interface Transaction {
        byte[] exec(Contract contract) throws InterruptedException, TimeoutException, ContractException;
    }

}
