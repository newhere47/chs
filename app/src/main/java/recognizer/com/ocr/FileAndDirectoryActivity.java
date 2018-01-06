package recognizer.com.ocr;


        import android.app.ListActivity;
        import android.os.Environment;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ArrayAdapter;

        import java.io.File;
        import java.util.ArrayList;
        import java.util.List;

public class FileAndDirectoryActivity extends ListActivity {

    private List<String> fileList = new ArrayList<String>();
    public final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ ";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File root = new File(path);
        ListDir(root);
    }

    void ListDir(File f) {
        File[] files = f.listFiles();
        fileList.clear();
        for (File file : files) {
            fileList.add(file.getName());
        }
        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList);
        setListAdapter(directoryList);
    }


}
