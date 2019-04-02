package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatHistoryController;
import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatResponse;
//import com.example.rhuarhri.carmaintenancechatbot.chathistory.messageHistoryTable;

import java.util.ArrayList;
import java.util.List;

public class chatRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    //chatHistoryController pastResponseDB;
    private List<String> chatResponses;
    private List<String> responseFrom;
    private List<String> Emotions;
    private List<String> ImageLocations;
    private List<String> ImageDescriptons;

    responseImageHandler ImageHandler = new responseImageHandler();
    Context context;

    public chatRVAdapter(Context AppContext, List<chatResponse> history)
    {
        context = AppContext;

        if(history.size() > 0 || history != null) {
            List<String> FoundChatResponses = new ArrayList<String>();
            List<String> FoundResponseFrom = new ArrayList<String>();
            List<String> FoundEmotions = new ArrayList<String>();
            List<String> FoundImageLocations = new ArrayList<String>();
            List<String> FoundImageDescriptions = new ArrayList<String>();

            for (int i = 0; i < history.size(); i++) {
                FoundChatResponses.add(history.get(i).getMessage());
                FoundResponseFrom.add(history.get(i).getFrom());
                FoundEmotions.add(history.get(i).getEmotion());
                FoundImageLocations.add(history.get(i).getImage());
                FoundImageDescriptions.add(history.get(i).getImageDescription());
            }

            chatResponses =  FoundChatResponses;
            responseFrom = FoundResponseFrom;
            Emotions = FoundEmotions;
            ImageLocations = FoundImageLocations;
            ImageDescriptons = FoundImageLocations;
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
        public ImageView emotionIV;
        public ImageView infoIV;

        public chatbotResponseVH(View itemView) {
            super(itemView);

            responseTXT = (TextView) itemView.findViewById(R.id.responseTXT);
            emotionIV = (ImageView) itemView.findViewById(R.id.emotionIV);
            infoIV = (ImageView) itemView.findViewById(R.id.infoIV);
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
            ImageHandler.setEmotion(viewHolder.emotionIV, Emotions.get(position));
            ImageHandler.setInfoImage(context, viewHolder.infoIV,
                    ImageLocations.get(position), ImageDescriptons.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return chatResponses.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(responseFrom.get(position).equals("user"))
        {
            return 0;
        }
        else
        {
            return 1;
        }

    }

}
