package ruemouffetard.stewardboard.SpreadSheetServices;

import android.app.Activity;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 07/02/2018.
 */

public class SheetsReadRequests extends MakeSheetsAPIRequestTask {


    public SheetsReadRequests(GoogleAccountCredential credential, Activity activity, List<String> mOutputText, String spreadSheetId, String range) {
        super(credential, activity, mOutputText, spreadSheetId, range);
    }

    @Override
    protected List<List<Object>> doInBackground(Void... params) {

        Sheets.Spreadsheets.Values spreadsheet = this.mService.spreadsheets().values();

        try {


            ValueRange response = null;

            if (spreadsheet.get(spreadSheetId, range) != null) {

                response = spreadsheet.get(spreadSheetId, range).execute();

            } else {
                Log.d(getClass().getSimpleName(), "Suddenly shut down cause get(sheetId, range) returned null");
            }

            assert response != null;
            return response.getValues();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

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
