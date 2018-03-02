package ruemouffetard.stewardboard.SpreadSheetServices;

import android.app.Activity;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.List;

/**
 * Created by admin on 07/02/2018.
 */

public class SheetsAppendRequests extends MakeSheetsAPIRequestTask {

    public SheetsAppendRequests(GoogleAccountCredential credential, Activity activity, List<String> mOutputText, String spreadSheetId, String range) {
        super(credential, activity, mOutputText, spreadSheetId, range);
    }


    @Override
    protected Object doInBackground(Void... params) {

                /*jsonArray = new JSONArray(
                        "{\n" +
                                "  \"range\": \"Sheet1!A1:E1\",\n" +
                                "  \"majorDimension\": \"ROWS\",\n" +
                                "  \"values\": [\n" +
                                "    [\"Door\", \"$15\", \"2\", \"3/15/2016\"],\n" +
                                "    [\"Engine\", \"$100\", \"1\", \"3/20/2016\"],\n" +
                                "  ],\n" +
                                "}"
                );

                valuesItem = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    valuesItem.add(jsonArray.get(i));
                }

                values = Arrays.asList(
                        valuesItem,valuesItem
                );

                body = new ValueRange()
                        .setValues(values);
                AppendCellsRequest appendResult =
                        mService.spreadsheets().values().append(spreadSheetId, range, body)
                                .setValueInputOption("USER_ENTERED")
                                .execute();
                System.out.printf("%d cells appended.", appendResult.getUpdates().getUpdatedCells());*/

        return null;
    }

    @Override
    protected void onPostExecute(List<List<Object>> result) {

    }
}
