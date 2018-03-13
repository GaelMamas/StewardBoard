package ruemouffetard.stewardboard.LogicOnModel;

import ruemouffetard.stewardboard.Model.ExpensesTable;
import ruemouffetard.stewardboard.Model.UsualExpenseItem;

import static ruemouffetard.stewardboard.UsefulMehtod.sumFloatList;

/**
 * Created by admin on 13/03/2018.
 */

public class MonthlyExpenseItemCheckUp {

    private UsualExpenseItem budgeted;
    private ExpensesTable expense;

    public MonthlyExpenseItemCheckUp(UsualExpenseItem budgeted, ExpensesTable expense) {
        this.budgeted = budgeted;
        this.expense = expense;

        workoutLeftovers();
    }

    private void workoutLeftovers(){

        budgeted.setLeftOvers(budgeted.getMonthlyDefinedAmount()
                -sumFloatList(expense.getExpenseItems()) );

    }

    public UsualExpenseItem getBudgeted() {
        return budgeted;
    }

    public ExpensesTable getExpense() {
        return expense;
    }

}
