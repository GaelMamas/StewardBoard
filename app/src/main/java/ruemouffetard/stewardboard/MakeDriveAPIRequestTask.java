package ruemouffetard.stewardboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 15/09/2017.
 */

public class MakeDriveAPIRequestTask extends AsyncTask<Void, Void, JSONArray> {

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private com.google.api.services.drive.Drive mService = null;
    private Exception mLastError = null;

    private Activity activity;
    private JSONArray jsonArray;

    MakeDriveAPIRequestTask(GoogleAccountCredential credential, Activity activity, JSONArray data) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        this.mService = new com.google.api.services.drive.Drive.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Drive API Android Quickstart")
                .build();

        this.activity = activity;
        this.jsonArray = data;

    }

    /**
     * Background task to call Drive API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected JSONArray doInBackground(Void... params) {
        try{

            return getDataFromApi();
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
    }

    /**
     * Fetch a list of up to 10 file names and IDs.
     * @return List of Strings describing files, or an empty list if no files
     *         found.
     * @throws IOException
     */
    private JSONArray getDataFromApi() throws IOException {
        // Get a list of up to 10 files.
        //List<String> fileInfo = new ArrayList<String>();
        JSONArray jsonArray = new JSONArray();

        FileList result = mService.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files != null) {
            for (File file : files) {

                if(file.getName().contains("Test Stewardship")){
                    Log.d(this.getClass().getSimpleName(), ""+file.getId());
                }

                /*fileInfo.add(String.format("%s (%s)\n",
                        file.getName(), file.getId()));*/

                try {
                    jsonArray.put(new JSONObject().put("name", file.getName())
                            .put("id", file.getId()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        return jsonArray;
    }


    @Override
    protected void onPreExecute() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPostExecute(JSONArray output) {
        if (output == null || output.length() == 0) {
            Toast.makeText(activity, "User does have Google Sheet File", Toast.LENGTH_SHORT).show();
        } else {
            this.jsonArray = jsonArray;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCancelled() {
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
               // mOutputText.setText("The following error occurred:\n"
                 //       + mLastError.getMessage());
            }
        } else {
            //mOutputText.setText("Request cancelled.");
        }
    }

    private void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                activity,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }
}
