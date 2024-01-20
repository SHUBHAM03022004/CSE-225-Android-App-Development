package com.example.contactsapp.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.MainActivity
import com.example.contactsapp.R
import com.example.contactsapp.model.Contact

class ContactAdapter(private val contactList:ArrayList<Contact>, private val context: Context, private val cellClickListener: MainActivity) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val txtContact: TextView = view.findViewById(R.id.textViewContactItem)
        val btnCall: ImageButton = view.findViewById(R.id.call_contact_btn)


        fun bindView(contact: Contact){
            txtContact.text = contact.contactName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item,parent,false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currItem = contactList[position]
        holder.bindView(currItem)

        holder.btnCall.setOnClickListener {
            cellClickListener.onCallClickListner(currItem,position)
        }

        holder.txtContact.setOnClickListener {
            cellClickListener.onCellClickListner(currItem,position)
        }
    }
}