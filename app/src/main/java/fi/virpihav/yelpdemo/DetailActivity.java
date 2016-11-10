package fi.virpihav.yelpdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fi.virpihav.yelpdemo.model.Business;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_BUSINESS = "business";
    private TextView name;
    private TextView category;
    private TextView distance;
    private TextView rating;
    private TextView price;
    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        name = ((TextView) findViewById(R.id.name));
        category = ((TextView) findViewById(R.id.category));
        distance = ((TextView) findViewById(R.id.distance));
        rating = ((TextView) findViewById(R.id.rating));
        price = ((TextView) findViewById(R.id.price));
        image = ((ImageView) findViewById(R.id.image));
        Business business = getIntent().getParcelableExtra(EXTRA_BUSINESS);
        loadImage(business);
        setData(business);
    }

    private void loadImage(Business business) {
        String imageurl = business.getImageurl();
        if (imageurl == null) {
            Log.d(TAG, "no imageUrl");
            return;
        }
        Picasso.with(this).load(imageurl).into(image);
    }

    private void setData(Business business) {
        appendTvTitle(name, business.getName());
        if (business.getCategories() != null) {
            appendTvTitle(category, business.getCategories().get(0).getTitle());
        }
        appendTvTitle(distance, business.getDistance());
        appendTvTitle(rating, business.getRating());
        appendTvTitle(price, business.getPrice());
    }

    private void appendTvTitle(TextView tv, String data) {
        String oldText = tv.getText().toString();
        if (!oldText.isEmpty()) {
            oldText += ": ";
        }
        if (data == null) {
            tv.setText(oldText + "unknown");
        } else {
            tv.setText(oldText + data);
        }
    }
}
