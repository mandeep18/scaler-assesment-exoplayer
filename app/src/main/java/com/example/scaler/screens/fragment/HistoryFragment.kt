package com.example.scaler.screens.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scaler.AppController.Companion.databaseHelper
import com.example.scaler.R
import com.example.scaler.custom.CustomAdapter
import com.example.scaler.databinding.FragmentHistoryBinding
import com.example.scaler.extensions.showToast
import com.example.scaler.listener.OnItemClickListener
import com.example.scaler.model.VideoModel

class HistoryFragment : Fragment() {

    companion object{
        private val TAG = HistoryFragment::class.java.simpleName
    }
    private var _viewBinder: FragmentHistoryBinding? = null
    private var customAdapter: CustomAdapter<VideoModel>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinder = FragmentHistoryBinding.inflate(inflater, container,false )
        return _viewBinder?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
    }

    private fun initViews(){
        customAdapter = CustomAdapter<VideoModel>(activity =  activity as AppCompatActivity).apply {
            setItemLayout(R.layout.item_video)
            setOnClickListener(onItemClickListener)
        }
        _viewBinder?.rvHistoryVideos?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = customAdapter
        }
    }

    private fun setObservers(){
        databaseHelper?.subscribeToVideoList()?.observe(requireActivity()) {
            if (it.size>0) {
                customAdapter?.addItems(it as MutableList<Any>)
                _viewBinder?.history?.clNoHistory?.visibility = View.GONE
            } else
                _viewBinder?.history?.clNoHistory?.visibility = View.VISIBLE
        }
    }

    private val onItemClickListener : OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(videoModel: VideoModel) {
            showToast("History Item-> ${videoModel.title}")
        }

    }

}