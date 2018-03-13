package ruemouffetard.stewardboard.Model;

/**
 * Created by admin on 13/03/2018.
 */

public class ExpenseItem extends ModelBase {

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
