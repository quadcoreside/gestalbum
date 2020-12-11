package gzik.Obj;

import gzik.*;

/**
 * Classe de gestion d'un Livre Audio
 * @author Romain & Moussa
 * @version 1.0
 */
public class LivreAudio extends ElementMusical {
  private int id;
  private String auteur;
  private int langues;
  private int categorie;

  public int getId() {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }

  public String getAuteur() {
    return this.auteur;
  }
  public void setAuteur(String auteur) {
    this.auteur = auteur;
  }

  public int getLangues() {
    return this.langues;
  }
  public void setLangues(int langues) {
    this.langues = langues;
  }

  public int getCategorie() {
    return this.categorie;
  }
  public void setCategorie(int categorie) {
    this.categorie = categorie;
  }
}
