package classes;

import java.time.LocalDate;

public class Don {
    private String nom_donneur, type_sang, medecin_responsable, groupe_sanguin,remarques,nom_hopital;
    private LocalDate dateCollecte;
    private int ID_DON;


    public Don( int id_don,String nom_donneur, String groupe_sanguin, String type_sang, LocalDate dateCollecte,String medecin_responsable, String nom_hopital) {
        this.ID_DON = id_don;
        this.nom_donneur = nom_donneur;
        this.groupe_sanguin = groupe_sanguin;
        this.type_sang = type_sang;
        this.dateCollecte = dateCollecte;
        this.medecin_responsable = medecin_responsable;
        this.nom_hopital = nom_hopital;
    }

    public void setNom_donneur(String nom_donneur) {
        this.nom_donneur = nom_donneur;
    }

    public void setType_sang(String type_sang) {
        this.type_sang = type_sang;
    }

    public void setMedecin_responsable(String medecin_responsable) {
        this.medecin_responsable = medecin_responsable;
    }

    public void setGroupe_sanguin(String groupe_sanguin) {
        this.groupe_sanguin = groupe_sanguin;
    }

    public void setDateCollecte(LocalDate dateCollecte) {
        this.dateCollecte = dateCollecte;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    @Override
    public String toString() {
        return "Don{" +
                "nom_donneur='" + nom_donneur + '\'' +
                ", type_sang='" + type_sang + '\'' +
                ", medecin_responsable='" + medecin_responsable + '\'' +
                ", groupe_sanguin='" + groupe_sanguin + '\'' +
                ", nom_hopital='" + nom_hopital + '\'' +
                ", dateCollecte=" + dateCollecte +
                ", ID_DON=" + ID_DON +
                '}';
    }

    public void setID_DON(int ID_DON) {
        this.ID_DON = ID_DON;
    }

    public void setNom_hopital(String nom_hopital) {
        this.nom_hopital = nom_hopital;
    }

    public String getNom_donneur() {
        return nom_donneur;
    }

    public String getType_sang() {
        return type_sang;
    }

    public String getMedecin_responsable() {
        return medecin_responsable;
    }

    public String getGroupe_sanguin() {
        return groupe_sanguin;
    }

    public String getRemarques() {
        return remarques;
    }

    public String getNom_hopital() {
        return nom_hopital;
    }

    public LocalDate getDateCollecte() {
        return dateCollecte;
    }

    public int getID_DON() {
        return ID_DON;
    }
}