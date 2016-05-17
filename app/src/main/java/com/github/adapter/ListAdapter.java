package com.github.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.activity.R;
import com.github.model.ArrayModel;

import java.util.List;
/**
 * Created by shubham on 17/5/16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.DataObjectHolder> {
    private static String LOG_TAG = ListAdapter.class.getSimpleName();
    private List<ArrayModel> mDataset;
    private Context mContext;


    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txtEmail,txtMsg,txtHeader;

        public DataObjectHolder(View itemView) {
            super(itemView);

            txtHeader = (TextView)itemView.findViewById(R.id.txtHeader);
            txtEmail = (TextView)itemView.findViewById(R.id.txtEmail);
            txtMsg = (TextView)itemView.findViewById(R.id.txtMessage);
        }

    }


    public ListAdapter(Context context, List<ArrayModel> myDataset) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commit_item, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.txtHeader.setText(mDataset.get(position).getCommit().getCommitter().getName());
        holder.txtEmail.setText(mDataset.get(position).getCommit().getCommitter().getEmail());
        holder.txtMsg.setText(mDataset.get(position).getCommit().getMessage());
    }

    public void addItem(ArrayModel dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }




}
