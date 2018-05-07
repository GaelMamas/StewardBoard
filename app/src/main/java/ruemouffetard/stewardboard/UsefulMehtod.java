package ruemouffetard.stewardboard;

import android.graphics.PointF;
import android.text.TextUtils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.InputStream;
import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;

import ruemouffetard.stewardboard.Model.ExpenseItem;
import ruemouffetard.stewardboard.Model.ModelBase;

/**
 * Created by admin on 12/03/2018.
 */

public class UsefulMehtod {

    public static float sumFloatList(List<ExpenseItem> expenseItems) {
        float sum = 0F;
        for (ExpenseItem item : expenseItems) sum += item.getCost();

        return sum;
    }

    public static ExpenseItem saveExpenseItem(String sheet, String table, String currency, String miscellaneous, float cost) {

        ExpenseItem expenseItem = new ExpenseItem();

        expenseItem.setBelongingSheet(TextUtils.isEmpty(sheet) ? "" : sheet);

        expenseItem.setBelonginTable(TextUtils.isEmpty(table) ? "" : table);

        expenseItem.setCurrency(TextUtils.isEmpty(currency) ? "" : currency);

        expenseItem.setMiscellaneous(TextUtils.isEmpty(miscellaneous) ? "" : miscellaneous);

        expenseItem.setCost(cost < 0f ? 0f : cost);

        return expenseItem;

    }


    public static String makeUpRequestContent(ExpenseItem savable) {

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


    public static <T extends ModelBase> T setDefaultModelData(InputStream inputStream, TypeToken<T> typeToken) {
        try {
            int size = inputStream.available();

            byte[] buffer = new byte[size];

            inputStream.read(buffer);

            inputStream.close();

            return new Gson().fromJson(new String(buffer, Constants.UTF8_KEY),
                    typeToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PointF getSymmetryPointWithRespectToAPoint(PointF landMarkPoint, PointF targetedPoint){

        return new PointF(2 * landMarkPoint.x - targetedPoint.x, 2 * landMarkPoint.y - targetedPoint.y);
    }

    public  static  PointF getSymmetryWithRespectToARight(PointF rightVector, PointF belongingPoint, PointF targetedPoint){

        float symmetryConstant = 2 * (rightVector.x * (targetedPoint.y - belongingPoint.y) - rightVector.y * (targetedPoint.x - belongingPoint.x))
                /(rightVector.x * rightVector.x + rightVector.y * rightVector.y);

        return new PointF(targetedPoint.x + symmetryConstant * rightVector.y,
                targetedPoint.y - symmetryConstant * rightVector.x);
    }
}
