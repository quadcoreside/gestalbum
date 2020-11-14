class ElementMusical {
  private String name;
  private String Contenue;
  private float Duree;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}

class Chanson extends ElementMusical {
  private String artiste;
  private int Genre;

  public String getArtiste() {
    return artiste;
  }
  public void setArtiste(String artiste) {
    this.artiste = artiste;
  }
}

class LivreAudio extends ElementMusical {
  private String Auteur;
  private int Langues;
  private int Categorie;
}
