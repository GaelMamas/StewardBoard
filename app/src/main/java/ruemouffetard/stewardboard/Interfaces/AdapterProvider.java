package ruemouffetard.stewardboard.Interfaces;

import android.view.View;

import ruemouffetard.stewardboard.Model.ModelBase;
import ruemouffetard.stewardboard.ViewHolder.BaseViewHolder;

/**
 * Created by admin on 25/03/2018.
 */

public interface AdapterProvider <T extends ModelBase>{

    int getLayoutId();

    BaseViewHolder getHolder(View view);

    void onItemClicked(T data, int position);

}
