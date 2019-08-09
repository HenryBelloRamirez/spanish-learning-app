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

public class ColorsActivity extends AppCompatActivity {

    //Declare a global variable that will be used to store the audio file of the respective object that the user clicked on in the ListView
    private MediaPlayer mColorAudio;

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
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //Pause playback
                mColorAudio.pause();
                mColorAudio.seekTo(0); //Tells the application to start from the beginning of the audio, since the audio paused is a short audio
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //Resume playback
                mColorAudio.start();
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
        final ArrayList<Word> colorWords = new ArrayList<Word>();

        //Add the appropriate elements into the appropriate ArrayList
        colorWords.add(new Word("red", "rojo", R.drawable.color_red, R.raw.color_red));
        colorWords.add(new Word("orange", "aranjado", R.drawable.color_orange, R.raw.color_orange));
        colorWords.add(new Word("yellow", "amarillo", R.drawable.color_mustard_yellow, R.raw.color_yellow));
        colorWords.add(new Word("green ", "verde", R.drawable.color_green, R.raw.color_green));
        colorWords.add(new Word("blue", "azul", R.drawable.color_blue, R.raw.color_blue));
        colorWords.add(new Word("purple", "amorado", R.drawable.color_purple, R.raw.color_purple));
        colorWords.add(new Word("brown", "cafe", R.drawable.color_brown, R.raw.color_brown));
        colorWords.add(new Word("white", "blanco", R.drawable.color_white, R.raw.color_white));
        colorWords.add(new Word("black", "negro", R.drawable.color_black, R.raw.color_black));
        colorWords.add(new Word("gray", "gris", R.drawable.color_gray, R.raw.color_grey));
        colorWords.add(new Word("pink", "rosada", R.drawable.color_pink, R.raw.color_pink));


        //Create an Custom Adapter(Thus you must have a new Java class)... that will use the data of the respective Objects you created
        //It will use the data by placing them(each data) into the appropriate View of the specified list item layout, in the CustomAdapter's class
        WordAdapter adapter = new WordAdapter(this, colorWords, R.color.category_colors);

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
                Word colorObject = colorWords.get(position);

                //In case the user to chose another audio file to play before the previous one has finished, release the resources of the MediaPlayer Object and set its initialization to null
                releaseMediaPlayer();

                //Call method requestAudioFocus( ) on the AudioManager Object so that your app can obtain the AudioFocus of the Device so that it can play the respective sound
                int result = mAudioManager.requestAudioFocus(mAudioFocusRequest);

                //Check if the application was granted the AudioFocus
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Because your app was granted the AudioFocus you can start the media playback
                    //Get the respective Audio file of the corresponding object and assign it to the global variable
                    mColorAudio = MediaPlayer.create(ColorsActivity.this, colorObject.getAudioResourceId());

                    //Execute(play) the respective Audio file of the corresponding object that the usre clicked on
                    mColorAudio.start();

                    //Set the OnCompletionListener to the MediaPlayer Object
                    //This Listener is to be triggered and thus carry out the respective tasks once the Audio file has been finished playing
                    mColorAudio.setOnCompletionListener(mOnCompletionListener);
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
        if(mColorAudio != null) {
            //This method will release the resources of the stored in the MediaPlayer Object
            mColorAudio.release();

            //This code statement will make sure that the MediaPlayer Object mDigitAudio wil be reset back to its defualt value
            mColorAudio = null;

            //Whether this application was granted the AudioFocus or not, abandon it
            //This code statement also unregisters the OnAudioFocusChangeListener so that you dont get anymore callbacks
            mAudioManager.abandonAudioFocusRequest(mAudioFocusRequest);
        }
    }
}