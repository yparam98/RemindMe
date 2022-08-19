package yath.sfwrtech4sa3.remindme;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewAlliesAdapter extends RecyclerView.Adapter<ViewAlliesAdapter.ViewHolder> {
    private String[] display_names;
    private String[] picture_uris;

    public ViewAlliesAdapter() {}

    public ViewAlliesAdapter(String[] incoming_names, String[] incoming_picture_uis) {
        this.display_names = incoming_names;
        this.picture_uris = incoming_picture_uis;
    }

    @NonNull
    @Override
    public ViewAlliesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this is the code to instantiate the ViewHolder...
        // it is 3:02am in Pasa
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        // fancy ternary operator
        return display_names.length > picture_uris.length ? display_names.length : picture_uris.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public ViewHolder(CardView incoming_cardView) {
            // need to call super constructor b/c ViewHolder req. metadata for adapter to work
            super(incoming_cardView);
            this.cardView = incoming_cardView;
        }
    }

}
