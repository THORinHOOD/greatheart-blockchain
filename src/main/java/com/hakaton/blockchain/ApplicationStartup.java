package com.hakaton.blockchain;

import com.hakaton.blockchain.configs.MainConfiguration;
import com.hakaton.blockchain.services.EnrollAdmin;
import com.hakaton.blockchain.services.RegisterUser;
import org.apache.log4j.chainsaw.Main;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final EnrollAdmin enrollAdmin;
    private final RegisterUser registerUser;
    private boolean successStartup;

    public ApplicationStartup(EnrollAdmin enrollAdmin, RegisterUser registerUser) {
        this.enrollAdmin = enrollAdmin;
        this.registerUser = registerUser;
        successStartup = false;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        startup();
    }

    public boolean startup() {
        successStartup = enrollAdmin.enrollAdmin();
        if (successStartup) {
            successStartup = registerUser.registerUser();
        }
        return successStartup;
    }

    public boolean isSuccessStartup() {
        return successStartup;
    }
}
