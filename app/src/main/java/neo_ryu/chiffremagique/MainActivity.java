package neo_ryu.chiffremagique;

// LIBRAIRIES
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;


public class MainActivity extends AppCompatActivity {
    // Variables GUI
    private TextView txtTitle, txtDesc, txtEssai, txtIndice, txtRepTitle, txtRepList;
    private EditText saisieNbr;
    private ImageButton imgButton;
    private ImageView hotGirl;
    private Button btTryAgain;
    private Spinner spinner;

    // Variables d'Environnement
    static int nbrAleatoire = 0;
    static int nMax = 100;
    static int reponse = 0;
    static int nbrEssais = 5;
    static int numEssai = 1;
    static int victory = 0;
    static int victMax = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //DECLARATION DES OBJETS DE LA PALETTES
            txtTitle = (TextView) findViewById(R.id.txt_Title);
            txtDesc = (TextView) findViewById(R.id.txt_Desc);
            txtEssai = (TextView) findViewById(R.id.txt_Essais);
            txtIndice = (TextView) findViewById(R.id.txt_Indices);
            txtRepTitle = (TextView) findViewById(R.id.txt_RepTitle);
            txtRepList = (TextView) findViewById(R.id.txt_RepList);
            saisieNbr = (EditText) findViewById(R.id.input_Saisie);
            hotGirl = (ImageView) findViewById(R.id.img_Girl);
            imgButton = (ImageButton) findViewById(R.id.img_Button);
            btTryAgain = (Button) findViewById(R.id.bt_TryAgain);
            spinner = (Spinner) findViewById(R.id.choixGirl);

            // Appel des méthodes
            config_Spinner();   // Configuration du Spinner
            thread_onClick();   // Permet l'ecoute des clics
            thread_Spinner();   // Permet l'ecoute d'un changement d'index sur un Spinner
            begin();

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            Log.w("TAG", "ERROR : "+e.toString());
        }
    }

    public void thread_onClick()
    {
        Log.w("TAG", "Ecoute des clics (button / imgbtn)");

        // Ecoute des clics pour la classe MainActivity
        View.OnClickListener ThreadOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Switch conditionnel selon le nom du bouton (View v)
                    if (v == imgButton) {
                        // Si l'image bouton OK est cliqué, on verifie la réponse avec la zone de saisie
                        if (victory <= victMax) {
                            checkNbr();
                            Log.w("TAG", "CONTINUE");
                        } else {
                            txtIndice.setText("Tu ne veux pas me rejoindre plutôt... ?");
                            Log.w("TAG", "VICTORY");
                        }
                    }
                    if (v == btTryAgain) {
                        // Si c'est le bouton pour rejouer qui ets cliqué, on regénère un nombre
                        begin();
                        Log.w("TAG", "TRY AGAIN");
                    }

                    if (v == txtEssai){
                        nbrAleatoire = 69;
                        Toast.makeText(MainActivity.this, "CHEAT CODE ACTIVE !\n 'Une histoire de tête à queue...'", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Le nombre doit etre compris entre 1 et 100 mon chou... !", Toast.LENGTH_LONG).show();
                    Log.w("TAG", "ERROR : "+e.toString());
                }
            }
        };
        imgButton.setOnClickListener(ThreadOnClick);    // VALIDER SAISIE
        btTryAgain.setOnClickListener(ThreadOnClick);   // RECOMMENCER
        txtEssai.setOnClickListener(ThreadOnClick);     // CHEAT CODE
    }

    public void thread_Spinner() {
        Log.w("TAG", "Ecoute du change select (spinner)");

        // Ecoute lors du changement d el'element selectionné dans le spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    int index = spinner.getSelectedItemPosition();
                    MyGirl();
                    victory = 0;
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Hum...", Toast.LENGTH_LONG).show();
                    Log.w("TAG", "ERROR : "+e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                try {
                    MyGirl();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Hum...", Toast.LENGTH_LONG).show();
                    Log.w("TAG", "ERROR : "+e.toString());
                }
            }
        });
    }

    public void config_Spinner() {
        Log.w("TAG", "CONFIG SPINNER");

        //Création d'une liste d'éléments à mettre dans le Spinner(pour l'exemple)
        List girlChoice = new ArrayList();
            girlChoice.add("Blonde");
            girlChoice.add("Brune");
            girlChoice.add("Rousse");
            girlChoice.add("Asiat");
            girlChoice.add("Ebony");

        //Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
        //un fichier de presentation par défaut avec la liste des elements (girlChoice)
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, girlChoice);

        //On definit une présentation du spinner quand il est déroulé
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(adapter);
    }

    public void begin()
    {
        Log.w("TAG", "BEGIN");
        try {
            // Initialisation d'une nouvelle partie
            MyGirl();       // On vérifie quelle fille est selectionnée
            generateNbr();  // On génère un nouveau chiffre aleatoire

            btTryAgain.setVisibility(View.GONE);    // On cache le bouton TRY AGAIN
            imgButton.setEnabled(true);
            numEssai = 1;
            txtEssai.setText("" + (nbrEssais - numEssai));
            saisieNbr.setText("");
            txtRepList.setText("");
            txtIndice.setText("J'ai chaud...");
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Encore !", Toast.LENGTH_LONG).show();
            Log.w("TAG", "ERROR : "+e.toString());
        }
    }

    // CREATION D'UN NOMBRE ALEATOIRE ENTRE 1 ET nMax
    public static void generateNbr() {
        Log.w("TAG", "GENERATE NUMBER");
        nbrAleatoire = (int) (Math.random() * nMax + 1);
    }

    // VERIFICATION DE LA SAISIE AVEC LE NOMBRE A TROUVER
    public void checkNbr()
    {
        Log.w("TAG", "CHECK INPUT");
        try {
            reponse = Integer.parseInt(saisieNbr.getText().toString());

            Log.v("TAG", Integer.toString(numEssai));
            if (reponse >= 1 && reponse <= nMax) {
                numEssai++;
                if (reponse == nbrAleatoire) {
                    victory++;
                    MyGirl();
                    txtRepList.setText(txtRepList.getText() + "\n" + reponse + " <3 !");
                    generateNbr();
                    numEssai = 1;
                    txtRepList.setText("");
                } else {
                    if (reponse < nbrAleatoire) {
                        txtIndice.setText(reponse + " est plus petit que le nombre à trouver...");
                        txtRepList.setText(txtRepList.getText() + "\n" + reponse + " < ?");
                    } else {
                        txtIndice.setText(reponse + " est plus grand que le nombre à trouver...");
                        txtRepList.setText(txtRepList.getText() + "\n" + reponse + " > ?");
                    }

                    if (numEssai == nbrEssais) {
                        txtIndice.setText("Dommage... La réponse était : " + nbrAleatoire);
                    }
                    if (numEssai >= nbrEssais) {
                        if (victory <= victMax) {
                            imgButton.setEnabled(false);
                            btTryAgain.setVisibility(View.VISIBLE); // On affiche le bouton TRY AGAIN
                        } else {
                            imgButton.setEnabled(false);            //
                        }
                    }
                }
            } else {
                txtIndice.setText("Le nombre doit etre compris entre 1 et 100 mon chou... !");
            }
            txtEssai.setText("" + (nbrEssais - numEssai));
            saisieNbr.setText("");
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Le nombre doit etre compris entre 1 et 100 mon chou... !", Toast.LENGTH_LONG).show();
            Log.w("TAG", "ERROR : "+e.toString());
        }
    }

    // C'est ici que sont géré les bibliothèque de Girl ;
    // une amélioration notable serait d'ajouter un fichier XML
    // qui les stockerai et qui serait lu automatiquement.
    public void MyGirl()
    {
        Log.w("TAG", "CHECK GIRL...");
        try {
            int index = spinner.getSelectedItemPosition();
            switch(spinner.getAdapter().getItem(index).toString().toUpperCase()) {
                case "BLONDE":
                    victMax = 3;
                    txtRepList.setText("");
                    switch (victory) {
                        case 1:
                            txtIndice.setText("Hum, bravo !");
                            hotGirl.setImageResource(R.drawable.babe2);
                            break;
                        case 2:
                            txtIndice.setText("Bien, bien...");
                            hotGirl.setImageResource(R.drawable.babe1);
                            break;
                        case 3:
                            txtIndice.setText("Tu aimes ?");
                            hotGirl.setImageResource(R.drawable.babe3);
                            break;
                        case 4:
                            txtIndice.setText("Tu viens... ?");
                            hotGirl.setImageResource(R.drawable.babe4);
                            break;
                        default:
                            txtIndice.setText("Prêt... ?");
                            hotGirl.setImageResource(R.drawable.babe0);
                            break;
                    }
                    Log.w("TAG", "BLONDE : "+victory);
                    break;

                case "BRUNE":
                    victMax = 1;
                    txtRepList.setText("");
                    switch (victory) {
                        case 1:
                            txtIndice.setText("Oups, mon soutif est tombé...");
                            hotGirl.setImageResource(R.drawable.brune1);
                            break;
                        case 2:
                            txtIndice.setText("Quelque chose est tombé par terre...");
                            hotGirl.setImageResource(R.drawable.brune2);
                            break;
                        default:
                            txtIndice.setText("Où sont mes vêtements... ?");
                            hotGirl.setImageResource(R.drawable.brune0);
                            break;
                    }
                    Log.w("TAG", "BRUNE : "+victory);
                    break;

                default:
                    victMax = 0;
                    txtIndice.setText("Oups... Il n'y a pas encore de modèle pour cette catégorie.");
                    hotGirl.setImageResource(R.drawable.hentai);
                    txtRepList.setText("Demande à ta soeur, ta petite amie, ou une simple amie, je suis quasi sûr qu'elles seront d'accord ! <3");
                    Log.w("TAG", "OTHER : "+victory);
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "C'est ton genre ?", Toast.LENGTH_LONG).show();
            Log.w("TAG", "ERROR : "+e.toString());
        }
    }
}