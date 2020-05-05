package com.asociateapp.pixabaysearcher.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import com.asociateapp.pixabaysearcher.R
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@Suppress("TooGenericExceptionCaught", "TooGenericExceptionThrown", "TooManyFunctions")
class ImageGalleryManager(
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
        this.temporaryBitmap = image
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

    internal fun createFileFromBitmap(bitmap: Bitmap) {
        var fileOutputStream: FileOutputStream? = null
        try {
            val storageDir: File = createAlbum(context)
            val image = File.createTempFile(getImageName(), IMAGE_EXTENSION, storageDir)

            // send the bitmap to a file
            fileOutputStream = FileOutputStream(image)
            bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_COMPRESSION_QUALITY, fileOutputStream)

            // trigger a media scan so that the image shows up in the gallery
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(image))
            this.context.sendBroadcast(mediaScanIntent)
            this.callback?.onImageSaved()
        } catch (ex: Exception) {
            this.callback?.onImageSaveError(context.getString(R.string.image_gallery_error_saving_image),
                context.getString(R.string.image_gallery_error_saving_image_retry)) { save(bitmap) }
        } finally {
            cleanResources(fileOutputStream, bitmap)
        }
    }

    private fun cleanResources(fileOutputStream: FileOutputStream?, bitmap: Bitmap?) {
        fileOutputStream?.close()
        bitmap?.recycle()
    }

    /**
     * Creates an album to save image in local memory
     *
     * @return the created directory where the image will be saved
     */
    private fun createAlbum(context: Context): File {
        val storageDir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            getAlbumName(context)
        )
        // if the album doesn't exist then create it
        if (!storageDir.exists() && !storageDir.mkdirs()) {
            throw RuntimeException("Error creating pictures album")
        }
        return storageDir
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
        private const val JPEG_COMPRESSION_QUALITY = 80
        private const val IMAGE_EXTENSION = ".jpg"
    }
}