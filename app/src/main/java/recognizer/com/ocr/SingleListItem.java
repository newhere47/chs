package recognizer.com.ocr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.samples.vision.ocrreader.R;


public class SingleListItem extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_text_layout);

        TextView txtProduct = (TextView) findViewById(R.id.text_value);

        Intent i = getIntent();
        // getting attached intent data
        String product = i.getStringExtra("number");
        // displaying selected product name
        txtProduct.setText(product);

    }
}