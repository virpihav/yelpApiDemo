package fi.virpihav.yelpdemo;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fi.virpihav.yelpdemo.model.Business;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {
    private List<Business> data;
    private final Callback callback;

    public BusinessAdapter(List<Business> data, Callback callback) {
        this.data = data;
        this.callback = callback;
    }

    @Override
    public BusinessAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Business business = data.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusinessAdapter.this.callback.onBusinessClicked(business);
            }
        });
        holder.name.setText(business.getName());
        holder.distance.setText(business.getDistance());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface Callback {

        void onBusinessClicked(Business business);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView distance;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            distance = (TextView) v.findViewById(R.id.distance);
        }
    }
}
