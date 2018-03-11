package ruemouffetard.stewardboard.SpreadSheetServices;

import java.util.List;

import ruemouffetard.stewardboard.Core;

/**
 * Created by admin on 12/03/2018.
 */

public class UsefulMehtod {

    public static float sumFloatList(List<Core.ExpenseItem> expenseItems){
        float sum = 0F;
        for (Core.ExpenseItem item: expenseItems)sum += item.getCost();

        return sum;
    }
}
