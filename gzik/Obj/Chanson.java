package gzik.obj;
import gzik.*;

public class Chanson extends ElementMusical {
  private Integer id;
  private String artiste;
  private int genre;

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
  public int getGenre() {
    return genre;
  }
  public void setGenre(int genre) {
    this.genre = genre;
  }
}