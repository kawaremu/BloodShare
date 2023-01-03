package classes;

import java.util.LinkedList;

public class Donneur{
    private String nom,adresse,email,groupeSanguin,remarques,genre;
    private int age,nombre_don;



    public Donneur(String nom, String adresse, String email ,int age,String genre,String groupeSanguin,String remarques,int nombre_don) {
        this.nom = nom;
        this.adresse = adresse;
        this.email = email;
        this.groupeSanguin = groupeSanguin;
        this.genre = genre;
        this.age = age;
        this.remarques = remarques;
        this.nombre_don = nombre_don;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroupeSanguin(String groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setage(int age) {
        this.age = age;
    }

    public void setNombre_don(int nombre_don) {
        this.nombre_don = nombre_don;
    }

    @Override
    public String toString() {
        return "Donneur{" +
                "nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", groupeSanguin='" + groupeSanguin + '\'' +
                ", remarques='" + remarques + '\'' +
                ", genre='" + genre + '\'' +
                ", age=" + age +
                ", nombre_don=" + nombre_don +
                '}';
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public String getGroupeSanguin() {
        return groupeSanguin;
    }

    public String getRemarques() {
        return remarques;
    }

    public String getGenre() {
        return genre;
    }

    public int getAge() {
        return age;
    }

    public int getNombre_don() {
        return nombre_don;
    }
}
