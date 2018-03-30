package ruemouffetard.stewardboard.ViewHolder;

import android.view.View;
import android.widget.TextView;

import ruemouffetard.stewardboard.Model.ProjectInvestmentItem;
import ruemouffetard.stewardboard.databinding.CellEnterpriseBinding;

/**
 * Created by admin on 24/03/2018.
 */

public class EnterpriseCellHolder extends BaseViewHolder<ProjectInvestmentItem, CellEnterpriseBinding> {


    public EnterpriseCellHolder(CellEnterpriseBinding binding) {
        super(binding);
    }


    @Override
    public void onBindViewHolder() {

        //textView.setText(data.getTitle());

        //textView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        //TODO WHAT IS GENERIFY FOR?
        if (onChildClickedItemListener != null) {
            onChildClickedItemListener.onChildClickedItem(view, data, getAdapterPosition());
        }
    }
}
