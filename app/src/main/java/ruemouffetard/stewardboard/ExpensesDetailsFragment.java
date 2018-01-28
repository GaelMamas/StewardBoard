package ruemouffetard.stewardboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by admin on 28/01/2018.
 */

@SuppressLint("ValidFragment")
class ExpensesDetailsFragment extends Fragment{

    public ExpensesDetailsFragment(){}

    public static Fragment newInstance(int position){

        Bundle bundle = new Bundle();


        PlaceholderFragment placeholderFragment = new PlaceholderFragment();

        bundle.putInt("Constant int", position);
        placeholderFragment.setArguments(bundle);

        return placeholderFragment;
    }
}
