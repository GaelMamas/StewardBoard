package ruemouffetard.stewardboard.ViewHolder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ruemouffetard.stewardboard.Model.ModelBase;

/**
 * Created by admin on 23/03/2018.
 */

public abstract class BaseViewHolder<T extends ModelBase,VDB extends ViewDataBinding> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected VDB mBinding;
    protected T data;


    protected OnChildClickedItemListener onChildClickedItemListener;

    BaseViewHolder(VDB binding) {
        super(binding.getRoot());

        this.mBinding = binding;
    }


    public abstract void onBindViewHolder();

    public void setData(int variableId, T data) {
        mBinding.setVariable(variableId, data);
        mBinding.executePendingBindings();
    }

    public void setOnChildClickedItemListener(OnChildClickedItemListener onChildClickedItemListener) {
        this.onChildClickedItemListener = onChildClickedItemListener;
    }

    @Override
    public void onClick(View view) {

    }

    public interface OnChildClickedItemListener<T extends ModelBase> {

        void onChildClickedItem(View view, T data, int position);

    }
}