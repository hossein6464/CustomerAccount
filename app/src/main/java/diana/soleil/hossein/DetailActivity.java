package diana.soleil.hossein;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import diana.soleil.hossein.model.Customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener,
                                                                AdapterView.OnItemLongClickListener,
                                                                AdapterView.OnItemClickListener {

    ListView customerListView;
    ArrayAdapter<Customer> customerArrayAdapter;

    Intent intentFromMainActivity, intentToTransferToWithdrawActivity, intentFromWithdrawActivity, intentToTransferToMainActivity;
    Bundle bundleTransferredFromMainActivity, bundleToTransferToWithdrawActivity, bundleFromWithdrawActivity, bundleToTransferToMainActivity;
    Serializable serializableTransferredFromMainActivity, serializableToTransferToWithdrawActivity , serializableFromWithDrawActivity;
    ArrayList<Customer> customerArrayListTransferredFromMainActivity;
    Customer customerFromWithdrawActivity;

    Button backBtn;
    int positionOfItemFromWithdrawActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initialize();
    }

    private void initialize() {
        customerListView = findViewById(R.id.customerListViewId);
        customerListView.setOnItemClickListener(this);
        customerListView.setOnItemLongClickListener(this);
        getIntentFromMainActivity();
        customerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customerArrayListTransferredFromMainActivity);
        customerListView.setAdapter(customerArrayAdapter);

        backBtn =findViewById(R.id.backBtnId);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        preprationToSendInfoWithDrawActivity(position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        createAlertDialog(position);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    private void getIntentFromMainActivity () {
        intentFromMainActivity = getIntent();
        bundleTransferredFromMainActivity = intentFromMainActivity.getBundleExtra("bundle");
        serializableTransferredFromMainActivity = bundleTransferredFromMainActivity.getSerializable("serializableArrayOfCustomers");
        customerArrayListTransferredFromMainActivity = (ArrayList<Customer>) serializableTransferredFromMainActivity;
    }

    public void createAlertDialog(int positionItemToBeRemoved) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete!");
        alertDialogBuilder.setMessage(" Are You sure you want to delete an Item");
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                customerArrayListTransferredFromMainActivity.remove(positionItemToBeRemoved);
                customerArrayAdapter.notifyDataSetChanged();
            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }
    private void preprationToSendInfoWithDrawActivity(int position) {
        intentToTransferToWithdrawActivity = new Intent(DetailActivity.this, WithdrawActivity.class);
        bundleToTransferToWithdrawActivity = new Bundle();
        bundleToTransferToWithdrawActivity.putSerializable("customerObject", customerArrayListTransferredFromMainActivity.get(position));
        intentToTransferToWithdrawActivity.putExtra("bundleToWithDraw", bundleToTransferToWithdrawActivity);
        intentToTransferToWithdrawActivity.putExtra("position", position);
        startActivityForResult(intentToTransferToWithdrawActivity,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                bundleFromWithdrawActivity = data.getBundleExtra("customerToBeChangedBundled");
                System.out.println(bundleFromWithdrawActivity.toString());
                serializableFromWithDrawActivity = bundleFromWithdrawActivity.getSerializable("serializableDataFromWithdraw");
                positionOfItemFromWithdrawActivity = data.getIntExtra("positionOfItem", 0);
                customerFromWithdrawActivity = (Customer) serializableFromWithDrawActivity;
                customerArrayListTransferredFromMainActivity.set(positionOfItemFromWithdrawActivity,customerFromWithdrawActivity);
                customerArrayAdapter.notifyDataSetChanged();
            }
        }
    }

    public void backToMainActivity(View view) {
        intentToTransferToMainActivity = new Intent(DetailActivity.this, MainActivity.class);
        bundleToTransferToMainActivity= new Bundle();
        bundleToTransferToMainActivity.putSerializable("serializableDataFromDetailActivity",customerArrayListTransferredFromMainActivity);
        intentToTransferToMainActivity.putExtra("customerToBeChangedBundled",bundleToTransferToMainActivity);
        setResult(RESULT_OK,intentToTransferToMainActivity);
        finish();
    }


}