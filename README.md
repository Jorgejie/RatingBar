# RatingBar
自定义评分条RatingBar
取代系统RatingBar。支持自定义样式，支持半颗星（仅不可点击时），可设置星星大小。
```java  
  xml属性设置
   <org.jaaksi.view.fcy1.view.MyRatingBar
        android:id="@+id/rating_var"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        rating_bar:click_enable="true"
        rating_bar:num="4"
        rating_bar:rating_background="@drawable/bg_ratingbar"
        rating_bar:rating_height="wrap_content"
        rating_bar:rating_width="wrap_content"
        rating_bar:space="30dp" />
  代码设置
  TextView rating_desc = (TextView) findViewById(R.id.rating_desc);
  ratingBar = (MyRatingBar) findViewById(R.id.rating_var);
  ratingBar.setRating(3);
  ratingBar.bindDescView(rating_desc, getResources().getStringArray(R.array.rating_array));
  ratingBar.setOnSeekChangedListener(this);

  MyRatingBar ratingHalf = (MyRatingBar) findViewById(R.id.rating_half);
  ratingHalf.setRatings(3.6f);
```
![image](https://github.com/jaaksi/RatingBar/blob/master/ScreenShots/rating.png)
