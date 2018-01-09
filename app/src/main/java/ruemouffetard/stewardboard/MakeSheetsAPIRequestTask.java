package ruemouffetard.stewardboard;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 15/09/2017.
 */

public class MakeSheetsAPIRequestTask extends AsyncTask<Void, Void, List<String>> {

    private com.google.api.services.sheets.v4.Sheets mService = null;
    private Exception mLastError = null;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private Activity activity;
    private List<String> mOutputText;
    private ProgressDialog mProgress;
    private String spreadSheetId, range;

    MakeSheetsAPIRequestTask(GoogleAccountCredential credential, Activity activity, List<String> mOutputText, ProgressDialog mProgress,
                             String spreadSheetId, String range) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Sheets API Android Quickstart")
                .build();


        this.activity = activity;
        this.mOutputText = mOutputText;
        this.mProgress = mProgress;
        this.spreadSheetId = spreadSheetId;
        this.range = range;
    }


/**
 * Background task to call Google Sheets API.
 *
 * @param params no parameters needed for this task.
 */

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
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

        private List<String> getDataFromApi() throws IOException {

            List<String> results = new ArrayList<String>();
            ValueRange response = this.mService.spreadsheets().values()
                    .get(TextUtils.isEmpty(spreadSheetId)? "1Cu28LrxgAkMhEaiMhDXds05bkce6NzOOJyGcn3CNvKg": spreadSheetId,
                            TextUtils.isEmpty(range)? "Juin 2016!A1:C1": range)
                    .execute();
            List<List<Object>> values = response.getValues();
            if (values != null) {
                results.add("Name, Major");
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
