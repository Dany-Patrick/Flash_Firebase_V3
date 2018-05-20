package cl.dany.flash_firebase_v3.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmailProcesor {
    public String sanitizedEmail(String email)
    {
        return email.replace("@","AT").replace(".","DOT");
    }
    public String keyEmails(String otherEmail)
    {
        String currentMail = sanitizedEmail(new CurrentUser().email());
        List<String> emails = new ArrayList<>();
        emails.add(sanitizedEmail(otherEmail));
        emails.add(sanitizedEmail(currentMail)) ;

        Collections.sort(emails);
            return emails.get(0) + " - " + emails.get(1);
    }
}
