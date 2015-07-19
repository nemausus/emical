package in.codehub.emical;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class CalcInputActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_calc);
        Button button = (Button) findViewById(R.id.calculate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalcResultActivity.class);
                intent.putExtra(CalcResultActivity.EXTRA_ASSET_PRICE, getInput(R.id.asset_price));
                intent.putExtra(CalcResultActivity.EXTRA_INSURANCE_CHARGE, getInput(R.id.insurance_charge));
                intent.putExtra(CalcResultActivity.EXTRA_REGISTRATION_CHARGE, getInput(R.id.registration_charge));
                intent.putExtra(CalcResultActivity.EXTRA_FILE_CHARGE, getInput(R.id.file_charge));
                intent.putExtra(CalcResultActivity.EXTRA_OTHER_CHARGES, getInput(R.id.other_charges));
                intent.putExtra(CalcResultActivity.EXTRA_DOWN_PAYMENT, getInput(R.id.down_payment));
                intent.putExtra(CalcResultActivity.EXTRA_INTEREST_RATE, getInput(R.id.interest_rate));
                intent.putExtra(CalcResultActivity.EXTRA_LOAN_TENURE, getInput(R.id.loan_tenure));
                startActivity(intent);
            }
        });
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String fileCharge = sharedPref.getString("file_charge_key", "0.0");
        ((EditText)findViewById(R.id.file_charge)).setText(fileCharge);
        ((EditText)findViewById(R.id.insurance_charge)).setText("0.0");
        ((EditText)findViewById(R.id.registration_charge)).setText("0.0");
        ((EditText)findViewById(R.id.other_charges)).setText("0.0");
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

    private double getInput(int id) {
        EditText editText = (EditText) findViewById(id);
        String text = editText.getText().toString();
        return text.isEmpty() ? 0 : Double.valueOf(text);
    }
}
