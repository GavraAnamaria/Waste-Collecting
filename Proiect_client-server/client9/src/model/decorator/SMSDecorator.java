package model.decorator;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import model.Utilizator;

public class SMSDecorator extends Decorator {
    public SMSDecorator(Notifier n) {
        super(n);
    }
    public void sendMessage(String message, Utilizator u){
        super.sendMessage(message, u);
        String ACCOUNT_SID = "AC9d89c5af0c0ce2502410516e4fde0c2a";
        String AUTH_TOKEN = "fe12961111f8d323fdfb187b940acff2";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        System.out.println(u.getNr());
         message= "Azi livram comanda cu AWB 9563208351696. Prezinta curierului PIN-ul: X6909. Ramburs: 1275.20RON. Telefon curier 0770726113";
         Message message1 = Message.creator(new PhoneNumber(u.getNr()), new PhoneNumber("+12054488228"), message).create();
         //Message message1 = Message.creator(new PhoneNumber(u.getNr()), new PhoneNumber("+12054488228"), "Azi livram comanda cu AWB 9563208351696. Prezinta curierului PIN-ul: X6909. Acceseaza awb.fan.ro/TgT1lx8aMxTlg pentru alte optiuni de livrare. Ramburs: 975.20RON. Telefon curier 0770726113").create();
        System.out.println(message1.getSid());
    }
}