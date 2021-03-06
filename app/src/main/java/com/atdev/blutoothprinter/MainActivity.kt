package com.atdev.blutoothprinter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tscdll.TSCActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPrint.setOnClickListener { printNow() }
    }

    private fun printNow() {
        try {
            //1- Before ou Must Connect to Printer Tool and get the Bluetooth Mac Address
            //2- then get Mac address of it to set it as a Argument .address. > openport(address)
            //3-
            //Input the Bluetooth Mac Address, example:“00:19:0E:A0:04:E1
            val macAddress = "00:0C:BF:0A:4F:E8"
            val TscDll = TSCActivity()
            TscDll.openport(macAddress);
            TscDll.downloadpcx("UL.PCX");
            TscDll.downloadbmp("Triangle.bmp");
            TscDll.downloadttf("ARIAL.TTF");
            TscDll.setup(70, 110, 4, 4, 0, 0, 0);
            TscDll.clearbuffer();
            TscDll.sendcommand("SET TEAR ON\n");
            TscDll.sendcommand("SET COUNTER @1 1\n");
            TscDll.sendcommand("@1 = \"0001\"\n");
            TscDll.sendcommand("TEXT 100,300,\"3\",0,1,1,@1\n");
            TscDll.sendcommand("PUTPCX 100,300,\"UL.PCX\"\n");
            TscDll.sendcommand("PUTBMP 100,520,\"Triangle.bmp\"\n");
            TscDll.sendcommand("TEXT 100,760,\"ARIAL.TTF\",0,15,15,\"THIS IS ARIAL FONT\"\n");
            TscDll.barcode(100, 100, "128", 100, 1, 0, 3, 3, "123456789");
            TscDll.printerfont(100, 250, "3", 0, 1, 1, "987654321");
            val status = TscDll.printerstatus();
            textView.setText(status);
            TscDll.printlabel(2, 1);
            //TscDll.sendfile("zpl.txt");
            TscDll.closeport(5000);

        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(this, "Printer Failed on This Device", Toast.LENGTH_LONG).show()
        }
    }


}
