package ro.vlad.core_ui.bindings

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:set_visibility")
fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
