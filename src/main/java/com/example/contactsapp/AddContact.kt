package com.example.contactsapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.contactsapp.database.DbManager

class AddContact : AppCompatActivity() {
    //local variables
    var Id:Int=0
    var Name:String = ""
    var phoneNumber:String = ""

    //variables for text,num,btn
    private lateinit var contactName : EditText
    private lateinit var contactNumber : EditText
    private lateinit var btnSaveContact : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        //initialise
        contactName = findViewById(R.id.edit_contact_name)
        contactNumber = findViewById(R.id.edit_contact_number)
        btnSaveContact = findViewById(R.id.btn_save_contact)

        btnSaveContact.setOnClickListener {
            val Db = DbManager(this)
            if (Db.insertData(InsetDataToDatabase())) {   //data insertion
                Toast.makeText(this,"Contact Added",Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Contact was not added",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }

    //returns values
    fun InsetDataToDatabase() : ContentValues {
        val values = ContentValues()
        values.put("Name",contactName.text.toString())
        values.put("PhoneNumber",contactNumber.text.toString())
        return values
    }

}