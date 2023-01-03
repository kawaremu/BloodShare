package classes;

import java.time.LocalDateTime;

public class Message {
    String envoye_par,contenu_message;
    LocalDateTime envoye_a;

    public String getEnvoye_par() {
        return envoye_par;
    }

    public String getContenu_message() {
        return contenu_message;
    }

    public LocalDateTime getEnvoye_a() {
        return envoye_a;
    }

    public Message(String envoye_par, String contenu_message, LocalDateTime envoye_a)
    {
        this.envoye_par = envoye_par;
        this.contenu_message = contenu_message;
        this.envoye_a = envoye_a;
    }
}
