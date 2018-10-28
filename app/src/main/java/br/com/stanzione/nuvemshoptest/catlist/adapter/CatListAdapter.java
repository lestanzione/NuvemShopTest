package br.com.stanzione.nuvemshoptest.catlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.stanzione.nuvemshoptest.R;
import br.com.stanzione.nuvemshoptest.data.Cat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.ViewHolder> {

    private Context context;
    private List<Cat> catList = new ArrayList<>();

    public CatListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imageUrl = catList.get(position).getUrl();

        Picasso.with(context)
                .load(imageUrl)
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return (null != catList ? catList.size() : 0);
    }

    public void setItems(List<Cat> catList) {
        this.catList = catList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemCatImageView)
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
