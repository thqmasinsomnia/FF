package com.example.ff.fragments.add





import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ff.R
import com.example.ff.model.FridgeItem
import com.example.ff.viewmodel.FridgeViewModel
import com.google.zxing.integration.android.IntentIntegrator


class AddFragment : Fragment() {

    private lateinit var mFridgeViewModel: FridgeViewModel

    var upc = "HELLO"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mFridgeViewModel = ViewModelProvider(this).get(FridgeViewModel::class.java)

        view.findViewById<Button>(R.id.addButton).setOnClickListener(){
            insertDataToDatabase()
        }


        view.findViewById<Button>(R.id.scanButton).setOnClickListener(){
             val scanner = IntentIntegrator.forSupportFragment(this)
            scanner.initiateScan()
            view.findViewById<TextView>(R.id.upc).text = upc


        }

        return view

    }
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                upc = result.contents


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }



    private fun insertDataToDatabase(){
        val itemName = view?.findViewById<EditText>(R.id.itemName)?.text.toString()
        val itemDate = view?.findViewById<EditText>(R.id.expDate)?.text.toString()

        if(inputCheck(itemName, itemDate)) {
            //create item object
            val item = FridgeItem(0, itemName, itemDate)
            mFridgeViewModel.addItem(item)
            Toast.makeText(requireContext(), "Item added!", Toast.LENGTH_LONG).show()


            findNavController().navigate(R.id.action_addFragment_to_fridgeFragment)
        }
        else{
            Toast.makeText(requireContext(), "FILL BOTH FIELDS", Toast.LENGTH_LONG)
        }
    }


    private fun inputCheck(itemName: String, itemDate: String): Boolean{

        return !(TextUtils.isEmpty(itemName) && TextUtils.isEmpty(itemDate))

    }



}