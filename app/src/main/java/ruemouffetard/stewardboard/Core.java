package ruemouffetard.stewardboard;

import android.support.v4.math.MathUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.List;

import static ruemouffetard.stewardboard.SpreadSheetServices.UsefulMehtod.sumFloatList;

/**
 * Created by admin on 11/03/2018.
 */

public class Core {

    public static class Income{

        private float income;
        private int monthOfYear;
        private int year;
        private String currency;

        private String belongingSheet;

    }

    public static class ExpensesTable{

        private String belongingSheet;
        private String name;

        private List<ExpenseItem> expenseItems;
    }

    public static class UsualExpenseItem{

        protected float monthlyDefinedAmount;
        protected String currency;

        protected String title;

        protected String belongingSheet;

        protected float leftOvers;

        public float getMonthlyDefinedAmount() {
            return monthlyDefinedAmount;
        }

        public void setMonthlyDefinedAmount(float monthlyDefinedAmount) {
            this.monthlyDefinedAmount = monthlyDefinedAmount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBelongingSheet() {
            return belongingSheet;
        }

        public void setBelongingSheet(String belongingSheet) {
            this.belongingSheet = belongingSheet;
        }

        public float getLeftOvers() {
            return leftOvers;
        }

        public void setLeftOvers(float leftOvers) {
            this.leftOvers = leftOvers;
        }
    }

    public static class IrreducibleItem extends UsualExpenseItem{



    }

    public static class ProjectInvestmentItem extends UsualExpenseItem{

        private float monthlyWishedAmount;

    }

    public static class ExpenseItem{

        private float cost;
        private String currency;
        private String date;
        private String miscellaneous;

        private String belonginTable;
        private String belongingSheet;

        public float getCost() {
            return cost;
        }

        public void setCost(float cost) {
            this.cost = cost;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMiscellaneous() {
            return miscellaneous;
        }

        public void setMiscellaneous(String miscellaneous) {
            this.miscellaneous = miscellaneous;
        }

        public String getBelonginTable() {
            return belonginTable;
        }

        public void setBelonginTable(String belonginTable) {
            this.belonginTable = belonginTable;
        }

        public String getBelongingSheet() {
            return belongingSheet;
        }

        public void setBelongingSheet(String belongingSheet) {
            this.belongingSheet = belongingSheet;
        }
    }

    public static class ExpensesSheet{

        private String sheetName;
        private List<ExpensesTable> expensesTables;

    }

    //février 2018/repas avec d'autres/16.45£ d02 Pizza Mateo
    //février 2018!A23


    public static class ExpensesKeying{

        private Spinner sheetTablePicker;
        private Spinner currencyPicker;
        private EditText expenseItemField;
        private EditText miscellaneousEditText;

        private RadioButton todayExpenseButton;
        private DatePicker datePicker;

        private Button validateButton;

        private ProgressBar progressBar;


        public ExpenseItem save(){

            ExpenseItem expenseItem = new ExpenseItem();

            expenseItem.setBelongingSheet(datePicker.);

            expenseItem.setBelonginTable(sheetTablePicker.get);

            expenseItem.setCurrency(currencyPicker.get);

            expenseItem.setMiscellaneous(miscellaneousEditText.getText().toString());

            expenseItem.setCost(Integer.parseInt(expenseItemField.getText().toString()));

            return expenseItem;

        }

        //février 2018;repas avec d'autres;16.45£ d02 Pizza Mateo

        public String makeUpRequestContent(ExpenseItem savable){

            StringBuilder builder = new StringBuilder();

            builder.append(savable.belongingSheet)
                    .append(";")
                    .append(savable.belonginTable)
                    .append(";")
                    .append(savable.getCost()).append(savable.getCurrency())
                    .append(" ")
                    .append(savable.getDate())
                    .append(" ")
                    .append(savable.getMiscellaneous());

            return builder.toString();
        }

    }

    public static class CamembertChartView{


    }

    public static class MonthlyExpenseItemCheckUp{

        UsualExpenseItem budgeted;
        ExpensesTable expense;

        public void workoutLeftovers(){

            budgeted.setLeftOvers(budgeted.getMonthlyDefinedAmount()
                                    -sumFloatList(expense.expenseItems) );

        }

    }

    public static class MonthlyGeneralCheckUp{

        private Income income;
        private List<UsualExpenseItem> budgetedItemTables;
        private List<ExpensesTable> expensesTables;

        public void workoutLeftovers(){

            for(UsualExpenseItem usualExpenseItem: budgetedItemTables){

                for(ExpensesTable expensesTable: expensesTables){

                    if(usualExpenseItem.getTitle().contentEquals(expensesTable.name)){

                        usualExpenseItem.setLeftOvers(
                                usualExpenseItem.monthlyDefinedAmount
                                - sumFloatList(expensesTable.expenseItems)
                        );

                    }

                }

            }

        }

    }


}
