package recognizer.com.ocr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.google.android.gms.samples.vision.ocrreader.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileName extends Activity implements View.OnClickListener {

    public final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/readerFiles";
    private Button okButton;
    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_name_layout);

        okButton = findViewById(R.id.save_name);
        editText = findViewById(R.id.input_text);

        okButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_name) {
            if (!editText.getText().toString().isEmpty()) {
                File file = new File(path + "/" + editText.getText() + ".txt");
                String[] savedText = String.valueOf(MainActivity.textValue.getText()).split(System.getProperty("line.separator"));
                MainActivity.textValue.setText("");
                editText.setText("Text file saved");
                Save(file, savedText);
                while (editText.getText().toString().equals("Text file saved")) {
                    startActivity(new Intent(FileName.this, MainActivity.class));
                    break;
                }
            }
        } else {
            editText.setText("Not a valid name!");
        }
    }


    public static void Save(File file, String[] data) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                for (int i = 0; i < data.length; i++) {
                    fos.write(data[i].getBytes());
                    if (i < data.length - 1) {
                        fos.write("\n".getBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FileName.this, MainActivity.class));
        finish();

    }
}

