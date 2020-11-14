package gestalbum.obj;

class LivreAudio extends ElementMusical {
  private String auteur;
  private int langues;
  private int categorie;

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
