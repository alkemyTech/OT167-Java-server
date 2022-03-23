package com.alkemy.ong.service.impl;

import static com.alkemy.ong.enums.TipLog.INFO;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.utils.UtilsLog;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private Environment env;
    @Value("${alkemy.ong.email.sender}")
    private String emailSender;
    
    public void sendWelcomeEmailTo(String to) {
        String apiKey = env.getProperty("EMAIL_API_KEY");
        SendGrid sg = new SendGrid(apiKey);

        try {
            Request request = this.buildRequest(to);
            Response response = sg.api(request);

            UtilsLog.registrarInfo(EmailService.class, INFO, String.valueOf(response.getStatusCode()));
            UtilsLog.registrarInfo(EmailService.class, INFO, response.getBody());
            UtilsLog.registrarInfo(EmailService.class, INFO, String.valueOf(response.getHeaders()));
        } catch (IOException e) {
            System.out.println("Errors trying to send the email");
        }
    }

    public String getContentOfHtmlTemplate() {
        String filePath = new File("").getAbsolutePath() + "/src/main/resources/template/plantilla_email.html";
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
        String content = contentBuilder.toString();
        return content;
    }

    public Mail buildMail(String to) {

        Content content = new Content("text/html", getContentOfHtmlTemplate());
        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(to);
        String subject = "ONG Alkemy";

        return new Mail(fromEmail, subject, toEmail, content);
    }

    public Request buildRequest(String to) throws IOException {

        Mail mail = this.buildMail(to);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        return request;
    }

    public ModelAndView loadData() {
        
        ModelAndView model = new ModelAndView("plantilla_html");
        model.addObject("contact", "Lavalle 1856");

        return model;
    }
}
