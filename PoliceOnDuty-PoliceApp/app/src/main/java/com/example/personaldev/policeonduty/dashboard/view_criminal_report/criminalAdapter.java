package com.example.personaldev.policeonduty.dashboard.view_criminal_report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personaldev.policeonduty.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class criminalAdapter extends RecyclerView.Adapter<criminalAdapter.criminalAdapterVh> implements Filterable {

    private List<CriminalModel> criminalModelList;
    private List<CriminalModel> getCriminalModelListFiltered;
    private Context context;
    private SelectedCriminal selectedCriminal;

    public criminalAdapter(List<CriminalModel> criminalModelList,SelectedCriminal selectedCriminal) {
        this.criminalModelList = criminalModelList;
        this.getCriminalModelListFiltered = criminalModelList;
        this.selectedCriminal = selectedCriminal;
    }

    @NonNull
    @Override
    public criminalAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new criminalAdapterVh(LayoutInflater.from(context).inflate(R.layout.row_criminals, null));
    }

    @Override
    public void onBindViewHolder(@NonNull criminalAdapter.criminalAdapterVh holder, int position) {

        CriminalModel criminalModel = criminalModelList.get(position);

        String criminal_name = criminalModel.getCriminalName();
        String prefix = criminalModel.getCriminalName().substring(0, 1);

        holder.tvUsername.setText(criminal_name);
        holder.tvPrefix.setText(prefix);

    }

    @Override
    public int getItemCount() {
        return criminalModelList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence == null | charSequence.length()==0){
                    filterResults.count = getCriminalModelListFiltered.size();
                    filterResults.values = getCriminalModelListFiltered;

                }
                else{
                    String searchChar = charSequence.toString().toLowerCase();

                    List<CriminalModel> resultsData = new ArrayList<>();

                    for(CriminalModel criminalModel: getCriminalModelListFiltered){
                        if(criminalModel.getCriminalName().toLowerCase().contains(searchChar)){
                            resultsData.add(criminalModel);
                        }
                    }
                    filterResults.count = resultsData.size();
                    filterResults.values = resultsData;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {

                criminalModelList = (List<CriminalModel>) filterResults.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }

    public interface SelectedCriminal{

        void selectedCriminal(CriminalModel criminalModel);

    }


    public class criminalAdapterVh extends RecyclerView.ViewHolder {

        TextView tvPrefix;
        TextView tvUsername;
        ImageView imgIcon;

        public criminalAdapterVh(@NonNull View itemView) {
            super(itemView);

            tvPrefix = itemView.findViewById(R.id.prefix);
            tvUsername = itemView.findViewById(R.id.criminal_name);
            imgIcon = itemView.findViewById(R.id.image_next);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedCriminal.selectedCriminal(criminalModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}
