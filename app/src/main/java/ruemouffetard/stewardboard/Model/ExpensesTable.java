package ruemouffetard.stewardboard.Model;

import java.util.List;

/**
 * Created by admin on 13/03/2018.
 */

public class ExpensesTable extends ModelBase {

    private String belongingSheet;
    private String name;

    private List<ExpenseItem> expenseItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExpenseItem> getExpenseItems() {
        return expenseItems;
    }

    public void setExpenseItems(List<ExpenseItem> expenseItems) {
        this.expenseItems = expenseItems;
    }

    public String getBelongingSheet() {
        return belongingSheet;
    }

    public void setBelongingSheet(String belongingSheet) {
        this.belongingSheet = belongingSheet;
    }
}
