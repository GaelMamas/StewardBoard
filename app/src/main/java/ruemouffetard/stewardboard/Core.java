package ruemouffetard.stewardboard;

import android.support.v4.math.MathUtils;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Months;
import org.joda.time.ReadableInstant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        public float getIncome() {
            return income;
        }

        public void setIncome(float income) {
            this.income = income;
        }

        public int getMonthOfYear() {
            return monthOfYear;
        }

        public void setMonthOfYear(int monthOfYear) {
            this.monthOfYear = monthOfYear;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getBelongingSheet() {
            return belongingSheet;
        }

        public void setBelongingSheet(String belongingSheet) {
            this.belongingSheet = belongingSheet;
        }
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
        private String projectType;

        private float fund;

        private DateTime depositStartedDate;

        public float getMonthlyWishedAmount() {
            return monthlyWishedAmount;
        }

        public void setMonthlyWishedAmount(float monthlyWishedAmount) {
            this.monthlyWishedAmount = monthlyWishedAmount;
        }

        public String getProjectType() {
            return projectType;
        }

        public void setProjectType(String projectType) {
            this.projectType = projectType;
        }

        public float getFund() {
            return fund;
        }

        public void setFund(float fund) {
            this.fund += fund;
        }

        public DateTime getDepositStartedDate() {
            return depositStartedDate;
        }

        public void setDepositStartedDate(DateTime depositStartedDate) {
            this.depositStartedDate = depositStartedDate;
        }
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

            /*expenseItem.setBelongingSheet(datePicker.);

            expenseItem.setBelonginTable(sheetTablePicker.get);

            expenseItem.setCurrency(currencyPicker.get);

            expenseItem.setMiscellaneous(miscellaneousEditText.getText().toString());

            expenseItem.setCost(Integer.parseInt(expenseItemField.getText().toString()));*/

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

        private UsualExpenseItem budgeted;
        private ExpensesTable expense;

        public void workoutLeftovers(){

            budgeted.setLeftOvers(budgeted.getMonthlyDefinedAmount()
                                    -sumFloatList(expense.expenseItems) );

        }

        public UsualExpenseItem getBudgeted() {
            return budgeted;
        }

        public ExpensesTable getExpense() {
            return expense;
        }
    }

    public static class MonthlyGeneralCheckUp{

        private Income income;
        private List<MonthlyExpenseItemCheckUp> expenseItemCheckUps;

        public Map<String, Pair<Float, Float>> workoutPercentages(){

            Map<String, Pair<Float, Float>>
            consumptionsPercentages = new HashMap<>();

            for (MonthlyExpenseItemCheckUp itemCheckUp: expenseItemCheckUps){

                consumptionsPercentages.put(itemCheckUp.getBudgeted().getTitle(),
                        new Pair<>(itemCheckUp.getBudgeted().getMonthlyDefinedAmount()/income.getIncome(),
                                itemCheckUp.getBudgeted().getLeftOvers()/income.getIncome()));

            }

            return consumptionsPercentages;

        }

    }

    public static class EnterprisesMonthlyIndicators{

        private List<ProjectInvestmentItem> projectInvestmentItems;
        private List<MonthlyExpenseItemCheckUp> expenseItemCheckUps;


        public float checkupGeneralConsumtion(){

            float sum = 0F;
            for (MonthlyExpenseItemCheckUp item: expenseItemCheckUps)sum += item.getBudgeted().getLeftOvers();

            return sum;

        }

        public void setBudgetArbitration(Map<String, Float> investmentPriorities){

            for (int i = 0; i < projectInvestmentItems.size(); i++) {

                if(investmentPriorities.containsKey(projectInvestmentItems.get(i).getTitle())){

                    projectInvestmentItems.get(i).setFund(investmentPriorities.get(projectInvestmentItems.get(i).getTitle()));

                }
            }

        }

        public void setDefaultArbitration(String projectTile, float amount){

            for (int i = 0; i < projectInvestmentItems.size(); i++) {

                if(projectInvestmentItems.get(i).getTitle().contentEquals(projectTile))
                    projectInvestmentItems.get(i).setFund(amount);
            }

        }


        public float workoutForecastFundUpToNow(ProjectInvestmentItem investmentItem){

            Months months = Months.monthsBetween(investmentItem.getDepositStartedDate(),
                    LocalDateTime.now().toDateTime());


            return months.getMonths()*investmentItem.getMonthlyWishedAmount();

        }


    }


}
