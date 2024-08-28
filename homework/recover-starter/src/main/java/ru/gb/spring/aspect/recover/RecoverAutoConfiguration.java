package ru.gb.spring.aspect.recover;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RecoverProperties.class)
@ConditionalOnProperty(value = "application.recover.enabled", havingValue = "true")
public class RecoverAutoConfiguration {

    @Bean
    public RecoverAspect recoverAspect(RecoverProperties properties) {
        return new RecoverAspect(properties);
    }

}
