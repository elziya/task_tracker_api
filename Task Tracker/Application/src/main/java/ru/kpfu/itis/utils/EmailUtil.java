package ru.kpfu.itis.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ru.kpfu.itis.exceptions.SendingEmailException;

import java.util.Map;

import static ru.kpfu.itis.config.FreemarkerConfig.FREEMARKER_EXTENSION;

@RequiredArgsConstructor
@Component
public class EmailUtil {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    private final FreeMarkerConfigurer configurer;

    public void sendMail(String to, String subject, String templateName, Map<String, Object> data) {

        final String mailText;

        try {

            mailText = FreeMarkerTemplateUtils.processTemplateIntoString(
                    configurer.getConfiguration().getTemplate(templateName + FREEMARKER_EXTENSION), data
            );

            MimeMessagePreparator preparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setSubject(subject);
                messageHelper.setText(mailText, true);
                messageHelper.setTo(to);
                messageHelper.setFrom(from);
            };

            mailSender.send(preparator);

        } catch (Exception e) {
            throw new SendingEmailException();
        }
    }
}
