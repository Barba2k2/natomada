package com.barbatech.natomada.infrastructure.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * Configuração de internacionalização (i18n)
 *
 * Configura o MessageSource para carregar mensagens localizadas
 * e o LocaleResolver para detectar o idioma do Accept-Language header
 */
@Configuration
public class LocaleConfig {

    /**
     * Configura o MessageSource para carregar mensagens de arquivos .properties
     *
     * @return MessageSource configurado
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
            new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(new Locale("pt", "BR"));
        messageSource.setCacheSeconds(3600); // Cache por 1 hora

        return messageSource;
    }

    /**
     * Configura o LocaleResolver para ler o idioma do Accept-Language header
     *
     * @return LocaleResolver configurado
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("pt", "BR"));

        return localeResolver;
    }
}
