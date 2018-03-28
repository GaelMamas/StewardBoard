package ruemouffetard.stewardboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ruemouffetard.stewardboard.Adapter.MyBaseAdapter;
import ruemouffetard.stewardboard.Interfaces.AdapterProvider;
import ruemouffetard.stewardboard.Model.ExpensesTable;
import ruemouffetard.stewardboard.Model.ModelBase;
import ruemouffetard.stewardboard.Model.ProjectInvestmentItem;
import ruemouffetard.stewardboard.ViewHolder.BaseViewHolder;
import ruemouffetard.stewardboard.ViewHolder.ExpensesTableCellHolder;

/**
 * Created by admin on 28/01/2018.
 */

@SuppressLint("ValidFragment")
public class ExpensesDetailsFragment extends Fragment {

    private TextView mHeaderTextView, mBalanceTextView;
    private RecyclerView mExpensesRecyclerView, mEnterprisesRecyclerView;
    private ProgressBar mProgressBar;

    public ExpensesDetailsFragment() {
    }

    public static Fragment newInstance(int position) {

        Bundle bundle = new Bundle();


        ExpensesDetailsFragment expensesDetailsFragment = new ExpensesDetailsFragment();

        bundle.putInt("Constant int", position);
        expensesDetailsFragment.setArguments(bundle);

        return expensesDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expenses_details, container, false);

        assignViews(view);
        populateViews(savedInstanceState);

        return view;
    }

    private void assignViews(View rootView) {
        mHeaderTextView = rootView.findViewById(R.id.text_expense_header_title);
        mBalanceTextView = rootView.findViewById(R.id.text_expense_balance);

        mExpensesRecyclerView = rootView.findViewById(R.id.recyclerview_expenses);
        mEnterprisesRecyclerView = rootView.findViewById(R.id.recyclerview_enterprises);

        mProgressBar = rootView.findViewById(R.id.progressbar_expense_details);

    }

    private void populateViews(Bundle savedInstanceState) {

        mExpensesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        mEnterprisesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        mExpensesRecyclerView.setHasFixedSize(true);
        mEnterprisesRecyclerView.setHasFixedSize(true);

        //TODO START TEST
        mBalanceTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        mBalanceTextView.setEnabled(true);
                    }
                }, 5000);
            }
        });
        //TODO END TEST
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ExpensesModel expensesModelData = UsefulMehtod.setDefaultModelData(getResources().openRawResource(R.raw.expenses));

        List<ProjectInvestmentItem> enterprisesModelData = UsefulMehtod.setDefaultModelDataList(getResources().openRawResource(R.raw.enterprises));

        mHeaderTextView.setText("39409309€");

        mBalanceTextView.setText("293039€");


        mExpensesRecyclerView.setAdapter(setAdapter(expensesModelData != null ?
                expensesModelData.getExpensesTables() : null, R.layout.cell_expenses_table));

        mEnterprisesRecyclerView.setAdapter(setAdapter(enterprisesModelData,
                R.layout.cell_enterprise));
    }

    private <T extends ModelBase> MyBaseAdapter<T> setAdapter(List<T> modelData, final int resourceId) {

        MyBaseAdapter<T> adapter = new MyBaseAdapter<>(getContext(),
                (modelData == null || modelData.size() == 0) ? new ArrayList<T>() : modelData);

        adapter.setMyAdapterProvider(new AdapterProvider<T>() {
            @Override
            public int getLayoutId() {
                return resourceId;
            }

            @Override
            public BaseViewHolder getHolder(View view) {
                ExpensesTableCellHolder cellHolder = new ExpensesTableCellHolder(view);

                cellHolder.setOnChildClickedItemListener(new BaseViewHolder.OnChildClickedItemListener<T>() {


                    @Override
                    public void onChildClickedItem(View view, T data, int position) {
                        Toast.makeText(getContext(), "Bonjour les amis", Toast.LENGTH_SHORT).show();
                    }
                });

                return cellHolder;
            }

            @Override
            public void onItemClicked(T data, int position) {

            }
        });

        return adapter;

    }

    private class ExpensesModel extends ModelBase {

        private String sheetName;
        private List<ExpensesTable> expensesTables;

        public String getSheetName() {
            return sheetName;
        }

        public List<ExpensesTable> getExpensesTables() {
            return expensesTables;
        }
    }
}
