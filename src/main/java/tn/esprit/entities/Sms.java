package tn.esprit.entities;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class Sms {

        public static final String ACCOUNT_SID = "ACb7fc9b5ae99b6cdb841598fe0a218529";
        public static final String AUTH_TOKEN = "412dba4f9e5eb5fed9a3c6b6225d826f";

        public static void sendTwilioSMS(String recipientPhoneNumber, String messageText) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message;
            message = Message.creator(
                            new com.twilio.type.PhoneNumber(recipientPhoneNumber),
                            new com.twilio.type.PhoneNumber("+14023835521"),
                            messageText)
                    .create();

            System.out.println(message.getSid());
        }
    }


