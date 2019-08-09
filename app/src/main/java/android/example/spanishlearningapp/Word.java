package android.example.spanishlearningapp;

//This class represents a word that the user wants to learn
//It contains the english (default)translation and spanish translation for each word
//Thus, with the use of this class, a word object with the respectable data will be created
public class Word {

    //This global variable will be used to store the English (default) translation for the word
    private String mEnglishTranslation;

    //This global variable will be used to store Spanish translation for the word
    private String mSpanishTranslation;

    //global variable used to store the audio file of the respective object
    private int mAudioResourceId;

    //This global variable will be used to store the Custom Object's image
    private int mImageResourceId = No_IMAGE_PROVIDED;


    //This global variable will e assigned to global variable mImageResourceId
    //Thus, by default global variable "mImageResourceId" is Initialized with no image provide, unless otherwise provided
    private static final int No_IMAGE_PROVIDED = -1;



    //Constructor
    //Will be used to create an object with the three input parameters passed
    public Word(String englishTranslation, String spanishTranslation, int audioResourceId){
        mEnglishTranslation = englishTranslation;
        mSpanishTranslation = spanishTranslation;
        mAudioResourceId = audioResourceId;
    }


    //Constructor
    //Will be used to create an object with the four input parameters passed
    public Word(String englishTranslation, String spanishTranslation, int imageResourceId, int audioResourceId){
        mEnglishTranslation = englishTranslation;
        mSpanishTranslation = spanishTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }



    //Getter method to receive the english translation
    public String getEnglishTranslation(){
        return mEnglishTranslation;
    }


    //Getter method to receive the spanish translation
    public String getSpanishTranslation(){
        return mSpanishTranslation;
    }

    //Getter method to receive the Image
    //Note, this message is called only if there was an image provided for the Object(Which is specified when the word object was created)
    public int getImageResourceId(){
        return mImageResourceId;
    }

    //method that specifies whether the object created has an image or not. Thus, this method returns a boolean value
    //If it does return a value of true
    //If it doesn't return a value of false
    public boolean hasImage(){
        return mImageResourceId != No_IMAGE_PROVIDED;
    }

    //Getter method to receive the audio file
    public int getAudioResourceId(){
        return mAudioResourceId;
    }

}
