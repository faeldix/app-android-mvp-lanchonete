package rafael.com.br.lanchonete.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rafael.com.br.lanchonete.R;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.presenter.LunchListPresenter;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class LunchListAdapter extends RecyclerView.Adapter<LunchListAdapter.ItemViewHolder> {

    private Context context;
    private LunchListPresenter presenter;
    private Picasso picasso;

    private List<Lunch> itens;

    public LunchListAdapter(LunchListPresenter presenter, Picasso picasso, List<Lunch> itens) {
        this.presenter = presenter;
        this.picasso = picasso;
        this.itens = itens;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflated = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lunch_view, parent, false);
        return new ItemViewHolder(inflated);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final Lunch lunch = itens.get(position);

        holder.title.setText(lunch.getName());
        holder.price.setText("R$ " + lunch.getPrice().toString());
        holder.ingredients.setText("Ingredientes: "+lunch.getIngredientListDescription());

        picasso.load(lunch.getImage())
                .resize(75, 75)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                presenter.onSelectAnLunchOfList(lunch);
            }

        });
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

        @BindView(R.id.txt_lunch_price)
        TextView price;

        @BindView(R.id.txt_lunch_ingredients)
        TextView ingredients;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

}
