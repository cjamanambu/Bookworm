package comp3350.bookworm.Presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.bookworm.Objects.Review;
import comp3350.bookworm.R;

public class ReviewArrayAdapter extends ArrayAdapter <Review> {
    private final Context mContext;

    public ReviewArrayAdapter(Context context, ArrayList<Review> reviews ) {
        super( context, 0, reviews );
        mContext = context;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent ) {
        final Review review = getItem( pos );

        if ( convertView == null )
            convertView = LayoutInflater.from( getContext() ).inflate( R.layout.item_review, parent, false );

        TextView accountName = (TextView) convertView.findViewById( R.id.text_accName );
        TextView userRating = (TextView) convertView.findViewById( R.id.text_rating );
        TextView reviewContent = (TextView) convertView.findViewById( R.id.text_content );

        accountName.setText( review.getReviewWriter() );
        userRating.setText( Double.toString(review.getRatingPoint()) );
        reviewContent.setText( review.getContent() );

        return convertView;
    }
}
