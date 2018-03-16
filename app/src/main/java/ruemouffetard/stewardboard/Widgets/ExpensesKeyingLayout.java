package ruemouffetard.stewardboard.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;

import ruemouffetard.stewardboard.R;

/**
 * Created by admin on 14/03/2018.
 */

public class ExpensesKeyingLayout extends ViewGroup {

    //février 2018/repas avec d'autres/16.45£ d02 Pizza Mateo
    //février 2018!A23

    private BetterSpinner mExpensesTablePicker;
    private EditText mExpenseItemField;
    private BetterSpinner mCurrencyPicker;
    private EditText mMiscellaneousEditText;

    private CheckedTextView mTodayExpenseButton;
    private DatePicker mDatePicker;

    private Button mValidateButton;

    private ProgressBar mProgressBar;

    private View rootView;

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



    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
