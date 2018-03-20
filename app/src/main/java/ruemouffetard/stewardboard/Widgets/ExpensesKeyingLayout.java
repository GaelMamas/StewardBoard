package ruemouffetard.stewardboard.Widgets;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ruemouffetard.stewardboard.R;
import ruemouffetard.stewardboard.UsefulMehtod;

/**
 * Created by admin on 14/03/2018.
 */

public class ExpensesKeyingLayout extends CardView implements View.OnClickListener, AdapterView.OnItemSelectedListener,
        DatePickerDialog.OnDateSetListener {

    //février 2018/repas avec d'autres/16.45£ d02 Pizza Mateo
    //février 2018!A23

    private BetterSpinner mExpensesTablePicker;
    private EditText mExpenseItemField;
    private BetterSpinner mCurrencyPicker; //TODO When cryptocurrencies
    private EditText mMiscellaneousEditText;

    private CheckedTextView mTodayExpenseButton;
    private DatePickerDialog datePickerDialog = new DatePickerDialog(
            getContext(), this, Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

    private Button mValidateButton;

    private ProgressBar mProgressBar;

    private View rootView;

    private List<String> expensesTableNames = new ArrayList<>();

    private String sheetName, mExpensesTable, mCurrency;
    private boolean isTablePickerOK, isCurrencyPickerOK, isCostFieldOK;

    public ExpensesKeyingLayout(Context context) {
        this(context, null);


    }

    public ExpensesKeyingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ExpensesKeyingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        rootView = inflate(context, R.layout.board_expenses_keying, this);
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {

        ArrayAdapter<String> expensesTableAdapter = new ArrayAdapter<>(getContext(),
                R.layout.test_text, expensesTableNames);

        mExpensesTablePicker.setAdapter(expensesTableAdapter);

        //TODO Set up the actual currencies https://gist.github.com/Fluidbyte/2973986#file-common-currency-json
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(getContext(),
                R.layout.test_text, Arrays.asList(new String[]{"€", "£", "$"}));

        mCurrencyPicker.setAdapter(currencyAdapter);

        mTodayExpenseButton.setText(getContext().getString(R.string.keying_expense_occurred_date,
                new SimpleDateFormat("dd/MM/yyyy")
                        .format(new Date(System.currentTimeMillis()))));

        mExpensesTablePicker.setOnItemSelectedListener(this);
        mCurrencyPicker.setOnItemSelectedListener(this);

        mTodayExpenseButton.setOnClickListener(this);
        mValidateButton.setOnClickListener(this);

        enablePublishButton();

        mExpenseItemField.addTextChangedListener(new TextWatcher() {
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

    private void assignChildrenViews() {

        mExpensesTablePicker = rootView.findViewById(R.id.spinner_expenses_table_inputs);
        mExpenseItemField = rootView.findViewById(R.id.edittext_expense_input);
        mCurrencyPicker = rootView.findViewById(R.id.spinner_expense_currency);
        mMiscellaneousEditText = rootView.findViewById(R.id.edittext_expense_miscellaneous);
        mTodayExpenseButton = rootView.findViewById(R.id.checkedTextView_expense_occurred_date);
        mValidateButton = rootView.findViewById(R.id.button_expense_input_publish);
        mProgressBar = rootView.findViewById(R.id.progressbar_expense_in_saving);

    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        assignChildrenViews();
        init();

        super.onLayout(b, i, i1, i2, i3);

    }

    public void setKeyingLayout(List<String> expensesTableNames, String sheetName) {

        this.expensesTableNames = expensesTableNames;
        this.sheetName = sheetName;
        assignChildrenViews();
        init();
        invalidate();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.checkedTextView_expense_occurred_date:

                datePickerDialog.show();

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

                            mExpensesTablePicker.resetPlaceHolderText();
                            isTablePickerOK = false;

                            mExpenseItemField.setText("");
                            isCostFieldOK = false;

                            mMiscellaneousEditText.setText("");

                            Snackbar.make(rootView, R.string.keying_successfully_completed, Snackbar.LENGTH_SHORT).show();

                        }
                    }, 3000);
                    //TODO END
                }

                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long viewId) {
        switch ((int) viewId) {
            case R.id.spinner_expenses_table_inputs:

                mExpensesTable = (String) adapterView.getItemAtPosition(position);
                isTablePickerOK = !TextUtils.isEmpty(mExpensesTable);
                enablePublishButton();

                break;
            case R.id.spinner_expense_currency:

                mCurrency = (String) adapterView.getItemAtPosition(position);
                isCurrencyPickerOK = !TextUtils.isEmpty(mCurrency);
                enablePublishButton();

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void enablePublishButton() {
        mValidateButton.setSelected(isTablePickerOK & isCurrencyPickerOK & isCostFieldOK);
        mValidateButton.setEnabled(mValidateButton.isSelected());
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

        mTodayExpenseButton.setText(getContext().getString(R.string.keying_expense_occurred_date,
                (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)));

        String month = UsefulMehtod.formatMonth(monthOfYear, Locale.getDefault());

        sheetName = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase() + " " + year;

    }
}
