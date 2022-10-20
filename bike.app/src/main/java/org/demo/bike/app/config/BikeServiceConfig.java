package org.demo.bike.app.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BikeServiceConfig {

    @Bean
    public ModelMapper mapper() { // create the model mapper
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration() // get the mapper configuration and set new parameters to it
                .setMatchingStrategy(MatchingStrategies.STRICT) // specify the STRICT matching strategy
                .setFieldMatchingEnabled(true) // define if the field matching is enabled or not
                .setSkipNullEnabled(true) // define if null value will be skipped or not
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE); // specify the PRIVATE
                                                                                                // field access level
        return mapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic().and().csrf().disable();
        return http.build();
    }

}
