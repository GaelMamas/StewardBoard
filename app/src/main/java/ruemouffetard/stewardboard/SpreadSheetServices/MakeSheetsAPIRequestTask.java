package ruemouffetard.stewardboard.SpreadSheetServices;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import ruemouffetard.stewardboard.PlaceholderFragment;

/**
 * Created by user on 15/09/2017.
 */

public abstract class MakeSheetsAPIRequestTask extends AsyncTask<Void, Void, Object> {

    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;

    protected com.google.api.services.sheets.v4.Sheets mService = null;
    protected Exception mLastError = null;
    protected Activity activity;
    protected List<String> mOutputText;
    protected String spreadSheetId, range;

    public MakeSheetsAPIRequestTask(GoogleAccountCredential credential, Activity activity, List<String> mOutputText,
                                    String spreadSheetId, String range) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                transport, jsonFactory, credential)
                .setApplicationName(activity.getPackageName())
                .build();


        this.activity = activity;
        this.mOutputText = mOutputText;
        this.spreadSheetId = spreadSheetId;
        this.range = range;
    }


    /**
     * Background task to call Google Sheets API.
     *
     * @param params no parameters needed for this task.
     */

    @Override
    protected Object doInBackground(Void... params) {

        /*Sheets.Spreadsheets.Values spreadsheet = this.mService.spreadsheets().values();

        JSONArray jsonArray;
        List<List<Object>> values = new ArrayList<>();

        try{

            List<Object> objects = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                objects.add(i == 0 ? "1.5" :
                        i == 1 ? "144" : "=PRODUCT(B2:A2)");
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
        }*/

        return null;
    }


    @Override
    protected void onPreExecute() {
        //mOutputText.setText("");
        //mProgress.show();
    }

    @Override
    protected void onPostExecute(Object output) {

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

    protected abstract void onPostExecute(List<List<Object>> result);
}
