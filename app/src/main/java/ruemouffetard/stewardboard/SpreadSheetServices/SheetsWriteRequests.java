package ruemouffetard.stewardboard.SpreadSheetServices;

import android.app.Activity;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 07/02/2018.
 */

public class SheetsWriteRequests extends MakeSheetsAPIRequestTask {


    public SheetsWriteRequests(GoogleAccountCredential credential, Activity activity, List<String> mOutputText, String spreadSheetId, String range) {
        super(credential, activity, mOutputText, spreadSheetId, range);
    }

    @Override
    protected String doInBackground(Void... params) {

        Sheets.Spreadsheets.Values spreadsheet = this.mService.spreadsheets().values();

        JSONArray jsonArray;
        List<List<Object>> values;

        try {

            values = new ArrayList<>();

                    /*JSONObject jsonObject = new JSONObject("{" +
                            "  \"values\": [" +
                            "    [\"Item\", \"Cost\", \"Stocked\", \"Ship Date\"]," +
                            "    [\"Wheel\", \"$20.50\", \"4\", \"3/1/2016\"]," +
                            "    [\"Door\", \"$15\", \"2\", \"3/15/2016\"]," +
                            "    [\"Engine\", \"$100\", \"1\", \"30/20/2016\"]," +
                            "    [\"Totals\", \"=SUM(B2:B4)\", \"=SUM(C2:C4)\", \"=MAX(D2:D4)\"]" +
                            "  ]," +
                            "}");

                    jsonArray = jsonObject.getJSONArray("values");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);
                    ArrayList subList = new ArrayList();
                    if(jsonArray1 != null){
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            subList.add(jsonArray1.get(i));
                        }

                        values.add(subList);
                    }
                }*/


            List<Object> objects = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                objects.add(i == 0 ? "143" :
                        i == 1 ? "23" : "=SUM(A3:B3)");
            }


            values.add(objects);

            ValueRange body = new ValueRange()
                    .setValues(values).setMajorDimension("ROWS").setRange(range);
            UpdateValuesResponse result =
                    spreadsheet.update(spreadSheetId, range, body)
                            .setValueInputOption("USER_ENTERED")
                            .execute();


            return result.getUpdatedRange();

        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<List<Object>> result) {

    }
}
