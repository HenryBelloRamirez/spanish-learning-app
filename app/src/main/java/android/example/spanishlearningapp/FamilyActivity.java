package android.example.spanishlearningapp;

import android.content.Context;
import android.example.spanishlanguageappforbeginners.R;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {


    //Declare a global variable that will store the audio file of the corresponding object
    private MediaPlayer mFamilyAudio;

    //Declare an instance that will be used to request and abandon AudioFocus
    private AudioManager mAudioManager;

    //Declare listener to be used within the respective Listener
    //When an audio completes this Listener's callback method is to be executed
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            //call the Method that will release the resources of what was assigned to the MediaPlayer Object mDigitAudio
            releaseMediaPlayer();
        }
    };



    //Create am instance of the OnAudioFocusChangeListener
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //Pause playback
                mFamilyAudio.pause();
                mFamilyAudio.seekTo(0); //Tells the application to start from the beginning of the audio, since the audio paused is a short audio
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //Resume playback
                mFamilyAudio.start();
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



    //When the App is first launched it enters the ***Created state***
    //This method is the Callback to that state
    //Thus when your app enters this the created state, this method is executed
    //And thus everything inside this method is executed... the ListView with its List item views(with its respective data) are set up... the Listeners are registered to each list item view... etc...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);

        //Initialize the AudioManager instance
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Create a String type ArrayList that has with the appropriate elements
        //Also, declare this ArrayList as a constant sot that is able to be reference in the OnItemClickListener's callback method
        final ArrayList<Word> familyWords = new ArrayList<Word>();

        //Add the appropriate elements into the appropriate ArrayList
        familyWords.add(new Word("father", "padre", R.drawable.family_father, R.raw.family_father));
        familyWords.add(new Word("mother", "madre", R.drawable.family_mother, R.raw.family_mother));
        familyWords.add(new Word("grandpa", "abuelo", R.drawable.family_grandfather, R.raw.family_grandpa));
        familyWords.add(new Word("grandma", "abuela", R.drawable.family_grandmother, R.raw.family_grandma));
        familyWords.add(new Word("father-in-law", "suegro", R.drawable.family_father, R.raw.family_father_in_law));
        familyWords.add(new Word("mother-in-law", "suegra", R.drawable.family_mother, R.raw.family_mother_in_law));
        familyWords.add(new Word("brother-in-law", "cuñado", R.drawable.family_older_brother, R.raw.family_brother_in_law));
        familyWords.add(new Word("sister-in-law", "cuñada", R.drawable.family_older_sister, R.raw.family_sister_in_law));
        familyWords.add(new Word("uncle", "tío", R.drawable.family_father, R.raw.family_uncle));
        familyWords.add(new Word("aunt", "tía", R.drawable.family_mother, R.raw.family_aunt));
        familyWords.add(new Word("brother", "hermano", R.drawable.family_son, R.raw.family_brother));
        familyWords.add(new Word("sister", "hermana", R.drawable.family_daughter, R.raw.family_sister));
        familyWords.add(new Word("son", "hijo", R.drawable.family_son, R.raw.family_son));
        familyWords.add(new Word("daughter", "hija", R.drawable.family_daughter, R.raw.family_duaghter));
        familyWords.add(new Word("nephew", "sobrino", R.drawable.family_younger_brother, R.raw.family_nephew));
        familyWords.add(new Word("niece", "sobrina", R.drawable.family_younger_sister, R.raw.family_niece));
        familyWords.add(new Word("cousin", "primo(male) / prima(female)", R.raw.family_cousin));


        //Create an Custom Adapter(Thus you must have a new Java class)... that will use the data of the respective Objects you created
        //It will use the data by placing them(each data) into the appropriate View of the specified list item layout, in the CustomAdapter's class
        WordAdapter adapter = new WordAdapter(this, familyWords, R.color.category_family);

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
                Word familyObject = familyWords.get(position);

                //In case the user to chose another audio file to play before the previous one has finished, release the resources of the MediaPlayer Object and set its initialization to null
                releaseMediaPlayer();

                //Call method requestAudioFocus( ) on the AudioManager Object so that your app can obtain the AudioFocus of the Device so that it can play the respective sound
                int result = mAudioManager.requestAudioFocus(mAudioFocusRequest);

                //Check if the application was granted the AudioFocus
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Because your app was granted the AudioFocus you can start the media playback
                    //Get the respective Audio file of the corresponding object and assign it to the global variable
                    mFamilyAudio = MediaPlayer.create(FamilyActivity.this, familyObject.getAudioResourceId());

                    //Execute(play) the respective Audio file of the corresponding object that the usre clicked on
                    mFamilyAudio.start();

                    //Set the OnCompletionListener to the MediaPlayer Object
                    //This Listener is to be triggered and thus carry out the respective tasks once the Audio file has been finished playing
                    mFamilyAudio.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });

    }

    //This Activity lifecycle method when executed will stop any audio that is playing
    @Override
    protected void onPause() {
        super.onPause();
        //Call the method that will release the MediaPlayer Object's resources(in other words stop playing the audio file in general terms) when the user leaves the Activity in which the Audio file is playing in
        //In this code statement note how we didn't just call method "release( )"... Instead you called method "releaseMediaPlayer( )" which within it consists of method release( ) and some extra logic
        //its because of that extra logic that sets the MediaPlayer object back to null and not just release its resources that we called upon this method
        releaseMediaPlayer();
    }


    //This method when called will release the resources
    public void releaseMediaPlayer(){
        //Before executing the respective task(s), check  if the media player object was initialized with an audio
        //If so, then this if statement will be executed
        if(mFamilyAudio != null) {
            //This method will release the resources of the stored in the MediaPlayer Object
            mFamilyAudio.release();

            //This code statement will make sure that the MediaPlayer Object mDigitAudio wil be reset back to its defualt value
            mFamilyAudio = null;

            //Whether this application was granted the AudioFocus or not, abandon it
            //This code statement also unregisters the OnAudioFocusChangeListener so that you dont get anymore callbacks
            mAudioManager.abandonAudioFocusRequest(mAudioFocusRequest);
        }
    }


}
