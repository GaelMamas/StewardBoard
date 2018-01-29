package ruemouffetard.stewardboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expenses_details, container, false);


        return view;
    }
}
