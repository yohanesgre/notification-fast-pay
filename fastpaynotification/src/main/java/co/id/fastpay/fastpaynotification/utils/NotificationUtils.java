package co.id.fastpay.fastpaynotification.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import co.id.fastpay.fastpaynotification.R;

/**
 * Created by ${Saquib} on 12-08-2018.
 */

public class NotificationUtils {

    public final static String LOADING = "Loading";
    public final static String LOADED = "Loaded";
    public final static String CHECK_NETWORK_ERROR = "Check your network connection.";
    public static final String sources[] = {"bbc-news", "abc-news-au", "bloomberg", "cnbc"};
    public static String USER_ID = "G002AZPELV3LAQC60J29";
    public static String USER_ID_BODY = "";
    public static String API_KEY = "E4UXPAG3W403ABTR5PXF";
    public static String API_KEY_BODY = "20CA86237B58AFAB5C2FC3E3E080E78";
    public static String ID_OUTLET = "FA0006";//put your api_key generate it from "https://newsapi.org/docs"
    public static final int NOTIF_OFFSET = 15;
    public static final int ALL_FRAGMENT_INDEX = 0;
    public static final int PROMO_FRAGMENT_INDEX = 1;
    public static final int INFO_FRAGMENT_INDEX = 2;

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            @SuppressLint("MissingPermission") NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String dateConverter(String input){
        String outcome = "";
        SimpleDateFormat formatIncoming =
                new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat formatOutgoing = new SimpleDateFormat("EEE, dd MMMM yyyy");
        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
        System.out.println(tz.getDisplayName(false, TimeZone.SHORT, Locale.ENGLISH)); // WIB

        formatOutgoing.setTimeZone(tz);
        try {
            outcome = formatOutgoing.format(formatIncoming.parse(input));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (outcome.substring(8).contains("January") ){
            outcome=outcome.substring(0,7) +" Januari " +  outcome.substring(outcome.length()-4);
        }else if (outcome.substring(8).contains("February")){
            outcome=outcome.substring(0,7) +" Februari "  + outcome.substring(outcome.length()-4);
        }else if (outcome.substring(8).contains( "March")){
            outcome=outcome.substring(0,7) +" Maret "  + outcome.substring(outcome.length()-4);
        }else if (outcome.substring(8).contains( "April")){
            outcome=outcome.substring(0,7) +" April "  + outcome.substring(outcome.length()-4);
        }else if (outcome.substring(8).contains( "May ")){
            outcome=outcome.substring(0,7) +" Mei"  + outcome.substring(outcome.length()-4);
        }else if (outcome.substring(8).contains( "June")){
            outcome=outcome.substring(0,7) +" Juni "  + outcome.substring(outcome.length()-4);
        }else if (outcome.substring(8).contains( "July")){
            outcome=outcome.substring(0,7) +" Juli "  + outcome.substring(outcome.length()-4);
        }else if (outcome.substring(8).contains( "August")){
            outcome=outcome.substring(0,7) +" Agustus "  + outcome.substring(outcome.length()-4);
        }else if (outcome.substring(8).contains( "September")){
            outcome=outcome.substring(0,7) +" September "  + outcome.substring(outcome.length()-4);
        }else if (outcome.substring(8).contains( "October")){
            outcome=outcome.substring(0,7) +" Oktober "  + outcome.substring(outcome.length()-4);
        }else if (outcome.substring(8).contains( "November")){
            outcome=outcome.substring(0,7) +" November"  + outcome.substring(outcome.length()-4);
        }else if (outcome.substring(8).contains( "December")){
            outcome=outcome.substring(0,7) +" Desember "  + outcome.substring(outcome.length()-4);
        }

        switch(outcome.substring(0,3)){
            case "Sun":{
                outcome="Minggu" + outcome.substring(3);
                break;
            }
            case "Mon":{
                outcome="Senin" + outcome.substring(3);
                break;
            }
            case "Tue":{
                outcome="Selasa" + outcome.substring(3);
                break;
            }
            case "Wed":{
                outcome="Rabu" + outcome.substring(3);
                break;
            }
            case "Thu":{
                outcome="Kamis" + outcome.substring(3);
                break;
            }
            case "Fri":{
                outcome="Jumat" + outcome.substring(3);
                break;
            }
            case "Sat":{
                outcome="Sabtu" + outcome.substring(3);
                break;
            }
        }
        return outcome;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void toggleStatusBarColor(Activity activity, int color) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.material_color_blue_700));
    }

    public static String getGreetingString(){
        String greeting = "";
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("HH");
        String formattedDate = df.format(c.getTime());
        int hour = Integer.parseInt(formattedDate);
        if (hour < 10){
            greeting = "Selamat Pagi";
        }else if (hour >= 10 && hour < 15){
            greeting = "Selamat Siang";
        }else if (hour >= 10 && hour < 18){
            greeting = "Selamat Sore";
        }else{
            greeting = "Selamat Malam";
        }
        return greeting;
    }

    public static String convertToRupiah(double amount){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(amount);
    }

    /**
     * API 21
     *
     * @see Build.VERSION_CODES#LOLLIPOP
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


    /**
     * API 23
     *
     * @see Build.VERSION_CODES#M
     */
    public static boolean hasMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
