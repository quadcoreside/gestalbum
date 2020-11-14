class ElementMusical {
  private String name;
  private String content;
  private float duree;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public float getDuree() {
    return duree;
  }
  public void setDuree(float duree) {
    this.duree = duree;
  }
}

class Chanson extends ElementMusical {
  private String artiste;
  private int genre;

  public String getArtiste() {
    return artiste;
  }
  public void setArtiste(String artiste) {
    this.artiste = artiste;
  }
  public float getGenre() {
    return genre;
  }
  public void setGenre(float genre) {
    this.genre = genre;
  }
}

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
