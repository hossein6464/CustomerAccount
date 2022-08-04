package diana.soleil.hossein;

import androidx.appcompat.app.AppCompatActivity;
import diana.soleil.hossein.model.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class WithdrawActivity extends AppCompatActivity {
    private EditText withdrawEditText;
    private TextView balanceTextView;
    private Button backBtn, withdrawBtn;
    private Intent intentForRecievingData;
    private Bundle bundleFromListActivity, bundleToReturnToListActivity;
    private Serializable serializableInfo, serializableReturnBackToListActivity;
    private Customer customerTransfered;

    int transferredPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        initialize();
    }

    private void initialize () {
        withdrawEditText = findViewById(R.id.amountWithdrawEditTextId);
        balanceTextView = findViewById(R.id.balanceTextViewId);

        withdrawBtn = findViewById(R.id.withdrawBtnId);
        backBtn = findViewById(R.id.backBtnId);

        balanceTextView.setText(String.valueOf(getBundle().getAccount().getBalance()));

    }

    private Customer getBundle () {
        intentForRecievingData = getIntent();
        bundleFromListActivity = intentForRecievingData.getBundleExtra("bundleToWithDraw");
        serializableInfo = bundleFromListActivity.getSerializable("customerObject");
        transferredPosition = intentForRecievingData.getIntExtra("position",-1);
        customerTransfered = (Customer) serializableInfo;
        return customerTransfered;
    }
    public void backToListView(View view) {
        Intent intent2 = new Intent(WithdrawActivity.this, DetailActivity.class);
        bundleToReturnToListActivity = new Bundle();
        bundleToReturnToListActivity.putSerializable("serializableDataFromWithdraw",customerTransfered);
        intent2.putExtra("customerToBeChangedBundled",bundleToReturnToListActivity);
        intent2.putExtra("positionOfItem",transferredPosition);
        setResult(RESULT_OK,intent2);
        finish();
    }

    public void withdrawMoney(View view){
        float moneyToBeWithdrawed = Float.parseFloat(withdrawEditText.getText().toString());
        float userBalance;
        if (customerTransfered.getAccount().getBalance() < moneyToBeWithdrawed) {
            Toast.makeText(this, "Sorry amount you requested is larger than your balance", Toast.LENGTH_SHORT).show();
        } else {
          userBalance =  customerTransfered.getAccount().getBalance() - moneyToBeWithdrawed;
          balanceTextView.setText(String.valueOf(userBalance));
          customerTransfered.getAccount().setBalance(userBalance);
        }
    }

}