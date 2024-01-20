package com.example.contactsapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.contactsapp.database.DbManager

class UpdateContact : AppCompatActivity() {

    //local var init
    var Name : String = ""
    var Id : Int = 0
    var phoneNumber : String = ""

    //initailising of ui var
    private lateinit var contactName : EditText
    private lateinit var contactNumber : EditText
    private lateinit var btnUpdateContact : Button
    private lateinit var btnDeleteContact : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_contact)

        contactName = findViewById(R.id.update_contact_name)
        contactNumber = findViewById(R.id.update_contact_number)
        btnUpdateContact = findViewById(R.id.btn_update_contact)
        btnDeleteContact = findViewById(R.id.btn_delete_contact)

        Id = intent.getIntExtra("Id",0)
        Name = intent.getStringExtra("Name").toString()
        phoneNumber = intent.getStringExtra("phoneNumber").toString()

        contactNumber.setText(phoneNumber)
        contactName.setText(Name)
        val Db = DbManager(this)
        var selectionArgs = arrayOf(phoneNumber)
        val values = ContentValues()
        values.put("Name",contactName.text.toString())
        values.put("PhoneNumber",contactNumber.text.toString())

        //onclicklistners
        btnUpdateContact.setOnClickListener {
            if (Db.Update(values, "PhoneNumber=?",selectionArgs)) {   //data insertion
                Toast.makeText(this,"Contact Updated", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Contact was not updated", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        btnDeleteContact.setOnClickListener {
            if (Db.Delete(values, "PhoneNumber=?",selectionArgs)) {   //data insertion
                Toast.makeText(this,"Contact Deleted", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Contact was not deleted", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }

}