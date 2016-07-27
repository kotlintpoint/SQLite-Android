package sqlite.sodhankit.com.testapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import sqlite.sodhankit.com.testapplication.R;
import sqlite.sodhankit.com.testapplication.adapter.ContactAdapter;
import sqlite.sodhankit.com.testapplication.model.Contact;
import sqlite.sodhankit.com.testapplication.other.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    EditText etName, etNumber;
    Button btnSave, btnDelete, btnCancel;
    RecyclerView recyclerView;
    DatabaseHandler databaseHandler;
    ArrayList<Contact> contactArrayList;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.etName);
        etNumber = (EditText) findViewById(R.id.etNumber);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHandler = new DatabaseHandler(getApplicationContext());
        setContactAdapter();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String number = etNumber.getText().toString();
                String command = btnSave.getText().toString();
                if (command.equals("Save")) {
                    contact = new Contact(name, number);
                    insertContact(contact);
                } else if (contact != null) {
                    contact.setName(name);
                    contact.setNumber(number);
                    updateContact(contact);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contact != null) {
                    deleteContact(contact);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetAllViews();
            }
        });
    }

    private void deleteContact(Contact contact) {
        int count = databaseHandler.deleteContact(contact.getId());
        if (count > 0) {
            Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            resetAllViews();
            setContactAdapter();
        }
    }

    private void updateContact(Contact contact) {
        int count = databaseHandler.UpdateContact(contact.getId(), contact);
        if (count > 0) {
            Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            resetAllViews();
            setContactAdapter();
        }
    }

    private void setContactAdapter() {
        contactArrayList = databaseHandler.SelectAllContacts();
        ContactAdapter adapter = new ContactAdapter(contactArrayList, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void insertContact(Contact contact) {
        long id = databaseHandler.InsertContact(contact);
        if (id > 0) {
            Toast.makeText(MainActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
            resetAllViews();
            setContactAdapter();
        }

    }

    private void resetAllViews() {
        etName.setText("");
        etNumber.setText("");
        etName.requestFocus();
        btnSave.setText("Save");
        btnDelete.setVisibility(View.GONE);
        contact = null;
    }

    public void setContactData(Contact contact) {
        this.contact = contact;
        etName.setText(contact.getName());
        etNumber.setText(contact.getNumber());
        btnSave.setText("Update");
        btnDelete.setVisibility(View.VISIBLE);
    }

    public void makeCall(String number) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
            return;
        }
        String uri = "tel:" + number;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_PERMISSIONS_REQUEST_CALL_PHONE)
        {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(MainActivity.this, "Thank you!!! Now Try Again To Call", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity.this, "Sorry We Can't Call!!!", Toast.LENGTH_SHORT).show();
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;
        }
    }
}
