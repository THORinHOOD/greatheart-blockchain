package com.hakaton.blockchain.controllers;

import com.hakaton.blockchain.ApplicationStartup;
import com.hakaton.blockchain.Response;
import com.hakaton.blockchain.controllers.dto.OperationDto;
import com.hakaton.blockchain.controllers.models.FundWallet;
import com.hakaton.blockchain.controllers.models.Operation;
import com.hakaton.blockchain.services.DirectoryService;
import com.hakaton.blockchain.services.FabricApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockchain")
public class MainController {

    private final ApplicationStartup applicationStartup;
    private final FabricApi fabricApi;
    private final DirectoryService directoryService;

    public MainController(ApplicationStartup applicationStartup,
                          FabricApi fabricApi,
                          DirectoryService directoryService) {
        this.applicationStartup = applicationStartup;
        this.fabricApi = fabricApi;
        this.directoryService = directoryService;
    }

    @GetMapping("/startup")
    public ResponseEntity<Response<Boolean>> startup() {
        return Response
                .EXECUTE_RAW(applicationStartup::startup)
                .makeResponse();
    }

    @GetMapping("/started")
    public ResponseEntity<Response<Boolean>> isStarted() {
        return Response
                .EXECUTE_RAW(applicationStartup::isSuccessStartup)
                .makeResponse();
    }

    @PostMapping("/donation")
    public ResponseEntity<Response<Operation>> addDonation(@RequestBody OperationDto request) {
        return Response.EXECUTE(() -> {
            Response<Operation> response = fabricApi
                    .addDonation(request.getUserId(), request.getAmount(), request.getTimestamp(),
                            request.getDescription());
            if (!response.isSuccess()) {
                return response;
            }
            return directoryService
                    .addTransactionToEntity(request.getEntityId(), response.getBody());
        }).makeResponse();

    }

    @PostMapping("/consumption")
    public ResponseEntity<Response<Operation>> addConsumption(@RequestBody OperationDto request) {
        return Response.EXECUTE(() -> {
            Response<Operation> response = fabricApi
                    .addConsumption(request.getUserId(), request.getAmount(), request.getTimestamp(),
                            request.getDescription());
            if (!response.isSuccess()) {
                return response;
            }
            return directoryService
                    .addTransactionToEntity(request.getEntityId(), response.getBody());
        }).makeResponse();
    }

    @GetMapping("/donation")
    public ResponseEntity<Response<Operation>> getDonation(@RequestParam Long userId,
                                                           @RequestParam String timestamp) {
        return fabricApi
                .readDonation(userId, timestamp)
                .makeResponse();
    }

    @GetMapping("/consumption")
    public ResponseEntity<Response<Operation>> getConsumption(@RequestParam Long userId,
                                                              @RequestParam String timestamp) {
        return fabricApi
                .readConsumption(userId, timestamp)
                .makeResponse();
    }

    @GetMapping("/fund_balance")
    public ResponseEntity<Response<FundWallet>> getFundBalance() {
        return fabricApi.readBalance().makeResponse();
    }

}
