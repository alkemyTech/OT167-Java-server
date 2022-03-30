package com.alkemy.ong.service.impl;

import com.alkemy.ong.enums.MailMessage;
import static com.alkemy.ong.enums.TipLog.ERROR;
import static com.alkemy.ong.enums.TipLog.INFO;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.exception.BadRequestException;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.utils.UtilsLog;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    SendGrid sendGrid;

    @Value("${alkemy.ong.email.sender}")
    private String emailSender;
    @Value("${alkemy.ong.email.api_key}")
    private String api_key;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private OrganizationRepository organizationRepository;

    public void sendWelcomeEmailTo(UserEntity user) {
        SendGrid sg = new SendGrid(api_key);

        try {
            Organization org = this.findOrganization();
            Request request = this.buildRequest(user, org);
            Response response = sg.api(request);

            UtilsLog.registrarInfo(EmailService.class, INFO, String.valueOf(response.getStatusCode()));
            UtilsLog.registrarInfo(EmailService.class, INFO, response.getBody());
            UtilsLog.registrarInfo(EmailService.class, INFO, String.valueOf(response.getHeaders()));
        } catch (IOException e) {
            UtilsLog.registrarInfo(EmailService.class, ERROR, "Errors trying to send the email");
        } catch (NullPointerException npe) {
            UtilsLog.registrarInfo(EmailService.class, ERROR, "There is no established organization");
        }

    }

    private String preparedWelcomeBodyEmail(UserEntity user, Organization org) {

        Context context = new Context();
        context.setVariable("contactMail", MailMessage.CONTACT_MAIL + org.getEmail());
        context.setVariable("contactPhone", MailMessage.CONTACT_PHONE + String.valueOf(org.getPhone()));
        context.setVariable("contactAddress", MailMessage.CONTACT_ADDRESS + org.getAddress());
        context.setVariable("messegeWelcome", MailMessage.getWelcomeMsg(user.getFirstName(), user.getLastName()));

        return templateEngine.process("plantilla_email.html", context);

    }

    public Mail buildMail(UserEntity user, Organization org) {

        Content content = new Content("text/html", preparedWelcomeBodyEmail(user, org));
        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(user.getEmail());
        String subject = "ONG Alkemy";

        return new Mail(fromEmail, subject, toEmail, content);
    }

    public Request buildRequest(UserEntity user, Organization org) throws IOException {

        Mail mail = this.buildMail(user, org);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        return request;
    }

    public Organization findOrganization() {

        List<Organization> findAll = organizationRepository.findAll();
        Optional<Organization> result = findAll.stream().findFirst();
        
        return result.get();
    }
  
    @Override
    public Response sendEmail(String subject,String recipentEmail, String message) {
        Mail mail = new Mail(new Email(emailSender),
                subject,
                new Email(recipentEmail),
                new Content("text/plain", message));
        mail.setReplyTo(new Email(emailSender));
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = this.sendGrid.api(request);

        }catch (IOException ex){
            throw new BadRequestException(ex.getMessage());}
        return response;
    }
}
