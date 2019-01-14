package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatHistoryController;
import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatResponse;
//import com.example.rhuarhri.carmaintenancechatbot.chathistory.messageHistoryTable;

import java.util.ArrayList;
import java.util.List;

public class chatRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //public String[] chatResponses = {"Welcome", "hi", "what appears to be the problem", "lights don't work"};
    //public Boolean[] isFromUser = {false, true, false, true};

    //chatHistoryController pastResponseDB;
    private List<String> chatResponses;
    private List<String> responseFrom;

    public chatRVAdapter(List<chatResponse> history)
    {

        if(history.size() > 0 || history != null) {
            List<String> FoundChatResponses = new ArrayList<String>();
            List<String> FoundResponseFrom = new ArrayList<String>();

            for (int i = 0; i < history.size(); i++) {
                FoundChatResponses.add(history.get(i).getMessage());
                FoundResponseFrom.add(history.get(i).getFrom());
            }

            chatResponses =  FoundChatResponses;
            responseFrom = FoundResponseFrom;
        }

    }


    /*
    public void updateDisplayedChats()
    {
        List<messageHistoryTable> history = pastResponseDB.getHistory();
        List<String> FoundChatResponses = new ArrayList<String>();
        List<String> FoundResponseFrom = new ArrayList<String>();

        for (int i = 0; i < history.size(); i++)
        {
            FoundChatResponses.add(history.get(i).getMessage());
            FoundResponseFrom.add(history.get(i).getSentBy());
        }

        chatResponses = (String[]) FoundChatResponses.toArray();
        responseFrom = (String[]) FoundResponseFrom.toArray();


    }*/

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

        //updateDisplayedChats();

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
            viewHolder.responseTXT.setText(chatResponses.get(position));
        }else
        {
            chatbotResponseVH viewHolder = (chatbotResponseVH) holder;
            viewHolder.responseTXT.setText(chatResponses.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return chatResponses.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(responseFrom.get(position) == "user")
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
