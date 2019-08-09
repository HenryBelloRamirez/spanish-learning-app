package android.example.spanishlearningapp;

import android.app.Activity;
import android.example.spanishlanguageappforbeginners.R;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//This class populates a list item view with the appropriate data, which is then returned to the Adapter View(In this case being a ListView)
public class WordAdapter extends ArrayAdapter<Word> {


    //Declare a global variable, that will will represent the respective category color. Thus, storing the color of the Respective category
    private int mColorResourceId;


    //This constructor is called to instantiate a custom adapter
    public WordAdapter(Activity context, ArrayList<Word> wordNumbers, int colorResourceId) {


        //Let the Application  know that the ArrayAdapter's constructor will not provide list item views to the AdapterView
        super(context, 0, wordNumbers);

        //assign the passed category color to the global variable mColorResourceId so that that the passed value can be stored and used later
        mColorResourceId = colorResourceId;

    }


    //This method provides a view(List item view) consisting of the appropriate data to the AdapterView (ListView)
    /**
     * @param position  The AdapterView position that is requesting a View
     * @param convertView The recycled View to(that needs to be populated)/This represents a potential view , passed by the AdapterView, that could be reused
     * @param parent The parent ViewGroup that is used for inflation
     *
     * @return The parent(ViewGroup) of all of the list item views (In this case AdapterView: ListView)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        //The Current object's data will be retrieved for the purpose of adding the respective data, to the respective view, of listItemView
        Word currentWordObject = getItem(position);


        //In this code statement , it assigns a scrapped (list item view) view(if there is one) from the ListView to the variable listItemView
        View listItemView = convertView;

        // This if statement is checked to see if a scrapped View was assigned to variable listItemView(meaning if a List Item View was reused)
        // If no list item view was reused, thus making View type variable listItemView null
        // A list item view is inflated(created) using the xml layout file specified
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }



        // Find the TextView in the list_item.xml layout with the ID english_word_text_view
        TextView englishTextView = listItemView.findViewById(R.id.english_word_text_view);

        // Get the english translation from the current Word Object and
        // set this text on the englishTextView(A child element of the list item view)
        englishTextView.setText(currentWordObject.getEnglishTranslation());



        // Find the TextView in the list_item.xml layout with the ID spanish_word_text_view
        TextView spanishTextView = listItemView.findViewById(R.id.spanish_word_text_view);

        // Get the spanish translation from the current Word Object object and
        // set this text on the spanishTextView(A child element of the list item view
        spanishTextView.setText(currentWordObject.getSpanishTranslation());





        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView imageView = listItemView.findViewById(R.id.image);

        //Check if the current Word type object has an image(Done through calling the respective method  on the Object)
        if(currentWordObject.hasImage()) {
            //Set the correct/respective image of the current Word Object on the image view
            imageView.setImageResource(currentWordObject.getImageResourceId());

            //Make the View visible if an image was assigned to the current Word Object
            imageView.setVisibility(View.VISIBLE);
        }
        //All of the above so far assumes that the list item view has an image.... but if the current Word object does not have an image
        //Then this control flow statement will hide the image view(thus, making the list item view not have an image)
        else{
            imageView.setVisibility(View.INVISIBLE);
        }




        //Find the View group view with the ID text_view_container
        View textViewContainer = listItemView.findViewById(R.id.text_view_container);

        //Get the color that was assigned to global variable mColor
        int color = ContextCompat.getColor(getContext(), mColorResourceId);

        //set the respective category color onto the appropriate view group
        textViewContainer.setBackgroundColor(color);



        //Return the list item view to the AdapterView, which had requested it
        return listItemView;
    }


}