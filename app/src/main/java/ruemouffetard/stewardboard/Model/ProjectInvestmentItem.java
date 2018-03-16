package ruemouffetard.stewardboard.Model;

import org.joda.time.DateTime;

/**
 * Created by admin on 13/03/2018.
 */

public class ProjectInvestmentItem extends UsualExpenseItem {

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