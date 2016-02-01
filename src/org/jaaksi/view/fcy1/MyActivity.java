package org.jaaksi.view.fcy1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import org.jaaksi.view.fcy1.view.MyRatingBar;

public class MyActivity extends Activity
{

	private MyRatingBar ratingBar;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ratingbar);

		ratingBar = (MyRatingBar) findViewById(R.id.rating_var);
		ratingBar.setRatings(2.5f);
		ratingBar.setRating(4);
		ratingBar.setRatings(3.5f);
	}

	public void getRating(View view)
	{
		float rating = ratingBar.getRatings();
		Toast.makeText(this, "rating=" + rating, Toast.LENGTH_SHORT).show();
	}
}
