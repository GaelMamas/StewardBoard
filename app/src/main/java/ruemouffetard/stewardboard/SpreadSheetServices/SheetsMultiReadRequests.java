package ruemouffetard.stewardboard.SpreadSheetServices;

import android.app.Activity;

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


        List<String> ranges = Arrays.asList(
                "Test!A1:A16",
                "Test!C2:F3"
        );
        BatchGetValuesResponse getMultiResult = null;
        try {
            getMultiResult = mService.spreadsheets().values().batchGet(spreadSheetId)
                    .setRanges(ranges).execute();
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
        if (result == null || result.size() == 0) {
            mOutputText.add("No results returned.");
        } else {
            mOutputText.addAll(release(result));
        }
    }
}
