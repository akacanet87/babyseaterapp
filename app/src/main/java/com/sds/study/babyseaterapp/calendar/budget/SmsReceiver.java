package com.sds.study.babyseaterapp.calendar.budget;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import static com.sds.study.babyseaterapp.calendar.budget.BudgetActivity.budgetActivity;

public class SmsReceiver extends BroadcastReceiver{
    String TAG;

    public SmsReceiver() {
        TAG=this.getClass().getName();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // SMS를 받았을 경우에만 반응하도록 if문을 삽입
        if (intent.getAction().equals(
                "android.provider.Telephony.SMS_RECEIVED")) {
            StringBuilder sms = new StringBuilder();    // SMS문자를 저장할 곳
            Bundle bundle = intent.getExtras();         // Bundle객체에 문자를 받아온다

            if (bundle != null) {
                // 번들에 포함된 문자 데이터를 객체 배열로 받아온다
                Object[] pdusObj = (Object[]) bundle.get("pdus");

                // SMS를 받아올 SmsMessage 배열을 만든다
                SmsMessage[] messages = new SmsMessage[pdusObj.length];
                for (int i = 0; i < pdusObj.length; i++) {
                    messages[i] =
                            SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    // SmsMessage의 static메서드인 createFromPdu로 pdusObj의
                    // 데이터를 message에 담는다
                    // 이 때 pdusObj는 byte배열로 형변환을 해줘야 함
                }

                // SmsMessage배열에 담긴 데이터를 append메서드로 sms에 저장
                for (SmsMessage smsMessage : messages) {
                    // getMessageBody메서드는 문자 본문을 받아오는 메서드
                    sms.append(smsMessage.getMessageBody());
                }

               sms.toString(); // StringBuilder 객체 sms를 String으로 변환
        //        Toast.makeText(context, "받은 문자는"+sms, Toast.LENGTH_SHORT).show();

                String[] magarr=msgCut(sms.toString(), context);
                budgetActivity.itemInputLayout.getMsgData(magarr);
            }
        }
    }
    public String[] msgCut(String sms,Context context){
        String[] msgarr=new String[4];
        int cardFistIndex=sms.indexOf("]");
        int cardLastIndex=sms.indexOf("드");

        msgarr[0]=sms.substring(cardFistIndex+1,cardLastIndex+1);

        int dateFirstIndex=sms.indexOf("님");
        msgarr[1]=sms.substring(dateFirstIndex+1,dateFirstIndex+7);

        int costLastIndex=sms.indexOf("원");
        msgarr[2]=sms.substring(dateFirstIndex+14,costLastIndex);

        int contentLastIndex=sms.indexOf("누");
        msgarr[3]=sms.substring(costLastIndex+1,contentLastIndex);

        return msgarr;
    }
}
