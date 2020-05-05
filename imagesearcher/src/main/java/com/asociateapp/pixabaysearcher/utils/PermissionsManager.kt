package com.asociateapp.pixabaysearcher.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

private const val PERMISSION_REQUEST_CODE = 11

class PermissionsManager(private val activity: Activity) {

    private var fragment: Fragment? = null

    constructor(fragment: Fragment): this(fragment.activity!!) {
        this.fragment = fragment
    }

    interface OnPermissionsResultListener {
        /**
         * Notifies when the requested permissions were granted by the user.<<
         *
         * In Android versions previously to Marshmallow, this callback will be called
         * immediately, since permissions are not granted in runtime.
         *
         * If permissions were previously granted, this method will be called immediately too.
         */
        fun onPermissionsGranted()

        /**
         * Notifies when the requested permissions weren't granted by the user.
         *
         * If one permission is denied, although the others were granted, this
         * method will be called.
         */
        fun onPermissionsDenied()
    }

    /**
     * Listener to return permission request result
     */
    private var permissionResultListener: OnPermissionsResultListener? = null

    /**
     * Sets a callback to listen the results of asking for permissions
     */
    fun setOnPermissionResultListener(listener: OnPermissionsResultListener) {
        this.permissionResultListener = listener
    }

    /**
     * Checks whether the permissions are already granted or the device O.S.
     * is available to request them (permissions are introduced in Marshmallow)
     */
    fun requestPermissionsIfNeeded(permissions: Array<String>) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || arePermissionsEnabled(activity, permissions)) {
            permissionResultListener?.onPermissionsGranted()
            return
        }
        if (fragment == null) {
            activity.requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        } else {
            fragment!!.requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }
    }

    /**
     * @param context - a [Context] to use in the check permission process
     * @param permission - the name of the permission being checked
     * @return - true if the permission has been granted, false any other case
     */
    fun isPermissionEnabled(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * @param context - a [Context] to use in the check permission process.
     * @param permissions - a list with the name of the permissions being checked.
     * @return - true if all the permissions have been granted, false when at least one is not granted.
     */
    fun arePermissionsEnabled(context: Context, permissions: Array<String>): Boolean {
        var granted = true
        for (permission in permissions) {
            granted = granted and (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
        }
        return granted
    }

    fun onRequestPermissionResult(permissions: Array<out String>, grantResults: IntArray) {
        verifyPermissionResult(grantResults).also { permissionsGranted ->
            if (permissionsGranted) {
                permissionResultListener?.onPermissionsGranted()
            } else {
                permissionResultListener?.onPermissionsDenied()
            }
        }
    }

    private fun verifyPermissionResult(grantResults: IntArray): Boolean {
        var allPermissionsGranted = true
        grantResults.forEach { result ->
            if (result == PackageManager.PERMISSION_DENIED) {
                allPermissionsGranted = false
            }
        }
        return allPermissionsGranted
    }
}