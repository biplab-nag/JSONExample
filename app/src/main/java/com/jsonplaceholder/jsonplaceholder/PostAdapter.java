package com.jsonplaceholder.jsonplaceholder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * Created by biplab on 13-Mar-18.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context context;
    private List<PostResponse> PostDataList;
    private List<UserResponse> UserDataList;
    int previousposition =0;

    public PostAdapter(Context context, List<PostResponse> postDataList, List<UserResponse> userDataList) {
        this.context = context;
        this.PostDataList = postDataList;
        this.UserDataList=userDataList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.user_post_row,parent,false);
        return new PostViewHolder(v);

    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.postTitleTV.setText(PostDataList.get(position).getTitle());
        for(int i = 0 ; i<UserDataList.size();i++){
            if(PostDataList.get(position).getUserId()==UserDataList.get(i).getId()){
                 holder.useNameTV.setText(UserDataList.get(i).getName());
            }
        }


        if(position > previousposition){

            AnimationUtil.animate(holder,true);
        }
        else{
            AnimationUtil.animate(holder,false);
        }
        previousposition = position;
    }

    @Override
    public int getItemCount() {
        return PostDataList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        TextView postTitleTV;
        TextView useNameTV;

        public PostViewHolder(View itemView) {
            super(itemView);
            postTitleTV = itemView.findViewById(R.id.userPostTitle);
            useNameTV = itemView.findViewById(R.id.userName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getAdapterPosition();

                    final AlertDialog.Builder dialog;
                    dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Post Details ");
                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    LinearLayout ll = (LinearLayout) layoutInflater.inflate(R.layout.post_details_row,null,false);

                    final TextView TitleTV = ll.findViewById(R.id.PostTitle);
                    final TextView DetailsTV = ll.findViewById(R.id.PostDetails);
                    final TextView UsernameTV = ll.findViewById(R.id.user);
                    final TextView EmailTV = ll.findViewById(R.id.userEmail);

                    TitleTV.setText(PostDataList.get(itemPosition).getTitle().toString());
                    DetailsTV.setText(PostDataList.get(itemPosition).getBody().toString());

                    for(int i=0; i<UserDataList.size();i++){
                        if(PostDataList.get(itemPosition).getUserId()==UserDataList.get(i).getId()){
                            UsernameTV.setText("User :"+String.valueOf(UserDataList.get(i).getName()));
                            EmailTV.setText("Email :"+String.valueOf(UserDataList.get(i).getEmail()));
                        }
                    }

                    dialog.setView(ll);
                    dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.show();



                }
            });
        }
    }
}
