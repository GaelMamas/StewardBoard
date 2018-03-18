package ruemouffetard.stewardboard;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;

import ruemouffetard.stewardboard.Model.ExpenseItem;

/**
 * Created by admin on 12/03/2018.
 */

public class UsefulMehtod {

    public static float sumFloatList(List<ExpenseItem> expenseItems){
        float sum = 0F;
        for (ExpenseItem item: expenseItems)sum += item.getCost();

        return sum;
    }

    public static ExpenseItem saveExpenseItem(String sheet, String table, String currency, String miscellaneous, float cost){

        ExpenseItem expenseItem = new ExpenseItem();

            expenseItem.setBelongingSheet(sheet);

            expenseItem.setBelonginTable(table);

            expenseItem.setCurrency(currency);

            expenseItem.setMiscellaneous(miscellaneous);

            expenseItem.setCost(cost);

        return expenseItem;

    }


    public static String makeUpRequestContent(ExpenseItem savable){

        //février 2018;repas avec d'autres;16.45£ d02 Pizza Mateo

        StringBuilder builder = new StringBuilder();

        builder.append(savable.getBelongingSheet())
                .append(";")
                .append(savable.getBelonginTable())
                .append(";")
                .append(savable.getCost()).append(savable.getCurrency())
                .append(" ")
                .append(savable.getDate())
                .append(" ")
                .append(savable.getMiscellaneous());

        return builder.toString();
    }

    public static String formatMonth(int month, Locale locale) {
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] monthNames = symbols.getMonths();
        return monthNames[month - 1];
    }
}
