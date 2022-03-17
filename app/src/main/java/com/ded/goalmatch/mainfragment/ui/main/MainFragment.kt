package com.ded.goalmatch.mainfragment.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ded.goalmatch.MainActivity
import com.ded.goalmatch.R
import com.ded.goalmatch.mainfragment.DialogUtils
import com.ded.goalmatch.mainfragment.model.MainVM


class MainFragment : Fragment() {

    private var isStop: Boolean = false
    lateinit var vm: MainVM
    lateinit var mainMatchesAdapter: MainMatchesAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var mainActivity: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(MainVM::class.java)
        mainActivity = requireActivity() as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvMain)
        vm.loading(requireContext())
        vm.mutableEmplLiveData.observe(viewLifecycleOwner) { mainMatches ->
            mainMatchesAdapter = MainMatchesAdapter(mainMatches, mainActivity)
            recyclerView.adapter = mainMatchesAdapter

            vm.isInternet

        }

        vm.mutableEmplLiveInternet.observe(viewLifecycleOwner) { isInternet ->

            if (!isInternet) {
                createAlertVideoNotDownload()
            }

        }


    }

    fun createAlertVideoNotDownload() {

        DialogUtils.showDialog(
            requireContext(),
            "Internet Connection",
            "Please,check your internet connection",
            "Ok",

            yesClickListener = { dialog, which ->
                dialog.dismiss()
                //todo снова питаться загрузить
            },

            )
    }
    override fun onStop() {
        super.onStop()
        isStop = true
    }

    override fun onResume() {
        super.onResume()
        if (isStop) {
            vm.loading(requireContext())

            isStop = false
        }
    }

}