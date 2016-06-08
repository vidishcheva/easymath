package a.vidishcheva.easymath.adapters_listeners;

import android.app.Activity;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by Алёна on 01.06.2016.
 */
public class Localization {

    private static String[] languages = {"en", "ru"};
    public static int locInd = 0;
    private Activity activity;

    public Localization(Activity activity) {
        this.activity = activity;
    }

    public void setLocalization() {
        System.out.println("locInd = " + locInd);
        Locale locale = new Locale(languages[locInd]);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getResources().updateConfiguration(config, activity.getResources().getDisplayMetrics());
    }
}
