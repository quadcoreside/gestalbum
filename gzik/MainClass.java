
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
			Scanner sc = new Scanner(System.in);
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


			String choice = sc.next();

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
          listPlaylistRangeParNom();
        break;
        case "ll":
          listeLivreAudioRangeParAuteur();
        break;

        /* edit */
        case "c":
          addChanson();
        break;
        case "a":
          addAlbum();
        break;
        case "+":
          setChansonToAlbum();
        break;
        case "l":
          addLivreAudio();
        break;
        case "p":
          createPlaylist();
        break;
        case "-":
          deletePlaylist();
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

  /************************* LISTE ***********************************/
  private static void titreAlbumParDate(){
    /* Ne pas confondre notre Collection classe et la classe Collections dans java.util */
    // On appelle notre methode de comparaison par date qui static
    Collections.sort(listAlbum, Collections.reverseOrder(new Album.DateComparator())); //reverseOrder plus récent en premier

    for (int i = 0; i < listAlbum.size() ; i++) {
      Album alb = listAlbum.get(i);
      println((i+1) + ". " + alb.getName() + "\t" + alb.getArtiste() + "\t" + alb.getDate() + "\t" + getDureeMin(alb.getDuree()));
    }
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir l'album: ");
    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listAlbum.size());

    Album albch = listAlbum.get(choix - 1);
    println("Vous avez choisit: " + choix + " \n " + albch.getName() + "\t" + albch.getArtiste() + "\t" + albch.getDate() + "\t" + getDureeMin(albch.getDuree())
              + " (" + albch.getEM().size() + " chanson)");

    viewCollectionEM(albch);
  }
  private static void listPlaylistRangeParNom() {
    /*
    Also for sorting dynamically without implements :)
    Collections.sort(listPlaylist,new Comparator<Collection>() {
        @Override
        public int compare(Collection a, Collection b) {
            return a.getName().compareTo(b.getName());
        }
    });*/
    /// ICI notionn d'implemnts
    Collections.sort(listPlaylist); //sort by ASC != reverseOrder

    for (int i = 0; i < listPlaylist.size() ; i++) {
      Playlist pl = listPlaylist.get(i);
      println((i+1) + ". " + pl.getName() + "\t elements: " + pl.getEM().size() );
    }
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir une playlist: ");
    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listPlaylist.size());

    Playlist plch = listPlaylist.get(choix - 1);
    println("Vous avez choisit: " + choix + " \n " + plch.getName()
              + " (" + plch.getEM().size() + " elements)");

    viewCollectionEM(plch);
  }
  private static void listeChansonAlbum() {
    for (int i = 0; i < listAlbum.size() ; i++) {
      Album alb = listAlbum.get(i);
      println((i+1) + ". " + alb.getName() + "\t" + alb.getArtiste() + "\t" + alb.getDate() + "\t" + getDureeMin(alb.getDuree()));
    }
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir l'album: ");
    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listAlbum.size());

    Album albch = listAlbum.get(choix - 1);
    println("Vous avez choisit: " + choix + " \n " + albch.getName() + "\t" + albch.getArtiste() + "\t" + albch.getDate() + "\t" + getDureeMin(albch.getDuree())
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
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir une chanson (" + listPlayable.size() + "): ");

    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listPlayable.size());

    int id = listPlayable.get(choix - 1);

    Chanson chch = (Chanson)getEmById(id);
    println("Vous avez choisit: " + choix + " \n " + chch.getName() + "\t" + chch.getArtiste() + "\t" + chch.getGenre() + "\t" + getDureeMin(chch.getDuree()));
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
              println("\t" + (a+1) + " . " + la.getName() + "\t" + la.getAuteur() + "\t" + getLangById(la.getLangues()) + "\t" + getCatById(la.getCategorie()) + "\t" + getDureeMin(la.getDuree()));
              listPlayable.put(a, la.getId());
              a++;
          }
        }
      }
    });

    println("");
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir un livre audio (" + listPlayable.size() + "): ");

    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listPlayable.size());

    int id = listPlayable.get(choix - 1);

    LivreAudio la = (LivreAudio)getEmById(id);
    println("Vous avez choisit: " + choix + " \n " + la.getName() + "\t" + la.getAuteur() + "\t" + getLangById(la.getLangues()) + "\t" + getCatById(la.getCategorie()) + "\t" + getDureeMin(la.getDuree()));
  }
  /************************* LISTE FIN ***********************************/
  /************************* EDITION à faire ***********************************/
  private static void addChanson() {
    Scanner sc = new Scanner(System.in);
    println("Veuillez entrer le nom de la chanson: ");
    String name = sc.nextLine();

    println("Veuillez entrer l\"artiste: ");
    String artiste = sc.nextLine();

    println("Veuillez choisir le genre: ");
    for (Map.Entry<Integer, String> itm : dicGenre.entrySet()) {
      println((itm.getKey()+1) + ". " + itm.getValue());
    }
    int genre = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        genre = sc.nextInt();
    } while (genre < 0 || genre > dicGenre.size());

    println("entrer la duree en seconde: ");
    while (!sc.hasNextInt()) sc.next();
    int duree = sc.nextInt();

    println("entrer le contenu: ");
    String content = sc.nextLine();

    Chanson ch = new Chanson();
    ch.setId(listElementMusical.size());
    ch.setName(name);
    ch.setArtiste(artiste);
    ch.setGenre(genre);
    ch.setDuree(duree);
    ch.setContent(content);

    listElementMusical.add(ch);
  }
  private static void addLivreAudio() {
    Scanner sc = new Scanner(System.in);
    println("Veuillez entrer le nom de la chanson: ");
    String name = sc.nextLine();

    println("Veuillez entrer l\"auteur: ");
    String auteur = sc.nextLine();

    /********************LANGUE************************/
    println("Veuillez choisir la langue: ");
    for (Map.Entry<Integer, String> itm : dicLangue.entrySet()) {
      println((itm.getKey()+1) + ". " + itm.getValue());
    }
    int langue = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        langue = sc.nextInt();
    } while (langue < 0 || langue > dicLangue.size());

    /********************CAT************************/
    println("Veuillez choisir la categorie: ");
    for (Map.Entry<Integer, String> itm : dicCat.entrySet()) {
      println((itm.getKey()+1) + ". " + itm.getValue());
    }
    int cat = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        cat = sc.nextInt();
    } while (cat < 0 || cat > dicCat.size());

    println("entrer la duree en seconde: ");
    while (!sc.hasNextInt()) sc.next();
    int duree = sc.nextInt();

    println("entrer le contenu: ");
    String content = sc.nextLine();

    LivreAudio la = new LivreAudio();
    la.setId(listElementMusical.size());
    la.setName(name);
    la.setAuteur(auteur);
    la.setLangues(langue);
    la.setCategorie(cat);
    la.setDuree(duree);
    la.setContent(content);

    listElementMusical.add(la);
  }
  private static void addAlbum() {
    Scanner sc = new Scanner(System.in);
    println("Veuillez entrer le nom de l\"album: ");
    String name = sc.nextLine();

    println("Veuillez entrer l\"artiste: ");
    String artiste = sc.nextLine();

    println("entrer la date: ");
    while (!sc.hasNextInt()) sc.next();
    int date = sc.nextInt();

    println("entrer la duree en seconde: ");
    while (!sc.hasNextInt()) sc.next();
    int duree = sc.nextInt();

    Album a = new Album();
    a.setId(listAlbum.size());
    a.setName(name);
    a.setArtiste(artiste);
    a.setDate(date);
    a.setDuree(duree);

    listAlbum.add(a);
  }
  private static void setChansonToAlbum() {
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir l\"album de destination");
    for (int i = 0; i < listAlbum.size(); ) {
      println((i+1) + ". " + listAlbum.get(i).getName());
    }
    int choix_album = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix_album = sc.nextInt();
    } while (choix_album < 0 || choix_album > listAlbum.size());

    println("Veuillez choisir la chanson");
    Map<Integer, Integer> listSelectable = new Hashtable<>();
    a = 0; // compteur global
    for (int i = 0; i < listElementMusical.size(); i++) {
      if (!listElementMusical.get(i).getIsLivreAudio()) {
        Chanson ch = (Chanson)listElementMusical.get(i);
        listSelectable.put(a, ch.getId());
        println((a+1) + ". " + ch.getName() + "\t" + ch.getArtiste() + "\t" + ch.getGenre() + "\t" + getDureeMin(ch.getDuree()));
        a++;
      }
    }

    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listSelectable.size());

    int id = listSelectable.get(choix - 1);
    Chanson ch = (Chanson)getEmById(id);

    Album alb = listAlbum.get(choix_album);
    alb.addEM(id);

    listAlbum.set(choix_album, alb);
  }
  private static void createPlaylist() {
    Scanner sc = new Scanner(System.in);
    println("Veuillez le nom de la playlist");
    String name = sc.nextLine();

    Playlist pl = new Playlist();
    pl.setId(listPlaylist.size());
    pl.setName(name);

    println("Veuillez choisir les elements musicaux a ajouter a la playlist: ");
    println("appuyer sur t une fois terminer");
    for (int i = 0; i < listElementMusical.size(); i++) {
      if (!listElementMusical.get(i).getIsLivreAudio()) {
        Chanson ch = (Chanson)listElementMusical.get(i);
        println((i+1) + ". " + ch.getName() + "\t" + ch.getArtiste() + "\t" + ch.getGenre() + "\t" + getDureeMin(ch.getDuree())+ "\t" + "CHANSON");
      } else {
        LivreAudio la = (LivreAudio)listElementMusical.get(i);
        println("\t" + (a+1) + " . " + la.getName() + "\t" + la.getAuteur() + "\t" + getLangById(la.getLangues()) + "\t" + getCatById(la.getCategorie()) + "\t" + getDureeMin(la.getDuree()));
      }
    }

    String entry = "";
    while (sc.hasNextLine()) {
        entry = sc.nextLine();

        if (entry.equals("t")) {
          break;
        }

        int a = 0;
        a = Integer.parseInt(entry);
        if (a > 0 && a <= listElementMusical.size()) {
          pl.addEM( listElementMusical.get(a - 1).getId() );
          println(listElementMusical.get(a - 1).getName() + "ajouté.");
        } else {
          println("erreur entré incorrecte.");
          println("entrer \"t\" une fois terminer");
        }
    }

    listPlaylist.add(pl);
  }
  private static void deletePlaylist() {
    Scanner sc = new Scanner(System.in);
    println("Choisir la playlist a supprimer:");
    for (int i = 0; i < listPlaylist.size() ; i++) {
      Playlist pl = listPlaylist.get(i);
      println((i+1) + ". " + pl.getName() + "\t elements: " + pl.getEM().size() );
    }

    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listPlaylist.size());

    Playlist pl = listPlaylist.get(choix);
    listPlaylist.remove(choix);
    println(pl.getName() + " suprimmé");
  }
  /************************* EDITION FIN ***********************************/

  private static void viewCollectionEM(Collection coll) {
    ArrayList<Integer> listEM = coll.getEM();
    for (int i = 0; i < listEM.size(); i++ ) {
      ElementMusical em = getEmById(listEM.get(i));
      if (em.getIsLivreAudio()) {
        LivreAudio la = (LivreAudio)em;
        println((i+1) +  ". "  + la.getName() + "\t" + la.getAuteur() + "\t" + getLangById(la.getLangues()) + "\t" + getCatById(la.getCategorie()) + "\t" + getDureeMin(la.getDuree()) + "\t LivreAudio");
      } else {
        Chanson ch = (Chanson)em;
        println((i+1) +  ". "  + ch.getName() + "\t" + ch.getArtiste() + "\t" + ch.getGenre() + "\t" + getDureeMin(ch.getDuree()) + "\t Chanson");
      }
    }
  }
  private static String getDureeMin(int sec) {
    int min = sec / 60;
    int rSec = (int)((((double)sec / 60.0) - (double)min) * 60.0);
    return min + ":" + rSec;
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
    return (String)dicLangue.get(id);
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
          obj.setDuree(itm.optInt("duree"));
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
      System.out.println( "Chanson: " + length );

      for(int i = 0; i < length; i++) {
          JSONObject itm = chansonArr.getJSONObject(i);
          Chanson obj = new Chanson();
          obj.setId(itm.getInt("id"));
          obj.setIsLivreAudio(false);
          obj.setName(itm.getString("name"));
          obj.setArtiste(itm.getString("artiste"));
          obj.setGenre(itm.getInt("genre"));
          obj.setContent(itm.getString("content"));
          obj.setDuree(itm.optInt("duree"));
          listElementMusical.add(obj);
      }

      JSONArray livreaudioArr = objCtn.getJSONArray("livreaudio");
      length = livreaudioArr.length();
      System.out.println( "Livre Audio: " + length );

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
          obj.setDuree(itm.optInt("duree"));
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
