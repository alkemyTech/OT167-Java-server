package com.alkemy.ong.service.impl;

import com.alkemy.ong.enums.MailMessage;
import static com.alkemy.ong.enums.TipLog.ERROR;
import static com.alkemy.ong.enums.TipLog.INFO;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.model.User;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.utils.UtilsLog;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${alkemy.ong.email.sender}")
    private String emailSender;
    @Value("${alkemy.ong.email.api_key}")
    private String api_key;    

    @Autowired
    private TemplateEngine templateEngine;
    
    @Autowired
    private OrganizationService organizationService;
    
    public void sendWelcomeEmailTo(User user, Long id) {
        SendGrid sg = new SendGrid(api_key);

        try {
            Request request = this.buildRequest(user, id);
            Response response = sg.api(request);

            UtilsLog.registrarInfo(EmailService.class, INFO, String.valueOf(response.getStatusCode()));
            UtilsLog.registrarInfo(EmailService.class, INFO, response.getBody());
            UtilsLog.registrarInfo(EmailService.class, INFO, String.valueOf(response.getHeaders()));
        } catch (IOException e) {
            UtilsLog.registrarInfo(EmailService.class, ERROR, "Errors trying to send the email");
        }
    }

    private String preparedWelcomeBodyEmail(User user, Long id) {

        Organization org = organizationService.findById(id).get();
        
        Context context = new Context();
        context.setVariable("contactMail", MailMessage.CONTACT_MAIL + org.getEmail());
        context.setVariable("contactPhone", MailMessage.CONTACT_PHONE + String.valueOf(org.getPhone()));
        context.setVariable("contactAddress", MailMessage.CONTACT_ADDRESS + org.getAddress());
        context.setVariable("messegeWelcome", MailMessage.getWelcomeMsg(user.getFirstName(), user.getLastName()));
        
        return templateEngine.process("plantilla_email.html", context);

    }

    public Mail buildMail(User user, Long id) {

        Content content = new Content("text/html", preparedWelcomeBodyEmail(user, id));
        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(user.getEmail());
        String subject = "ONG Alkemy";

        return new Mail(fromEmail, subject, toEmail, content);
    }

    public Request buildRequest(User user, Long id) throws IOException {

        Mail mail = this.buildMail(user, id);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        return request;
    }
    
}
