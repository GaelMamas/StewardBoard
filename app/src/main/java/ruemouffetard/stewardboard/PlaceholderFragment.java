package ruemouffetard.stewardboard;

/**
 * Created by user on 01/09/2017.
 */

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.SheetsScopes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import ruemouffetard.stewardboard.SpreadSheetServices.MakeDriveAPIRequestTask;
import ruemouffetard.stewardboard.SpreadSheetServices.SheetsMultiReadRequests;
import ruemouffetard.stewardboard.SpreadSheetServices.SheetsMultiWriteRequests;
import ruemouffetard.stewardboard.SpreadSheetServices.SheetsReadRequests;
import ruemouffetard.stewardboard.SpreadSheetServices.SheetsWriteRequests;
import ruemouffetard.stewardboard.Widgets.ExpensesKeyingLayout;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class PlaceholderFragment extends Fragment
        implements TextView.OnEditorActionListener, EasyPermissions.PermissionCallbacks, View.OnClickListener {

    GoogleAccountCredential mCredential, mCredentialDrive;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    public static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {DriveScopes.DRIVE_METADATA_READONLY};
    private static final String[] SCOPES_SHEETS = {SheetsScopes.SPREADSHEETS};

    JSONArray userGoogleSheetFiles = new JSONArray();
    String spreadsheetId;


    private static final String ARG_SECTION_NUMBER = "section_number";


    private AppCompatSpinner spinner;
    private AppCompatEditText editText, addInputEditText;
    private List<String> inputList = new ArrayList<>();
    private FloatingActionButton actionButton;
    private ArrayAdapter<String> adapter;

    public PlaceholderFragment() {
    }


    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        spinner = (AppCompatSpinner) rootView.findViewById(R.id.spinner);
        editText = (AppCompatEditText) rootView.findViewById(R.id.edittext);
        addInputEditText = (AppCompatEditText) rootView.findViewById(R.id.edittext_add_input);

        rootView.findViewById(R.id.button_0).setOnClickListener(this);
        rootView.findViewById(R.id.button_1).setOnClickListener(this);
        rootView.findViewById(R.id.button_2).setOnClickListener(this);
        rootView.findViewById(R.id.button_3).setOnClickListener(this);


        adapter = new ArrayAdapter<>(getContext(), R.layout.test_text, inputList);

        spinner.requestLayout();
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String inputTitle = (String) adapterView.getSelectedItem();

                editText.setHint(getString(R.string.specific_input_field_hint, inputTitle));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        actionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hideShowInputView(addInputEditText.getVisibility() == View.GONE);

                getResultsFromApi();

            }
        });

        actionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new MakeDriveAPIRequestTask(mCredentialDrive, getActivity(), userGoogleSheetFiles).execute();

                for (int i = 0; i < userGoogleSheetFiles.length(); i++) {

                    try {
                        if (((JSONObject) (userGoogleSheetFiles.get(i))).get("name").equals("Test Stewardship")) {
                            spreadsheetId = (String) ((JSONObject) (userGoogleSheetFiles.get(i))).get("id");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                return false;
            }
        });

        editText.setOnEditorActionListener(this);
        addInputEditText.setOnEditorActionListener(this);


        mCredential = GoogleAccountCredential.usingOAuth2(
                // SCOPES_SHEETS
                getActivity().getApplicationContext(), Arrays.asList(SCOPES_SHEETS))
                .setBackOff(new ExponentialBackOff());

        mCredentialDrive = GoogleAccountCredential.usingOAuth2(
                // SCOPES_DRIVE
                getActivity().getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());




        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        spreadsheetId = Constants.SPREADSHEETID;

    }


    private void hideShowInputView(boolean doIShowInvisibleOne) {
        spinner.setVisibility(doIShowInvisibleOne ? View.INVISIBLE : VISIBLE);
        editText.setVisibility(doIShowInvisibleOne ? View.INVISIBLE : View.VISIBLE);
        addInputEditText.setVisibility(doIShowInvisibleOne ? View.VISIBLE : View.GONE);
        actionButton.setVisibility(doIShowInvisibleOne ? GONE : VISIBLE);

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (TextUtils.isEmpty(textView.getText()) || (keyEvent != null && keyEvent.getAction() != MotionEvent.ACTION_DOWN)) {
            return false;
        }


        switch (textView.getId()) {
            case R.id.edittext:
                if (getView() != null) {
                    editText.setText("");
                    Toast.makeText(getContext(), getString(R.string.invoice_output,
                            inputList.get(spinner.getSelectedItemPosition()), textView.getText().toString()), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.edittext_add_input:
                inputList.add(textView.getText().toString());
                adapter.notifyDataSetChanged();
                spinner.setSelection(inputList.size() - 1);
                addInputEditText.setText("");
                hideShowInputView(false);

                break;
        }

        hideKeyboard(getActivity().getCurrentFocus());
        return true;
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            Toast.makeText(getContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
        } else {
            new SheetsReadRequests(mCredential, getActivity(),
                    inputList, spreadsheetId, "Vases!A1:C2").execute();
        }
    }


    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                getActivity(), Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getActivity().getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }


    @Override
    public void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {

                    Toast.makeText(getContext(), "This app requires Google Play Services. Please install " +
                            "Google Play Services on your device and relaunch this app.", Toast.LENGTH_SHORT).show();


                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }


    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }


    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        return (networkInfo != null && networkInfo.isConnected());
    }


    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(getActivity());
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }


    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }



    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                getActivity(),
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_0:

                new SheetsReadRequests(mCredential, getActivity(),
                        inputList, spreadsheetId, "Vases!A1:C5").execute();

            case R.id.button_1:

                new SheetsMultiReadRequests(mCredential, getActivity(),
                        inputList, spreadsheetId, "Test!A1:C1").execute();

            case R.id.button_2:

                new SheetsWriteRequests(mCredential, getActivity(),
                        inputList, spreadsheetId, "Vases!A8:C8").execute();

            case R.id.button_3:

                new SheetsMultiWriteRequests(mCredential, getActivity(),
                        inputList, spreadsheetId, "Test!A1:A4").execute();

        }
    }
}
