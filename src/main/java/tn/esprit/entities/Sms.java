package tn.esprit.entities;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class Sms {

        public static final String ACCOUNT_SID = "AC9b090d215f32b9ed0dc933a762c9a34b";
        public static final String AUTH_TOKEN = "d883445dcaf9856e7fe7cde0dfd0ed2e";

        public static void sendTwilioSMS(String recipientPhoneNumber, String messageText) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message;
            message = Message.creator(
                            new com.twilio.type.PhoneNumber(recipientPhoneNumber),
                            new com.twilio.type.PhoneNumber("+19063980531"),
                            messageText)
                    .create();

            System.out.println(message.getSid());
        }
    }


