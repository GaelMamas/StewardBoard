package ruemouffetard.stewardboard.ViewHolder;

import android.view.View;
import android.widget.TextView;

import ruemouffetard.stewardboard.ExpensesDetailsFragment;
import ruemouffetard.stewardboard.R;

/**
 * Created by admin on 24/03/2018.
 */

public class EnterpriseCellHolder extends BaseViewHolder<ExpensesDetailsFragment.Title> {

    TextView textView;

    public EnterpriseCellHolder(View view) {
        super(view);
    }

    @Override
    protected void assignViews(View rootView) {

        textView = rootView.findViewById(R.id.text_enterprise);

    }

    @Override
    public void onBindViewHolder() {

        textView.setText(data.getTitle());

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        if(onChildClickedItemListener != null){
            onChildClickedItemListener.onChildClickedItem(view, data, getAdapterPosition());
        }
    }
}
