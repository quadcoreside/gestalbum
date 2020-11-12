import org.json.*;

class Main extends Parent implements Interface {


  public static void main(String[] args) {
    readData();



    /* Commande Exec */


    saveData();
  }

  private static void readData(){
    String fld = "Datas/";
    String jsonString = Files.readString(fld + "Collections.json"); //assign your JSON String here
    JSONObject obj = new JSONObject(jsonString);

    /* Chargement en memoire */

    String fld = "Datas/";
    String jsonString = Files.readString(fld + "ElementsMusicaux.json"); //assign your JSON String here
    JSONObject obj = new JSONObject(jsonString);

    /* Chargement en memoire */


  }

  private static saveData() {
    
  }

}
