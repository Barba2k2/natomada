package com.barbatech.natomada.infrastructure.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Serviço utilitário para facilitar o acesso a mensagens localizadas
 */
@Service
public class MessageSourceService {

    private final MessageSource messageSource;

    public MessageSourceService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Obtém mensagem traduzida usando o locale do contexto atual
     *
     * @param code Código da mensagem
     * @return Mensagem traduzida
     */
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * Obtém mensagem traduzida com parâmetros usando o locale do contexto atual
     *
     * @param code Código da mensagem
     * @param args Argumentos para interpolação
     * @return Mensagem traduzida
     */
    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, code, getLocale());
    }

    /**
     * Obtém mensagem traduzida ou retorna valor padrão se não encontrar
     *
     * @param code Código da mensagem
     * @param defaultMessage Mensagem padrão
     * @return Mensagem traduzida ou padrão
     */
    public String getMessageOrDefault(String code, String defaultMessage) {
        return messageSource.getMessage(code, null, defaultMessage, getLocale());
    }

    /**
     * Obtém o locale atual do contexto
     *
     * @return Locale atual
     */
    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
