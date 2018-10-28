package br.com.stanzione.nuvemshoptest.doglist.adapter;

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
import br.com.stanzione.nuvemshoptest.data.Dog;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.ViewHolder> {

    public interface OnDogSelectedListener {
        void onDogImageSelected(int position);
    }

    private Context context;
    private List<Dog> dogList = new ArrayList<>();
    private OnDogSelectedListener listener;

    public DogListAdapter(Context context, OnDogSelectedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_dog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imageUrl = dogList.get(position).getUrl();

        Picasso.with(context)
                .load(imageUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(view -> listener.onDogImageSelected(position));

    }

    @Override
    public int getItemCount() {
        return (null != dogList ? dogList.size() : 0);
    }

    public void setItems(List<Dog> DogList) {
        this.dogList = DogList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemDogImageView)
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
