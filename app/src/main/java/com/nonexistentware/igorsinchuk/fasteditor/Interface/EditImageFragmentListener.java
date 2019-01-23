package com.nonexistentware.igorsinchuk.fasteditor.Interface;

public interface EditImageFragmentListener {
    void onBrightnessChanged(int brightness);
    void onSaturationChanged(float saturation);
    void onContrastLayout(float contrast);
    void onEditSarted();
    void onEditComplete();
}
