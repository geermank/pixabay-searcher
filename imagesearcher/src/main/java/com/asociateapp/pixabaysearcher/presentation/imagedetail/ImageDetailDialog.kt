package com.asociateapp.pixabaysearcher.presentation.imagedetail

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asociateapp.pixabaysearcher.databinding.DialogImageDetailBinding
import com.asociateapp.pixabaysearcher.images.ImageGalleryManager
import com.asociateapp.pixabaysearcher.images.ImageLoader
import com.asociateapp.pixabaysearcher.presentation.BaseDialogFragment
import com.asociateapp.pixabaysearcher.utils.*
import com.asociateapp.pixabaysearcher.utils.extensions.asBitmap
import com.asociateapp.pixabaysearcher.utils.extensions.changeVisibility

internal class ImageDetailDialog : BaseDialogFragment(), ImageGalleryManager.OnImageSavedListener {

    interface OnImageInteractListener {
        fun onImageDownloaded(uri: Uri)
    }

    companion object {
        const val TAG = "ImageDetailDialog"

        fun newInstance(imageUrl: String, showDownloadOption: Boolean) = ImageDetailDialog().apply {
            arguments = Bundle().also {
                it.putString(IMAGE_URL, imageUrl)
                it.putBoolean(SHOW_DOWNLOAD_OPTION, showDownloadOption)
            }
        }

        private const val IMAGE_URL = "IMAGE_URL"
        private const val SHOW_DOWNLOAD_OPTION = "SHOW_DOWNLOAD_OPTION"
    }

    private val permissionManager by lazy {
        PermissionsManager(this)
    }

    private val galleryManager by lazy {
        context?.let { context ->
            ImageGalleryManager(context, permissionManager).also { it.setOnImageSavedListener(this) }
        }
    }

    private var onImageInteractListener: OnImageInteractListener? = null

    private lateinit var binding: DialogImageDetailBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onImageInteractListener = context as? OnImageInteractListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        load(getImageUrl())
        setCloseClickListener()
        setUpDownloadOption()
    }

    private fun setUpDownloadOption() {
        if (isDownloadOptionEnable()) {
            setOnDownloadClickListener()
        } else {
            binding.ivDownload.changeVisibility(false)
        }
    }

    private fun setOnDownloadClickListener() {
        binding.ivDownload.setOnClickListener {
            galleryManager?.save(binding.ivImage.asBitmap())
        }
    }

    private fun setCloseClickListener() {
        binding.tvClose.setOnClickListener { dismiss() }
    }

    private fun getImageUrl() = requireArguments().getString(IMAGE_URL)!!

    private fun isDownloadOptionEnable() = requireArguments().getBoolean(SHOW_DOWNLOAD_OPTION)

    private fun load(url: String) {
        ImageLoader.load(url, binding.ivImage, { hideLoading() }, { hideLoading() })
    }

    private fun hideLoading() {
        binding.pb.changeVisibility(false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionResult(permissions, grantResults)
    }

    override fun onImageSaved(imageUri: Uri) {
        dismiss()
        onImageInteractListener?.onImageDownloaded(imageUri)
    }

    override fun onImageSaveError(message: String) {
        // TODO handle error
    }

}