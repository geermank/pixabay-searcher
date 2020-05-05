package com.asociateapp.pixabaysearcher.utils

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import com.asociateapp.pixabaysearcher.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.UUID
import java.util.concurrent.Executors

@Suppress("TooGenericExceptionCaught", "TooGenericExceptionThrown", "TooManyFunctions")
internal class ImageGalleryManager(
    private val context: Context,
    private val permissionsManager: PermissionsManager
) : PermissionsManager.OnPermissionsResultListener {

    interface OnImageSavedListener {
        /**
         * Notifies when the Bitmap was saved in the user's
         * gallery successfully
         */
        fun onImageSaved()

        /**
         * Notifies when an error occurs while trying to
         * save a Bitmap in the user's gallery
         */
        fun onImageSaveError(message: String, buttonMessage: String, action: () -> Unit)
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
    @Suppress("LateinitUsage")
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
        this.temporaryBitmap = Bitmap.createBitmap(image)
        this.permissionsManager.requestPermissionsIfNeeded(permissionsNeededForStorage())
    }

    override fun onPermissionsGranted() {
        check(::temporaryBitmap.isInitialized) { "You must provide an image to save" }
        createFileFromBitmap(temporaryBitmap)
    }

    override fun onPermissionsDenied() {
        callback?.onImageSaveError(context.getString(R.string.image_gallery_image_not_saved),
            context.getString(R.string.image_gallery_error_open_config)) { openSettings(context) }
    }

    private fun createFileFromBitmap(bitmap: Bitmap) {
        Executors.newSingleThreadExecutor().submit {
            val resolver = context.contentResolver
            val imageCollection = MediaStore.Images.Media.getContentUri(getVolume())

            val newImage = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "${getImageName()}$IMAGE_EXTENSION")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val uri = resolver.insert(imageCollection, newImage)
            uri?.let {
                resolver.openOutputStream(it)?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_COMPRESSION_QUALITY, stream)
                    cleanResources(stream, bitmap)
                }
            }
        }
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

    /**
     * Go to device settings
     *
     */
    private fun openSettings(context: Context) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    /**
     * @return the name of the meli album where to store pictures
     */
    private fun getAlbumName(context: Context) = context.getString(R.string.image_gallery_pictures_album_name)

    private fun getImageName() = UUID.randomUUID().toString()

    private fun permissionsNeededForStorage() = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

    companion object {
        private const val JPEG_COMPRESSION_QUALITY = 100
        private const val IMAGE_EXTENSION = ".jpeg"
    }
}