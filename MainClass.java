import org.json.*;
import java.util.*;
package gestalbum;

class MainClass {

  private static ArrayList<ElementMusical> listElementMusica;
  private static Dictionary dicGenre = new Hashtable();
  private static Dictionary dicCat = new Hashtable();
  private static Dictionary dicLangue = new Hashtable();

  public static void main(String[] args) {
    loadData();
    readData();

    /* Commande Exec */
    while (true) {
			Scanner scan = new Scanner(System.in);
      System.out.println( "Welcom" );
      System.out.println( "lc: \tListe chanson d'un album" );
      System.out.println( "ld: \tles titres des albums, rangés par date de sortie" );
      System.out.println( "lg: \tles titres des albums, rangés par genre" );
      System.out.println( "lp: \tles playlists, rangées par nom" );
      System.out.println( "ll: \tles livres audio rangés par auteur" );
      System.out.println( "q: \tQuitter" );
			System.out.println( "" );

			String choice = scan.next();

			if (choice.equals("q")) {
				return;
			}

      switch(choice) {
        case "lc":
        break;
        case "ld":
        break;
        case "lg":
        break;
        case "lp":
        break;
        case "ll":
        break;

        default:
        System.out.println( "ERREUR: Choix inconnu" );
        break;
      }

    }

    saveData();
  }

  private static void readData(){
    String fld = "Datas/";

    String jsonString = Files.readString(fld + "Collections.json"); //assign your JSON String here
    JSONObject obj = new JSONObject(jsonString);

    /* Chargement en memoire */

    String jsonString = Files.readString(fld + "ElementsMusicaux.json"); //assign your JSON String here
    JSONObject obj = new JSONObject(jsonString);

    /* Chargement en memoire */


  }

  private static saveData() {

  }

  private static loadData() {
    this.dicGenre.put("1", "Jazz");
    this.dicGenre.put("2", "Hip-Hop");
    this.dicGenre.put("3", "Rock");
    this.dicGenre.put("4", "Pop");
    this.dicGenre.put("5", "Rap");

    this.dicCat.put("1", "Jeunesse");
    this.dicCat.put("2", "Roman");
    this.dicCat.put("3", "Théatre");
    this.dicCat.put("4", "Discours");
    this.dicCat.put("5", "Documetaire");

    this.dicLangue.put("1", "Francais");
    this.dicLangue.put("2", "Anglais");
    this.dicLangue.put("3", "Italien");
    this.dicLangue.put("4", "Espagnol");
    this.dicLangue.put("5", "Allemand");
  }

}
