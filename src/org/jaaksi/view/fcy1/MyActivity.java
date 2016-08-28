package org.jaaksi.view.fcy1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.jaaksi.view.fcy1.view.MyRatingBar;

public class MyActivity extends Activity implements MyRatingBar.OnSeekChangedListener {

    private MyRatingBar ratingBar;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratingbar);

        TextView rating_desc = (TextView) findViewById(R.id.rating_desc);
        ratingBar = (MyRatingBar) findViewById(R.id.rating_var);
        ratingBar.setRating(0);
        ratingBar.bindDescView(rating_desc, getResources().getStringArray(R.array.rating_array));
        ratingBar.setOnSeekChangedListener(this);
    }

    public void getRating(View view) {
        float rating = ratingBar.getRatings();
        Toast.makeText(this, "rating=" + rating, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSeekChanged(int lastCount, int count) {
        Toast.makeText(this, "last count =" + lastCount + ",curr =" + count, 1).show();
    }
}
