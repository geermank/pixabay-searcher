package com.asociateapp.pixabaysearcher.presentation

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.asociateapp.pixabaysearcher.R

abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog)
    }

}