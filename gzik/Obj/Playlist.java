package gzik.obj;
import gzik.*;

public class Playlist extends Collection {
  private Integer id;
  private String name;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}