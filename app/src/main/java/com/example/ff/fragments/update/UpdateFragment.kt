package com.example.ff.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ff.R
import com.example.ff.model.FridgeItem
import com.example.ff.viewmodel.FridgeViewModel


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mFridgeViewModel: FridgeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mFridgeViewModel = ViewModelProvider(this).get(FridgeViewModel::class.java)
        view.findViewById<TextView>(R.id.itemName_et).text = args.currentItem.name
        view.findViewById<TextView>(R.id.expDate_et).text = args.currentItem.expDate


        view.findViewById<Button>(R.id.updatebutton).setOnClickListener{
            updateItem()
        }

        //ADD MENU
        setHasOptionsMenu(true)


        return view
    }

    private fun updateItem(){
        val itemName = view?.findViewById<TextView>(R.id.itemName_et)?.text.toString()
        val expDate = view?.findViewById<TextView>(R.id.expDate_et)?.text.toString()

        if(inputCheck(itemName, expDate))
        {
            //create ite
            val updateFridgeItem = FridgeItem(args.currentItem.id, itemName, expDate)
            //
            mFridgeViewModel.updateItem(updateFridgeItem)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_fridgeFragment)

        }else
        {
            Toast.makeText(requireContext(), "Please fill both fields!", Toast.LENGTH_SHORT).show()
        }

    }


    private fun inputCheck(itemName: String, itemDate: String): Boolean{

        return !(TextUtils.isEmpty(itemName) && TextUtils.isEmpty(itemDate))

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteUser()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser()
    {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mFridgeViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(), "Removed ${args.currentItem.name}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_fridgeFragment)
        }
        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Delete ${args.currentItem.name}")
        builder.setMessage("Are you sure you want to delete ${args.currentItem.name}")
        builder.create().show()
        }
    }
