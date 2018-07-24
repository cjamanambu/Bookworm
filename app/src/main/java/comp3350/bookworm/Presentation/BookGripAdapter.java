package comp3350.bookworm.Presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.R;

// Reference:
// https://www.raywenderlich.com/127544/android-gridview-getting-started

public class BookGripAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Book> books;

    public BookGripAdapter(Context context, ArrayList<Book> books) {
        this.mContext = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        if(books == null)
            return 0;
        return books.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Book book = books.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.grid_book, null);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.grid_book_name);
        final TextView authorTextView = (TextView)convertView.findViewById(R.id.grid_book_author);

        // replace image with real book cover
        imageView.setImageResource(R.drawable.default_book_icon);
        nameTextView.setText(book.getBookName());
        authorTextView.setText(book.getAuthorName());

        Button btButton = (Button) convertView.findViewById(R.id.btn_library_book);
        btButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("BookName", book.getBookName());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
