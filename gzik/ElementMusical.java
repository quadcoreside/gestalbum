package gzik;

/**
 * Classe de gestion d'un element musiscal, constitué majoritairement s'accesseurs (get,set)
 * @author Romain & Moussa
 * @version 1.0
 */
public class ElementMusical {
  /**
 * id de l'element musical
 * @see Id#getId()
 * @see Id#setId(String)
 */
  private int id;
  private String name;
  private String content;
  private int duree;

  protected boolean isLivreAudio = false;

  /**
  * Retourne l'ID de l'element musical
  * @return L'identifiant de l'element musical.
  */
  public int getId() {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }

  /**
  * Retourne true si c'est un livre audio et false pour une chanson
  * @return true si c'est un livre audio, false pour une chanson
  */
  public boolean getIsLivreAudio() {
    return this.isLivreAudio;
  }
  public void setIsLivreAudio(boolean typeIndice) {
    this.isLivreAudio = typeIndice;
  }

  /**
  * Retourne le nom de l'element
  * @return nom
  */
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
  * Retourne le contenu
  * @return chemin de l'element
  */
  public String getContent() {
    return this.content;
  }
  public void setContent(String content) {
    this.content = content;
  }

  /**
  * Retourne la durée
  * @return durée en seconde
  */
  public int getDuree() {
    return this.duree;
  }
  public void setDuree(int duree) {
    this.duree = duree;
  }

  /* En cas d'appel de cette methode on returne la chhaine de caractere nom */
  public String toString() {
      return this.name;
  }

}
