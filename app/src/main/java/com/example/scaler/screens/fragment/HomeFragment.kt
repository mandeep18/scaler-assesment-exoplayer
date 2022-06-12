package com.example.scaler.screens.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scaler.AppController.Companion.databaseHelper
import com.example.scaler.custom.CustomAdapter
import com.example.scaler.R
import com.example.scaler.databinding.FragmentHomeBinding
import com.example.scaler.extensions.removeFragmentFromBottom
import com.example.scaler.extensions.replaceFragmentFromBottom
import com.example.scaler.listener.OnItemClickListener
import com.example.scaler.model.VideoModel
import com.example.scaler.network.BaseViewModelFactory
import com.example.scaler.viewmodel.VideoViewModel


class HomeFragment : Fragment() {
    companion object{
        private val TAG = HomeFragment::class.java.simpleName
    }
    private var _viewBinder: FragmentHomeBinding? = null
    private var customAdapter: CustomAdapter<VideoModel>? = null
    var videoDetailsFragment : VideoDetailsFragment? = null
    private lateinit var videoViewModel: VideoViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinder = FragmentHomeBinding.inflate(inflater, container, false)
        return _viewBinder?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
        initViews()
        setObservers()
        fetchDataFromServer()
    }


    private fun initViewModels(){
        videoViewModel = ViewModelProviders.of(requireActivity(), BaseViewModelFactory(VideoViewModel(), VideoViewModel::class.java)).get(VideoViewModel::class.java)
    }

    private fun initViews(){
            customAdapter = CustomAdapter<VideoModel>(activity =  activity as AppCompatActivity).apply {
            setItemLayout(R.layout.item_home_video)
            setOnClickListener(onItemClickListener)
        }
        _viewBinder?.rvVideos?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = customAdapter
        }
    }

    private fun setObservers(){
        videoViewModel.videoListLiveData.observe(viewLifecycleOwner) {
            if(it.videos!=null){
                customAdapter?.clearItems()
                customAdapter?.addItems(it.videos as MutableList<Any>)

            }
        }
    }
    private fun fetchDataFromServer(){
        videoViewModel.getVideoList()
    }

    private val onItemClickListener : OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(videoModel: VideoModel, clMainVideoView: ImageView?) {
            showVideoDetailsFragment(videoModel)
            databaseHelper?.updateVideoViewed(videoModel)
        }

    }

    fun showVideoDetailsFragment(videoModel: VideoModel){
        videoDetailsFragment = VideoDetailsFragment.getInstance()

        if (videoDetailsFragment==null)
            return

        activity?.supportFragmentManager?.removeFragmentFromBottom(fragment = videoDetailsFragment!!)
        videoDetailsFragment?.arguments = VideoDetailsFragment.getBundle(videoModel)
        activity?.supportFragmentManager?.replaceFragmentFromBottom(R.id.flMainContainer,videoDetailsFragment!!,VideoDetailsFragment.TAG)

    }

}