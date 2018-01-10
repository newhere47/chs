package recognizer.com.ocr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.samples.vision.ocrreader.R;

import java.io.File;


public class SingleListItem extends Activity implements View.OnClickListener {
    private Button deleteButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_text_layout);

        deleteButton = findViewById(R.id.delete_text);
        findViewById(R.id.delete_text).setOnClickListener(this);

        TextView txtProduct = findViewById(R.id.text_value);

        Intent intent = getIntent();
        String product = intent.getStringExtra("fileName");
        txtProduct.setText(product);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.delete_text) {
            Intent i = getIntent();
            String fileName = i.getStringExtra("filePath");
            File file = new File(fileName);
            file.delete();
            Intent intent = new Intent(SingleListItem.this, FileAndDirectoryActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SingleListItem.this, FileAndDirectoryActivity.class));
        finish();

    }
}