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

public class PhrasesActivity extends AppCompatActivity {

    //Declare a local variable that will be used to store the audio of the respective object
    private MediaPlayer mPhrasesAudio;

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
                mPhrasesAudio.pause();
                mPhrasesAudio.seekTo(0); //Tells the application to start from the beginning of the audio, since the audio paused is a short audio
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //Resume playback
                mPhrasesAudio.start();
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
        final ArrayList<Word> phrases = new ArrayList<Word>();

        //Add the appropriate elements into the appropriate ArrayList
        phrases.add(new Word("Hi", "Hola", R.raw.phrases_hi));
        phrases.add(new Word("Bye", "Hasta luego", R.raw.phrases_bye));
        phrases.add(new Word("See you later", "Te veré más tarde", R.raw.phrases_see_you_later));
        phrases.add(new Word("Good morning", "Buenos días", R.raw.phrases_good_mourning));
        phrases.add(new Word("Good afternoon/Good evening", "Buenas tardes", R.raw.phrases_good_afternoon));
        phrases.add(new Word("Good night", "Buenas noches", R.raw.phrases_good_night));
        phrases.add(new Word("Hi! how are you?", "¡Hola! ¿cómo estás?", R.raw.phrases_hi_how_are_you));
        phrases.add(new Word("My name is __", "Me llamo __", R.raw.phrases_my_name_is));
        phrases.add(new Word("What is your name?", "¿Cuál es su nombre?", R.raw.phrases_what_is_your_name));
        phrases.add(new Word("Excuse me", "Disculpe", R.raw.phrases_excuse_me));
        phrases.add(new Word("Can you help me?", "¿Me puedes ayudar?", R.raw.phrases_can_you_help_me));
        phrases.add(new Word("How can I help you?", "¿Como te puede ayudar?", R.raw.phrases_how_can_i_help_you));
        phrases.add(new Word("Do you need help?", "¿Necesitas ayuda?", R.raw.phrases_do_you_need_help));
        phrases.add(new Word("Where can I find__?", "¿Dónde puedo encontrar__?", R.raw.phrases_where_can_i_find));
        phrases.add(new Word("Thank you so much!", "¡Muchas gracias!", R.raw.phrases_thank_you_so_much));
        phrases.add(new Word("No Thank you", "No gracias", R.raw.phrases_no_thank_you));
        phrases.add(new Word("That sounds great!", "¡Eso suena genial!", R.raw.phrases_that_sounds_great));
        phrases.add(new Word("I'm sorry / My apologies", "Perdón / mis disculpas", R.raw.phrases_im_sorry_my_apologies));
        phrases.add(new Word("It doesn't matter", "No importa", R.raw.phrases_it_doesnt_matter));
        phrases.add(new Word("I don't understand", "No entiendo", R.raw.phrases_i_dont_understand));
        phrases.add(new Word("Can you repeat that please?", "¿Puede repetir eso por favor?", R.raw.phrases_can_you_repeat_that_please));
        phrases.add(new Word("Can you please talk slower?", "¿Puedes por favor hablar más lento?", R.raw.phrases_can_you_please_talk_slower));
        phrases.add(new Word("What do you mean?", "¿Qué quieres decir?", R.raw.phrases_what_do_you_mean));
        phrases.add(new Word("I am barely began learning Spanish", "Apenas empecé a aprender español", R.raw.phrases_i_barely_began_learning_spanish));
        phrases.add(new Word("Where are you from?", "¿De donde eres?", R.raw.phrases_where_are_you_from));
        phrases.add(new Word("What are you doing?", "¿Qué estás haciendo?", R.raw.phrases_what_are_you_doing));
        phrases.add(new Word("What’s your phone number?", "¿Cuál es tu número de teléfono?", R.raw.phrases_whats_your_phone_number));
        phrases.add(new Word("I’ll call you!", "¡Te llamare!", R.raw.phrases_ill_call_you));
        phrases.add(new Word("I’ll text you!", "¡Te escribiré un mensaje!", R.raw.phrases_ill_text_you));


        //Create an Custom Adapter(Thus you must have a new Java class)... that will use the data of the respective Objects you created
        //It will use the data by placing them(each data) into the appropriate View of the specified list item layout, in the CustomAdapter's class
        PhrasesAdapter adapter = new PhrasesAdapter(this, phrases, R.color.category_phrases);

        //Find the ListView with the ID list_view
        ListView listView = findViewById(R.id.list);


        //Attach the (custom)adapter to the list view
        listView.setAdapter(adapter);


        //set a item click listener to the AdapterView so that when each respective item is clicked(touched)by the user, the appropriate audio file is played
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the object(represented by a list item view) that the user clicked on
                //In getting the object at the specified position you will be able to get its respective data
                Word phrasesObject = phrases.get(position);

                //In case the user to chose another audio file to play before the previous one has finished, release the resources of the MediaPlayer Object and set its initialization to null
                releaseMediaPlayer();

                //Call method requestAudioFocus( ) on the AudioManager Object so that your app can obtain the AudioFocus of the Device so that it can play the respective sound
                int result = mAudioManager.requestAudioFocus(mAudioFocusRequest);

                //Check if the application was granted the AudioFocus
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Because your app was granted the AudioFocus you can start the media playback
                    //Get the respective Audio file of the corresponding object and assign it to the global variable
                    mPhrasesAudio = MediaPlayer.create(PhrasesActivity.this, phrasesObject.getAudioResourceId());

                    //Execute(play) the respective Audio file of the corresponding object that the usre clicked on
                    mPhrasesAudio.start();

                    //Set the OnCompletionListener to the MediaPlayer Object
                    //This Listener is to be triggered and thus carry out the respective tasks once the Audio file has been finished playing
                    mPhrasesAudio.setOnCompletionListener(mOnCompletionListener);
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
        if(mPhrasesAudio != null) {
            //This method will release the resources of the stored in the MediaPlayer Object
            mPhrasesAudio.release();

            //This code statement will make sure that the MediaPlayer Object mDigitAudio wil be reset back to its defualt value
            mPhrasesAudio = null;

            //Whether this application was granted the AudioFocus or not, abandon it
            //This code statement also unregisters the OnAudioFocusChangeListener so that you dont get anymore callbacks
            mAudioManager.abandonAudioFocusRequest(mAudioFocusRequest);
        }
    }

}