package comp3350.bookworm.Presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.bookworm.BusinessLogic.Time.Real.TimeProviderReal;
import comp3350.bookworm.BusinessLogic.Time.TimeProvider;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.R;


public class BookArrayAdapter extends ArrayAdapter<Book> {
    private final Context mContext;
    private final ArrayList<Book> searchListArray;

    public BookArrayAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
        mContext = context;
        this.searchListArray = new ArrayList<>();
        this.searchListArray.addAll(books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book, parent, false);
        }

        TextView bookName = (TextView) convertView.findViewById(R.id.bookName);
        TextView authorName = (TextView) convertView.findViewById(R.id.authorName);
        TextView price = (TextView) convertView.findViewById(R.id.bookPrice);

        bookName.setText(book.getBookName());
        authorName.setText(book.getAuthorName());
        // check half-price day
        TimeProvider timeProvider = new TimeProviderReal();
        if(timeProvider.isHalfPriceDay()) {
            price.setTextColor(Color.RED);
            price.setText(Double.toString(book.getHalfBookPrice()) + "  50% off!");
        }
        else
            price.setText(Double.toString(book.getBookPrice()));


        Button btButton = (Button) convertView.findViewById(R.id.btn_arrayAdapter_book);
        btButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("BookName", book.getBookName());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

}