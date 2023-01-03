package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Notification
{
    private String  type_notif;
    private LocalDateTime arrivee_a;

    public Notification(LocalDateTime arrivee_a,String type_notif) {
        this.type_notif = type_notif;
        this.arrivee_a = arrivee_a;
    }

    public String getType_notif() {
        return type_notif;
    }

    public void setType_notif(String type_notif) {
        this.type_notif = type_notif;
    }

    public LocalDateTime getArrivee_a() {
        return arrivee_a;
    }

    public void setArrivee_a(LocalDateTime arrivee_a) {
        this.arrivee_a = arrivee_a;
    }
}
