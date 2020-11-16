package gzik.obj;
import gzik.*;

class Album extends Collection {
  private Integer id;
  private String artiste;
  private float duree;

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
}
