package co.id.fastpay.fastpaynotification.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DummyContent {
    private static DummyContent instance;
    private ArrayList<InboxModel> listInbox = new ArrayList<>();

    private DummyContent() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get single instance of this class");
        }
    }

    public static DummyContent getInstance() {
        if (instance == null) {
            instance = new DummyContent();
            instance.SeedListInbox();
        }
        return instance;
    }

    public List<InboxModel> GetListInbox() {
        return listInbox;
    }

    private void SeedListInbox() {
        Date currentTime = Calendar.getInstance().getTime();
        for (int i = 0; i < 5; i++){
            switch (i){
                case 0:{
                    InboxModel inbox = new InboxModel(i+1, "Inbox " + i+1, "Testing Inbox " + i+1, "no-image.jpg", "https://twitter.com", currentTime.toString(), currentTime.toString(),
                            "0", "1", "PROMO", "", 1, "", 0, 0, "", "", "", "", 0.0, 0.0, null);
                    listInbox.add(inbox);
                }
                case 1:{
                    InboxModel inbox = new InboxModel(i+1, "Inbox " + i+1, "Testing Inbox " + i+1, "no-image.jpg", "https://twitter.com", currentTime.toString(), currentTime.toString(),
                            "0", "1", "SYSTEM", "", 1, "", 0, 0, "", "", "", "", 0.0, 0.0, null);
                    listInbox.add(inbox);
                }
                case 2:{
                    InboxModel inbox = new InboxModel(i+1, "Inbox " + i+1, "Testing Inbox " + i+1, "no-image.jpg", "https://twitter.com", currentTime.toString(), currentTime.toString(),
                            "0", "1", "NOTIFIKASI", "", 1, "", 0, 0, "", "", "", "", 0.0, 0.0, null);
                    listInbox.add(inbox);
                }
                case 3:{
                    InboxModel inbox = new InboxModel(i+1, "Inbox " + i+1, "Testing Inbox " + i+1, "no-image.jpg", "https://twitter.com", currentTime.toString(), currentTime.toString(),
                            "0", "1", "NEWS", "", 1, "", 0, 0, "", "", "", "", 0.0, 0.0, null);
                    listInbox.add(inbox);
                }
                case 4:{
                    AdditionalDataModel addData = new AdditionalDataModel(1, "Test Group", "1", "Test Product 1", "1", "Test", 10000.0, "Test Customer 1", "5");
                    AdditionalDataModel addData2 = new AdditionalDataModel(2, "Test Group", "2", "Test Product 2", "1", "Test", 5000.0, "Test Customer 1", "5");
                    List<AdditionalDataModel> listAddData = new ArrayList<>();
                    listAddData.add(addData);
                    listAddData.add(addData2);
                    InboxModel inbox = new InboxModel(i+1, "Inbox " + i+1, "Testing Inbox " + i+1, "no-image.jpg", "https://twitter.com", currentTime.toString(), currentTime.toString(),
                            "0", "1", "TRANSAKSI", "", 1, "", 0, 0, "", "Test Customer 1", "0000", currentTime.toString(), 3000.0, 5000.0, listAddData);
                    listInbox.add(inbox);
                }
                case 5:{
                    InboxModel inbox = new InboxModel(i+1, "Inbox " + i+1, "Testing Inbox " + i+1, "no-image.jpg", "https://twitter.com", currentTime.toString(), currentTime.toString(),
                            "0", "1", "INFO", "", 1, "", 0, 0, "", "", "", "", 0.0, 0.0, null);
                    listInbox.add(inbox);
                }
            }
        }
    }
}
