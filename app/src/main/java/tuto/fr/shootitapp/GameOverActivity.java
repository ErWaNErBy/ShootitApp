package tuto.fr.shootitapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//Activité qui affiche l'écran de fin du jeu
public class GameOverActivity extends AppCompatActivity {


    private Button StartGameAgain,MainMenu;              //Déclaration des boutons de rejouer, retour à l'acceuil
    private TextView DisplayScore, DisplayHighScore;     //Déclaration des textes du score et du meilleur score


    //Méthode onCreate appellée lors de la création de l'activité par le système
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);   // | retire la barre de notification android pour que le jeu soit en plein écran
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);                                                                 // |
        setContentView(R.layout.activity_game_over); //Défini le contenu de l'activité à partir d'une ressource de mise en page

        //Trouver les vue par leurs identifants dans les ressources
        StartGameAgain = findViewById(R.id.play_again_btn);
        MainMenu = findViewById(R.id.main_menu);
        DisplayScore = findViewById(R.id.displayScore);
        DisplayHighScore = findViewById(R.id.DisplayHighScore);

        //clic sur le bouton pour relancer l'activité du jeu pour rejouer
        StartGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });

        //clic sur le bouton pour fermer l'activité et venir directement sur l'acceuil
        MainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Récupèrer le score que le  joueur à réalisé et l'affiche
        final int score = getIntent().getIntExtra("SCORE",0);
        final String StrScore = ""+score;
        DisplayScore.setText(this.getString(R.string.score) +StrScore);

        //Récupère les données de préférences pour afficher le meilleur score ( si il n'y en a pas lavaleur par défaut est 0 )
        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        String highScore = settings.getString("HIGH_SCORE","0");

        //Si le score est supérieur au meilleur score on modifie le meilleur score dans les données de préférences puis on affiche le nouveau meilleur score
        if (score > Integer.valueOf(highScore)){
            DisplayHighScore.setText(this.getString(R.string.highscore) + score);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("HIGH_SCORE", StrScore);
            editor.commit();

            //Sinon on affiche le meilleurs score présent dans les données de préférences
        } else DisplayHighScore.setText(this.getString(R.string.highscore) + highScore);

        //Création d'une boite de dialogue pour enregistrer le nom du joueur
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(GameOverActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_save, null);

        //Trouver les vue de la boite de dialogue par leurs identifants dans les ressources de la boite de dialogue
        TextView displayScore = mView.findViewById(R.id.displayScore);
        Button btnAdd = mView.findViewById(R.id.btnAdd);
        final TextView text = mView.findViewById(R.id.text);
        final EditText nameZone = mView.findViewById(R.id.editText);

        mBuilder.setView(mView);    //liaison de la boite de dialogue et de la vue
        final AlertDialog dialog= mBuilder.create();    //création de la boite de dialogue

        dialog.setCancelable(false);             //désactivation de la fermeture dela boite de dialogue en appuyant sur la touche retour du téléphone
        dialog.setCanceledOnTouchOutside(false); //désactivation de la fermeture dela boite de dialogue en appuyant en dehors dela zone
        dialog.show();  //affichage de la boite de dialogue

        //affichage du score dans la boite de dialogue
        displayScore.setText(this.getString(R.string.score) + StrScore);

        //clic sur le bouton pour valider la saisie du nom d'utilisateur
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //si la zone de texte n'est pas vide
                if(!nameZone.getText().toString().isEmpty()){
                    //Enregistrment du nom et du score dans les données de préférences
                    SharedPreferences setting = getApplicationContext().getSharedPreferences("PREFS",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editors = setting.edit();
                    editors.putString(nameZone.getText().toString(), StrScore);
                    editors.commit();

                    dialog.dismiss();   //fermeture de la boite de dialogue

                    Toast.makeText(GameOverActivity.this, R.string.reg_succes, Toast.LENGTH_SHORT).show();  //message pop-up pour dire que l'enregistrement est fait
                } else {
                    Toast.makeText(GameOverActivity.this, R.string.reg_empty , Toast.LENGTH_SHORT).show();//message pop-up pour dire que le joueur doit écrire son nom dans la zone de texte
                }
            }
        });
    }
}
