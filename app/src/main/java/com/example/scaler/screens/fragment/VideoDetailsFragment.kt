package com.example.scaler.screens.fragment

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scaler.AppController
import com.example.scaler.custom.CustomAdapter
import com.example.scaler.R
import com.example.scaler.constants.AppConstants
import com.example.scaler.databinding.FragmentVideoDetailsBinding
import com.example.scaler.extensions.hideShowActionBar
import com.example.scaler.listener.OnItemClickListener
import com.example.scaler.model.VideoModel
import com.example.scaler.network.BaseViewModelFactory
import com.example.scaler.viewmodel.VideoViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class VideoDetailsFragment : Fragment(){

    companion object{
        var TAG = VideoDetailsFragment::class.java.simpleName

        fun getInstance():VideoDetailsFragment{
            return VideoDetailsFragment()
        }

        fun getBundle(videoModel: VideoModel):Bundle{
            return Bundle().apply {
                putSerializable(AppConstants.KEY_VIDEO_DETAILS,videoModel)
            }
        }
    }

    private val exoPlayer: ExoPlayer by lazy { ExoPlayer.Builder(requireContext()).build()}
    private var mVideoModel:VideoModel? = null
        get() {
            return arguments?.getSerializable(AppConstants.KEY_VIDEO_DETAILS) as VideoModel?
        }

    private var _viewBinder: FragmentVideoDetailsBinding? = null
    private var customAdapter: CustomAdapter<VideoModel>? = null
    var fullScreenMode = false
    private lateinit var videoViewModel: VideoViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinder = FragmentVideoDetailsBinding.inflate(inflater, container, false)
        return _viewBinder?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
        initViews()
        setListeners()
        setObservers()
        setUpExoPlayer(mVideoModel)
    }

    private fun setListeners(){
        _viewBinder?.playerView?.findViewById<ImageView>(R.id.exo_fullscreen_icon)?.setOnClickListener(onClickListener)
        exoPlayer.addListener(eventListener)
    }
    private fun initViewModels(){
        videoViewModel = ViewModelProviders.of(requireActivity(), BaseViewModelFactory(
            VideoViewModel(), VideoViewModel::class.java)
        ).get(VideoViewModel::class.java)
    }

    private fun setObservers(){
        videoViewModel.videoListLiveData.observe(viewLifecycleOwner) {
            if(it.videos!=null){
                customAdapter?.clearItems()
                customAdapter?.addItems(it.videos as MutableList<Any>)
            }
        }
    }

    private fun initViews(){
        customAdapter = CustomAdapter<VideoModel>(activity =  activity as AppCompatActivity).apply {
            setItemLayout(R.layout.item_video)
            setOnClickListener(onItemClickListener)
        }
        _viewBinder?.rvVideos?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = customAdapter
        }
    }


    private fun setUpExoPlayer(videoModel:VideoModel?){
        _viewBinder?.playerView?.apply {
            player = exoPlayer
        }

        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        exoPlayer.setMediaSource(
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(videoModel?.video_url))))
        _viewBinder?.playerView?.findViewById<TextView>(R.id.tvTitle)?.text = videoModel?.title
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }


    private val onItemClickListener : OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(videoModel: VideoModel) {
            setUpExoPlayer(videoModel)
            AppController.databaseHelper?.updateVideoViewed(videoModel)
        }

    }

    private fun playWhenReady(status:Boolean){
        exoPlayer.playWhenReady = status
    }

    private fun destroyVideoObjects(){
        exoPlayer.release()
    }

    private val onClickListener : View.OnClickListener = object : View.OnClickListener{
        override fun onClick(p0: View?) {
            when(p0?.id){
                R.id.exo_fullscreen_icon -> {
                    toggleFullScreen()
                }
            }
        }

    }

    private val eventListener: Player.Listener = object : Player.Listener {

        override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray) {

        }

        override fun onLoadingChanged(isLoading: Boolean) {

        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {

                Player.STATE_ENDED -> {

                }

                Player.STATE_READY -> {
                    _viewBinder?.progressBar?.visibility = View.GONE
                }

                Player.STATE_BUFFERING -> {
                    _viewBinder?.progressBar?.visibility = View.VISIBLE
                }

                Player.STATE_IDLE -> {

                }
            }
        }

        override fun onRepeatModeChanged(repeatMode: Int) {

        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

        }

        override fun onPositionDiscontinuity(reason: Int) {

        }

        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {

        }

        override fun onSeekProcessed() {

        }
    }

    fun toggleFullScreen(){
        if(context==null)
            return

        if (fullScreenMode) {
            _viewBinder?.playerView?.findViewById<ImageView>(R.id.exo_fullscreen_icon)?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_fullscreen))
            showSystemUI()
            activity?.hideShowActionBar(true)
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            val params = _viewBinder?.playerView?.layoutParams as ConstraintLayout.LayoutParams
            params.height = 0
            params.dimensionRatio = "H,16,9"
            _viewBinder?.playerView?.layoutParams = params

            fullScreenMode = false
        } else {
            _viewBinder?.playerView?.findViewById<ImageView>(R.id.exo_fullscreen_icon)?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_fullscreen_exit))
            hideSystemUI()
            activity?.hideShowActionBar(false)
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            _viewBinder?.playerView?.layoutParams = _viewBinder?.playerView?.layoutParams?.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
            fullScreenMode = true
        }
    }

    private fun hideSystemUI() {
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView).show(
            WindowInsetsCompat.Type.systemBars())
    }


    override fun onStart() {
        super.onStart()
        playWhenReady(true)
    }

    override fun onPause() {
        super.onPause()
        playWhenReady(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        customAdapter?.clearItems()
        destroyVideoObjects()
        playWhenReady(false)
    }


}