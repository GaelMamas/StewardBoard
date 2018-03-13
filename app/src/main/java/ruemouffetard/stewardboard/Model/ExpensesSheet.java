package ruemouffetard.stewardboard.Model;

import java.util.List;

/**
 * Created by admin on 13/03/2018.
 */

public class ExpensesSheet extends ModelBase {

    private String sheetName;
    private List<ExpensesTable> expensesTables;

    public ExpensesSheet(String sheetName, List<ExpensesTable> expensesTables) {
        this.sheetName = sheetName;
        this.expensesTables = expensesTables;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<ExpensesTable> getExpensesTables() {
        return expensesTables;
    }

    public void setExpensesTables(List<ExpensesTable> expensesTables) {
        this.expensesTables = expensesTables;
    }
}
