package com.hakaton.blockchain.controllers;

import com.hakaton.blockchain.ApplicationStartup;
import com.hakaton.blockchain.Response;
import com.hakaton.blockchain.controllers.dto.OperationDto;
import com.hakaton.blockchain.controllers.models.FundWallet;
import com.hakaton.blockchain.controllers.models.Operation;
import com.hakaton.blockchain.services.FabricApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockchain")
public class MainController {

    private final ApplicationStartup applicationStartup;
    private final FabricApi fabricApi;

    public MainController(ApplicationStartup applicationStartup,
                          FabricApi fabricApi) {
        this.applicationStartup = applicationStartup;
        this.fabricApi = fabricApi;
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
        return fabricApi
                .addDonation(request.getUserId(), request.getAmount(), request.getTimestamp())
                .makeResponse();
    }

    @PostMapping("/consumption")
    public ResponseEntity<Response<Operation>> addOperation(@RequestBody OperationDto request) {
        return fabricApi
                .addConsumption(request.getUserId(), request.getAmount(), request.getTimestamp())
                .makeResponse();
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
