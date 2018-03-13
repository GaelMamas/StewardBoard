package ruemouffetard.stewardboard.Model;

/**
 * Created by admin on 13/03/2018.
 */

public class UsualExpenseItem extends ModelBase {

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
