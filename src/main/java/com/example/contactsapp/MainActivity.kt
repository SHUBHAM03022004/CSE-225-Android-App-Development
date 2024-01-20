package com.example.contactsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.net.Uri
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.iterator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.recyclerview.adapter.ContactAdapter
import com.example.contactsapp.database.DbManager
import com.example.contactsapp.model.Contact

class MainActivity : AppCompatActivity(),CellClickListner{

    //recycle view ref initialise
    lateinit var recyclerView: RecyclerView
    lateinit var contactList: ArrayList<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //call checkpermissins
        checkPermissions()

        //Array list initalise
        contactList = arrayListOf<Contact>()

        //recycler view
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.iterator()
        loadData("%")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.contacts_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_contact -> {startActivity(Intent(this@MainActivity,AddContact::class.java))}
            R.id.search_contact -> {Toast.makeText(this,"serach btn clicked",Toast.LENGTH_SHORT).show()}
        }
        return super.onOptionsItemSelected(item)
    }

    //Method to fetch data from sqllite database
    @SuppressLint("Range")
    fun loadData(Name:String){
        val DBManager = DbManager(this)
        val projection = arrayOf("Id","Name","PhoneNumber")
        val selectedArgs = arrayOf(Name)
        val cursor: Cursor = DBManager.fetchData(projection,"Name like ?",selectedArgs,"Name")
        contactList.clear()

        if(cursor.moveToFirst()){
            do{
                val ID = cursor.getColumnIndex("Id")
                val Name = cursor.getString(cursor.getColumnIndex("Name"))
                val PhoneNumber = cursor.getString(cursor.getColumnIndex("PhoneNumber"))
                contactList.add(Contact(ID, Name,PhoneNumber))
            }while (cursor.moveToNext())
            recyclerView.adapter = ContactAdapter(contactList,this,this)
        }
    }

    //implementing oncellclickListner
    override fun onCellClickListner(data: Contact, position: Int){
        var intent = Intent(this,UpdateContact::class.java)
        intent.putExtra("Id",data.Id)
        intent.putExtra("Name",data.contactName)
        intent.putExtra("phoneNumber",data.phoneNumber)
        startActivity(intent)
    }

    //implementing oncallclickLinstner
    override fun onCallClickListner(data: Contact, position: Int) {
        var callIntent = Intent(Intent.ACTION_CALL)
       callIntent.data = Uri.parse("tel:${data.phoneNumber}")
        startActivity(callIntent)
    }

    //check for runTime permissions
    private fun checkPermissions(){
        //check for permission
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),101)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData("%")
    }
}


interface CellClickListner{
    fun onCallClickListner(data: Contact,position: Int)
    fun onCellClickListner(data: Contact,position: Int)
}
