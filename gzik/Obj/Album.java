package gzik.Obj;

import gzik.*;
import java.util.Comparator;

public class Album extends Collection {
  private Integer id;
  private String artiste;
  private int duree;
  private Integer date;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  public String getArtiste() {
    return artiste;
  }
  public void setArtiste(String artiste) {
    this.artiste = artiste;
  }

  public int getDuree() {
    return duree;
  }
  public void setDuree(int duree) {
    this.duree = duree;
  }

  public Integer getDate() {
    return date;
  }
  public void setDate(Integer date) {
    this.date = date;
  }

  /* Algo Class implements Comparator interface, for date comprator :) */
  public static class DateComparator implements Comparator<Album>
  {
      public int compare(Album a1, Album a2)
      {
          if (a1.getDate() == a2.getDate())
              return 0;
          else if (a1.getDate() > a2.getDate())
              return 1;
          else
              return -1;
      }
  }
}
