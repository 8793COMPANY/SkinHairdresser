package com.victoria.skinhairdresser.Thread;
import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;

    Boolean PMA = false;
    Boolean SOK = false;

    public Boolean getPMA() {
        return PMA;
    }

    public void setPMA(Boolean PMA) {
        this.PMA = PMA;
    }

    public Boolean getSOK() {
        return SOK;
    }

    public void setSOK(Boolean SOK) {
        this.SOK = SOK;
    }

    public ConnectedThread(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()
        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.available();
                if (bytes != 0) {
                    buffer = new byte[1024];
                    SystemClock.sleep(50); //pause and wait for rest of data. Adjust this depending on your sending speed.
                    bytes = mmInStream.available(); // how many bytes are ready to be read?
                    bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
                    final String incomingMessage = new String(buffer, 0, bytes);
                    Log.i("From Machine P : ", incomingMessage);

                    if (incomingMessage.length() > 2) {

                        switch (incomingMessage.substring(0, 3)) {
                            case "PMA" :
                                Log.i("From Machine P : ", incomingMessage.substring(0, 3) + " Enable");
                                PMA = true;
                                break;


                            case "SOK" :
                                Log.i("From Machine P : ", incomingMessage.substring(0, 3) + " Enable");
                                SOK = true;
                                this.write("OK");
                                Log.e("돈다","Sok");
                                break;

                        }
                    }else if (incomingMessage.length() > 1){
                        switch (incomingMessage.substring(0, 2)) {

                            case "OK" :
                                Log.i("From Machine P : ", incomingMessage.substring(0, 2) + " Enable");
                                SOK = true;
                                this.write("OK");
                                Log.e("돈다","ok");
                                break;


                        }
                    }else{
                        switch (incomingMessage.substring(0, 1)) {

                            case "S" :
                                Log.i("From Machine P : ", incomingMessage.substring(0, 1) + " Enable");
                                SOK = true;
                                this.write("OK");
                                Log.e("돈다","s");
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

                break;
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(String input) {
        byte[] bytes = input.getBytes();           //converts entered String into bytes
        try {
            mmOutStream.write(bytes);
            Log.i("To Machine P : ", new String(bytes));
        } catch (IOException e) {
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}