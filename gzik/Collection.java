package gzik;
import java.util.*;

public class Collection {
  private String name;
  private ArrayList<Integer> listEM;

  public Collection() {
    this.listEM = new ArrayList<Integer>();
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<Integer> getEM() {
    return listEM;
  }
  public void setEM(ArrayList<Integer> listEM) {
    this.listEM = listEM;
  }

  public void addEM(Integer id) {
    this.listEM.add(id);
  }
  public void delEM(Integer id) {
    this.listEM.remove(id);
  }

}
