package ruemouffetard.stewardboard.SpreadSheetServices;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 02/03/2018.
 */

public class SheetsMultiReadRequests extends MakeSheetsAPIRequestTask {
    public SheetsMultiReadRequests(GoogleAccountCredential mCredential, Activity activity, List<String> inputList, String spreadsheetId, String ranges) {
        super(mCredential, activity, inputList, spreadsheetId, ranges);
    }


    @Override
    protected List<List<Object>> doInBackground(Void... params) {

        Sheets.Spreadsheets.Values spreadsheet = this.mService.spreadsheets().values();


        String [] rangeArray = range.split(";") ;

        if(rangeArray.length == 0) return null;

        List<String> ranges = Arrays.asList(rangeArray);

        BatchGetValuesResponse getMultiResult = null;
        try {

            if(spreadsheet.batchGet(spreadSheetId) != null){

                if(spreadsheet.batchGet(spreadSheetId).setRanges(ranges) != null){

                    getMultiResult =  spreadsheet.batchGet(spreadSheetId)
                            .setRanges(ranges).execute();

                }else{

                    Log.d(getClass().getSimpleName(), "Suddenly shut down cause setRanges() returned null");
                }

            }else{

                Log.d(getClass().getSimpleName(), "Suddenly shut down cause execute() returned null");

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        if (getMultiResult != null) {
            return getMultiResult.getValueRanges().get(0).getValues();
        }


        return null;

    }

    private List<String> release(List<List<Object>> mGetValues) {

        List<String> results = new ArrayList<>();

        if (mGetValues != null) {
            for (List row : mGetValues) {
                if (row != null && !row.isEmpty())
                    results.add(row.get(0) + ", " + row.size());
            }
        }

        return results;
    }

    @Override
    protected void onPostExecute(List<List<Object>> result) {
        super.onPostExecute(new ArrayList<String>());

        if (result == null || result.size() == 0) {
            mOutputText.add("No results returned.");
        } else {
            mOutputText.addAll(release(result));
        }
    }
}
