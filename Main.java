import org.json.*;
import java.util.*;

class Main extends Parent implements Interface {

  static ArrayList<ElementMusical> listElementMusica;
  static Hashtable<Integer, String> TabGenre;
  static Hashtable<Integer, String> tabCat;
  static Hashtable<Integer, String> tabLangue;

  public static void main(String[] args) {
    readData();

    /* Commande Exec */
    while (true) {
			Scanner scan = new Scanner(System.in);
			System.out.println( "Welcom" );
			System.out.println( "h: Help" );
      System.out.println( "q: Quit" );
      System.out.println( "lc: Liste chanson d'un album" );
      System.out.println( "ld: les titres des albums, rangés par date de sortie" );
      System.out.println( "lg: les titres des albums, rangés par genre" );
      System.out.println( "lp: les playlists, rangées par nom" );
      System.out.println( "ll: les livres audio rangés par auteur" );
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
        System.out.println( "ERREUR: Choix inconu" );
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

}
