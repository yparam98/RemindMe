package yath.sfwrtech4sa3.remindme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;

public class ViewAlliesAdapter extends RecyclerView.Adapter<ViewAlliesAdapter.ViewHolder> {
    private String[] uids;
    private String[] display_names;
    private String[] profile_picture_uris;

    public ViewAlliesAdapter() {}

    public ViewAlliesAdapter(String[] incoming_uids, String[] incoming_names, String[] incoming_picture_uris) {
        this.uids = incoming_uids;
        this.display_names = incoming_names;
        this.profile_picture_uris = incoming_picture_uris;
    }

    @NonNull
    @Override
    public ViewAlliesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this is the code to instantiate the ViewHolder...

        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);
        return new ViewAlliesAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;


        try {
            ImageView imageView = (ImageView) cardView.findViewById(R.id.ally_card_info_image);
            InputStream inputStream = (InputStream) new URL(this.profile_picture_uris[position]).getContent();

            imageView.setImageDrawable(Drawable.createFromStream(inputStream, "url: " + this.profile_picture_uris[position]));
            imageView.setContentDescription(this.display_names[position] + " profile picture");
        } catch (Exception exception) {
            Log.d("yathavan", "Error accessing profile picture for CardView");
        }

        TextView textView = (TextView) cardView.findViewById(R.id.ally_card_info_text);
        textView.setText(this.display_names[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper();
                databaseHelper.getUser(uids[holder.getAdapterPosition()], new ViewsCallback() {
                    @Override
                    public void isUserExist(boolean doesExist, User user) {
                        if (doesExist) {
                            FragmentManager fragmentManager = ((AppCompatActivity)cardView.getContext()).getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.homescreen_transaction_frame, new TaskListView(user));
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        // fancy ternary operator
        return this.display_names.length > this.profile_picture_uris.length ? this.display_names.length : this.profile_picture_uris.length;
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
