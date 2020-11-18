package gzik.Obj;
import gzik.*;

public class Album extends Collection {
  private Integer id;
  private String artiste;
  private float duree;
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

  public float getDuree() {
    return duree;
  }
  public void setDuree(float duree) {
    this.duree = duree;
  }

  public Integer getDate() {
    return date;
  }
  public void setDate(Integer date) {
    this.date = date;
  }
}
