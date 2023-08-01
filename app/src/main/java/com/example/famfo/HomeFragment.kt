package com.example.famfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // Activity Life Cycle Functions' of View {onPause, onResume, etc are others}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listMembers = listOf<MemberModel>(
            MemberModel("Vivek"),
            MemberModel("Jay"),
            MemberModel("Jaykumar"),
        )

        val adapter = MemberAdapter(listMembers)

        val recycler = requireView().findViewById<RecyclerView>(R.id.recycler_member)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        val inviteAdapter = InviteAdapter(fetchContacts())

        val inviterecycler = requireView().findViewById<RecyclerView>(R.id.recycler_invite)
        inviterecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        inviterecycler.adapter = inviteAdapter
    }

    @SuppressLint("Range")
    private fun fetchContacts(): ArrayList<ContactModel> {
        val cr = requireActivity().contentResolver
        val cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        val listContacts:ArrayList<ContactModel> = ArrayList()

        if(cursor != null && cursor.count > 0){
            while(cursor != null && cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                if(hasPhoneNumber > 0) {
                    val pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = ?", arrayOf(id), "")
                    if(pCur != null && pCur.count > 0) {
                        while(pCur != null && pCur.moveToNext()) {
                            val phoneNum = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            listContacts.add(ContactModel(name, phoneNum))
                        }
                        pCur.close()
                    }
                }
            }
            if(cursor != null) {
                cursor.close()
            }
        }
        return listContacts
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}