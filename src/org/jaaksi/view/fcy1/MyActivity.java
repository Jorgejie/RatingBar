package org.jaaksi.view.fcy1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import org.jaaksi.view.fcy1.view.DensityUtil;
import org.jaaksi.view.fcy1.view.MyRatingBar;

public class MyActivity extends Activity {

    private MyRatingBar ratingBar;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratingbar);

        ratingBar = (MyRatingBar) findViewById(R.id.rating_var);

    }

    public void getRating(View view) {
        int rating = ratingBar.getRating();
        Toast.makeText(this,"rating="+rating,Toast.LENGTH_SHORT).show();
    }
}