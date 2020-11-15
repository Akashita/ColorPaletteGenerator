package com.nakashita.tpmobile.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nakashita.tpmobile.R;
import com.nakashita.tpmobile.modele.ColorBundle;
import com.nakashita.tpmobile.storage.ColorsJsonFileStorage;

public abstract class BundleAdapter extends RecyclerView.Adapter<BundleAdapter.BundleHolder> {

    private final Context context;

    public BundleAdapter(Context context){
        this.context = context;
    }

    static class BundleHolder extends RecyclerView.ViewHolder{
        public View color1;
        public View color2;
        public View color3;

        public TextView label;

        public BundleHolder(@NonNull View itemView) {
            super(itemView);
            color1 = itemView.findViewById(R.id.saved_color1);
            color2 = itemView.findViewById(R.id.saved_color2);
            color3 = itemView.findViewById(R.id.saved_color3);
            label = itemView.findViewById(R.id.saved_label);
        }
    }

    @NonNull
    @Override
    public BundleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Getting the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bundle_color, parent, false);

        // ---------------------
        // Listeners
        // ---------------------
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return onItemLongClick(v);
            }
        });
        return new BundleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BundleHolder holder, int position) {

        //Find current palette from the file storage
        ColorBundle bundle = ColorsJsonFileStorage.get(context).findAll().get(position);

        // ---------------------
        // Set the view specification on the current holder
        // ---------------------
        holder.itemView.setTag(bundle.getId());
        holder.label.setText(bundle.getLabel());
        holder.color1.setBackgroundColor(bundle.getColor(ColorBundle.COLOR1).getHexa());
        holder.color2.setBackgroundColor(bundle.getColor(ColorBundle.COLOR2).getHexa());
        holder.color3.setBackgroundColor(bundle.getColor(ColorBundle.COLOR3).getHexa());
    }

    @Override
    public int getItemCount() {
        if (ColorsJsonFileStorage.get(context).findAll() == null)
            return 0;
        return ColorsJsonFileStorage.get(context).findAll().size();
    }

    public abstract void onItemClick(View v);

    public abstract boolean onItemLongClick(View v);
}
