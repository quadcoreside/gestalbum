package gzik;

public class ElementMusical {
  private Integer id;
  private String name;
  private String content;
  private int duree;

  private boolean isLivreAudio = false;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  public void setIsLivreAudio(boolean s) {
    this.isLivreAudio = s;
  }
  public boolean getIsLivreAudio() {
    return isLivreAudio;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }

  public int getDuree() {
    return duree;
  }
  public void setDuree(int duree) {
    this.duree = duree;
  }

  public String toString() {
      return this.name;
  }

}
