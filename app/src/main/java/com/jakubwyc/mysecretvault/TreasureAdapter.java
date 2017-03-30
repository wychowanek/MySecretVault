package com.jakubwyc.mysecretvault;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakubwyc.mysecretvault.model.Treasure;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TreasureAdapter extends RecyclerView.Adapter<TreasureAdapter.ViewHolder> {

    private final List<Treasure> treasures;
    private final TreasureOnClickListener onClickListener;

    public TreasureAdapter(final List<Treasure> treasures, final VaultActivity listener) {
        this.treasures = treasures;
        this.onClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.treasure_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(treasures.get(position).getText());
        String dateString = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date(treasures.get(position).getDate()));
        holder.date.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return treasures.size();
    }

    public void addTreasure(Treasure treasureSwiped, int adapterPosition) {
        treasures.add(adapterPosition, treasureSwiped);
        notifyItemInserted(adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView text;
        public TextView date;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.text);
            date = (TextView) v.findViewById(R.id.date);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onTreasureClick(treasures.get(getAdapterPosition()));
            }
        }
    }

    public Treasure getTreasureAt(int i) {
        return treasures.get(i);
    }

    public void removeTreasure(final Treasure treasure, final int adapterPosition) {
        treasures.remove(treasure);
        notifyItemRemoved(adapterPosition);
    }

}
