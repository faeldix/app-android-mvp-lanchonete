package rafael.com.br.lanchonete.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rafael.com.br.lanchonete.R;
import rafael.com.br.lanchonete.model.Lunch;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class LunchListAdapter extends RecyclerView.Adapter<LunchListAdapter.ItemViewHolder> {

    private List<Lunch> itens;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View inflated = inflater.inflate(R.layout.item_lunch_view, parent, false);

        return new ItemViewHolder(inflated);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_lunch_img)
        ImageView img;

        @BindView(R.id.txt_lunch_title)
        TextView title;

        @BindView(R.id.txt_lunch_desc)
        TextView description;

        @BindView(R.id.txt_lunch_ingredients)
        TextView ingredients;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

}
