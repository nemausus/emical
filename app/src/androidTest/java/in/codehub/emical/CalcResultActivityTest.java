package in.codehub.emical;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.widget.TextView;

public class CalcResultActivityTest extends ActivityInstrumentationTestCase2<CalcResultActivity> {

    private CalcResultActivity mActivity;

    public CalcResultActivityTest() {
        super(CalcResultActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        intent.putExtra(CalcResultActivity.EXTRA_ASSET_PRICE, 50000.0);
        intent.putExtra(CalcResultActivity.EXTRA_INSURANCE_CHARGE, 3000.0);
        intent.putExtra(CalcResultActivity.EXTRA_REGISTRATION_CHARGE, 1500.0);
        intent.putExtra(CalcResultActivity.EXTRA_FILE_CHARGE, 1999.0);
        intent.putExtra(CalcResultActivity.EXTRA_OTHER_CHARGES, 100.0);
        intent.putExtra(CalcResultActivity.EXTRA_DOWN_PAYMENT, 23400.0);
        intent.putExtra(CalcResultActivity.EXTRA_INTEREST_RATE, 12.5);
        intent.putExtra(CalcResultActivity.EXTRA_LOAN_TENURE, 24.0);
        setActivityIntent(intent);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getInstrumentation().getTargetContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("service_charge_key", "1.0");
        editor.apply();
        mActivity = getActivity();
    }

    public void testExtras() {
        assertEquals(50000.0, mActivity.getIntent().getDoubleExtra(CalcResultActivity.EXTRA_ASSET_PRICE, 0));
    }

    public void testEmiAmount() {
        assertEquals("33199.00", readTextView(R.id.base_loan));
        assertEquals("(1.0%) 331.99", readTextView(R.id.service_charge));
        assertEquals("33530.99", readTextView(R.id.given_loan));
        assertEquals("8383.00", readTextView(R.id.interest));
        assertEquals("41913.99", readTextView(R.id.mature_loan));
        assertEquals("1746.00", readTextView(R.id.emi_amount));
    }

    private String readTextView(int id) {
        return ((TextView)mActivity.findViewById(id)).getText().toString();
    }
}