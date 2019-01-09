package com.example.rhuarhri.carmaintenancechatbot;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class chatRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public String[] chatResponses = {"Welcome", "hi", "what appears to be the problem", "lights don't work"};
    public Boolean[] isFromUser = {false, true, false, true};

    public class userResponseVH extends RecyclerView.ViewHolder{

        public TextView responseTXT;

        public userResponseVH(View itemView) {
            super(itemView);

            responseTXT = (TextView) itemView.findViewById(R.id.responseTXT);
        }
    }

    public class chatbotResponseVH extends RecyclerView.ViewHolder{

        public TextView responseTXT;

        public chatbotResponseVH(View itemView) {
            super(itemView);

            responseTXT = (TextView) itemView.findViewById(R.id.responseTXT);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View newView;

        if(viewType == 0)
        {
            //is user response
            newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_response_layout, parent, false);
            return new userResponseVH(newView);
        }
        else
        {
            //chat bot response
            newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbot_response_layout, parent, false);
            return new chatbotResponseVH(newView);
        }





    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder.getItemViewType() == 0)
        {
            userResponseVH viewHolder = (userResponseVH) holder;
            viewHolder.responseTXT.setText(chatResponses[position]);
        }else
        {
            chatbotResponseVH viewHolder = (chatbotResponseVH) holder;
            viewHolder.responseTXT.setText(chatResponses[position]);
        }

    }

    @Override
    public int getItemCount() {
        return chatResponses.length;
    }

    @Override
    public int getItemViewType(int position) {

        if(isFromUser[position] == true)
        {
            return 0;
        }
        else
        {
            return 1;
        }

        //return super.getItemViewType(position);
    }



}
