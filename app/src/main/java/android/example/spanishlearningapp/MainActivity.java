package android.example.spanishlearningapp;


import android.content.Intent;
import android.example.spanishlanguageappforbeginners.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This code statment pretty much says to attatch the respective activity to this java code file
        setContentView(R.layout.activity_main);


        //Assign the TextView objects of the layout file "activity_main" to a variable
        TextView numbersTextView = findViewById(R.id.numbers);

        TextView familyTextView = findViewById(R.id.family);

        TextView colorsTextView = findViewById(R.id.colors);

        TextView  phrasesTextView = findViewById(R.id.phrases);

        TextView animalsTextView = findViewById(R.id.animals);


        //Attach an onclick event listeners to all of the TextView Objects(Which represent categories in the main screen of the application)
        //In doing so also add the action to be taken, when the event listener detects an a click input event
        numbersTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }
        });

        familyTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyIntent);
            }
        });

        colorsTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(colorsIntent);
            }
        });

        phrasesTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesIntent);
            }
        });

        animalsTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent animalsIntent = new Intent(MainActivity.this, AnimalsActivity.class);
                startActivity(animalsIntent);
            }
        });

    }


}
