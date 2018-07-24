package comp3350.bookworm.Presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.R;

public class SearchListAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Book> searchListArray;
    private List<Book> modifiedList = new ArrayList<>();

    public SearchListAdapter(Context context, ArrayList<Book> books) {
        this.mContext = context;
        this.searchListArray = books;
        this.modifiedList.addAll(searchListArray);
    }
    @Override
    public int getCount() {
        if(searchListArray == null)
            return 0;
        return searchListArray.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Book getItem(int position) {
        return null;
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
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if(day == Calendar.TUESDAY) {
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
    public void filter(String charText) {
        charText = charText.toLowerCase();
        searchListArray.clear();
       if (charText.length() == 0){
           searchListArray.addAll(modifiedList);
       } else {
            for (Book book : modifiedList) {
                if (book.getBookName().toLowerCase().contains(charText)) {
                    searchListArray.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }

    public Context getContext() {
        return mContext;
    }
}