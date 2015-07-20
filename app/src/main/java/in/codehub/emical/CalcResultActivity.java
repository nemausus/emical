package in.codehub.emical;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class CalcResultActivity extends Activity {
    private static final String TAG = "EMI-" + CalcResultActivity.class.getSimpleName();
    public static final String EXTRA_ASSET_PRICE = "asset_price";
    public static final String EXTRA_INSURANCE_CHARGE = "insurance_charge";
    public static final String EXTRA_REGISTRATION_CHARGE = "registration_charge";
    public static final String EXTRA_FILE_CHARGE = "file_charge";
    public static final String EXTRA_OTHER_CHARGES = "other_charges";
    public static final String EXTRA_DOWN_PAYMENT = "down_payment";
    public static final String EXTRA_INTEREST_RATE = "interest_rate";
    public static final String EXTRA_LOAN_TENURE = "loan_tenure";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        Log.d(TAG, "AssetPrice " + intent.getStringExtra(EXTRA_ASSET_PRICE));
        double baseLoan = intent.getDoubleExtra(EXTRA_ASSET_PRICE, 0)
                + intent.getDoubleExtra(EXTRA_INSURANCE_CHARGE, 0)
                + intent.getDoubleExtra(EXTRA_REGISTRATION_CHARGE, 0)
                + intent.getDoubleExtra(EXTRA_FILE_CHARGE, 0)
                + intent.getDoubleExtra(EXTRA_OTHER_CHARGES, 0)
                - intent.getDoubleExtra(EXTRA_DOWN_PAYMENT, 0);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        double serviceChargeRate = Double.parseDouble(sharedPref.getString("service_charge_key", "1.0"));
        double serviceCharge = baseLoan * serviceChargeRate / 100;
        double givenLoan = baseLoan + serviceCharge;
        double loanTenure = intent.getDoubleExtra(EXTRA_LOAN_TENURE, 0);
        double interest = Math.ceil(givenLoan * intent.getDoubleExtra(EXTRA_INTEREST_RATE, 0) * loanTenure / 1200);
        double matureLoan = givenLoan + interest;
        double emiAmount = Math.round(loanTenure == 0 ? 0 : matureLoan/loanTenure);
        setValue(R.id.base_loan, baseLoan);
        ((TextView) findViewById(R.id.service_charge)).setText(String.format("(%.1f%%) %.2f", serviceChargeRate, serviceCharge));
        setValue(R.id.given_loan, givenLoan);
        setValue(R.id.interest, interest);
        setValue(R.id.mature_loan, matureLoan);
        setValue(R.id.emi_amount, emiAmount);
    }

    private void setValue(int id, double value) {
        ((TextView) findViewById(id)).setText(String.format("%.2f", value));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
