package rafael.com.br.lanchonete.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import rafael.com.br.lanchonete.model.Promo;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public class PromoListAdapter extends RecyclerView.Adapter<PromoListAdapter.ItemViewHolder> {

    private List<Promo> promos;
    private Picasso picasso;
    private Context context;

    public PromoListAdapter(List<Promo> promos, Picasso picasso, Context context) {
        this.promos = promos;
        this.picasso = picasso;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return promos.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }

    }

}
