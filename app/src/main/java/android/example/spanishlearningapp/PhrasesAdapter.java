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
public class PhrasesAdapter extends ArrayAdapter<Word> {



    //Declare a global variable, that will will represent the respective category color
    private int mColorResourceId;



    //Constructor
    public PhrasesAdapter(Activity context, ArrayList<Word> phrases, int colorResourceId){
        //Call the superclass's constructor and specify that its constructor will not provide a List item view for the AdapterView of the respective activity
        super(context, 0 , phrases);

        //assign the passed category color to the global variable mColorResourceId so that that the passed value can be stored and used later
        //This code statement must come second to the previous code statement(REMEMBER)
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

        //Begin by finding the current object being requested
        Word currentWordObject = getItem(position);



        //Reuse a list item view (If the AdapterView passed a scapped view to this custom adapter)
        View listItemView = convertView; //This is statement that a view will be reused (if there is one that is)

        //Inflate a new list item view(if the AdapterView didn't pass a scrap view to the AdapterView)...This is checked in the condition
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }



        //Repopulate the child views of the reused list item view or Populate the child view of the list item view inflated from scratch... using the data of the current object

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


        ImageView imageView = listItemView.findViewById(R.id.image);

        if(currentWordObject.hasImage()){
            //set the resctive image of the current Word object onto the
            imageView.setImageResource(currentWordObject.getImageResourceId());

            //Make the View visible if an image was assigned to the current Word Object
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
        }



        //Find the View group view with the ID text_view_container
        View textViewContainer = listItemView.findViewById(R.id.text_view_container);

        //Get the color that was assigned to global variable mColor
        int color = ContextCompat.getColor(getContext(), mColorResourceId);

        //Finally, assign the repective(color) category color to the specified view
        textViewContainer.setBackgroundColor(color);


        //Once all of the child views have been populated... return the listItemView(That was reused or inflated) to the AdapterView, requesting the list item view
        return listItemView;
    }
}
