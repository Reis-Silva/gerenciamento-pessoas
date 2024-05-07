package org.trainning.gerenciamentopessoas.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class AppConfiguration {

    private static final MessageSource messageSource = messageSource();

    @Bean
    public static MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "classpath:/messages"
        );
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
