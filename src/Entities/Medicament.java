package Entities;

public class Medicament
{
    private int numero;
    private String nom;
    private double prix;
    private int quantite;
    public Medicament(){}

    public Medicament(int unNum, String unNom,  double unPrix,int uneQte)
    {
        numero = unNum;
        nom = unNom;
        prix = unPrix;
        quantite = uneQte;
    }
    public Medicament(int unNum, String unNom,  double unPrix)
    {
        numero = unNum;
        nom = unNom;
        prix = unPrix;
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public int getQuantite() {
        return quantite;
    }
}
