package tv.lipsum.app.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tyln on 19.10.2015.
 */
public class SharedPrefHelper {
    private String MASTER_KEY;
    private SharedPreferences sharedPreferences;

    public SharedPrefHelper(Context context, String MASTER_KEY) {
        this.MASTER_KEY = MASTER_KEY;
        sharedPreferences = context.getSharedPreferences(MASTER_KEY, Context.MODE_PRIVATE);
    }

    public void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void save(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void clearData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void saveBoolean(String key, boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, true);
    }


    public String load(String key) {
        return sharedPreferences.getString(key, "");
    }

    public int loadInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }
}
