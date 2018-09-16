package io.github.pulakdp.mfcomparator.util;

import android.databinding.BindingAdapter;
import android.view.View;

public class AppUtils {
    @BindingAdapter("booleanVisibility")
    public static void bindVisibility(View view, Boolean val) {
        if (val == null || !val) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
