package com.example.apiuniversity;

import static com.android.volley.VolleyLog.TAG;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UniversitiesAdapter extends RecyclerView.Adapter<UniversitiesAdapter.ViewHolder> {

    private List<University> universities;
    long DURATION = 300;

    public UniversitiesAdapter(List<University> universities) {
        this.universities = universities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.university_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        University university = universities.get(position);
        holder.textViewName.setText(university.getName());
        holder.textViewCountry.setText("Country: " + university.getCountry());
        List<String> websites = university.getWebPages();
        StringBuilder websiteListBuilder = new StringBuilder();
        websiteListBuilder.append("Website(s): ");
        for (String website : websites) {
            websiteListBuilder.append(website);
            websiteListBuilder.append(", ");
        }
        if (websites.size() > 0) {
            websiteListBuilder.delete(websiteListBuilder.length() - 2, websiteListBuilder.length()); // Remove the last comma and space
        }
        holder.textViewWebPage.setText(websiteListBuilder.toString());

        List<String> domains = university.getDomains();
        StringBuilder domainListBuilder = new StringBuilder();
        domainListBuilder.append("Domain(s): ");
        for (String domain : domains) {
            domainListBuilder.append(domain);
            domainListBuilder.append(", ");
        }
        if (domains.size() > 0) {
            domainListBuilder.delete(domainListBuilder.length() - 2, domainListBuilder.length()); // Remove the last comma and space
        }
        holder.textViewDomaine.setText(domainListBuilder.toString());
        FromRightToLeft(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return universities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewCountry;
        public TextView textViewWebPage;
        public TextView textViewDomaine;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewCountry = itemView.findViewById(R.id.text_view_country);
            textViewWebPage = itemView.findViewById(R.id.text_view_webSite);
            textViewDomaine = itemView.findViewById(R.id.text_view_Domaine);
        }
    }

    private void FromRightToLeft(View itemView, int i) {
        boolean on_attach = true;
        if(!on_attach){
            i = -1;
        }
        boolean not_first_item = i == -1;
        i = i + 1;
        itemView.setTranslationX(itemView.getX() + 20);
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", itemView.getX() + 400, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1.f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animatorTranslateY.setStartDelay(not_first_item ? DURATION : (i * DURATION));
        animatorTranslateY.setDuration((not_first_item ? 1 : 2) * DURATION);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();
    }
}
z