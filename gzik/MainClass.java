
package gzik;
import gzik.*;
import gzik.Obj.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.FileWriter;
import java.io.IOException;

import org.json.*;
import java.util.*;
import java.lang.*;

class MainClass {
  static String fld = System.getProperty("user.dir") + "/Datas/";
  static String filePathColl = fld + "Collections_Write.json";
  static String filePathEM = fld + "ElementsMusicaux_Write.json";

  private static ArrayList<ElementMusical> listElementMusical;
  private static ArrayList<Album> listAlbum;
  private static ArrayList<Playlist> listPlaylist;

  private static Map<Integer, String> dicGenre = new Hashtable();
  private static Map<Integer, String> dicCat = new Hashtable();
  private static Map<Integer, String> dicLangue = new Hashtable();

  public static void main(String[] args) {
    loadData();
    System.out.println( "DATA Loaded !" );
    readData();
    System.out.println( "DATA Read !" );

    /* Commande Exec */
    while (true) {
			Scanner scan = new Scanner(System.in);
      System.out.println( "Welcom" );

      System.out.println( "" );
      System.out.println( "-------------------PARCOURIR----------------------" );
      System.out.println( "lc: \tListe chanson d'un album" );
      System.out.println( "ld: \tles titres des albums, ranges par date de sortie" );
      System.out.println( "lg: \tles titres des albums, ranges par genre" );
      System.out.println( "lp: \tles playlists, rangees par nom" );
      System.out.println( "ll: \tles livres audio ranges par auteur" );

      System.out.println( "" );
      System.out.println( "-------------------EDITION----------------------" );
      System.out.println( "c: \tRajout d\"une nouvelle chanson" );
      System.out.println( "a: \tRajout d\"un nouvel album" );
      System.out.println( "+: \tRajout d\"une chanson existante a un album " );
      System.out.println( "l: \tRajout d\"un nouveau livre audio" );
      System.out.println( "p: \tCreation d\"une nouvelle playlist a partir de chansons et livres audio existants" );
      System.out.println( "-: \tSuppression d\"une playlist" );
      System.out.println( "s: \tSauvegarde des playlists, des albums, des chansons et des livres audios dans les fichiers concernes." );

      System.out.println( "" );
      System.out.println( "q: \tQuitter" );
			System.out.println( "" );

			String choice = scan.next();

			if (choice.equals("q")) {
				return;
			}

      switch(choice) {
        case "lc":
          listeChansonAlbum();
        break;
        case "ld":
          titreAlbumParDate();
        break;
        case "lg":
          listeTitreRangeParGenre();
        break;
        case "lp":
        break;
        case "ll":
          listeLivreAudioRangeParAuteur();
        break;

        /* edit */
        case "c":
        break;
        case "a":
        break;
        case "+":
        break;
        case "l":
        break;
        case "p":
        break;
        case "-":
        break;
        case "s":
          saveData();
        break;

        default:
          System.out.println( "ERREUR: Choix inconnu" );
        break;
      }
    }
  }

  private static void titreAlbumParDate(){
    System.out.println("Liste des albums (Par ordre alphabétique) : " );
    int [] filtredate = {};
    for (int i = 0; i < listAlbum.size(); i++) {
      Album alb = listAlbum.get(i);
      System.out.println(alb.getName() + " " + alb.getDate());
    }
    Arrays.sort(filtredate);


    /*
    Album alb = listAlbum.get(i)
    dz
    */
  }

  private static void listeChansonAlbum() {
    for (int i = 0; i < listAlbum.size() ; i++) {
      Album alb = listAlbum.get(i);
      println((i+1) + ". " + alb.getName() + "\t" + alb.getArtiste() + "\t" + alb.getDate() + "\t" + alb.getDuree());
    }
    Scanner scan = new Scanner(System.in);
    println("Veuillez choisir l'album: ");
    while (!scan.hasNextInt()) scan.next();
    int choix = scan.nextInt();

    Album albch = listAlbum.get(choix - 1);
    println("Vous avez choisit: " + choix + " \n " + albch.getName() + "\t" + albch.getArtiste() + "\t" + albch.getDate() + "\t" + albch.getDuree()
              + " (" + albch.getEM().size() + " chanson)");

    ArrayList<Integer> idChanson = albch.getEM();
    for (int i = 0; i < idChanson.size(); i++) {
      int id = idChanson.get(i);
      ElementMusical em = getEmById(id);
      if (em != null && !em.getIsLivreAudio()) {
        Chanson ch = (Chanson)em;
        println((i+1) + " . " + ch.getName() + "\t" + ch.getArtiste() + "\t" + ch.getGenre());
      } else {
        println("ERR: Une chason n a pas ete trouver dans l album, verifier qu'il s agit bien d une chanson: " + id);
      }
    }
  }
  private static int a = 0;
  private static void listeTitreRangeParGenre() {
    int[] g;
    Map<Integer, Integer> listPlayable = new Hashtable<>();
    a = 0; // compteur global

    dicGenre.forEach((k, v) -> {
      System.out.println("GENRE \t Nom : " + v + "\t ID : " + k);
      for (int i = 0; i < listElementMusical.size(); i++) {
        ElementMusical em = listElementMusical.get(i);
        if (!em.getIsLivreAudio()) {
          Chanson ch = (Chanson)em;
          if (ch.getGenre() == k) {
            println("\t" + (a+1) + " . " + ch.getName() + "\t" + ch.getArtiste() + "\t" + ch.getGenre());
            listPlayable.put(a, ch.getId());
            a++;
          }
        }
      }
    });

    println("");
    Scanner scan = new Scanner(System.in);
    println("Veuillez choisir une chanson (" + listPlayable.size() + "): ");
    while (!scan.hasNextInt()) scan.next();
    int choix = scan.nextInt();
    int id = listPlayable.get(choix - 1);

    Chanson chch = (Chanson)getEmById(id);
    println("Vous avez choisit: " + choix + " \n " + chch.getName() + "\t" + chch.getArtiste() + "\t" + chch.getGenre() + "\t" + chch.getDuree());
  }
  private static void listeLivreAudioRangeParAuteur() {
    int[] g;
    Map<String, Integer> distinctAuteur = new Hashtable<>();
    Map<Integer, Integer> listPlayable = new Hashtable<>();

    a = 0;
    for (int i = 0; i < listElementMusical.size(); i++) {
      if (listElementMusical.get(i).getIsLivreAudio()) {
        ElementMusical em = listElementMusical.get(i);
        if (em.getIsLivreAudio()) {
          LivreAudio la = (LivreAudio)em;
          String key = la.getAuteur().toLowerCase();
          println("key = " + key);
          if (!distinctAuteur.containsKey(key)) {
            distinctAuteur.put(key, a);
            a++;
          }
        }
      }
    }
    println("Nombre d auteur different: " + distinctAuteur.size() + "\n");

    a = 0;
    distinctAuteur.forEach((k, v) -> {
      System.out.println("-> AUTEUR \t Nom : " + k.substring(0, 1).toUpperCase() + k.substring(1) + "\t Num : " + v);
      for (int i = 0; i < listElementMusical.size(); i++) {
        ElementMusical em = listElementMusical.get(i);
        if (em.getIsLivreAudio()) {
          LivreAudio la = (LivreAudio)em;
          String key = la.getAuteur().toLowerCase();
          if (k.equals(key)) {
              println("\t" + (a+1) + " . " + la.getName() + "\t" + la.getAuteur() + "\t" + getLangById(la.getLangues()) + "\t" + getCatById(la.getCategorie()) + "\t" + la.getDuree());
              listPlayable.put(a, la.getId());
              a++;
          }
        }
      }
    });

    println("");
    Scanner scan = new Scanner(System.in);
    println("Veuillez choisir un livre audio (" + listPlayable.size() + "): ");

    while (!scan.hasNextInt()) scan.next();
    int choix = scan.nextInt();

    int id = listPlayable.get(choix - 1);

    LivreAudio la = (LivreAudio)getEmById(id);
    println("Vous avez choisit: " + choix + " \n " + la.getName() + "\t" + la.getAuteur() + "\t" + getLangById(la.getLangues()) + "\t" + getCatById(la.getCategorie()) + "\t" + la.getDuree());
  }

  private static ElementMusical getEmById(int id) {
    ElementMusical em = null;
    for (int i = 0; i < listElementMusical.size(); i++) {
      em = listElementMusical.get(i);
      if (em.getId() == id) {
        break;
      }
    }
    return em;
  }
  private static String getLangById(int id) {
    return (String)dicCat.get(id);
  }
  private static String getCatById(int id) {
    return (String)dicCat.get(id);
  }
  public static void println(String str) {
      System.out.println(str);
  }

  private static void readData(){
    try
    {
      String jsonString = readFile(filePathColl);
      JSONObject objCtn = new JSONObject(jsonString);
      listAlbum = new ArrayList<Album>();
      listPlaylist = new ArrayList<Playlist>();
      listElementMusical = new ArrayList<ElementMusical>();

      JSONArray albumsArr = objCtn.getJSONArray("albums");
      int length = albumsArr.length();
      System.out.println( "albums: " + length );

      for(int i = 0; i < length; i++) {
          JSONObject itm = albumsArr.getJSONObject(i);
          Album obj = new Album();
          obj.setId(itm.getInt("id"));
          obj.setName(itm.getString("name"));
          obj.setArtiste(itm.getString("artiste"));
          obj.setDuree((float)itm.getDouble("duree"));
          obj.setDate(itm.optInt("date"));
          JSONArray chansonArr = itm.optJSONArray("em");
          int lengthChanson = chansonArr.length();
          for (int a = 0; a < lengthChanson; a++) {
              obj.addEM( chansonArr.optInt(a) );
          }
          listAlbum.add(obj);
      }

      JSONArray playlistArr = objCtn.getJSONArray("playlists");
      length = playlistArr.length();
      System.out.println( "playlists: " + length );

      for(int i = 0; i < length; i++) {
          JSONObject itm = playlistArr.getJSONObject(i);
          Playlist obj = new Playlist();
          obj.setId(itm.getInt("id"));
          obj.setName(itm.getString("name"));
          JSONArray chansonArr = itm.optJSONArray("em");
          int lengthChanson = chansonArr.length();
          for (int a = 0; a < lengthChanson; a++) {
              obj.addEM( chansonArr.optInt(a) );
          }
          listPlaylist.add(obj);
      }

      jsonString = readFile(filePathEM);
      objCtn = new JSONObject(jsonString);

      /* Chargement en memoire */
      JSONArray chansonArr = objCtn.getJSONArray("chanson");
      length = chansonArr.length();
      System.out.println( "chanson: " + length );

      for(int i = 0; i < length; i++) {
          JSONObject itm = chansonArr.getJSONObject(i);
          Chanson obj = new Chanson();
          obj.setId(itm.getInt("id"));
          obj.setIsLivreAudio(false);
          obj.setName(itm.getString("name"));
          obj.setArtiste(itm.getString("artiste"));
          obj.setGenre(itm.getInt("genre"));
          obj.setContent(itm.getString("content"));
          obj.setDuree((float)itm.getDouble("duree"));
          listElementMusical.add(obj);
      }

      JSONArray livreaudioArr = objCtn.getJSONArray("livreaudio");
      length = livreaudioArr.length();

      for(int i = 0; i < length; i++) {
          JSONObject itm = livreaudioArr.getJSONObject(i);
          LivreAudio obj = new LivreAudio();
          obj.setId(itm.getInt("id"));
          obj.setIsLivreAudio(true);
          obj.setName(itm.getString("name"));
          obj.setAuteur(itm.getString("auteur"));
          obj.setLangues(itm.getInt("langues"));
          obj.setCategorie(itm.getInt("categorie"));
          obj.setContent(itm.getString("content"));
          obj.setDuree((float)itm.getDouble("duree"));
          listElementMusical.add(obj);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
      System.out.println("Une exception attrapee : " + e.getLocalizedMessage());
    } catch (Exception e){
      System.out.println("Une exception attrapee =" + e.getMessage());
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
  private static FileWriter file;
  private static void saveData() {
    try {
      JSONObject coll = new JSONObject();

      JSONArray albums = new JSONArray();
      for (int i = 0; i < listAlbum.size(); i++) {
        JSONObject obj = new JSONObject();
        Album alb = listAlbum.get(i);
        obj.put("id", alb.getId());
        obj.put("name", alb.getName());
        obj.put("artiste", alb.getArtiste());
        obj.put("duree", alb.getDuree());
        obj.put("date", alb.getDate());
        JSONArray em = new JSONArray();
        ArrayList<Integer> listEm = alb.getEM();
        for (int a = 0; a < listEm.size(); a++) {
          em.put(listEm.get(a));
        }
        obj.put("em", em);

        albums.put(i, obj);
      }
      coll.put("albums", albums);

      JSONArray playlists = new JSONArray();
      for (int i = 0; i < listPlaylist.size(); i++) {
        JSONObject obj = new JSONObject();
        Playlist pl = listPlaylist.get(i);
        obj.put("id", pl.getId());
        obj.put("name", pl.getName());
        JSONArray em = new JSONArray();
        ArrayList<Integer> listEm = pl.getEM();
        for (int a = 0; a < listEm.size(); a++) {
          em.put(listEm.get(a));
        }
        obj.put("em", em);

        albums.put(i, obj);
      }
      coll.put("playlists", playlists);

      file = new FileWriter(filePathColl);
      file.write(coll.toString());

      JSONObject emObj = new JSONObject();

      JSONArray chanson = new JSONArray();
      int nbrChanson = 0;
      for (int i = 0; i < listElementMusical.size(); i++) {
        ElementMusical em = listElementMusical.get(i);
        if (!em.getIsLivreAudio()) {
          Chanson ch = (Chanson)em;
          JSONObject obj = new JSONObject();
          obj.put("id", ch.getId());
          obj.put("isLivreAudio", ch.getIsLivreAudio());
          obj.put("name", ch.getName());
          obj.put("artiste", ch.getArtiste());
          obj.put("genre", ch.getGenre());
          obj.put("duree", ch.getDuree());
          obj.put("content", ch.getContent());
          chanson.put(nbrChanson, obj);
          nbrChanson++;
        }
      }
      emObj.put("chanson", chanson);

      JSONArray livreaudio = new JSONArray();
      int nbrLivreAdio = 0;
      for (int i = 0; i < listElementMusical.size(); i++) {
        ElementMusical em = listElementMusical.get(i);
        if (em.getIsLivreAudio()) {
          LivreAudio la = (LivreAudio)em;
          JSONObject obj = new JSONObject();
          obj.put("id", la.getId());
          obj.put("isLivreAudio", la.getIsLivreAudio());
          obj.put("name", la.getName());
          obj.put("auteur", la.getAuteur());
          obj.put("langues", la.getLangues());
          obj.put("categorie", la.getCategorie());
          obj.put("duree", la.getDuree());
          obj.put("content", la.getContent());
          livreaudio.put(nbrLivreAdio, obj);
          nbrLivreAdio++;
        }
      }
      emObj.put("livreaudio", livreaudio);

      file = new FileWriter(filePathEM);
      file.write(emObj.toString());

      System.out.println("Successfully Copied JSON Object to File...");
      System.out.println("\nJSON Object: " + coll);

    } catch (IOException e) {
        e.printStackTrace();

    } catch (JSONException e) {
      e.printStackTrace();
      System.out.println("Une exception attrapee : " + e.getLocalizedMessage());
    } finally {
        try {
            file.flush();
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
  }
  private static void loadData() {
    dicGenre.put(1, "Jazz");
    dicGenre.put(2, "Hip-Hop");
    dicGenre.put(3, "Rock");
    dicGenre.put(4, "Pop");
    dicGenre.put(5, "Rap");

    dicCat.put(1, "Jeunesse");
    dicCat.put(2, "Roman");
    dicCat.put(3, "Theatre");
    dicCat.put(4, "Discours");
    dicCat.put(5, "Documetaire");

    dicLangue.put(1, "Francais");
    dicLangue.put(2, "Anglais");
    dicLangue.put(3, "Italien");
    dicLangue.put(4, "Espagnol");
    dicLangue.put(5, "Allemand");
  }

}
