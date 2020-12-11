package gzik.Obj;

import gzik.*;

/**
 * Classe de gestion d'une Chanson, constitué majoritairement d'accesseurs
 * @author Romain & Moussa
 * @version 1.0
 */
public class Chanson extends ElementMusical {
  private int id;
  private String artiste;
  private int genre;

  public int getId() {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }

  public String getArtiste() {
    return this.artiste;
  }
  public void setArtiste(String artiste) {
    this.artiste = artiste;
  }

  public int getGenre() {
    return this.genre;
  }
  public void setGenre(int genre) {
    this.genre = genre;
  }
}
