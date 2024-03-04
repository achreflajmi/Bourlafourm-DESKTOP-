package tn.esprit.entities;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
import java.lang.reflect.Array;

public class Whatsapp {
    /* public void createMessage(String MessageBody){
        Message message = Message.creator(
                new PhoneNumber("whatsapp:"+ ExerciceUtility.ToPhoneNumber),
                new PhoneNumber("whatsapp:+14155238886"),
                "Happy Birthday"+MessageBody).create();
        Message message1 = Message.creator(
                new PhoneNumber("whatsapp:"+ ExerciceUtility.ToPhoneNumber),
                new PhoneNumber("whatsapp:+14155238886"),
                Array(
        )

        )

     */

        public static final String ACCOUNT_SID = "AC6613fd7114e1f9072fbcd717b095dbc5";
        public static final String AUTH_TOKEN = "07b2fd9f0ef08f20ec445d989529c205";

        public void Whats (String key) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            String messageBody = "Your "+key+" subscription is confirmed"; // String to hold the message content

            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("whatsapp:+21625004258"),
                    new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                    messageBody
            ).create();

            System.out.println(message.getSid());
        }
    }
