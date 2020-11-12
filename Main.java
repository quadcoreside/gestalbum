class Main extends Parent implements Interface {
  public static void main(String[] args) {
    System.out.println( "Welcom" );
  }


}

/*

Commande Main d'affichage:
- « lc » : les chansons d’un album

    Afficher la liste des album
    Taper N° Album
    Liste de chansons

- « ld » : les titres des albums, rangés par date de sortie

    Liste de tout le json trié par date

- « lg » : les titres des albums, rangés par genre

    Liste de tout le json trié par genre

- « lp » : les playlists, rangées par nom

    Liste de tout le json playlist trié par nom

- « ll » : les livres audio rangés par auteur

    Liste de tout le json playlist trié par auteur

Commande Main d'écriture:
- « c » : rajout d’une nouvelle chanson
- « a » : rajout d’un nouvel album
- « + » : rajout d’une chanson existante à un album
- « l » : rajout d’un nouveau livre audio
- « p » : création d’une nouvelle playlist à partir de chansons et livres audio existants
- « - » : suppression d’une playlist
- « s » : sauvegarde des playlists, des albums, des chansons et des livres audios dans les fichiers concernés.
- « h » : aide avec les détails des commandes précédentes.

Collection {
  ID index
  Name

  Album -> Titre, Artiste, Durée, date de sortie, Chanson(index)
  Playlist ->  Elementmusical(index)
}

ElementMusical {
  ID index
  Name
  Durée
  Contenu chemin du fichier
  Chanson -> Artiste, Genre(index)
  LivreAudio ->  Auteur, Langues(index), Catégorie(index)
}



TabGenre{1 = Jazz,2 = Hip-Hop,3 = Rock, 4 = Pop, 5 = Rap}

Catégorie{1 = Jeunesse, 2 = Roman,3 = Théatre,4 = Discours,5 = Documetaire}

Langues{1 = Francais,2 = Anglais,3 = Italien,4 = Espagnol,5 = Allemand}




{
"Collection": {
  "Name" : "file"
  "album": {,
  "name": "",
    "name": "",
  },
  "playlist": [
    {"value": "New", "onclick": "CreateNewDoc()"},
    {"value": "Open", "onclick": "OpenDoc()"},
    {"value": "Close", "onclick": "CloseDoc()"}
  ]
}


{
"ElementMusical": {
  "Name" : "file"
  "Duree" : "file"
}
  { "nom" }
}


*/
