package android.example.spanishlearningapp;

import android.content.Context;
import android.example.spanishlanguageappforbeginners.R;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class AnimalsActivity extends AppCompatActivity {

    //Declare a local variable that will be used to store the audio of the respective object
    private MediaPlayer mAnimalsAudio;

    //Declare an instance that will be used to request and abandon AudioFocus
    private AudioManager mAudioManager;

    //Declare listener to be used within the respective Listener
    //When an audio completes this Listener's callback method is to be executed
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            //call the method that will release the resources stored in the MediaPlayer Object and set its value null
            releaseMediaPlayer();
        }
    };

    //Create am instance of the OnAudioFocusChangeListener
    //This Listener's callback method is to be executed when a state change concerning the AudioFocus is detected
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //Pause playback
                mAnimalsAudio.pause();
                mAnimalsAudio.seekTo(0); //Tells the application to start from the beginning of the audio, since the audio paused is a short audio
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //Resume playback
                mAnimalsAudio.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //stop playback
                releaseMediaPlayer();
            }
        }
    };

    //Create an AudioAttributes instance which will be used as part of the process of creating an AudioFocusRequest instance
    private AudioAttributes mAudioAttributes = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build();

    //Create an AudioFocusRequest type instance which will be used to request and abondon the AudioFocus at the appropriate time
    private AudioFocusRequest mAudioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
            .setAudioAttributes(mAudioAttributes)
            .setAcceptsDelayedFocusGain(true)
            .setOnAudioFocusChangeListener(mOnAudioFocusChangeListener)
            .build();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);


        //Initialize the AudioManager instance
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        //Create a String type ArrayList that has with the appropriate elements
        //Also, declare this ArrayList as a constant sot that is able to be reference in the OnItemClickListener's callback method
        final ArrayList<Word> animals = new ArrayList<Word>();

        //Add the appropriate elements into the appropriate ArrayList
        animals.add(new Word("dog", "perra(fem) / perro (masc)", R.drawable.animals_dog, R.raw.animals_dog));
        animals.add(new Word("cat", "gato", R.drawable.animals_cat, R.raw.animals_cat));
        animals.add(new Word("mouse", "ratón", R.drawable.animals_mouse, R.raw.animals_mouse));
        animals.add(new Word("rabbit", "coneja(fem) / conejo(masc)", R.drawable.animals_rabbit, R.raw.animals_rabbit));
        animals.add(new Word("turtle", "tortuga", R.drawable.animals_turtle, R.raw.animals_turtle));
        animals.add(new Word("bird", "pájaro", R.drawable.animals_bird, R.raw.animals_bird));
        animals.add(new Word("rooster", "gallo", R.drawable.animals_rooster, R.raw.animals_rooster));
        animals.add(new Word("pig", "cerda(fem) / cerdo(masc)", R.drawable.animals_pig, R.raw.animals_pig));
        animals.add(new Word("cow", "vaca", R.drawable.animals_cow, R.raw.animals_cow));
        animals.add(new Word("fish", "pez", R.drawable.animals_fish, R.raw.animals_fish));
        animals.add(new Word("snake", "serpiente", R.drawable.animals_snake, R.raw.animals_snake));
        animals.add(new Word("goat", "cabra(fem) / cabro(masc)", R.drawable.animals_goat, R.raw.animals_goat));
        animals.add(new Word("lamb", "cordera(fem) / cordero(masc)", R.drawable.animals_lamb, R.raw.animals_lamb));
        animals.add(new Word("monkey", "mona(fem) / mono(masc)", R.drawable.animals_monkey, R.raw.animals_monkey));
        animals.add(new Word("horse", "caballo", R.drawable.animals_horse, R.raw.animals_horse));
        animals.add(new Word("elephant", "elefante", R.drawable.animals_elephant, R.raw.animals_elephant));


        //Create an Custom Adapter(Thus you must have a new Java class)... that will use the data of the respective Objects you created
        //It will use the data by placing them(each data) into the appropriate View of the specified list item layout, in the CustomAdapter's class
        WordAdapter adapter = new WordAdapter(this, animals, R.color.category_animals);

        //Find the ListView with the ID list_view
        ListView listView = (ListView) findViewById(R.id.list);

        //Attach the (custom)adapter to the list view
        listView.setAdapter(adapter);

        //set a item click listener to the AdapterView so that when each respective item is clicked(touched)by the user, the appropriate audio file is played
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the object(represented by a list item view) that the user clicked on
                //In getting the object at the specified position you will be able to get its respective data
                Word animalsObject = animals.get(position);

                //In case the user to chose another audio file to play before the previous one has finished, release the resources of the MediaPlayer Object and set its initialization to null
                releaseMediaPlayer();

                //Call method requestAudioFocus( ) on the AudioManager Object so that your app can obtain the AudioFocus of the Device so that it can play the respective sound
                int result = mAudioManager.requestAudioFocus(mAudioFocusRequest);

                //Check if the application was granted the AudioFocus
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Because your app was granted the AudioFocus you can start the media playback
                    //Get the respective Audio file of the corresponding object and assign it to the global variable
                    mAnimalsAudio = MediaPlayer.create(AnimalsActivity.this, animalsObject.getAudioResourceId());

                    //Execute(play) the respective Audio file of the corresponding object that the usre clicked on
                    mAnimalsAudio.start();

                    //Set the OnCompletionListener to the MediaPlayer Object
                    //This Listener is to be triggered and thus carry out the respective tasks once the Audio file has been finished playing
                    mAnimalsAudio.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });

    }

    //This Activity lifecycle method when executed will stop any audio that is playing
    @Override
    protected void onPause() {
        super.onPause();
        //If this Activity is exited, then the respective audio should no longer be playing
        releaseMediaPlayer();
    }

    //This method when called will release the resources
    public void releaseMediaPlayer(){
        //Before executing the respective task(s), check  if the media player object was initialized with an audio
        //If so, then this if statement will be executed
        if(mAnimalsAudio != null) {
            //This method will release the resources of the stored in the MediaPlayer Object
            mAnimalsAudio.release();

            //This code statement will make sure that the MediaPlayer Object mDigitAudio wil be reset back to its defualt value
            mAnimalsAudio = null;

            //Whether this application was granted the AudioFocus or not, abandon it
            //This code statement also unregisters the OnAudioFocusChangeListener so that you dont get anymore callbacks
            mAudioManager.abandonAudioFocusRequest(mAudioFocusRequest);
        }
    }
}
