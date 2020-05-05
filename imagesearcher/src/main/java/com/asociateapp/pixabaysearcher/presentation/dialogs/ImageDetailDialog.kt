package com.asociateapp.pixabaysearcher.presentation.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.asociateapp.pixabaysearcher.R
import com.asociateapp.pixabaysearcher.utils.*
import kotlinx.android.synthetic.main.dialog_image_detail.*

internal class ImageDetailDialog : DialogFragment(), ImageGalleryManager.OnImageSavedListener {

    override fun onImageSaved() {
        dismiss()
        Toast.makeText(context, "Iuju, guardamos la imagen", Toast.LENGTH_SHORT).show()
    }

    override fun onImageSaveError(message: String, buttonMessage: String, action: () -> Unit) {

    }

    companion object {
        const val TAG = "ImageDetailDialog"

        fun newInstance(imageUrl: String) = ImageDetailDialog().apply {
            arguments = Bundle().also { it.putString(IMAGE_URL, imageUrl) }
        }

        private const val IMAGE_URL = "IMAGE_URL"
    }

    private val permissionManager by lazy {
        PermissionsManager(this)
    }

    private val galleryManager by lazy {
        context?.let {
            ImageGalleryManager(it, permissionManager).also { galleryManager -> galleryManager.setOnImageSavedListener(this) }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionResult(permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_image_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        load(getImageUrl())
        setCloseClickListener()
        setOnSaveClickListener()
    }

    private fun setOnSaveClickListener() {
        iv_download.setOnClickListener {
            val bitmap = iv_image.asBitmap()
            galleryManager?.save(bitmap)
        }
    }

    private fun setCloseClickListener() {
        tv_close.setOnClickListener { dismiss() }
    }

    private fun getImageUrl() = arguments!!.getString(IMAGE_URL)!!

    private fun load(url: String) {
        ImageLoader.load(url, iv_image, { hideLoading() }, { hideLoading() })
    }

    private fun hideLoading() {
        pb.changeVisibility(false)
    }

}