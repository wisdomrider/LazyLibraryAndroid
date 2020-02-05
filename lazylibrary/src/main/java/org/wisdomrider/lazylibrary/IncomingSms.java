package org.wisdomrider.lazylibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class IncomingSms extends BroadcastReceiver {
    public  static final String SMS_MESSAGE = "SMS_MESSAGE";
    public  static final String PHONE_NUMBER = "PHONE_NUMBER";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public static final String TAG = "SmsBroadcastReceiver";
    final SmsManager sms = SmsManager.getDefault();
    int indexOn;
    String msg, phoneNo;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(SMS_RECEIVED)) {
            Bundle dataBundle = intent.getExtras();
            if (dataBundle != null) {
                Object[] mypdc = (Object[]) dataBundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[mypdc.length];
                for (int i=0;i<mypdc.length;i++) {
                    // for build version >= ApI level 23
                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
                        String format = dataBundle.getString("format");
                        // From PDU we get all object and SmsMessage Object using following line of code
                        messages[i] = SmsMessage.createFromPdu((byte[]) mypdc[i], format);
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) mypdc[i]);
                    }

                    //indexOn = messages[i].getIndexOnIcc();
                    indexOn = messages[i].getIndexOnSim();

                    msg = messages[i].getMessageBody();
                    phoneNo = messages[i].getOriginatingAddress();
                }

                Intent in = new Intent("com.sms.broadcast");
                Bundle extras = new Bundle();
                extras.putString(SMS_MESSAGE,msg);
                extras.putString(PHONE_NUMBER, phoneNo);
                in.putExtras(extras);
                context.sendBroadcast(in);
            }
        }
    }


}