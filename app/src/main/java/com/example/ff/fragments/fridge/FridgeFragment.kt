package com.example.ff.fragments.fridge

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ff.R
import com.example.ff.viewmodel.FridgeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FridgeFragment : Fragment() {

    private lateinit var mFridgeViewModel: FridgeViewModel


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fridge, container, false)

        //RecyclerView
        val adapter = FridgeAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // FridgeViewModel

        mFridgeViewModel = ViewModelProvider(this).get(FridgeViewModel::class.java)
        mFridgeViewModel.readAllData.observe(viewLifecycleOwner, Observer { item ->
            adapter.setData(item)
        })



    view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener() {
        findNavController().navigate(R.id.action_fridgeFragment_to_addFragment)
    }

        setHasOptionsMenu(true)


    return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllItems()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllItems() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _, _ ->
            mFridgeViewModel.deleteAllItems()
            Toast.makeText(requireContext(), "Fridge Cleared!", Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("No"){ _, _ -> }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure you want to delete the whole fridge?")
        builder.create().show()
    }

}