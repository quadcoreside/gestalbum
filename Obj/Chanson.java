package gestalbum.obj;

class Chanson extends ElementMusical {
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
  public float getGenre() {
    return genre;
  }
  public void setGenre(float genre) {
    this.genre = genre;
  }
}
