package rs.ac.singidunum.madexam.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import rs.ac.singidunum.madexam.Environment;
import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.activities.FlightActivity;
import rs.ac.singidunum.madexam.api.models.FlightModel;

public class FlightAdapter extends RecyclerView.Adapter {

    Context context;
    List<FlightModel> flights;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView flightNumberTextView;
        TextView destinationTextView;
        ImageView iconImageView;
        ImageButton detailsImageButton;
        ImageButton startImageButton;

        public MyViewHolder(@NotNull View itemView) {
            super(itemView);
            view = itemView;
            flightNumberTextView = itemView.findViewById(R.id.flightNumberTextView);
            destinationTextView = itemView.findViewById(R.id.destinationTextView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            detailsImageButton = itemView.findViewById(R.id.detailsImageButton);
            startImageButton = itemView.findViewById(R.id.starImageButton);
        }

    }

    public FlightAdapter(Context context) {
        this.context = context;
        this.flights = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_flight, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FlightModel flight = flights.get(position);
        MyViewHolder hldr = (MyViewHolder) holder;

        hldr.flightNumberTextView.setText(flight.getFlightNumber());
        hldr.destinationTextView.setText(flight.getDestination());

        hldr.detailsImageButton.setOnClickListener(v -> {

            Intent intent = new Intent(context, FlightActivity.class);
            Bundle extras = new Bundle();

            extras.putInt("id", flight.getId());
            extras.putString("flightKey", flight.getFlightKey());
            extras.putString("flightNumber", flight.getFlightNumber());
            extras.putString("destination", flight.getDestination());
            extras.putLong("scheduledAtMilis", flight.getScheduledAt() != null ? flight.getScheduledAt().getTime() : 0);
            extras.putLong("estimatedAtMilis", flight.getEstimatedAt() != null ? flight.getEstimatedAt().getTime() : 0);
            extras.putString("connectedType", flight.getConnectedType());
            extras.putString("connectedFlight", flight.getConnectedFlight());
            extras.putString("plane", flight.getPlane());
            extras.putString("gate", flight.getGate());
            extras.putString("terminal", flight.getTerminal());

            intent.putExtras(extras);
            context.startActivity(intent);

        });

        String imageName = "/" + flight.getDestination().split(" ")[0].toLowerCase() + ".jpg";
        Glide.with(hldr.view)
                .load(Environment.DESTINATION_IMAGE_URL + imageName)
                .placeholder(R.drawable.flight_placeholder)
                .into(hldr.iconImageView);
    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    public void setData(List<FlightModel> flights) {
        this.flights.clear();
        this.flights.addAll(flights);
        notifyDataSetChanged();
    }

    public void addData(List<FlightModel> flights) {
        this.flights.addAll(flights);
        notifyDataSetChanged();
    }

}
