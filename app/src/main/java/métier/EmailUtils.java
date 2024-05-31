package métier;

import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import android.os.AsyncTask;
import android.util.Log;

public class EmailUtils {

    private static final String TAG = "EmailUtils";
    private static final String EMAIL = "finderinterim@gmail.com";  // Votre adresse e-mail ici
    private static final String APP_PASSWORD = "y l q k b t t e z p e x s c i x";  // Votre mot de passe spécifique à l'application ici

    public static int generateVerificationCode() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }

    public static void sendEmail(String to, String subject, String body) {
        new SendEmailTask().execute(to, subject, body);
    }

    private static class SendEmailTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String to = params[0];
                String subject = params[1];
                String body = params[2];

                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.starttls.enable", "true"); // TLS

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL, APP_PASSWORD);
                    }
                });

                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                Log.d(TAG, "Email envoyé avec succès");
            } catch (Exception e) {
                Log.e(TAG, "Échec de l'envoi de l'email", e);
                if (e.getCause() != null) {
                    Log.e(TAG, "Cause : " + e.getCause().toString());
                }
                Log.e(TAG, "Message : " + e.getMessage());
            }
            return null;
        }
    }
}
