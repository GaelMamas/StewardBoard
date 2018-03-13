package ruemouffetard.stewardboard.Model;

/**
 * Created by admin on 13/03/2018.
 */

public class Income extends ModelBase{

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
