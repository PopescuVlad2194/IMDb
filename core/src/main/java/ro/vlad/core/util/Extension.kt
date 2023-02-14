package ro.vlad.core.util

import android.os.Bundle
import androidx.fragment.app.Fragment

fun getFragmentByTag(
    fragment: Fragment,
    tag: String
): Fragment {
    val bundle = Bundle().apply {
        putString(Constants.FRAGMENT_TAG, tag)
    }
    fragment.arguments = bundle
    return fragment
}