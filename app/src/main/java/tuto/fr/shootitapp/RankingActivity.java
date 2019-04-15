package tuto.fr.shootitapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//Activité qui affiche les classement des joueurs
public class RankingActivity extends AppCompatActivity {

    private ListView resultsListView;   //déclaration de la liste de la vue
    private final List<HashMap<String,String>> listItems = new ArrayList<>();   //déclaration de la liste des items
    private SimpleAdapter adapter;  //déclaration de l'adapteur
    private HashMap<String,String> listOnView = new HashMap<>(); //déclaration du dictionnaire pour récupérer les valeurs dans les données de préférences

    //Méthode onCreate appellée lors de la création de l'activité par le système
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);   // | retire la barre de notification android pour que le jeu soit en plein écran
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);                                                                 // |
        setContentView(R.layout.activity_ranking); //Défini le contenu de l'activité à partir d'une ressource de mise en page

        //Trouver la vue par son identifant dans les ressources
        resultsListView = findViewById(R.id.listView);

        //Récupération du nom et du score des joueurs dans les données de préférences
        SharedPreferences setting = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        final Map<String, ?> getList = setting.getAll();

        //stockage des données dans une liste pour traiter par la suite
        for (Map.Entry<String,?> entry : getList.entrySet()){
            entry.getKey();
            entry.getValue();
            listOnView.put(entry.getKey(),String.valueOf(entry.getValue()));
        }

        //création de l'adapteur pour mapper les données statiques aux vues définies dans le fichier XML
        adapter = new SimpleAdapter(this,listItems,R.layout.activity_list_item,
                new String[]{"Name","Score"},
                new int[]{R.id.text1,R.id.text2});

        //stockage du nom et du score des joueurs dans la liste dela vue
        Iterator it = listOnView.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String,String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("Name", pair.getKey().toString());
            resultsMap.put("Score",pair.getValue().toString());
            listItems.add(resultsMap);
        }
        resultsListView.setAdapter(adapter);
    }
}