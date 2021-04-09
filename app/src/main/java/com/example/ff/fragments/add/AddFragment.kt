package com.example.ff.fragments.add
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ff.R
import com.example.ff.UPCList
import com.example.ff.model.FridgeItem
import com.example.ff.viewmodel.FridgeViewModel
import com.google.gson.GsonBuilder
import com.google.zxing.integration.android.IntentIntegrator
import okhttp3.*
import org.w3c.dom.Text
import java.io.IOException


class AddFragment : Fragment() {

    private lateinit var mFridgeViewModel: FridgeViewModel

    var upc = ""
    var itemname = "Item Name"

    var foundItem = false


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_add, container, false)
        view?.findViewById<EditText>(R.id.itemName)?.setText(itemname)
        mFridgeViewModel = ViewModelProvider(this).get(FridgeViewModel::class.java)

        view.findViewById<Button>(R.id.addButton).setOnClickListener(){
            insertDataToDatabase()
        }


        view.findViewById<Button>(R.id.scanButton).setOnClickListener() {
            val scanner = IntentIntegrator.forSupportFragment(this)
            scanner.initiateScan()
        }

        view.findViewById<Button>(R.id.loaditem).setOnClickListener(){
            println("stupid fix")
            view?.findViewById<EditText>(R.id.itemName)?.setText(itemname)

        }

        return view
    }


     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         println("SCANNER CALLED")
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                upc = result.contents.toString()



            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }


         }



    override fun onStart() {

        println("IM BACCCCCCCCCCCCCCCCCCCCCCK")

        super.onStart()
        fetchUPC(upc)

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

    fun fetchUPC(passedUPC: String) {
        println("************ATEMPTING JSON FETCH**********************")
        val url = "http://whydoifeellikeafailure.com/upcs.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            @SuppressLint("ShowToast")
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)

                val gson = GsonBuilder().create()
                var upcList = gson.fromJson(body, UPCList::class.java)


                for (item in upcList.upcs) {
                    if (passedUPC == item.upc_code) {
                        itemname = item.item_name.toString()
                        println(itemname)
                        println(item.item_name.toString())
                    }
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                println("failed request")
            }

        })

    }







}



