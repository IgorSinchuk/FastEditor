package com.nonexistentware.igorsinchuk.fasteditor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.nonexistentware.igorsinchuk.fasteditor.Interface.EditImageFragmentListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditImageFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private EditImageFragmentListener listener;
    SeekBar seekBarBrightness, seekBarContrast, seekBarSaturation;

    public void setListener(EditImageFragmentListener listener) {
        this.listener = listener;
    }

    public EditImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView= inflater.inflate(R.layout.fragment_edit_image, container, false);

        seekBarBrightness = (SeekBar) itemView.findViewById(R.id.seekBarBrightness);
        seekBarContrast = (SeekBar) itemView.findViewById(R.id.seekBarContrast);
        seekBarSaturation = (SeekBar) itemView.findViewById(R.id.seekBarSaturation);

        seekBarBrightness.setMax(200);
        seekBarBrightness.setProgress(100);

        seekBarContrast.setMax(20);
        seekBarContrast.setProgress(0);

        seekBarSaturation.setMax(30);
        seekBarSaturation.setProgress(10);

        seekBarBrightness.setOnSeekBarChangeListener(this);
        seekBarContrast.setOnSeekBarChangeListener(this);
        seekBarSaturation.setOnSeekBarChangeListener(this);

        return itemView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (listener != null) {
            if (seekBar.getId() == R.id.seekBarBrightness) {
                listener.onBrightnessChanged(progress-100);
            } else if (seekBar.getId() == R.id.seekBarContrast) {
                progress+=10;
                float value = .10f*progress;
                listener.onContrastLayout(value);
            } else if (seekBar.getId() == R.id.seekBarSaturation) {
                float value = .10f*progress;
                listener.onSaturationChanged(value);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (listener != null) {
            listener.onEditSarted();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (listener != null) {
            listener.onEditComplete();
        }
    }

    public void resetControls() {
        seekBarBrightness.setProgress(100);
        seekBarContrast.setProgress(0);
        seekBarSaturation.setProgress(10);
    }
}
