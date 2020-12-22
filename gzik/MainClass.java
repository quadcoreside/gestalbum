
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

/**
 * Classe principale de gestion des elements musicaux
 * @author Romain & Moussa
 * @version 1.0
 */
class MainClass {
  /* Chemin d'enrgistrement des données */
  static final String fld = System.getProperty("user.dir") + "/Datas/";
  static final String filePathColl = fld + "Collections_Write.json";
  static final String filePathEM = fld + "ElementsMusicaux_Write.json";

  /* Tableaux contenant nos collections de données */
  private static ArrayList<ElementMusical> listElementMusical;
  private static ArrayList<Album> listAlbum;
  private static ArrayList<Playlist> listPlaylist;

  /* Tableaux contenant nos genre, catégorie, langues */
  private static Map<Integer, String> dicGenre = new Hashtable();
  private static Map<Integer, String> dicCat = new Hashtable();
  private static Map<Integer, String> dicLangue = new Hashtable();

  /* Pour la demande d'affichage du menu ou quitter le programme */
  private static boolean firstCmd = true;
  /* Objet global de type FileWriter */
  private static FileWriter file;

  /* Vairiable global "a" nous sert de compteur, pour pouvoir afficher une suite logique de choix par exemple */
  private static int a = 0;

  public static void main(String[] args) {
    /* Chargement des données en mémoire */
    loadData();
    System.out.println( "DATA Loaded !" );
    readData();
    System.out.println( "DATA Read !" );

    println( "Bienvenue" );

    /* Boucle infini */
    while (true) {
      /* On définit l'objet scanner pour capture l'entré de l'utilisateur */
      Scanner sc = new Scanner(System.in);

      /* Au premier démmarage on ne l'affiche pas, on affiche le menu en entier */
      if (!firstCmd) {
        System.out.println( "" );
        System.out.println( "1. Afficher le menu" );
        System.out.println( "2. Quitter" );

        int choix = 1; // par défault
        do {
            while (!sc.hasNextInt()) sc.next();
            choix = sc.nextInt();
        } while (choix < 0 || choix > 2);

        if (choix == 2) { // Correspond à quitter
          break;
        }
      }
      firstCmd = false;

      /* Affichage du manu pour l'utilisateur  */
      println( "" );
      println( "-------------------PARCOURIR----------------------" );
      println( "lc: \tListe chanson d'un album" );
      println( "ld: \tles titres des albums, ranges par date de sortie" );
      println( "lg: \tles titres des albums, ranges par genre" );
      println( "lp: \tles playlists, rangees par nom" );
      println( "ll: \tles livres audio ranges par auteur" );

      println( "" );
      println( "-------------------EDITION----------------------" );
      println( "c: \tRajout d\"une nouvelle chanson" );
      println( "a: \tRajout d\"un nouvel album" );
      println( "+: \tRajout d\"une chanson existante a un album " );
      println( "l: \tRajout d\"un nouveau livre audio" );
      println( "p: \tCreation d\"une nouvelle playlist a partir de chansons et livres audio existants" );
      println( "-: \tSuppression d\"une playlist" );
      println( "s: \tSauvegarde des playlists, des albums, des chansons et des livres audios dans les fichiers concernes." );

      println( "" );
      println( "q: \tQuitter" );
			println( "" );

      /* Récuperation de la saisie dans la variable choice */
			String choice = sc.next();

			if (choice.equals("q")) { //Si la var choice equivaut à "q"
				return; /* On quitte la boucle infini, soit le programme */
			}

      /* On injecte la variable choice dans notre switch, pour appeler les methodes correspondante */
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

          /* Par défault si la variable injectée dans le switch ne correspond à aucun choix définit */
        default:
          println( "ERREUR: Choix inconnu" );
          break;
      }
    }
  }

  /************************* AFFICHAGE DE LISTE ***********************************/
  private static void titreAlbumParDate(){
    /* A Ne pas confondre notre Collection classe et la classe Collections dans java.util */
    /* On appelle notre methode de comparaison par date qui est static */
    Collections.sort(listAlbum, Collections.reverseOrder(new AlbumDateComparator())); //reverseOrder plus récent en premier

    /* On affiche la liste des album  */
    for (int i = 0; i < listAlbum.size() ; i++) {
      Album alb = listAlbum.get(i);
      println((i+1) + ". " + alb.getName() + "\t" + alb.getArtiste() + "\t" + alb.getDate() + "\t" + getDureeMin(alb.getDuree()));
    }

    /* On capture la saisie du choix */
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir l'album: ");

    /* Récuperation de la saisie bornée, de maniere persistante */
    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listAlbum.size());

    /* On affiche l'album choisi */
    Album albch = listAlbum.get(choix - 1);
    println("Vous avez choisit: " + choix + " \n " + albch.getName() + "\t" + albch.getArtiste() + "\t" + albch.getDate() + "\t" + getDureeMin(albch.getDuree())
              + " (" + albch.getEM().size() + " chanson)");

    viewCollectionEM(albch);
  }

  /**
  * Lister les playlist par nom
  * l'utilisateur choisi une playlist puis on lui affiche son choix
  * @param void
  * @return void
  * @since 1.0
  */
  private static void listPlaylistRangeParNom() {
    /// ICI notionn d'implemnts
    Collections.sort(listPlaylist); //sort by ASC != reverseOrder

    for (int i = 0; i < listPlaylist.size() ; i++) {
      Playlist pl = listPlaylist.get(i);
      println((i+1) + ". " + pl.getName() + "\t elements: " + pl.getEM().size() );
    }
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir une playlist: ");

    /* Récuperation de la saisie bornée, de maniere persistante */
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

  /**
  * Lister les chanson d'un album
  * l'utilisateur choisi un album puis on affiche les elements musicaux contenu la dedans
  * @param void
  * @return void
  * @since 1.0
  */
  private static void listeChansonAlbum() {
    for (int i = 0; i < listAlbum.size() ; i++) {
      Album alb = listAlbum.get(i);
      println((i+1) + ". " + alb.getName() + "\t" + alb.getArtiste() + "\t" + alb.getDate() + "\t" + getDureeMin(alb.getDuree()));
    }
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir l'album: ");

    /* Récuperation de la saisie bornée, de maniere persistante */
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


  /**
  * Lister des elements musicaux ranger par genre
  * l'utilisateur choisi un element musical puis n lui affiche son choix
  * @param void
  * @return void
  * @since 1.0
  */
  private static void listeTitreRangeParGenre() {
    int[] g;
    Map<Integer, Integer> listPlayable = new Hashtable<>();
    a = 0; // compteur global

    dicGenre.forEach((k, v) -> {
      println("GENRE \t Nom : " + v + "\t ID : " + k);
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

    println(""); // Juste pour un saut de ligne "\r\n"
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir une chanson (" + listPlayable.size() + "): ");

    /* Récuperation de la saisie bornée, de maniere persistante */
    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listPlayable.size());

    /* On recupere l'id un entier correspondant a la liste de choix enumeré */
    int id = listPlayable.get(choix - 1);

    Chanson chch = (Chanson)getEmById(id);
    println("Vous avez choisit: " + choix + " \n " + chch.getName() + "\t" + chch.getArtiste() + "\t" + chch.getGenre() + "\t" + getDureeMin(chch.getDuree()));
  }

  /**
  * Lister des livres audio ranger par genre
  * l'utilisateur choisi un livre audio puis on lui affiche son choix
  * @param void
  * @return void
  * @since 1.0
  */
  private static void listeLivreAudioRangeParAuteur() {
    int[] g;
    Map<String, Integer> distinctAuteur = new Hashtable<>();
    Map<Integer, Integer> listPlayable = new Hashtable<>();

    /* Initialisation de la vairbale global "a" à 0 */
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

    /* Reset de la vairbale global "a" à 0 */
    a = 0;
    distinctAuteur.forEach((k, v) -> {
      println("-> AUTEUR \t Nom : " + k.substring(0, 1).toUpperCase() + k.substring(1) + "\t Num : " + v);
      for (int i = 0; i < listElementMusical.size(); i++) {
        ElementMusical em = listElementMusical.get(i);
        if (em.getIsLivreAudio()) {
          LivreAudio la = (LivreAudio)em;
          String key = la.getAuteur().toLowerCase();
          if (k.equals(key)) {
              println("\t" + (a+1) + " . " + la.getName() + "\t" + la.getAuteur() + "\t" + getLangById(la.getLangues()) +
                      "\t" + getCatById(la.getCategorie()) + "\t" + getDureeMin(la.getDuree()));
              listPlayable.put(a, la.getId());
              a++;
          }
        }
      }
    });

    println("");
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir un livre audio (" + listPlayable.size() + "): ");

    /* Récuperation de la saisie bornée, de maniere persistante */
    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listPlayable.size());

    /* On recupere l'id un entier correspondant a la liste de choix enumeré */
    int id = listPlayable.get(choix - 1);

    LivreAudio la = (LivreAudio)getEmById(id);
    println("Vous avez choisit: " + choix + " \n " + la.getName() + "\t" + la.getAuteur() + "\t" + getLangById(la.getLangues()) +
            "\t" + getCatById(la.getCategorie()) + "\t" + getDureeMin(la.getDuree()));
  }
  /************************* AFFICHAGE DE LISTE FIN ***********************************/


  /************************* EDITION ***********************************/

  /**
  * Ajouter une chanson
  * l'utilisateur saisi les differents attributs d'une chanson puis on l'ajoute
  * @param void
  * @return void
  * @since 1.0
  */
  private static void addChanson() {
    Scanner sc = new Scanner(System.in);
    println("Veuillez entrer le nom de la chanson: ");
    String name = sc.nextLine();

    println("Veuillez entrer l\"artiste: ");
    String artiste = sc.nextLine();

    println("Veuillez choisir le genre: ");
    for (Map.Entry<Integer, String> itm : dicGenre.entrySet()) {
      println((itm.getKey()) + ". " + itm.getValue());
    }

    /* Récuperation de la saisie bornée, de maniere persistante */
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

    /* On créer l'objet et met les valeurs dedans */
    Chanson ch = new Chanson();
    ch.setId(listElementMusical.size());
    ch.setName(name);
    ch.setArtiste(artiste);
    ch.setGenre(genre);
    ch.setDuree(duree);
    ch.setContent(content);

    /* On ajoute l'objet crée à la liste */
    listElementMusical.add(ch);
    println("Chanson ajoute.");
  }

  /**
  * Ajouter un livre audio
  * l'utilisateur saisi les differents attributs d'un livre audio puis on l'ajoute
  * @param void
  * @return void
  * @since 1.0
  */
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

    /* Récuperation de la saisie bornée, de maniere persistante */
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

    /* On créer l'objet et met les valeurs dedans */
    LivreAudio la = new LivreAudio();
    la.setId(listElementMusical.size());
    la.setName(name);
    la.setAuteur(auteur);
    la.setLangues(langue);
    la.setCategorie(cat);
    la.setDuree(duree);
    la.setContent(content);

    /* On ajoute l'objet crée à la liste */
    listElementMusical.add(la);
    println("Livre audio ajoute.");
  }

  /**
  * Ajouter un album
  * l'utilisateur saisi les differents attributs d'un album puis on l'ajoute
  * @param void
  * @return void
  * @since 1.0
  */
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

    /* On créer l'objet et met les valeurs dedans */
    Album a = new Album();
    a.setId(listAlbum.size());
    a.setName(name);
    a.setArtiste(artiste);
    a.setDate(date);
    a.setDuree(duree);

    /* On ajoute l'objet crée à la liste */
    listAlbum.add(a);
    println("Album ajoute.");
  }

  /**
  * Ajouter des chason dans un Album dêja créer
  * l'utilisateur saisi les differents chanson qu'il souhaite ajouter à l'album choisi
  * @param void
  * @return void
  * @since 1.0
  */
  private static void setChansonToAlbum() {
    Scanner sc = new Scanner(System.in);
    println("Veuillez choisir l\"album de destination");
    for (int i = 0; i < listAlbum.size(); i++) {
      println((i+1) + ". " + listAlbum.get(i).getName());
    }
    int choix_album = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix_album = sc.nextInt();
    } while (choix_album < 0 || choix_album > listAlbum.size());

    println("Veuillez choisir la chanson");
    Map<Integer, Integer> listSelectable = new Hashtable<>();

    a = 0; /* Initialisation de notre variable global qui nous sert de compteur */

    /* On créer une liste temporaire de elements choissisable par l'utilisateur */
    for (int i = 0; i < listElementMusical.size(); i++) {
      if (!listElementMusical.get(i).getIsLivreAudio()) {
        Chanson ch = (Chanson)listElementMusical.get(i);
        listSelectable.put(a, ch.getId());
        println((a+1) + ". " + ch.getName() + "\t" + ch.getArtiste() + "\t" + ch.getGenre() + "\t" + getDureeMin(ch.getDuree()));
        a++;
      }
    }

    /* Récuperation de la saisie bornée, de maniere persistante */
    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listSelectable.size());

    int id = listSelectable.get(choix - 1);
    Chanson ch = (Chanson)getEmById(id);

    Album alb = listAlbum.get(choix_album - 1);
    alb.addEM(id);

    listAlbum.set(choix_album - 1, alb);
    println(ch.getName() + " a ete ajoute a l\"album " + alb.getName());
  }

  /**
  * Créer une playlist
  * l'utilisateur créer une playlist puis choisit les differents elements musicaux à ajouté
  * @param void
  * @return void
  * @since 1.0
  */
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
        println("\t" + (i+1) + ". " + ch.getName() + "\t" + ch.getArtiste() + "\t" + ch.getGenre() + "\t" + getDureeMin(ch.getDuree())+ "\t" + "CHANSON");
      } else {
        LivreAudio la = (LivreAudio)listElementMusical.get(i);
        println("\t" + (i+1) + " . " + la.getName() + "\t" + la.getAuteur() + "\t" + getLangById(la.getLangues()) +
                "\t" + getCatById(la.getCategorie()) + "\t" + getDureeMin(la.getDuree()));
      }
    }

    /* Récuperation de la saisie bornée, de maniere persistante */
    String entry = "";
    while (sc.hasNextLine()) {
        entry = sc.nextLine();

        /* Si l'utilisateur sasie "t" on met fin a la sequence d'ajout de elements */
        if (entry.equals("t")) {
          break;
        }

        /* Sinon on verifie que la sasie est bien en entier et qui les compris dans les bornes des choix proposés */
        int s = 0;
        s = Integer.parseInt(entry);
        if (s > 0 && s <= listElementMusical.size()) {
          pl.addEM( listElementMusical.get(s - 1).getId() );
          println(listElementMusical.get(s - 1).getName() + " ajoute.");
        } else {
          println("Erreur entre incorrecte.");
          println("Entrer \"t\" une fois terminer");
        }
    }

    /* On ajoute l'onnjet dans notre liste de playlist */
    listPlaylist.add(pl);
    println("Playlist cree avec succes");
  }


  /**
  * Supprimer une playlist
  * l'utilisateur chosit la playlist à supprimer
  * @param void
  * @return void
  * @since 1.0
  */
  private static void deletePlaylist() {
    Scanner sc = new Scanner(System.in);
    println("Choisir la playlist a supprimer:");

    /* On affiche les playlist un à un */
    for (int i = 0; i < listPlaylist.size() ; i++) {
      Playlist pl = listPlaylist.get(i);
      println((i+1) + ". " + pl.getName() + "\t elements: " + pl.getEM().size() );
    }

    /* Récuperation de la saisie bornée, de maniere persistante */
    int choix = 1;
    do {
        while (!sc.hasNextInt()) sc.next();
        choix = sc.nextInt();
    } while (choix < 0 || choix > listPlaylist.size());

    /* On supprime l'element choisi */
    Playlist pl = listPlaylist.get(choix - 1);
    listPlaylist.remove(choix - 1);

    /* On affiche le nom de l'element supprimer */
    println(pl.getName() + " suprimme");
  }

  /************************* EDITION FIN ***********************************/


  /************************* FACILITATEUR ***********************************/

  /**
  * Afficher les elements musicaux d'une collection
  * Recherche par ID les differents elements musicaux dans notre tableau et les affiche
  * @param Prend parametre un objet de type Collection
  * @return void
  * @since 1.0
  */
  private static void viewCollectionEM(Collection coll) {
    /* On récupere dans listEM les elements musicaux de cette collection en paremetre "coll" */
    ArrayList<Integer> listEM = coll.getEM();

    /* On affiche ligne par ligne en fonction du type d'element musical Livre AUdio ou Chanson */
    for (int i = 0; i < listEM.size(); i++ ) {
      ElementMusical em = getEmById(listEM.get(i));

      /* On reconnais le type grace au boolean isLivreAudio dans l'objet ElementMusical */
      if (em.getIsLivreAudio()) {

         /* On castre donc l'element musical dans un LivreAudio */
        LivreAudio la = (LivreAudio)em;

         /* On affiche */
        println((i+1) +  ". "  + la.getName() + "\t" + la.getAuteur() + "\t" + getLangById(la.getLangues()) +
                         "\t" + getCatById(la.getCategorie()) + "\t" + getDureeMin(la.getDuree()) + "\t LivreAudio");

      } else {
        /* On castre donc l'element musical dans une Chanson */
        Chanson ch = (Chanson)em;

        /* On affiche */
        println((i+1) +  ". "  + ch.getName() + "\t" + ch.getArtiste() + "\t" + ch.getGenre() + "\t" + getDureeMin(ch.getDuree()) + "\t Chanson");

      }

    }
  }

  /**
  * Affiche un format minute:seconde
  * Retourner les secondes au format minute:seconde
  * @param Prend en parametre un entier en secondes
  * @return String
  * @since 1.0
  */
  private static String getDureeMin(int sec) {

    /* On peut egalement elargir le champ de la simplification en affichant h:m:i */
    int min = sec / 60;
    int rSec = (int) ( (((double) (sec / 60.0)) - (double) min) * 60.0 );
    return min + ":" + rSec;
  }

  /**
  * Permet d'obtenir un element musical par son id
  * On parcours la memoire pour trouver le premiere id correspondant à l'id entré en parametre
  * @param Prend en parametre un id entier
  * @return Retourne un ElementMusical
  * @since 1.0
  */
  private static ElementMusical getEmById(int id) {
    /* On crée un objet de type ElementMusical null */
    ElementMusical em = null;

    /* On parcours la liste ligne par ligne pour ainsi trouvé le premier id correspondant à l'id rechercher en parametre de cette methode */
    for (int i = 0; i < listElementMusical.size(); i++) {
      em = listElementMusical.get(i);

      /* Si l'id de l'element est identique a l'id recu en parametre casse la boucle */
      if (em.getId() == id) {
        break; // On arrete de parcourir le tableau
      }
    }

    /* On retourne l'element muscial correspondant à cette id */
    return em;
  }

  /**
  * Obtien le nom de la langue
  * On renvoi la clé correspondant l'id de la langue préchargé en memoire
  * @param Prend en parametre un id entier
  * @return String
  * @since 1.0
  */
  private static String getLangById(int id) {
    return (String)dicLangue.get(id);
  }

  /**
  * Obtien le nom de la catégorie
  * On renvoi la clé correspondant l'id de la catégorie préchargé en memoire
  * @param Prend en parametre un id entier
  * @return String
  * @since 1.0
  */
  private static String getCatById(int id) {
    return (String)dicCat.get(id);
  }


  /**
  * Imprimer du texte à l'ecran
  * On fait sortir sur la console la chaine de caractere reçu en parametre, donc c'une sorti vers l'utilisateur
  * @param Prend en parametre une chaine de caractere String
  * @return void
  * @since 1.0
  */
  public static void println(String str) {
    System.out.println(str);
  }

  /**
  * Lecture des donnée JSON
  * On lit les données JSON enrgistré dans le dossier Datas, puis on les charges en mémoire, dans nos tableaux.
  * @param void
  * @return void
  * @since 1.0
  */
  private static void readData(){
    try
    {
      /* Initialisation des variables global a la classe, pour ainsi stocké les données lu */
      String jsonString = readFile(filePathColl);
      JSONObject objCtn = new JSONObject(jsonString);
      listAlbum = new ArrayList<Album>();
      listPlaylist = new ArrayList<Playlist>();
      listElementMusical = new ArrayList<ElementMusical>();

      /* On commence par lire le jeton nommé "albums" qui est un tableau  */
      JSONArray albumsArr = objCtn.getJSONArray("albums");
      /* On récupere la taille du tableau */
      int length = albumsArr.length();
      /* On affiche à l'utilisateur le nombre d'album trouvé */
      System.out.println( "albums: " + length );

      /* On parcours le JSONArray nommé albumArr, pour ainsi en extraire des JSONObject corrspondant a des ligne */
      for(int i = 0; i < length; i++) {
          /* On récupere JSONobject */
          JSONObject itm = albumsArr.getJSONObject(i);

          /* On créer un objet de type ablbum */
          Album obj = new Album();

          /* On redefini ses valeurs */
          obj.setId(itm.getInt("id"));
          obj.setName(itm.getString("name"));
          obj.setArtiste(itm.getString("artiste"));
          obj.setDuree(itm.optInt("duree"));
          obj.setDate(itm.optInt("date"));

          /* Cette ligne de JSONObject contient elle même un JSONArray correspondant au id des elements musicaux */
          JSONArray chansonArr = itm.optJSONArray("em");
          /* On recupere la liste des elements contenu dedans  */
          int lengthChanson = chansonArr.length();

          /* On parcours le tableau un à un et on les ajoute à notre objet Album */
          for (int a = 0; a < lengthChanson; a++) {
              obj.addEM( chansonArr.optInt(a) );
          }

          /* On ajoute cet objet Album au tableau */
          listAlbum.add(obj);
      }

      /* On recommence l'operation avec les playlists */
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
          }
          obj.addEM( chansonArr.optInt(a) );

          listPlaylist.add(obj);
      }

      jsonString = readFile(filePathEM);
      objCtn = new JSONObject(jsonString);

      /* Chargement en memoire dans chanson */
      JSONArray chansonArr = objCtn.getJSONArray("chanson");
      length = chansonArr.length();
      System.out.println( "Chanson: " + length );

      for(int i = 0; i < length; i++) {
          JSONObject itm = chansonArr.getJSONObject(i);

          Chanson obj = new Chanson();
          obj.setId(itm.getInt("id"));
          obj.setName(itm.getString("name"));
          obj.setArtiste(itm.getString("artiste"));
          obj.setGenre(itm.getInt("genre"));
          obj.setContent(itm.getString("content"));
          obj.setDuree(itm.optInt("duree"));

          /* On sait que ce sont tous des Chanson donc false */
          obj.setIsLivreAudio(false);

          listElementMusical.add(obj);
      }

      JSONArray livreaudioArr = objCtn.getJSONArray("livreaudio");
      length = livreaudioArr.length();
      System.out.println( "Livre Audio: " + length );

      for(int i = 0; i < length; i++) {
          JSONObject itm = livreaudioArr.getJSONObject(i);

          LivreAudio obj = new LivreAudio();
          obj.setId(itm.getInt("id"));
          obj.setName(itm.getString("name"));
          obj.setAuteur(itm.getString("auteur"));
          obj.setLangues(itm.getInt("langues"));
          obj.setCategorie(itm.getInt("categorie"));
          obj.setContent(itm.getString("content"));
          obj.setDuree(itm.optInt("duree"));

          /* On sait que ce sont tous des livre audio donc true */
          obj.setIsLivreAudio(true);

          listElementMusical.add(obj);
      }
    }
    catch (IOException e1) { /* Exception de R/W de fichier */
      e1.printStackTrace();
      println("Erreur: " + e1.getMessage());
    } catch (JSONException e2) { /* Exception de type json */
      e2.printStackTrace();
      println("Une erreur JSON attrapee : " + e2.getMessage());
    } catch (Exception e3){
      println("Erreur:" + e3.getMessage());
    }
  }

  /**
  * Lecture d'un fichier
  * On lit les données enrgistré dans un fichier
  * @param String chemin du fichier
  * @return String contenu du fichier
  * @throws Retourne une exception de type IOException
  * @since 1.0
  */
  private static String readFile(String file) throws IOException { // On renvoie l'ex
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


  /**
  * Sauvegarde des données
  * On sauvegarde les données manipuler en mémoire, dans les fichiers correspondant
  * @param void
  * @return void
  * @since 1.0
  */
  private static void saveData(){
    try {
      /* On créer un objet JSONObject qui va contenir tous nos tableau */
      JSONObject coll = new JSONObject();

      /* Passe d'objet Album à JSONObject */
      JSONArray albums = new JSONArray();

      /* On parcours le tableau de la liste de nos album */
      for (int i = 0; i < listAlbum.size(); i++) {
        /* On créer notre JSONObject pour tranferer les donnée Album dans JSONObject */
        JSONObject obj = new JSONObject();

        /* On recupere l'objet Album */
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

        /* On insere l'objet JSONObject dans le tableau JSONArray */
        albums.put(i, obj);
      }

      /* On insere notre tableau JSONArray dans JSONObject mére */
      coll.put("albums", albums);

      /* On recommence la procedure pour les playlist */
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

      /* Etant donnée que Album et Playlist on à chosi de les stocké dans le meme fichier JSON donc ecri dans ce fichier  */
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

          /* Incrémention du compteur nombre chanson pour ainsi en informer l'utilisateur */
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

      /* Ecriture du JSON dans le fichier corrspondant */
      file = new FileWriter(filePathEM);
      file.write(emObj.toString());

      System.out.println("Successfully Copied JSON Object to File...");
      System.out.println("\nJSON Object: " + coll);

    } catch (IOException e) {

        e.printStackTrace();

    } catch (JSONException e) {

      e.printStackTrace();
      println("Erreur JSON : " + e.getMessage());

    } finally {

        try {

            file.flush();
            file.close();

        } catch (IOException e) {

            e.printStackTrace();
            println("Erreur R/W Fichier : " + e.getMessage());

        }

    }
  }

  /**
  * Chargement des données static
  * On précharge les données tel quel les langues, catégories, genres.
  * @param void
  * @return void
  * @since 1.0
  */
  private static void loadData() {
    /* Insertion des genre dans le tableau */
    dicGenre.put(1, "Jazz");
    dicGenre.put(2, "Hip-Hop");
    dicGenre.put(3, "Rock");
    dicGenre.put(4, "Pop");
    dicGenre.put(5, "Rap");
    dicGenre.put(6, "Classique");

    /* Insertion des genre dans le categorie */
    dicCat.put(1, "Jeunesse");
    dicCat.put(2, "Roman");
    dicCat.put(3, "Theatre");
    dicCat.put(4, "Discours");
    dicCat.put(5, "Documetaire");

    /* Insertion des langues */
    dicLangue.put(1, "Francais");
    dicLangue.put(2, "Anglais");
    dicLangue.put(3, "Italien");
    dicLangue.put(4, "Espagnol");
    dicLangue.put(5, "Allemand");
  }
}
