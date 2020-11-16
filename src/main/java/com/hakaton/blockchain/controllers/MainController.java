package com.hakaton.blockchain.controllers;

import com.hakaton.blockchain.ApplicationStartup;
import com.hakaton.blockchain.Response;
import com.hakaton.blockchain.controllers.dto.OperationDto;
import com.hakaton.blockchain.controllers.models.FundWallet;
import com.hakaton.blockchain.controllers.models.Operation;
import com.hakaton.blockchain.security.CustomUserDetails;
import com.hakaton.blockchain.services.FabricApi;
import com.hakaton.blockchain.services.TransactionsStoreService;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/blockchain")
public class MainController {

    private final ApplicationStartup applicationStartup;
    private final FabricApi fabricApi;
    private final TransactionsStoreService directoryService;
    private final TransactionsStoreService trackerService;


    public MainController(ApplicationStartup applicationStartup,
                          FabricApi fabricApi,
                          TransactionsStoreService directoryService,
                          TransactionsStoreService trackerService) {
        this.applicationStartup = applicationStartup;
        this.fabricApi = fabricApi;
        this.directoryService = directoryService;
        this.trackerService = trackerService;
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
    public ResponseEntity<Response<Operation>> addDonation(@RequestBody OperationDto request,
                                                           @AuthenticationPrincipal Optional<CustomUserDetails> user,
                                                           HttpServletRequest req) {
        return Response.EXECUTE(() -> {
            if (user.isEmpty()) {
                return Response.BAD("Not authorized");
            }
            Response<Operation> response = fabricApi
                    .addDonation(request.getLogin(), request.getAmount(), request.getTimestamp(),
                            request.getDescription());
            if (!response.isSuccess()) {
                return response;
            }

            return directoryService
                    .storeTransaction(request.getEntityId(), response.getBody(), req.getHeader("Authorization"));
        }).makeResponse();

    }

    @PostMapping("/consumption")
    public ResponseEntity<Response<Operation>> addConsumption(@RequestBody OperationDto request,
                                                              @AuthenticationPrincipal Optional<CustomUserDetails> user,
                                                              HttpServletRequest req) {
        return Response.EXECUTE(() -> {
            if (user.isEmpty()) {
                return Response.BAD("Not authorized");
            }
            Response<Operation> response = fabricApi
                    .addConsumption(request.getLogin(), request.getAmount(), request.getTimestamp(),
                            request.getDescription());
            if (!response.isSuccess()) {
                return response;
            }
            return trackerService
                    .storeTransaction(request.getEntityId(), response.getBody(), req.getHeader("Authorization"));
        }).makeResponse();
    }

    @GetMapping("/donation")
    public ResponseEntity<Response<Operation>> getDonation(@RequestParam String login,
                                                           @RequestParam String timestamp) {
        return fabricApi
                .readDonation(login, timestamp)
                .makeResponse();
    }

    @GetMapping("/consumption")
    public ResponseEntity<Response<Operation>> getConsumption(@RequestParam String login,
                                                              @RequestParam String timestamp) {
        return fabricApi
                .readConsumption(login, timestamp)
                .makeResponse();
    }

    @GetMapping("/fund_balance")
    public ResponseEntity<Response<FundWallet>> getFundBalance() {
        return fabricApi.readBalance().makeResponse();
    }

}
