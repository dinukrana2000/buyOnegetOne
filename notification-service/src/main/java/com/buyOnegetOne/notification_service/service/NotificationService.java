package com.buyOnegetOne.notification_service.service;

import com.buyOnegetOne.order_service.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Received order placed event: {}", orderPlacedEvent);
        //send email to customer
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("buyOneGetOne@email.com");
            messageHelper.setTo(orderPlacedEvent.getEmail().toString());
            messageHelper.setSubject(String.format("Order %s placed successfully", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                            hi
                            
                            Your order %s has been placed successfully
                            
                            Best regards
                            BuyOneGetOne team
                            """,
                    orderPlacedEvent.getOrderNumber()));
        };
    try {
        javaMailSender.send(mimeMessagePreparator);
        log.info("Email sent successfully");
    } catch (MailException e) {
        log.error("Error sending email", e);
        throw new RuntimeException("Error sending email", e);
    }
    }


}
