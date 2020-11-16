
package gzik;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.*;
import java.util.*;

import java.util.*;
import java.lang.*;

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

      System.out.println( "" );
      System.out.println( "-------------------PARCOURIR----------------------" );
      System.out.println( "lc: \tListe chanson d'un album" );
      System.out.println( "ld: \tles titres des albums, rangés par date de sortie" );
      System.out.println( "lg: \tles titres des albums, rangés par genre" );
      System.out.println( "lp: \tles playlists, rangées par nom" );
      System.out.println( "ll: \tles livres audio rangés par auteur" );

      System.out.println( "" );
      System.out.println( "-------------------EDITION----------------------" );
      System.out.println( "c: \tRajout d’une nouvelle chanson" );
      System.out.println( "a: \tRajout d’un nouvel album" );
      System.out.println( "+: \tRajout d’une chanson existante à un album " );
      System.out.println( "l: \tRajout d’un nouveau livre audio" );
      System.out.println( "p: \tCréation d’une nouvelle playlist à partir de chansons et livres audio existants" );
      System.out.println( "-: \tSuppression d’une playlist" );
      System.out.println( "s: \tSauvegarde des playlists, des albums, des chansons et des livres audios dans les fichiers concernés." );

      System.out.println( "" );
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

  }

  private static void readData(){
    String fld = System.getProperty("user.dir") + "/Datas/";
    String filePath = fld + "Collections.json";
    String filePath2 = fld + "ElementsMusicaux.json";

    try
    {
      String jsonString = readFile(filePath);
      JSONObject obj = new JSONObject(jsonString);

      /* Chargement en memoire */

      jsonString = readFile(filePath2);
      obj = new JSONObject(jsonString);

      /* Chargement en memoire */

    }
    catch (IOException e)
    {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
      System.out.println("Exception caught : " + e.getLocalizedMessage());
    } catch (Exception e){
      System.out.println("Exception caught =" + e.getMessage());
    }
  }
  private static String readFile(String file) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader (file));
    String         line = null;
    StringBuilder  stringBuilder = new StringBuilder();
    String         ls = System.getProperty("line.separator");

    try {
        while((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        return stringBuilder.toString();
    } finally {
        reader.close();
    }
  }


  private static void saveData() {

  }

  private static void loadData() {
    dicGenre.put("1", "Jazz");
    dicGenre.put("2", "Hip-Hop");
    dicGenre.put("3", "Rock");
    dicGenre.put("4", "Pop");
    dicGenre.put("5", "Rap");

    dicCat.put("1", "Jeunesse");
    dicCat.put("2", "Roman");
    dicCat.put("3", "Théatre");
    dicCat.put("4", "Discours");
    dicCat.put("5", "Documetaire");

    dicLangue.put("1", "Francais");
    dicLangue.put("2", "Anglais");
    dicLangue.put("3", "Italien");
    dicLangue.put("4", "Espagnol");
    dicLangue.put("5", "Allemand");
  }

}
