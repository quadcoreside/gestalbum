class Collection {
  private String name;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}

class Album extends Collection {
  private String artiste;
  private float duree;

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

class Playlist extends Collection {

}
