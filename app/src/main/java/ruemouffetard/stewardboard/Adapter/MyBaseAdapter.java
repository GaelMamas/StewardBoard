package ruemouffetard.stewardboard.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ruemouffetard.stewardboard.Interfaces.AdapterProvider;
import ruemouffetard.stewardboard.Model.ModelBase;
import ruemouffetard.stewardboard.ViewHolder.BaseViewHolder;

/**
 * Created by admin on 23/03/2018.
 */

public class MyBaseAdapter<T extends ModelBase> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<T> mDataset;

    private AdapterProvider<T> myAdapterProvider;


    public MyBaseAdapter(Context context, List<T> myDataset) {
        this.context = context;
        this.mDataset = myDataset;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
         View view =LayoutInflater.from(context)
                .inflate(myAdapterProvider.getLayoutId(), parent, false);

         return myAdapterProvider.getHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        myAdapterProvider.onItemClicked(mDataset.get(position), position);
        holder.setData(mDataset.get(position));
        holder.onBindViewHolder();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setMyAdapterProvider(AdapterProvider<T> myAdapterProvider) {
        this.myAdapterProvider = myAdapterProvider;
    }

}
