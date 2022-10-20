package org.demo.rental.app.config;

import org.demo.rental.app.dto.BikeDTO;
import org.demo.rental.app.dto.BikeRentalDTO;
import org.demo.rental.app.dto.ClientDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class RentalServiceConfig {

    @Bean
    public ModelMapper mapper() { // create the model mapper
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration() // get the mapper configuration and set new parameters to it
                .setMatchingStrategy(MatchingStrategies.STRICT) // specify the STRICT matching strategy
                .setFieldMatchingEnabled(true) // define if the field matching is enabled or not
                .setSkipNullEnabled(true) // define if null value will be skipped or not
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE); // specify the PRIVATE
                                                                                                // field access level

        TypeMap<ClientDTO, BikeRentalDTO> clientMapper = mapper.createTypeMap(ClientDTO.class, BikeRentalDTO.class);
        clientMapper.addMapping(ClientDTO::getName, BikeRentalDTO::setClient)
                    .addMapping(ClientDTO::getId, BikeRentalDTO::setClientId);
        TypeMap<BikeDTO, BikeRentalDTO> bikeMapper = mapper.createTypeMap(BikeDTO.class, BikeRentalDTO.class);
        bikeMapper.addMapping(BikeDTO::getId, BikeRentalDTO::setBikeId);
        return mapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic().and().csrf().disable();
        return http.build();
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "password");
    }
}
