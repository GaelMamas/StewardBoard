package ruemouffetard.stewardboard.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import ruemouffetard.stewardboard.Model.ModelBase;

/**
 * Created by admin on 23/03/2018.
 */

public abstract class BaseViewHolder<T extends ModelBase> extends RecyclerView.ViewHolder implements View.OnClickListener {

    private View rootView;
    protected T data;

    protected OnChildClickedItemListener onChildClickedItemListener;

    BaseViewHolder(View v) {
        super(v);

        this.rootView = v;
        assignViews(v);
    }

    protected abstract void assignViews(View rootView);


    public abstract void onBindViewHolder();

    public void setData(T data) {
        this.data = data;
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