package rafael.com.br.lanchonete.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rafael.com.br.lanchonete.R;
import rafael.com.br.lanchonete.model.Order;

/**
 * Created by rafaelfreitas on 8/20/17.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ItemViewHolder> {

    private List<Order> orders;
    private Picasso picasso;

    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public OrderListAdapter(List<Order> orders, Picasso picasso) {
        this.orders = orders;
        this.picasso = picasso;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflated = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_view, parent, false);
        return new ItemViewHolder(inflated);
    }



    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final Order order = orders.get(position);

        holder.title.setText(order.getLunch().getName());
        holder.price.setText("R$ " + order.getFinalPrice().toString());

        holder.ingredients.setText("Ingredientes: " + order.getLunch().getIngredientListDescription());
        holder.extras.setText("Extras: " + order.getDescriptionOfIngredientsExtras());
        holder.date.setText(formatter.format(order.getDate()));

        picasso.load(order.getLunch().getImage())
                .resize(75, 75)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img);
    }



    @Override
    public int getItemCount() {
        return orders.size();
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

        @BindView(R.id.txt_extra_ingredients)
        TextView extras;

        @BindView(R.id.txt_order_date)
        TextView date;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

}
