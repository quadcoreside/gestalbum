package gestalbum;
import gzik.*;

class LivreAudio extends ElementMusical {
  private Integer id;
  private String auteur;
  private int langues;
  private int categorie;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  public String getAuteur() {
    return auteur;
  }
  public void setAuteur(String auteur) {
    this.auteur = auteur;
  }

  public int getLangues() {
    return langues;
  }
  public void setLangues(int langues) {
    this.langues = langues;
  }

  public int getCategorie() {
    return categorie;
  }
  public void setCategorie(int categorie) {
    this.categorie = categorie;
  }
}
