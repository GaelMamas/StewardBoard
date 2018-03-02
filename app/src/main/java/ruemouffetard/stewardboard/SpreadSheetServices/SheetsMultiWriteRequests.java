package ruemouffetard.stewardboard.SpreadSheetServices;

import android.app.Activity;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 02/03/2018.
 */

public class SheetsMultiWriteRequests extends MakeSheetsAPIRequestTask {


    public SheetsMultiWriteRequests(GoogleAccountCredential credential, Activity activity, List<String> mOutputText, String spreadSheetId, String range) {
        super(credential, activity, mOutputText, spreadSheetId, range);
    }

    @Override
    protected String doInBackground(Void... params) {

        Sheets.Spreadsheets.Values spreadsheet = this.mService.spreadsheets().values();

        List<List<Object>> values;


                try {
                   /* jsonArray = new JSONArray(
                            "{" +
                                    "  \"data\": [" +
                                    "    {" +
                                    "      \"range\": \"Test!A1:A4\"," +
                                    "      \"majorDimension\": \"COLUMNS\"," +
                                    "      \"values\": [" +
                                    "        [\"Item\", \"Wheel\", \"Door\", \"Engine\"]" +
                                    "      ]" +
                                    "    }," +
                                    "    {" +
                                    "      \"range\": \"Test!B1:D2\"," +
                                    "      \"majorDimension\": \"ROWS\"," +
                                    "      \"values\": [" +
                                    "        [\"Cost\", \"Stocked\", \"Ship Date\"]," +
                                    "        [\"$20.50\", \"4\", \"3/1/2016\"]" +
                                    "      ]" +
                                    "    }" +
                                    "  ]" +
                                    "}"
                    );*/

                    List<Object> objects1 = new ArrayList<>();
                    List<Object> objects2 = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        objects1.add(i == 0 ? "25" :
                                i == 1 ? "8" : "=SUM(A3:B3)");
                    }
                    for (int i = 0; i < 4; i++) {
                        objects2.add(i == 0 ? "15" :
                                i == 1 ? "5" : "=PRODUCT(A4:B4)");
                    }

                    values = Arrays.asList(
                            objects1, objects2
                    );

                    List<ValueRange> data = new ArrayList<>();
                    data.add(new ValueRange()
                            .setRange(range)
                            .setValues(values));

                    BatchUpdateValuesRequest putMultiBody = new BatchUpdateValuesRequest()
                            .setValueInputOption("USER_ENTERED")
                            .setData(data);
                    BatchUpdateValuesResponse putMultiResult;

                    StringBuilder result = new StringBuilder();

                    try {
                        putMultiResult = spreadsheet.batchUpdate(spreadSheetId, putMultiBody).execute();

                        for (UpdateValuesResponse response : putMultiResult.getResponses()) {

                            result.append(response.getUpdatedRange()).append(",");

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    return result.toString();

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
