package ruemouffetard.stewardboard.LogicOnModel.Forecast;

import org.joda.time.LocalDateTime;
import org.joda.time.Months;

import java.util.List;
import java.util.Map;

import ruemouffetard.stewardboard.LogicOnModel.MonthlyExpenseItemCheckUp;
import ruemouffetard.stewardboard.Model.ProjectInvestmentItem;

/**
 * Created by admin on 14/03/2018.
 */

public class EnterprisesMonthlyIndicators {

    private List<ProjectInvestmentItem> projectInvestmentItems;
    private List<MonthlyExpenseItemCheckUp> expenseItemCheckUps;

    public EnterprisesMonthlyIndicators(List<ProjectInvestmentItem> projectInvestmentItems, List<MonthlyExpenseItemCheckUp> expenseItemCheckUps) {
        this.projectInvestmentItems = projectInvestmentItems;
        this.expenseItemCheckUps = expenseItemCheckUps;
    }

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
