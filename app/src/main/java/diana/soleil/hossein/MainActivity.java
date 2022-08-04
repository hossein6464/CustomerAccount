package diana.soleil.hossein;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import diana.soleil.hossein.model.Account;
import diana.soleil.hossein.model.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // All Views
    EditText accountNumberEditText, openDateEditText, balanceEditText, firstnameEditText, lastnameEditText, phoneEditText, sinEditText;
    Button addBtn, findBtn, removeBtn, updateBtn, clearBtn, showAllBtn;

    // Variables
    ArrayList<Customer> customerArrayList;
    Account userInputAccount;
    Customer userInputCustomer;

    double accountNumber, phone;
    float balance;
    int sin;
    String openDate, firstname, lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {

        // All Edit Text Views are here
        accountNumberEditText = findViewById(R.id.accountNumberEditTextId);
        openDateEditText = findViewById(R.id.openDateEditTextId);
        balanceEditText = findViewById(R.id.balanceEditTextId);
        firstnameEditText = findViewById(R.id.firstnameEditTextId);
        lastnameEditText = findViewById(R.id.lastnameEditTextId);
        phoneEditText = findViewById(R.id.phoneEditTextId);
        sinEditText = findViewById(R.id.sinEditTextId);

        // All Button Views are here
        addBtn = findViewById(R.id.addBtnId);
        addBtn.setOnClickListener(this);

        findBtn = findViewById(R.id.findBtnId);
        findBtn.setOnClickListener(this);

        removeBtn = findViewById(R.id.removeBtnId);
        removeBtn.setOnClickListener(this);

        updateBtn = findViewById(R.id.updateBtnId);
        updateBtn.setOnClickListener(this);

        clearBtn = findViewById(R.id.clearBtnId);
        clearBtn.setOnClickListener(this);

        showAllBtn = findViewById(R.id.showAllBtnId);
        showAllBtn.setOnClickListener(this);

        // Initializing Variables
        customerArrayList = new ArrayList<>();
    }

    // On Click is here
    @Override
    public void onClick(View v) {
        int sinForFind;
        switch (v.getId()) {
            case R.id.addBtnId:
                if (checkTextFields().equals("")) {
                    userInputCustomer = createCustomerObj();
                    addCustomers(findCustomer(userInputCustomer), userInputCustomer);
                    Toast.makeText(this, "Client Successfully got added to our DB", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, checkTextFields(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.findBtnId:
                sinForFind = Integer.parseInt(sinEditText.getText().toString());
                populateCustomerFound(findCustomerBySin(sinForFind), sinForFind);
                break;
            case R.id.removeBtnId:
                sinForFind = Integer.parseInt(sinEditText.getText().toString());
                removeCustomerBySIN(findCustomerBySin(sinForFind), sinForFind);
                break;
            case R.id.updateBtnId:
                if (checkTextFields().equals("")) {
                    sinForFind = Integer.parseInt(sinEditText.getText().toString());
                    updateCustomerBySIN(findCustomerBySin(sinForFind), sinForFind);
                    Toast.makeText(this, "Client Successfully got updated in our DB", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, checkTextFields(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.clearBtnId:
                clearAllTexts();
                break;
            case R.id.showAllBtnId:
                showAll();
                break;
        }
    }

    private Customer createCustomerObj () {
        accountNumber = Double.parseDouble(accountNumberEditText.getText().toString());
        balance = Float.parseFloat(balanceEditText.getText().toString());
        openDate = openDateEditText.getText().toString();
        firstname = firstnameEditText.getText().toString();
        lastname = lastnameEditText.getText().toString();
        phone = Double.parseDouble(phoneEditText.getText().toString());
        sin = Integer.parseInt(sinEditText.getText().toString());
        userInputAccount = new Account(accountNumber, openDate,balance);
        return new Customer(firstname, lastname, phone, sin, userInputAccount);
    }

    private void addCustomers (boolean customerExists, Customer customerToBeAdded) {
        String error = "Sorry the client " + customerToBeAdded.getFirstname() + " " + customerToBeAdded.getLastname() + " can not be added because we " +
                "have a client with the same SIN "  + customerToBeAdded.getSIN() + " in out DB";

        if (customerExists) {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        } else {
                customerArrayList.add(customerToBeAdded);
                clearAllTexts();
        }
    }

    private boolean findCustomer (Customer customerToFind) {
        boolean customerExists = false;

        if (customerArrayList.size()>0) {
            for (Customer customer: customerArrayList) {
                if (customer.equals(customerToFind)) {
                    customerExists = true;
                }
            }
        }
        return customerExists;
    }

    private boolean findCustomerBySin (int sinInput) {
        boolean customerExists = false;

        if (customerArrayList.size()>0) {
            for (Customer customer: customerArrayList) {
                if (customer.getSIN() == sinInput) {
                    customerExists = true;
                }
            }
        }
        return customerExists;
    }

    private void populateCustomerFound (boolean customerFoundBySin, int sin) {
        String success = "Client with SIN number " + sin + " was found";
        String error = "Sorry the client with sin number " + sin + " can not be found in our DB ";
        if (customerFoundBySin) {
                for (Customer customerToBePopulated: customerArrayList) {
                    if (customerToBePopulated.getSIN() == sin) {
                        accountNumberEditText.setText(String.valueOf(customerToBePopulated.getAccount().getAccount_number()));
                        openDateEditText.setText(customerToBePopulated.getAccount().getOpen_date());
                        balanceEditText.setText(String.valueOf(customerToBePopulated.getAccount().getBalance()));
                        firstnameEditText.setText(customerToBePopulated.getFirstname());
                        lastnameEditText.setText(customerToBePopulated.getLastname());
                        phoneEditText.setText(String.valueOf(customerToBePopulated.getPhone()));
                        sinEditText.setText(String.valueOf(customerToBePopulated.getSIN()));
                        Toast.makeText(this, success, Toast.LENGTH_LONG).show();
                    }
                }
        } else {
            Toast.makeText(this, error , Toast.LENGTH_LONG).show();
        }
    }

    private void removeCustomerBySIN(boolean customerExists, int sin) {
        String success = "Client with SIN number " + sin + " is remove from our DB";
        String error = "Sorry the client with sin number " + sin + " can not be found in our DB ";
        if (customerExists) {
            for (Customer customerToBePopulated: customerArrayList) {
                if (customerToBePopulated.getSIN() == sin) {
                    customerArrayList.remove(customerToBePopulated);
                    Toast.makeText(this, success, Toast.LENGTH_LONG).show();
                    clearAllTexts();
                }
            }
        } else {
            Toast.makeText(this, error , Toast.LENGTH_LONG).show();
        }
    }

    private void updateCustomerBySIN(boolean customerExists, int sin) {
        String success = "All Client info was updated including SIN in our DB";
        String error = "Sorry the client with sin number " + sin + " can not be found in our DB ";
        if (customerExists) {
            for (Customer customerToBePopulated: customerArrayList) {
                if (customerToBePopulated.getSIN() == sin) {
                    customerToBePopulated.getAccount().setAccount_number(Double.parseDouble(accountNumberEditText.getText().toString()));
                    customerToBePopulated.getAccount().setBalance(Float.parseFloat(balanceEditText.getText().toString()));
                    customerToBePopulated.getAccount().setOpen_date(openDateEditText.getText().toString());
                    customerToBePopulated.setFirstname(firstnameEditText.getText().toString());
                    customerToBePopulated.setLastname(lastnameEditText.getText().toString());
                    customerToBePopulated.setPhone(Double.parseDouble(phoneEditText.getText().toString()));
                    customerToBePopulated.setSIN(Integer.parseInt(sinEditText.getText().toString()));
                    Toast.makeText(this, success, Toast.LENGTH_LONG).show();
                    clearAllTexts();
                }
            }
        } else {
            Toast.makeText(this, error , Toast.LENGTH_LONG).show();
        }
    }

    private void clearAllTexts() {
        accountNumberEditText.setText("");
        openDateEditText.setText("");
        balanceEditText.setText("");
        firstnameEditText.setText("");
        lastnameEditText.setText("");
        phoneEditText.setText("");
        sinEditText.setText("");
    }

    private void showAll() {
        Intent intentToDetail = new Intent(MainActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("serializableArrayOfCustomers", customerArrayList);
        intentToDetail.putExtra("bundle", bundle);
        startActivityForResult(intentToDetail,2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Bundle bundleFromDetailActivity = data.getBundleExtra("customerToBeChangedBundled");
                System.out.println(bundleFromDetailActivity.toString());
                Serializable serializableFromWithDrawActivity = bundleFromDetailActivity.getSerializable("serializableDataFromDetailActivity");
                customerArrayList.clear();
                customerArrayList.addAll((ArrayList<Customer>) serializableFromWithDrawActivity);
            }
        }
    }
    public String checkTextFields () {
        StringBuilder emptyFieldExists = new StringBuilder();
        emptyFieldExists.append("");
        if (accountNumberEditText.getText().toString().equals("")) {
            emptyFieldExists.append("Account number field can not be empty.\n");
        } else if (accountNumberEditText.getText().toString().length() > 8) {
            emptyFieldExists.append("Account number should be less than 8 digits.\n");
        } else if (openDateEditText.getText().toString().equals("")) {
            emptyFieldExists.append("Open Date field can not be empty.\n");
        } else if (balanceEditText.getText().toString().equals("")) {
            emptyFieldExists.append("Balance field can not be empty.\n");
        } else if (firstnameEditText.getText().toString().equals("")) {
            emptyFieldExists.append("Firstname field can not be empty.\n");
        } else if (lastnameEditText.getText().toString().equals("")) {
            emptyFieldExists.append("Lastname field can not be empty.\n");
        } else if (sinEditText.getText().toString().equals("")) {
            emptyFieldExists.append("Sin field can not be empty.\n");
        } else if (phoneEditText.getText().toString().equals("")) {
            emptyFieldExists.append("Phone field can not be empty.\n");
        }
        return emptyFieldExists.toString();
    }
}

