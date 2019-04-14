package tuto.fr.shootitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

//Activité d'écran d'acceuil du Jeu
public class MainActivity extends AppCompatActivity {

    private Button game,ranking,credit;     //Déclaration des boutons du lancement de jeu, affichage classement et affichage des crédits

    //Méthode onCreate appellée lors de la création de l'activité par le système
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // | retire la barre de notification android pour que le jeu soit en plein écran
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);                                                               // |
        setContentView(R.layout.activity_main); //Défini le contenu de l'activité à partir d'une ressource de mise en page

        //Trouver les vue par leurs identifants dans les ressources
        this.game = findViewById(R.id.main_start);
        this.ranking = findViewById(R.id.main_ranking);
        this.credit = findViewById(R.id.main_credit);

        //clic sur le bouton pour ouvrir l'activité du jeu
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(),GameActivity.class);
                startActivity(otherActivity);
            }
        });

        //clic sur le bouton pour ouvrir l'activité du classement
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(),RankingActivity.class);
                startActivity(otherActivity);
            }
        });

        //clic sur le bouton pour ouvrir l'activité des crédits
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(),CreditActivity.class);
                startActivity(otherActivity);
            }
        });
    }
}