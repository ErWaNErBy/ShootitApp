package tuto.fr.shootitapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

//Activité qui affiche l'écran en jeu
public class GameActivity extends AppCompatActivity {

    private ImageButton[] imageButtons = new ImageButton[21];
    private TextView scoreView, countDownText;
    private int score=0;

    private long[] buttonState = new long[21];
    private ArrayList<String> buttonID= new ArrayList<>();

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000; //1min
    private ImageButton countDownButton;
    private boolean timerRunning;
    private MediaPlayer mp;

    //Méthode onCreate appellée lors de la création de l'activité par le système
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);   // | retire la barre de notification android pour que le jeu soit en plein écran
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);                                                                 // |
        setContentView(R.layout.activity_game); //Défini le contenu de l'activité à partir d'une ressource de mise en page

        //Trouver les vue par leurs identifants dans les ressources
        scoreView = findViewById(R.id.scoreView);
        countDownText = findViewById(R.id.time);
        countDownButton = findViewById(R.id.pauseBtn);

        //clic sur le bouton pour démarrer le démarrer ou stopper le jeu et lancer le décompte du timer
        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        mp = MediaPlayer.create(this, R.raw.ouille);
    }

    //fonction pour la création des fenêtres interactives
    public void newFenetres(){
        for (int i = 0; i <imageButtons.length ; i++) {

            String imageButtonID = "fen"+(i+1);
            int resID = getResources().getIdentifier(imageButtonID,"id",getPackageName());
            imageButtons[i] = findViewById(resID);
            final int[] img = new int[]{R.drawable.fenetre_vide,R.drawable.fenetre_voleur,R.drawable.fenetre_resident};
            final Random rand = new Random();

            final int randomImgFen = rand.nextInt(img.length);

            imageButtons[i].setImageDrawable(getResources().getDrawable(img[randomImgFen]));
            imageButtons[i].setSoundEffectsEnabled(false);
            imageButtons[i].getBackground();

            buttonState[i]= randomImgFen;
            buttonID.add(i, "" + imageButtons[i].getId());

            final int finalI = i;
            imageButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(timerRunning) {
                        addScore(v.getId() , finalI);
                    }
                }
            });
        }
    }

    //fonction pour identifier l'état de chaque bouton
    public void addScore(long id, int i){
        //Cet état représente le bouton quand il y a un voleur
        // --- Si l'état bouton correspond bien à un bouton d'un voleur
        if(buttonState[buttonID.indexOf(""+id)]==1){
            mp.start();
            scoreView.setText(this.getString(R.string.score) + ++score);    //incrémente le score de 1
            imageButtons[i].setImageDrawable(getResources().getDrawable(R.drawable.fenetre_vide));  //change le bouton fenêtre voleur en bouton fenêtre vide
            buttonState[buttonID.indexOf(""+id)]=0; //change son état à 0 ( correspond à une fenêtre vide )

        //Cet état représente le bouton quand il y a un habitant
        // --- Si l'état bouton correspond bien à un bouton d'un habitant
        } else if(buttonState[buttonID.indexOf(""+id)]==2) {
            mp.start();
            int scoreMinus5 = score  -= 5;
            scoreView.setText(this.getString(R.string.score) + scoreMinus5);    //décrément lescore de 5
            imageButtons[i].setImageDrawable(getResources().getDrawable(R.drawable.fenetre_vide));  //change le bouton fenêtre habitant en bouton fenêtre vide
            buttonState[buttonID.indexOf(""+id)]=0; //change son état à 0 ( correspond à une fenêtre vide )
        }
    }

    //fonction qui lance ou qui met sur pause le jeu ( fonction utiliser par un bouton )
    public void startStop(){
        //si à l'appui le temps est en cours d'écoulement on exécute la fonction pour stopper le timer et on remplace le bouton pause par le bouton play
        if(timerRunning){
            stopTimer();
            countDownButton.setImageDrawable(getResources().getDrawable(R.drawable.play));
        }
        //si à l'appui le temps est en en pause on exécute la fonction pour lancer le timer et on remplace le bouton play par le bouton pause
        else {
            startTimer();
            countDownButton.setImageDrawable(getResources().getDrawable(R.drawable.pause));
        }
    }

    //fonction du timer en marche
    public void startTimer(){
        //création du temps qui sécoule chaque 1s
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();  //chaque 1s on lance la fonction qui gère la mise à jour du timer
            }
            @Override
            public void onFinish() {}
        }.start();
        timerRunning = true;
    }

    //fonction du timer en pause
    public void stopTimer(){
        countDownTimer.cancel();    //stop le temps
        timerRunning = false;       //passele booléen qui gère le bouton à faux
    }

    //fonction de mise à jour du timer et de son affichage
    public void updateTimer(){
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        if(seconds%2==0) newFenetres(); //toutes les 2s les fenêtres sont mise à jour afin d'afficher de nouveaux boutons

        //Création et affichage du timer
        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds<10) timeLeftText += 0;
        timeLeftText += seconds;
        countDownText.setText(timeLeftText);

        //si le temps est fini
        if(minutes==0 && seconds==0){
            //message pop-up pour afficher la fin du jeu
            Toast.makeText(GameActivity.this, this.getString(R.string.gameover), Toast.LENGTH_SHORT).show();

            //passage a l'activité de fin du jeu, envoi du score à cette même activité et fermeture de cette activité
            Intent GameOverIntent = new Intent(getApplicationContext(), GameOverActivity.class);
            GameOverIntent.putExtra("SCORE",score);
            GameActivity.this.startActivity(GameOverIntent);
            finish();
        }
    }
}
