package ruemouffetard.stewardboard.Widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import ruemouffetard.stewardboard.LogicOnModel.MonthlyExpenseItemCheckUp;
import ruemouffetard.stewardboard.LogicOnModel.MonthlyGeneralCheckUp;

/**
 * Created by admin on 14/03/2018.
 */

public class CamembertChartView extends View {

    private MonthlyGeneralCheckUp generalCheckUp;

    public CamembertChartView(Context context, MonthlyGeneralCheckUp generalCheckUp) {
        super(context);

        this.generalCheckUp = generalCheckUp;
    }

    public CamembertChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


}
