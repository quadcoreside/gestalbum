package gzik;

public class ElementMusical {
  private int id;
  private String name;
  private String content;
  private int duree;

  protected boolean isLivreAudio = false;

  public int getId() {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }

  public void setIsLivreAudio(boolean typeIndice) {
    this.isLivreAudio = typeIndice;
  }
  public boolean getIsLivreAudio() {
    return this.isLivreAudio;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return this.content;
  }
  public void setContent(String content) {
    this.content = content;
  }

  public int getDuree() {
    return this.duree;
  }
  public void setDuree(int duree) {
    this.duree = duree;
  }

  public String toString() {
      return this.name;
  }

}
