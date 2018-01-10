package recognizer.com.ocr;


import android.os.Environment;
import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.samples.vision.ocrreader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {


    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    public static TextView textValue;
    private Button saveButton;
    private Button viewTextsButton;
    public final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/readerFiles";
    private static final int RC_OCR_CAPTURE = 9003;
    private static final String TAG = "MainActivity";
    private List<String> fileList = new ArrayList<String>();
    public final File dir = new File(path);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveButton = findViewById(R.id.save_text);
        viewTextsButton = findViewById(R.id.view_texts);
        statusMessage = findViewById(R.id.status_message);
        textValue = findViewById(R.id.text_value);
        autoFocus = findViewById(R.id.auto_focus);
        useFlash = findViewById(R.id.use_flash);
        //File dir = new File(path);
        dir.mkdir();

        findViewById(R.id.read_text).setOnClickListener(this);
        findViewById(R.id.save_text).setOnClickListener(this);
        findViewById(R.id.view_texts).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_text) {
            // launch Ocr capture activity.
            Intent intent = new Intent(this, OcrCaptureActivity.class);
            intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_OCR_CAPTURE);
        }
        if (v.getId() == R.id.save_text) {
            if (textValue.getText() != "") {
                Intent intent = new Intent(MainActivity.this, FileName.class);
                startActivity(intent);
            } else {
                statusMessage.setText("No text to save");
            }
        }
        if (v.getId() == R.id.view_texts) {
            Intent intent = new Intent(MainActivity.this, FileAndDirectoryActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    statusMessage.setText(R.string.ocr_success);
                    textValue.setText(text);
                    Log.d(TAG, "Text read: " + text);
                } else {
                    statusMessage.setText(R.string.ocr_failure);
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.ocr_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        savedInstance.putString("detectedString", textValue.getText().toString());
        savedInstance.putString("statusString", statusMessage.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstance) {
        super.onRestoreInstanceState(savedInstance);
        textValue.setText(savedInstance.getString("detectedString"));
        statusMessage.setText(savedInstance.getString("statusString"));
    }

    @Override
    public void onBackPressed()
    {

        moveTaskToBack(true); // exist app

    }

}