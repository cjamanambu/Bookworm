package comp3350.bookworm.Presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.R;

public class CartArrayAdapter extends ArrayAdapter<Book> {
    private final Context mContext;

    public CartArrayAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_book, parent, false);
        }

        TextView bookName = (TextView) convertView.findViewById(R.id.bookName);
        TextView authorName = (TextView) convertView.findViewById(R.id.authorName);
        TextView price = (TextView) convertView.findViewById(R.id.bookPrice);

        bookName.setText(book.getBookName());
        authorName.setText(book.getAuthorName());
        price.setText(Double.toString(book.getBookPrice()));

        return convertView;
    }
}
