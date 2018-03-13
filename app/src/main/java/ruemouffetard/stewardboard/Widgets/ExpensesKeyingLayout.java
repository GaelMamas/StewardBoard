package ruemouffetard.stewardboard.Widgets;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;

/**
 * Created by admin on 14/03/2018.
 */

public class ExpensesKeyingLayout extends ViewGroup {

    //février 2018/repas avec d'autres/16.45£ d02 Pizza Mateo
    //février 2018!A23

    private Spinner sheetTablePicker;
    private Spinner currencyPicker;
    private EditText expenseItemField;
    private EditText miscellaneousEditText;

    private RadioButton todayExpenseButton;
    private DatePicker datePicker;

    private Button validateButton;

    private ProgressBar progressBar;

    public ExpensesKeyingLayout(Context context) {
        super(context);
    }

    public ExpensesKeyingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpensesKeyingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExpensesKeyingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
