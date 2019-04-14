package tuto.fr.shootitapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

//Activité qui affiche les crédits
public class CreditActivity extends AppCompatActivity {

    //Méthode onCreate appellée lors de la création de l'activité par le système
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);   // | retire la barre de notification android pour que le jeu soit en plein écran
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);                                                                 // |
        setContentView(R.layout.activity_credit); //Défini le contenu de l'activité à partir d'une ressource de mise en page
    }
}
