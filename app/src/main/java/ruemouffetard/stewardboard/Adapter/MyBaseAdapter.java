package ruemouffetard.stewardboard.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ruemouffetard.stewardboard.Interfaces.AdapterProvider;
import ruemouffetard.stewardboard.Model.ModelBase;
import ruemouffetard.stewardboard.ViewHolder.BaseViewHolder;
import ruemouffetard.stewardboard.databinding.CellExpensesTableBinding;

/**
 * Created by admin on 23/03/2018.
 */

public class MyBaseAdapter<T extends ModelBase, VDB extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<T> mDataset;

    private AdapterProvider<T, VDB> myAdapterProvider;


    public MyBaseAdapter(Context context, List<T> myDataset) {
        this.context = context;
        this.mDataset = myDataset;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        VDB vdb = DataBindingUtil.inflate(inflater, myAdapterProvider.getLayoutId(), parent, false);

        return myAdapterProvider.getHolder(vdb);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        myAdapterProvider.onItemClicked(mDataset.get(position), position);
        holder.setData(myAdapterProvider.getVariableId() ,mDataset.get(position));
        holder.onBindViewHolder();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setMyAdapterProvider(AdapterProvider<T, VDB> myAdapterProvider) {
        this.myAdapterProvider = myAdapterProvider;
    }

}
