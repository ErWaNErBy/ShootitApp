package tuto.fr.shootitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

//Activité qui affiche l'écran de chargement lors du démarrage du jeu
public class StartingActivity extends AppCompatActivity {

    //Méthode onCreate appellée lors de la création de l'activité par le système
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);   // | retire la barre de notification android pour que le jeu soit en plein écran
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);                                                                 // |
        setContentView(R.layout.activity_starting); //Défini le contenu de l'activité à partir d'une ressource de mise en page

        //Timer qui a bout de 5s lance l'activité de l'écran d'acceuil
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try { sleep(5000); }
                catch (Exception e) { e.printStackTrace(); }
                finally {
                    Intent mainIntent = new Intent(StartingActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        thread.start();
    }
    //ferme cette activité une fois le temps écouler
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
