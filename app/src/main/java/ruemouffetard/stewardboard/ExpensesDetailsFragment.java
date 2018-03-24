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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ruemouffetard.stewardboard.Adapter.MyBaseAdapter;
import ruemouffetard.stewardboard.Model.ModelBase;
import ruemouffetard.stewardboard.ViewHolder.BaseViewHolder;

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

        mHeaderTextView.setText("39409309€");

        mBalanceTextView.setText("293039€");

        String [] stringsTest = new String[]{"eurhfeir", "erfefef", "eferf", "eferf", "zezer", "zrezer", "rrzer", "zrzr", "zerzer", "zrerr", "rezrzrz", "zrzer", "tryytr", "yyyyytutt"};

        List<Title> titles = new ArrayList<>();

        for (int i = 0; i < stringsTest.length; i++) {
            titles.add(new Title(stringsTest[i]));
        }

        MyBaseAdapter<Title> myAdapter = new MyBaseAdapter<>(getContext(), titles);

        myAdapter.setMyAdapterProvider(new MyBaseAdapter.AdapterProvider<Title>() {
            @Override
            public int getLayoutId() {
                return R.layout.cell_enterprise;
            }

            @Override
            public BaseViewHolder getHolder(View view) {
                return null;
            }

            @Override
            public void setData(Title data, int position) {

            }
        });

        mExpensesRecyclerView.setAdapter(myAdapter);
        mEnterprisesRecyclerView.setAdapter(myAdapter);
    }

    public static class Title extends ModelBase{

        private String title;

        public Title(String title){
            this.title = title;
        }

        public String getTitle(){
            return title;
        }
    }
}
