package gzik.Obj;

import gzik.*;
import java.util.Comparator;

/**
 * Classe de gestion d'un album, constitué majoritairement d'accesseurs (get/set)
 * @author Romain & Moussa
 * @version 1.0
 */
public class Album extends Collection {
  private String artiste;
  private int duree;
  private int date;

  public String getArtiste() {
    return this.artiste;
  }
  public void setArtiste(String artiste) {
    this.artiste = artiste;
  }

  public int getDuree() {
    return this.duree;
  }
  public void setDuree(int duree) {
    this.duree = duree;
  }

  public int getDate() {
    return this.date;
  }
  public void setDate(int date) {
    this.date = date;
  }
  
}
