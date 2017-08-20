package rafael.com.br.lanchonete.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rafael.com.br.lanchonete.model.Order;

/**
 * Created by rafaelfreitas on 8/20/17.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ItemViewHolder> {

    private List<Order> orders;


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }

    }

}
