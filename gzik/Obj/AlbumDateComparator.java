package gzik.Obj;

import gzik.*;
import java.util.Comparator;

/* Classe définissant la régle de trie par date
* @author Romain & Moussa
* @version 1.0
*/
public class AlbumDateComparator implements Comparator<Album> {
    /**
    * Permet de comparer deux Album en fonction de leurs date
    * l'utilisateur choisi une playlist puis on lui affiche son choix
    * @param Prend en parametre deux Album
    * @return Retourne un entier définissant la cmparaison des deux album
    * @since 1.0
    */
    public int compare(Album a1, Album a2) {
        if (a1.getDate() == a2.getDate()) {
            return 0;
        } else if (a1.getDate() > a2.getDate()) {
            return 1;
        } else {
            return -1;
        }
    }
}
