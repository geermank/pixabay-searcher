package com.asociateapp.pixabaysearcher.images

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import com.asociateapp.pixabaysearcher.R
import com.asociateapp.pixabaysearcher.utils.PermissionsManager
import java.io.OutputStream
import java.util.UUID
import java.util.concurrent.Executors

internal class ImageGalleryManager(
    private val context: Context,
    private val permissionsManager: PermissionsManager
) : PermissionsManager.OnPermissionsResultListener {

    interface OnImageSavedListener {
        /**
         * Notifies when the Bitmap was saved in the user's
         * gallery successfully
         */
        fun onImageSaved(imageUri: Uri)

        /**
         * Notifies when an error occurs while trying to
         * save a Bitmap in the user's gallery
         */
        fun onImageSaveError(message: String)
    }

    init {
        permissionsManager.setOnPermissionResultListener(this)
    }

    /**
     * Listener to return image save results to caller class
     */
    private var callback: OnImageSavedListener? = null

    /**
     * Temporary save in memory the Bitmap to be saved in the gallery,
     * until permissions results are received
     */
    private lateinit var temporaryBitmap: Bitmap

    /**
     * Sets a callback to listen the results of saving the image
     */
    fun setOnImageSavedListener(listener: OnImageSavedListener) {
        this.callback = listener
    }

    /**
     * Attempts to save a Bitmap in the user's gallery.
     *
     * If storage permissions weren't granted, prompts the user to grant them
     */
    fun save(image: Bitmap) {
        this.temporaryBitmap = image
        this.permissionsManager.requestPermissionsIfNeeded(permissionsNeededForStorage())
    }

    override fun onPermissionsGranted() {
        check(::temporaryBitmap.isInitialized) { "You must provide an image to save" }
        createFileFromBitmap(temporaryBitmap)
    }

    override fun onPermissionsDenied() {
        callback?.onImageSaveError(context.getString(R.string.image_gallery_image_not_saved))
    }

    private fun createFileFromBitmap(bitmap: Bitmap) {
        Executors.newSingleThreadExecutor().submit {
            val imageCollection = MediaStore.Images.Media.getContentUri(getVolume())

            val newImage = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "${getImageName()}$IMAGE_EXTENSION")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val resolver = context.contentResolver
            resolver.insert(imageCollection, newImage)?.let { uri ->
                resolver.openOutputStream(uri)?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_COMPRESSION_QUALITY, stream)
                    cleanResources(stream, bitmap)
                    notifySuccess(uri)
                }
            }
        }
    }

    private fun notifySuccess(uri: Uri) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable { callback?.onImageSaved(uri) }
        handler.post(runnable)
    }

    private fun getVolume() = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
        MediaStore.VOLUME_EXTERNAL
    } else {
        MediaStore.VOLUME_EXTERNAL_PRIMARY
    }

    private fun cleanResources(fileOutputStream: OutputStream?, bitmap: Bitmap?) {
        fileOutputStream?.close()
        bitmap?.recycle()
    }

    private fun getImageName() = UUID.randomUUID().toString()

    private fun permissionsNeededForStorage() = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

    companion object {
        private const val JPEG_COMPRESSION_QUALITY = 100
        private const val IMAGE_EXTENSION = ".jpeg"
    }
}