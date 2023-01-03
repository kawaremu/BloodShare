package classes;

import java.sql.Date;
import java.time.LocalDate;

public class HistoriqueDon {
    private LocalDate date_don;
    private String groupe_sanguin,type_sang;

    public HistoriqueDon(LocalDate date_don, String groupe_sanguin, String type_sang) {
        this.date_don = date_don;
        this.groupe_sanguin = groupe_sanguin;
        this.type_sang = type_sang;
    }


    public String getGroupe_sanguin() {
        return groupe_sanguin;
    }
    public String getType_sang() {
        return type_sang;
    }
    public LocalDate getDate_don() {
        return date_don;
    }

}
