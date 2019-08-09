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

public class NumbersActivity extends AppCompatActivity {

    //Declare a MediaPlayer Object that will be used to store the respective/ appropriate audio file
    private MediaPlayer mDigitAudio;

    //Declare an instance that will be used to request and abandon AudioFocus
    private AudioManager mAudioManager;

    //Declare listener to be used within the respective Listener
    //When an audio completes this Listener's callback method is to be executed
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            //Call the method that will release the resources and reset the defualt value of the object variable that will store the resources such as (memory and the audio file which is the value)
            releaseMediaPlayer();;
        }
    };



    //Create am instance of the OnAudioFocusChangeListener
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //Pause playback
                mDigitAudio.pause();
                mDigitAudio.seekTo(0); //Tells the application to start from the beginning of the audio, since the audio paused is a short audio
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //Resume playback
                mDigitAudio.start();
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
        final ArrayList<Word> numberWords = new ArrayList<Word>();

        //Add the appropriate elements into the appropriate ArrayList
        numberWords.add(new Word("one", "uno", R.drawable.digit_one, R.raw.digit_one));
        numberWords.add(new Word("two", "dos", R.drawable.digit_two, R.raw.digit_two));
        numberWords.add(new Word("three", "tres", R.drawable.digit_three, R.raw.digit_three));
        numberWords.add(new Word("four", "quatro", R.drawable.digit_four, R.raw.digit_four));
        numberWords.add(new Word("five", "cinco", R.drawable.digit_five, R.raw.digit_five));
        numberWords.add(new Word("six", "seis", R.drawable.digit_six, R.raw.digit_six));
        numberWords.add(new Word("seven", "siete", R.drawable.digit_seven, R.raw.digit_seven));
        numberWords.add(new Word("eight", "ocho", R.drawable.digit_eight, R.raw.digit_eight));
        numberWords.add(new Word("nine", "nueve", R.drawable.digit_nine, R.raw.digit_nine));
        numberWords.add(new Word("ten", "diez", R.drawable.digit_ten, R.raw.digit_ten));
        numberWords.add(new Word("eleven", "once", R.drawable.digit_eleven, R.raw.digit_eleven));
        numberWords.add(new Word("twelve", "doce", R.drawable.digit_twelve, R.raw.digit_twelve));
        numberWords.add(new Word("thirten", "trece", R.drawable.digit_thirteen, R.raw.digit_thirteen));
        numberWords.add(new Word("fourteen", "catorce", R.drawable.digit_fourteen, R.raw.digit_fourteen));
        numberWords.add(new Word("fifthteen", "quince", R.drawable.digit_fifteen, R.raw.digit_fifteen));
        numberWords.add(new Word("sixteen", "dieciséis", R.drawable.digit_sixthteen, R.raw.digit_sixteen));
        numberWords.add(new Word("seventeen", "diecisiete", R.drawable.digit_seventeen, R.raw.digit_seventeen));
        numberWords.add(new Word("eightteen", "dieciocho", R.drawable.digit_eightteen, R.raw.digit_eighteen));
        numberWords.add(new Word("nineteen", "diecinueve", R.drawable.digit_nineteen, R.raw.digit_nineteen));
        numberWords.add(new Word("twenty", "veinte", R.drawable.digit_twenty, R.raw.digit_twenty));
        numberWords.add(new Word("twenty one", "veintiuno", R.drawable.digit_twenty_one, R.raw.digit_twenty_one));
        numberWords.add(new Word("twenty two", "veintidós", R.drawable.digit_twenty_two, R.raw.digit_twenty_two));
        numberWords.add(new Word("twenty three", "veintitrés", R.drawable.digit_twenty_three, R.raw.digit_twenty_three));
        numberWords.add(new Word("twenty four", "veinticuatro", R.drawable.digit_twenty_four, R.raw.digit_twenty_four));
        numberWords.add(new Word("twenty five", "veinticinco", R.drawable.digit_twenty_five, R.raw.digit_twenty_five));


        //Create an Custom Adapter(Thus you must have a new Java class)... that will use the data of the respective Objects you created
        //It will use the data by placing them(each data) into the appropriate View of the specified list item layout, in the CustomAdapter's class
        WordAdapter adapter = new WordAdapter(this, numberWords, R.color.category_numbers);

        //Find the ListView with the ID list_view
        ListView listView = findViewById(R.id.list);


        //Attach the (custom)adapter to the list view
        listView.setAdapter(adapter);


        //set a item click listener to the AdapterView so that when each respective item is clicked(touched)by the user, the appropriate audio file is played
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //note, in this code statement within the methods parenthesis, an "anonymous class" is declared an instantiated... its method(s) can reference local or global variables if it/they are declared final
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the object(represented by a list item view) that the user clicked on
                //In getting the object at the specified position you will be able to get its respective data
                Word numberObject = numberWords.get(position);

                //In case the user to chose another audio file to play before the previous one has finished, release the resources of the MediaPlayer Object and set its initialization to null
                releaseMediaPlayer();

                //Call method requestAudioFocus( ) on the AudioManager Object so that your app can obtain the AudioFocus of the Device so that it can play the respective sound
                int result = mAudioManager.requestAudioFocus(mAudioFocusRequest);

                //Check if the application was granted the AudioFocus
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Because your app was granted the AudioFocus you can start the media playback
                    //Get the respective Audio file of the corresponding object and assign it to the global variable
                    mDigitAudio = MediaPlayer.create(NumbersActivity.this, numberObject.getAudioResourceId());

                    //Execute(play) the respective Audio file of the corresponding object that the usre clicked on
                    mDigitAudio.start();

                    //Set the OnCompletionListener to the MediaPlayer Object
                    //This Listener is to be triggered and thus carry out the respective tasks once the Audio file has been finished playing
                    mDigitAudio.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });

    }


    //This Activity lifecycle method when executed will stop any audio that is playing
    @Override
    protected void onPause() {
        super.onPause();

        releaseMediaPlayer();
    }


    //This method when called will release the resources
    public void releaseMediaPlayer(){
        //Before executing the respective task(s), check  if the media player object was initialized with an audio
        //If so, then this if statement will be executed
        if(mDigitAudio != null) {
            //This method will release the resources of the stored in the MediaPlayer Object
            mDigitAudio.release();

            //This code statement will make sure that the MediaPlayer Object mDigitAudio wil be reset back to its defualt value
            mDigitAudio = null;

            //Whether this application was granted the AudioFocus or not, abandon it
            //This code statement also unregisters the OnAudioFocusChangeListener so that you dont get anymore callbacks
            mAudioManager.abandonAudioFocusRequest(mAudioFocusRequest);
        }
    }

}