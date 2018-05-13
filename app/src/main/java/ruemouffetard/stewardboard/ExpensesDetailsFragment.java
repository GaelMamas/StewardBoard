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

import com.google.common.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ruemouffetard.stewardboard.Adapter.MyBaseAdapter;
import ruemouffetard.stewardboard.Interfaces.AdapterProvider;
import ruemouffetard.stewardboard.Model.ExpensesSheet;
import ruemouffetard.stewardboard.Model.ExpensesTable;
import ruemouffetard.stewardboard.Model.ModelBase;
import ruemouffetard.stewardboard.Model.ProjectInvestmentItem;
import ruemouffetard.stewardboard.Model.UsualExpenseItem;
import ruemouffetard.stewardboard.ViewHolder.BaseViewHolder;
import ruemouffetard.stewardboard.ViewHolder.EnterpriseCellHolder;
import ruemouffetard.stewardboard.ViewHolder.ExpensesTableCellHolder;
import ruemouffetard.stewardboard.Widgets.WeighBalance;
import ruemouffetard.stewardboard.databinding.CellEnterpriseBinding;
import ruemouffetard.stewardboard.databinding.CellExpensesTableBinding;

/**
 * Created by admin on 28/01/2018.
 */

@SuppressLint("ValidFragment")
public class ExpensesDetailsFragment extends Fragment {

    private TextView mHeaderTextView, mBalanceTextView;
    private RecyclerView mExpensesRecyclerView, mEnterprisesRecyclerView;
    private ProgressBar mProgressBar;

    private WeighBalance weighBalance;

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

        weighBalance = rootView.findViewById(R.id.view_enterprise_state);

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
                //mProgressBar.setVisibility(View.VISIBLE);
                //view.setEnabled(false);

                if(weighBalance != null){
                    weighBalance.setWeighBalance(new WeighBalance.LoadForBalance(1.00001f, android.R.drawable.ic_menu_call),
                            new WeighBalance.LoadForBalance(10, android.R.drawable.ic_menu_agenda));
                }

               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        mBalanceTextView.setEnabled(true);
                    }
                }, 1000);*/
            }
        });
        //TODO END TEST
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Expenses expensesSheet = UsefulMehtod.setDefaultModelData(getResources().openRawResource(R.raw.expenses), new TypeToken<Expenses>() {
        });

        Enterprises enterprises = UsefulMehtod.setDefaultModelData(getResources().openRawResource(R.raw.enterprises), new TypeToken<Enterprises>() {
        });

        ExpensesBudgeting expensesBudgeting = UsefulMehtod.setDefaultModelData(getResources().openRawResource(R.raw.usual_expense_items), new TypeToken<ExpensesBudgeting>() {
        });


        mExpensesRecyclerView.setAdapter(setAdapterExpenses(expensesSheet != null ?
                expensesSheet.getExpensesSheet() != null ?
                        expensesSheet.getExpensesSheet().getExpensesTables() : null : null));

        mEnterprisesRecyclerView.setAdapter(setAdapterEnterprises(enterprises != null ? enterprises.getProjectInvestmentItems() : null));


        if(expensesBudgeting != null && expensesBudgeting.getUsualExpenseItems() != null && expensesBudgeting.getUsualExpenseItems().size() > 0) {

            float centralExecutionBudget = 0, centralLeftovers = 0;
            //TODO FETCH the currency from the preferences
            String currency = expensesBudgeting.getUsualExpenseItems().get(0).getCurrency();

            for (UsualExpenseItem usualExpenseItem: expensesBudgeting.getUsualExpenseItems()){

                centralExecutionBudget += usualExpenseItem.getMonthlyDefinedAmount();
                centralLeftovers += usualExpenseItem.getLeftOvers();

            }

            mHeaderTextView.setText(getString(R.string.checkup_expenses_central_budget, currency,centralExecutionBudget));

            mBalanceTextView.setText(getString(R.string.checkup_expenses_central_budget, currency, centralLeftovers));

        }
    }

    private MyBaseAdapter<ExpensesTable, CellExpensesTableBinding> setAdapterExpenses(List<ExpensesTable> modelData) {

        MyBaseAdapter<ExpensesTable, CellExpensesTableBinding> adapter = new MyBaseAdapter<>(getContext(),
                (modelData == null || modelData.size() == 0) ? new ArrayList<ExpensesTable>() : modelData);

        adapter.setMyAdapterProvider(new AdapterProvider<ExpensesTable, CellExpensesTableBinding>() {
            @Override
            public int getLayoutId() {
                return R.layout.cell_expenses_table;
            }

            @Override
            public int getVariableId() {
                return BR.expensesTable;
            }

            @Override
            public BaseViewHolder getHolder(CellExpensesTableBinding binding) {
                ExpensesTableCellHolder cellHolder = new ExpensesTableCellHolder(binding);

                cellHolder.setOnChildClickedItemListener(new BaseViewHolder.OnChildClickedItemListener<ExpensesTable>() {

                    @Override
                    public void onChildClickedItem(View view, ExpensesTable data, int position) {
                        Toast.makeText(getContext(), "Bonjour les amis", Toast.LENGTH_SHORT).show();
                    }
                });

                return cellHolder;
            }

            @Override
            public void onItemClicked(ExpensesTable data, int position) {

            }
        });

        return adapter;

    }

    private MyBaseAdapter<ProjectInvestmentItem, CellEnterpriseBinding> setAdapterEnterprises(List<ProjectInvestmentItem> modelData) {

        MyBaseAdapter<ProjectInvestmentItem, CellEnterpriseBinding> adapter = new MyBaseAdapter<>(getContext(),
                (modelData == null || modelData.size() == 0) ? new ArrayList<ProjectInvestmentItem>() : modelData);

        adapter.setMyAdapterProvider(new AdapterProvider<ProjectInvestmentItem, CellEnterpriseBinding>() {
            @Override
            public int getLayoutId() {
                return R.layout.cell_enterprise;
            }

            @Override
            public int getVariableId() {
                return BR.enterprise;
            }

            @Override
            public BaseViewHolder getHolder(CellEnterpriseBinding binding) {
                EnterpriseCellHolder cellHolder = new EnterpriseCellHolder(binding);

                cellHolder.setOnChildClickedItemListener(new BaseViewHolder.OnChildClickedItemListener<ProjectInvestmentItem>() {


                    @Override
                    public void onChildClickedItem(View view, ProjectInvestmentItem data, int position) {
                        Toast.makeText(getContext(), "Bonjour les amis", Toast.LENGTH_SHORT).show();
                    }
                });

                return cellHolder;
            }

            @Override
            public void onItemClicked(ProjectInvestmentItem data, int position) {

            }
        });

        return adapter;

    }

    public static class Enterprises extends ModelBase {

        List<ProjectInvestmentItem> projectInvestmentItems;

        public List<ProjectInvestmentItem> getProjectInvestmentItems() {
            return projectInvestmentItems;
        }
    }

    public static class Expenses extends ModelBase {

        ExpensesSheet expensesSheet;

        public ExpensesSheet getExpensesSheet() {
            return expensesSheet;
        }
    }

    public static class ExpensesBudgeting extends ModelBase{

        List<UsualExpenseItem> usualExpenseItems;

        public List<UsualExpenseItem> getUsualExpenseItems() {
            return usualExpenseItems;
        }
    }
}
