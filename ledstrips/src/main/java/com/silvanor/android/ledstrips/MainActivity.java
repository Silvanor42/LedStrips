/*
 * Copyright (C) 2017 Jared Rummler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.silvanor.android.ledstrips;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.os.ParcelUuid;
import androidx.appcompat.app.AppCompatActivity;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.demo.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity implements ColorPickerDialogListener {

  private static final String TAG = "MainActivity";

  private static final int DIALOG_ID = 0;
  BluetoothAdapter btAdapter;
  InputStream bluetoothInput;
  OutputStream bluetoothOutput;
  private int REQUEST_ENABLE_BT;
  @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getSupportFragmentManager().beginTransaction().add(android.R.id.content, new DemoFragment()).commit();
      btAdapter =  BluetoothAdapter.getDefaultAdapter();
      getStreams();
    }

  void getStreams()
  {
    btAdapter.enable();
    Set<BluetoothDevice> myBondedDevices = btAdapter.getBondedDevices();
    String uuid = "PLAATS UUID HIER";
    for (BluetoothDevice device: myBondedDevices)
    {
      if(device.getUuids().toString().equals(uuid))
      {
        try {
          BluetoothSocket sock = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid));
          sock.connect();
          bluetoothInput = sock.getInputStream();
          bluetoothOutput = sock.getOutputStream();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }


  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_color_picker_dialog:
        ColorPickerDialog.newBuilder()
            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
            .setAllowPresets(false)
            .setDialogId(DIALOG_ID)
            .setColor(Color.BLACK)
            .setShowAlphaSlider(true)
            .show(this);
        return true;
      case R.id.menu_github:
        try {
          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Silvanor42/LedStrips")));
        } catch (ActivityNotFoundException ignored) {
        }
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onColorSelected(int dialogId, int color) {
    Log.d(TAG, "onColorSelected() called with: dialogId = [" + dialogId + "], color = [" + color + "]");
    switch (dialogId) {
      case DIALOG_ID:
        // We got result from the dialog that is shown when clicking on the icon in the action bar.
        Toast.makeText(MainActivity.this, "Selected Color: #" + Integer.toHexString(color), Toast.LENGTH_SHORT).show();
        break;
    }
  }

  @Override public void onDialogDismissed(int dialogId) {
    Log.d(TAG, "onDialogDismissed() called with: dialogId = [" + dialogId + "]");
  }
}
 