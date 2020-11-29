package gzik;

import java.util.*;

/* Attention le name "Collection" existe dêja dans java.util.* */

public class Collection implements Comparable<Collection> {
  private int id;
  private String name;
  private ArrayList<Integer> listEM;

  public Collection() {
    this.listEM = new ArrayList<Integer>();
  }

  public Integer getId() {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
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

  public void addEM(int id) {
    this.listEM.add(id);
  }
  public void delEM(int index) {
    this.listEM.remove(index);
  }

  /* For sorting ASC by name */
  @Override
  public int compareTo(Collection c) {
    return this.getName().compareTo(c.getName());
  }
  @Override
  public String toString() {
      return this.name;
  }

}
