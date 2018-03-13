package ruemouffetard.stewardboard.LogicOnModel;

import android.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ruemouffetard.stewardboard.Model.Income;

/**
 * Created by admin on 13/03/2018.
 */

public class MonthlyGeneralCheckUp {

    private Income income;
    private List<MonthlyExpenseItemCheckUp> expenseItemCheckUps;

    public MonthlyGeneralCheckUp(Income income, List<MonthlyExpenseItemCheckUp> expenseItemCheckUps) {
        this.income = income;
        this.expenseItemCheckUps = expenseItemCheckUps;
    }

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
