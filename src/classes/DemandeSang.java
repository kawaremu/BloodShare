package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DemandeSang {
    private LocalDateTime cree_a;
    private LocalDate date_demande;
    private String demande_par,groupe_sanguin,objectifs,type_sang;

    public DemandeSang(LocalDateTime cree_a, LocalDate date_demande, String demande_par, String groupe_sanguin, String objectifs, String type_sang) {
        this.cree_a = cree_a;
        this.date_demande = date_demande;
        this.demande_par = demande_par;
        this.groupe_sanguin = groupe_sanguin;
        this.objectifs = objectifs;
        this.type_sang = type_sang;
    }

    @Override
    public String toString() {
        return "DemandeSang{" +
                "cree_a=" + cree_a +
                ", date_demande=" + date_demande +
                ", demande_par='" + demande_par + '\'' +
                ", groupe_sanguin='" + groupe_sanguin + '\'' +
                ", objectifs='" + objectifs + '\'' +
                ", type_sang='" + type_sang + '\'' +
                '}';
    }

    public LocalDateTime getCree_a() {
        return cree_a;
    }

    public LocalDate getDate_demande() {
        return date_demande;
    }

    public String getDemande_par() {
        return demande_par;
    }

    public String getGroupe_sanguin() {
        return groupe_sanguin;
    }

    public String getObjectifs() {
        return objectifs;
    }

    public String getType_sang() {
        return type_sang;
    }

    public void setDemande_par(String demande_par) {
        this.demande_par = demande_par;
    }

    public void setGroupe_sanguin(String groupe_sanguin) {
        this.groupe_sanguin = groupe_sanguin;
    }

    public void setObjectifs(String objectifs) {
        this.objectifs = objectifs;
    }


}
