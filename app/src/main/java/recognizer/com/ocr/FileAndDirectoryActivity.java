package recognizer.com.ocr;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FileAndDirectoryActivity extends ListActivity {

    private List<String> fileList = new ArrayList<String>();
    public final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/readerFiles";
    String line = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File root = new File(path);
        ListDir(root);
        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String fileName = ((TextView) view).getText().toString();
                Intent intent = new Intent(getApplicationContext(), SingleListItem.class);
                intent.putExtra("fileName", ReadFile(fileName));
                intent.putExtra("filePath", path + "/" + fileName);
                startActivity(intent);
            }
        });
    }

    public void ListDir(File f) {
        File[] files = f.listFiles();
        fileList.clear();
        for (File file : files) {
            fileList.add(file.getName());
        }
        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList);
        setListAdapter(directoryList);
    }

    public String ReadFile(String fileName) {
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path + "/" + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        System.out.println(line);
        return line;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FileAndDirectoryActivity.this, MainActivity.class));
        finish();

    }


}
