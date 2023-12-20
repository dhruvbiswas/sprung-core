package com.di.core.lib.test;

import com.di.core.annotations.Bean;
import com.di.core.annotations.Configuration;

@Configuration("AppConfigurationBean")
public class AppConfiguration {

    @Bean(name="AppClass6Bean")
    public AppClass6 getAppClass6() {
        return new AppClass6();
    }
}
