package fi.virpihav.yelpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import fi.virpihav.yelpdemo.model.Business;

public class ResultActivity extends AppCompatActivity implements BusinessAdapter.Callback {

    public static final String EXTRA_BUSINESSES = "businesses";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        List<Business> businesses = getIntent().getParcelableArrayListExtra(EXTRA_BUSINESSES);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new BusinessAdapter(businesses, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBusinessClicked(Business business) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_BUSINESS, business);
        startActivity(intent);
    }
}

