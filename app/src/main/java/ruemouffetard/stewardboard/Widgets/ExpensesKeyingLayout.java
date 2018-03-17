package ruemouffetard.stewardboard.Widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ruemouffetard.stewardboard.Model.ExpenseItem;
import ruemouffetard.stewardboard.R;
import ruemouffetard.stewardboard.UsefulMehtod;

/**
 * Created by admin on 14/03/2018.
 */

public class ExpensesKeyingLayout extends ConstraintLayout implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //février 2018/repas avec d'autres/16.45£ d02 Pizza Mateo
    //février 2018!A23

    private BetterSpinner mExpensesTablePicker;
    private EditText mExpenseItemField;
    private BetterSpinner mCurrencyPicker; //TODO When cryptocurrencies
    private EditText mMiscellaneousEditText;

    private CheckedTextView mTodayExpenseButton;

    private Button mValidateButton;

    private ProgressBar mProgressBar;

    private View rootView;

    private String sheetName, mExpensesTable, mCurrency;
    private boolean isTablePickerOK, isCurrencyPickerOK, isCostFieldOK;

    public ExpensesKeyingLayout(Context context) {
        this(context, null);


    }

    public ExpensesKeyingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        rootView = inflate(context, R.layout.board_expenses_keying, this);

        mExpensesTablePicker = rootView.findViewById(R.id.spinner_expenses_table_inputs);
        mExpenseItemField = rootView.findViewById(R.id.edittext_expense_input);
        mCurrencyPicker = rootView.findViewById(R.id.spinner_expense_currency);
        mMiscellaneousEditText = rootView.findViewById(R.id.edittext_expense_miscellaneous);
        mTodayExpenseButton = rootView.findViewById(R.id.checkedTextView_expense_occurred_date);
        mValidateButton = rootView.findViewById(R.id.button_expense_input_publish);
        mProgressBar = rootView.findViewById(R.id.progressbar_expense_in_saving);


    }

    @SuppressLint("SimpleDateFormat")
    private void init(Context context, List<String> expensesTableNames, String sheetName) {

        this.sheetName = sheetName;

        ArrayAdapter<String> expensesTableAdapter = new ArrayAdapter<>(context,
                R.layout.test_text, expensesTableNames);

        mExpensesTablePicker.setAdapter(expensesTableAdapter);

        //TODO Set up the actual currencies https://gist.github.com/Fluidbyte/2973986#file-common-currency-json
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(context,
                R.layout.test_text, new String[]{"€", "£", "$"});

        mCurrencyPicker.setAdapter(currencyAdapter);

        mTodayExpenseButton.setText(context.getString(R.string.keying_expense_occurred_date,
                new SimpleDateFormat("dd/mm/yyyy")
                        .format(new Date(System.currentTimeMillis()))));

        mExpensesTablePicker.setOnItemSelectedListener(this);
        mCurrencyPicker.setOnItemSelectedListener(this);

        mTodayExpenseButton.setOnClickListener(this);
        mValidateButton.setOnClickListener(this);

        mMiscellaneousEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isCostFieldOK = !TextUtils.isEmpty(editable);
                enablePublishButton();
            }
        });

    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //TODO START TEST
        List<String> testList = new ArrayList<>();
        testList.add("Courses Alimentaires");
        testList.add("Déjeuner");
        testList.add("Shopping");
        //TODO END TEST


        init(getContext(), testList, "March 2018");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.checkedTextView_expense_occurred_date:

                //TODO Pop up the Dialog Picker
                Toast.makeText(getContext(), "Pop the Dialog Picker", Toast.LENGTH_SHORT).show();

                break;
            case R.id.button_expense_input_publish:

                if (mProgressBar.getVisibility() == GONE) {

                    mProgressBar.setVisibility(VISIBLE);

                    //TODO Flush in to Data base
                    Toast.makeText(getContext(),
                            UsefulMehtod.makeUpRequestContent(
                                    UsefulMehtod.saveExpenseItem(sheetName, mExpensesTable, mCurrency,
                                            mMiscellaneousEditText.getText().toString(),
                                            Float.parseFloat(mExpenseItemField.getText().toString()))),
                            Toast.LENGTH_SHORT).show();

                    //TODO START Set up an async request
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(GONE);
                        }
                    }, 3000);
                    //TODO END
                }

                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()){
            case R.id.spinner_expenses_table_inputs:

                mExpensesTable = (String)adapterView.getItemAtPosition(position);
                isTablePickerOK = !TextUtils.isEmpty(mExpensesTable);
                enablePublishButton();

                break;
            case R.id.spinner_expense_currency:

                mCurrency = (String)adapterView.getItemAtPosition(position);
                isCurrencyPickerOK = !TextUtils.isEmpty(mCurrency);
                enablePublishButton();

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void enablePublishButton(){
        mValidateButton.setSelected(isTablePickerOK&&isCurrencyPickerOK&&isCostFieldOK);
    }
}
