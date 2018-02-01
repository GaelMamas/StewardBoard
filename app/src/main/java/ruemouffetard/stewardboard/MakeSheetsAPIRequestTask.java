package ruemouffetard.stewardboard;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendCellsRequest;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 15/09/2017.
 */

public class MakeSheetsAPIRequestTask extends AsyncTask<Void, Void, List<String>> {

    private static final int GET_TYPE = 0, GET_MULTI_TYPE = 1,
                PUT_TYPE = 2, PUT_MULTI_TYPE = 3, APPEND_TYPE = 4;

    private com.google.api.services.sheets.v4.Sheets mService = null;
    private Exception mLastError = null;

    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;

    private Activity activity;
    private List<String> mOutputText;
    private String spreadSheetId, range;
    private int spreadsheetRequestType;

    MakeSheetsAPIRequestTask(GoogleAccountCredential credential, Activity activity, List<String> mOutputText,
                             String spreadSheetId, String range, int spreadsheetRequestType) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Sheets API Android Quickstart")
                .build();


        this.activity = activity;
        this.mOutputText = mOutputText;
        this.spreadSheetId = spreadSheetId;
        this.range = range;
        this.spreadsheetRequestType = spreadsheetRequestType;
    }


    /**
     * Background task to call Google Sheets API.
     *
     * @param params no parameters needed for this task.
     */

    @Override
    protected List<String> doInBackground(Void... params) {
        try {

            return transactDataWithApi();

        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
    }


    /**
     * Fetch a list of names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     *
     * @return List of names and majors
     * @throws IOException
     */

    private List<String> transactDataWithApi() throws IOException, JSONException {

        List<String> results = new ArrayList<String>();

        Sheets.Spreadsheets.Values spreadsheet = this.mService.spreadsheets().values();
        spreadSheetId = TextUtils.isEmpty(spreadSheetId) ? "1TQFsCpcmBixrkC9hBo9ri_DsxvbbwLa33YLcE1PN7OU" : spreadSheetId;
        range = TextUtils.isEmpty(range) ? "Test!A1:C1" : range;

        ValueRange response;
        switch (spreadsheetRequestType){


            case PUT_TYPE:

                JSONArray jsonArray = new JSONArray("{\n" +
                        "  \"range\": \"Test!A1:D5\",\n" +
                        "  \"majorDimension\": \"ROWS\",\n" +
                        "  \"values\": [\n" +
                        "    [\"Item\", \"Cost\", \"Stocked\", \"Ship Date\"],\n" +
                        "    [\"Wheel\", \"$20.50\", \"4\", \"3/1/2016\"],\n" +
                        "    [\"Door\", \"$15\", \"2\", \"3/15/2016\"],\n" +
                        "    [\"Engine\", \"$100\", \"1\", \"30/20/2016\"],\n" +
                        "    [\"Totals\", \"=SUM(B2:B4)\", \"=SUM(C2:C4)\", \"=MAX(D2:D4)\"]\n" +
                        "  ],\n" +
                        "}");

                List<Object> valuesItem = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    valuesItem.add(jsonArray.get(i));
                }

                List<List<Object>> values = Arrays.asList(
                        valuesItem,valuesItem
                );
                ValueRange body = new ValueRange()
                        .setValues(values);
                UpdateValuesResponse result =
                        mService.spreadsheets().values().update(spreadSheetId, range, body)
                                .setValueInputOption("USER_ENTERED")
                                .execute();
                System.out.printf("%d cells updated.", result.getUpdatedCells());



            case PUT_MULTI_TYPE:

                jsonArray = new JSONArray(
                  "{\n" +
                          "  \"valueInputOption\": \"USER_ENTERED\",\n" +
                          "  \"data\": [\n" +
                          "    {\n" +
                          "      \"range\": \"Test!A1:A4\",\n" +
                          "      \"majorDimension\": \"COLUMNS\",\n" +
                          "      \"values\": [\n" +
                          "        [\"Item\", \"Wheel\", \"Door\", \"Engine\"]\n" +
                          "      ]\n" +
                          "    },\n" +
                          "    {\n" +
                          "      \"range\": \"Test!B1:D2\",\n" +
                          "      \"majorDimension\": \"ROWS\",\n" +
                          "      \"values\": [\n" +
                          "        [\"Cost\", \"Stocked\", \"Ship Date\"],\n" +
                          "        [\"$20.50\", \"4\", \"3/1/2016\"]\n" +
                          "      ]\n" +
                          "    }\n" +
                          "  ]\n" +
                          "}"
                );

                valuesItem = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    valuesItem.add(jsonArray.get(i));
                }

                values = Arrays.asList(
                        valuesItem,valuesItem
                );

                List<ValueRange> data = new ArrayList<>();
                data.add(new ValueRange()
                        .setRange(range)
                        .setValues(values));

                BatchUpdateValuesRequest putMultiBody = new BatchUpdateValuesRequest()
                        .setValueInputOption("USER_ENTERED")
                        .setData(data);
                BatchUpdateValuesResponse putMultiResult =
                        mService.spreadsheets().values().batchUpdate(spreadSheetId, putMultiBody).execute();
                System.out.printf("%d cells updated.", putMultiResult.getTotalUpdatedCells());



            case APPEND_TYPE:

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




            case GET_MULTI_TYPE:

                List<String> ranges = Arrays.asList(
                        "Test!A1:A16",
                        "Test!C2:F3"
                );
                BatchGetValuesResponse getMultiResult = mService.spreadsheets().values().batchGet(spreadSheetId)
                        .setRanges(ranges).execute();
                System.out.printf("%d ranges retrieved.", getMultiResult.getValueRanges().size());

            case GET_TYPE:
            default:

                response = spreadsheet
                        .get(spreadSheetId, range)
                        .execute();
        }



        List<List<Object>> values = response.getValues();
        if (values != null) {
            for (List row : values) {
                results.add(row.get(0) + ", " + row.size());
            }
        }
        return results;
    }


    @Override
    protected void onPreExecute() {
        //mOutputText.setText("");
        //mProgress.show();
    }

    @Override
    protected void onPostExecute(List<String> output) {
        //mProgress.hide();
        if (output == null || output.size() == 0) {
            //mOutputText.setText("No results returned.");
            mOutputText.add("No results returned.");
        } else {
            //output.add(0, "Data retrieved using the Google Sheets API:");
            //mOutputText.setText(TextUtils.join("\n", output));
            mOutputText.addAll(output);
        }
    }

    @Override
    protected void onCancelled() {
        //mProgress.hide();
        if (mLastError != null) {
            if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                showGooglePlayServicesAvailabilityErrorDialog(
                        ((GooglePlayServicesAvailabilityIOException) mLastError)
                                .getConnectionStatusCode());
            } else if (mLastError instanceof UserRecoverableAuthIOException) {
                activity.startActivityForResult(
                        ((UserRecoverableAuthIOException) mLastError).getIntent(),
                        PlaceholderFragment.REQUEST_AUTHORIZATION);
            } else {
                    /*mOutputText.setText("The following error occurred:\n"
                            + mLastError.getMessage());*/

                mOutputText.add("The following error occurred:\n"
                        + mLastError.getMessage());
            }
        } else {
            //mOutputText.setText("Request cancelled.");
            mOutputText.add("Request cancelled.");
        }
    }

    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                activity,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }
}
