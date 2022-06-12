package com.example.scaler.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

val FragmentManager.currentNavigationFragment: Fragment?
        get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()