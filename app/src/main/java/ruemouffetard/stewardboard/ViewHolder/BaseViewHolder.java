package ruemouffetard.stewardboard.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ruemouffetard.stewardboard.Model.ModelBase;

/**
 * Created by admin on 23/03/2018.
 */

public class BaseViewHolder<T extends ModelBase> extends RecyclerView.ViewHolder {

    private View rootView;
    private T data;

    public BaseViewHolder(View v) {
        super(v);

        assignViews(v);
    }

    public void assignViews(View rootView) {
        this.rootView = rootView;
    }


    public void onBindViewHolder(T data){
        this.data = data;
    }
}