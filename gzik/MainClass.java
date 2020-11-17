
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

  private static ArrayList<ElementMusical> listElementMusical;
  private static ArrayList<Album> listAlbum;
  private static ArrayList<Playlist> listPlaylist;

  private static Dictionary dicGenre = new Hashtable();
  private static Dictionary dicCat = new Hashtable();
  private static Dictionary dicLangue = new Hashtable();

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
        /* foreach */
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

  static String fld = System.getProperty("user.dir") + "/Datas/";
  static String filePath = fld + "Collections_Write.json";
  static String filePath2 = fld + "ElementsMusicaux_Write.json";

  private static void readData(){
    try
    {
      String jsonString = readFile(filePath);
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

      jsonString = readFile(filePath2);
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
  private static void saveData() { //https://crunchify.com/how-to-write-json-object-to-file-in-java/
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

      file = new FileWriter(filePath);
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

      file = new FileWriter(filePath2);
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
    dicGenre.put("1", "Jazz");
    dicGenre.put("2", "Hip-Hop");
    dicGenre.put("3", "Rock");
    dicGenre.put("4", "Pop");
    dicGenre.put("5", "Rap");

    dicCat.put("1", "Jeunesse");
    dicCat.put("2", "Roman");
    dicCat.put("3", "Theatre");
    dicCat.put("4", "Discours");
    dicCat.put("5", "Documetaire");

    dicLangue.put("1", "Francais");
    dicLangue.put("2", "Anglais");
    dicLangue.put("3", "Italien");
    dicLangue.put("4", "Espagnol");
    dicLangue.put("5", "Allemand");
  }

}
