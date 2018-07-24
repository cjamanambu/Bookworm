
package comp3350.bookworm.Presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import comp3350.bookworm.BusinessLogic.BookManager;
import comp3350.bookworm.R;

public class BookPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_preview);

        final TextView textView_previewContent = (TextView) findViewById( R.id.textView_previewContent );
        String preview = getIntent().getStringExtra( "PREVIEW_CONTENT");
        if(preview.isEmpty())
            textView_previewContent.setText("Not available");
        else
            textView_previewContent.setText(preview);

    }
}
