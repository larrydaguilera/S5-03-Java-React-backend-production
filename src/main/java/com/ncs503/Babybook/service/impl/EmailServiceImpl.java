package com.ncs503.Babybook.service.impl;


import com.ncs503.Babybook.service.EmailService;
import com.ncs503.Babybook.utils.EmailUtils;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${babybook.email.sender}")
    private String organizationId;
    @Value("${babybook.email.apikey}")
    private String sendGridKey;
    @Value("${babybook.email.templateid}")
    private String templateId;
    @Value("${babybook.email.templateidcontact}")
    private String templateContactId;

    @Transactional
    public void sendEmail(Mail email) throws IOException {
        SendGrid sendGrid = new SendGrid(sendGridKey);
        Request request = new Request();
        try{
            request.setMethod(Method.POST);
            request.setEndpoint("/mail/send");
            request.setBody(email.build());
            Response response = sendGrid.api(request);
        } catch (IOException ex) {
            throw ex;
        }
    }

    @Transactional
    public void getEmailReady(String to, String template, String contentValue, String subject) throws IOException {
        Email fromEmail = new Email(organizationId);
        Email toEmail = new Email(to);
        Content content = new Content("text/html", contentValue);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);
        sendEmail(mail);
    }

    @Transactional
    public void checkFromRequest(String to, String from) throws IOException {
        if (from.equalsIgnoreCase("userRegistered"))
            getEmailReady(to, templateId, EmailUtils.content("Bienvenido!", "Gracias por registrarse!"), "LifeBook");
        else
            getEmailReady(to, templateContactId, EmailUtils.content("Solicitud recibida",
                    "Muchas gracias por \n " + "contactarte con nosotros \n " +
                            "te mandaremos un mensaje a la brevedad"), "Somos LifeBook");
    }

    @Transactional
    public void sendRecoverPassword(String to, String from, String passAux) throws IOException {
        if (from.equalsIgnoreCase("userRegistered"))
            getEmailReady(to, templateId, EmailUtils.content("Recupero Contraseña : " , "\nTu nueva Contraseña es : " + passAux ), "LifeBook");
        else
            getEmailReady(to, templateContactId, EmailUtils.content("Solicitud recibida",
                    "Muchas gracias por \n " + "contactarte con nosotros \n " +
                            "te mandaremos un mensaje a la brevedad"), "Somos LifeBook");
    }


}