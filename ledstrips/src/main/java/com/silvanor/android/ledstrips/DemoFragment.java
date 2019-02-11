package com.silvanor.android.ledstrips;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.preference.Preference;
import com.jaredrummler.android.colorpicker.ColorPreferenceCompat;
import com.jaredrummler.android.colorpicker.demo.R;

import java.io.DataOutputStream;

import static com.silvanor.android.ledstrips.MainActivity.bluetoothOutput;
import static com.silvanor.android.ledstrips.MainActivity.getRGB;

public class DemoFragment extends BasePreferenceFragment {

  private static final String TAG = "DemoFragment";

  private static final String LED1 = "led1";
  private static final String LED2 = "led2";
  private static final String LED3 = "led3";
  private static final String LED4 = "led4";

  @Override public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.main, rootKey);

    // Example showing how we can get the new color when it is changed:
    ColorPreferenceCompat Strip1 = (ColorPreferenceCompat) findPreference(LED1);
      Strip1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
          @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
          @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
              LED1.equals(preference.getKey()); {
                  int rgbint = (int) newValue;
                  byte[] rgb = getRGB(rgbint);
                    new DataOutputStream(bluetoothOutput.write(1));
                    new DataOutputStream(bluetoothOutput.write(rgb, 0, 3));

              }
              return true;

          }
      });
      ColorPreferenceCompat Strip2 = (ColorPreferenceCompat) findPreference(LED2);
      Strip2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
          @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
              LED2.equals(preference.getKey()); {
                  String newDefaultColor = Integer.toHexString((int) newValue);
                  Log.d(TAG, "New default color is: #" + newDefaultColor);
              }
              return true;

          }
      });
      ColorPreferenceCompat Strip3 = (ColorPreferenceCompat) findPreference(LED3);
      Strip3.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
          @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
              LED3.equals(preference.getKey()); {
                  String newDefaultColor = Integer.toHexString((int) newValue);
                  Log.d(TAG, "New default color is: #" + newDefaultColor);
              }
              return true;

          }
      });
      ColorPreferenceCompat Strip4 = (ColorPreferenceCompat) findPreference(LED4);
      Strip4.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
          @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
              LED4.equals(preference.getKey()); {
                  String newDefaultColor = Integer.toHexString((int) newValue);
                  Log.d(TAG, "New default color is: #" + newDefaultColor);
              }
              return true;

          }
      });
  }
}
