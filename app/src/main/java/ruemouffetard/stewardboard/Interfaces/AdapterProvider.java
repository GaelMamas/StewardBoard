package ruemouffetard.stewardboard.Interfaces;

import android.databinding.ViewDataBinding;
import android.view.View;

import ruemouffetard.stewardboard.Model.ModelBase;
import ruemouffetard.stewardboard.ViewHolder.BaseViewHolder;

/**
 * Created by admin on 25/03/2018.
 */

public interface AdapterProvider <T extends ModelBase, VDB extends ViewDataBinding>{

    int getLayoutId();

    BaseViewHolder getHolder(VDB vdb);

    void onItemClicked(T data, int position);

    int getVariableId();

}
