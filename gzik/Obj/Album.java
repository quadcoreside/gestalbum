package gzik.Obj;

import gzik.*;
import java.util.Comparator;

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

  /* Algo Class implements Comparator interface, for date comprator :) */
  public static class DateComparator implements Comparator<Album> {
      public int compare(Album a1, Album a2) {
          if (a1.getDate() == a2.getDate()) {
              return 0;
          } else if (a1.getDate() > a2.getDate()) {
              return 1;
          } else {
              return -1;
          }
      }
  }
}
