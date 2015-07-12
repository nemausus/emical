package in.codehub.emical;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CalcResultActivity extends Activity {
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
        int baseLoan = intent.getIntExtra(EXTRA_ASSET_PRICE, 0)
                + intent.getIntExtra(EXTRA_INSURANCE_CHARGE, 0)
                + intent.getIntExtra(EXTRA_REGISTRATION_CHARGE, 0)
                + intent.getIntExtra(EXTRA_FILE_CHARGE, 0)
                + intent.getIntExtra(EXTRA_OTHER_CHARGES, 0)
                - intent.getIntExtra(EXTRA_DOWN_PAYMENT, 0);
        int serviceCharge = baseLoan/100;
        int givenLoan = baseLoan + serviceCharge;
        int loanTenure = intent.getIntExtra(EXTRA_LOAN_TENURE, 0);
        int interest = (int) Math.ceil(givenLoan * intent.getIntExtra(EXTRA_INTEREST_RATE, 0) * loanTenure / 1200);
        int matureLoan = givenLoan + interest;
        int emiAmount = Math.round(loanTenure == 0 ? 0 : matureLoan/loanTenure);
        ((TextView) findViewById(R.id.base_loan)).setText("" + baseLoan);
        ((TextView) findViewById(R.id.service_charge)).setText("+" + serviceCharge);
        ((TextView) findViewById(R.id.given_loan)).setText("" + givenLoan);
        ((TextView) findViewById(R.id.interest)).setText("+" + interest);
        ((TextView) findViewById(R.id.mature_loan)).setText("" + matureLoan);
        ((TextView) findViewById(R.id.emi_amount)).setText("" + emiAmount);
    }
}
