package com.supermonster.hardest2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Global {
    private static final String TAG = "Global";
    public static final String backStackName = "BackStack";
    public static final String SLOW_PREF = "SLOW_PREF";
    public static final String BEST_SCORE = "BEST_SCORE";
    public static final String RESUME_ENABLE = "RESUME_ENABLE";
    public static final String CURRENT_SCORE = "CURRENT_SCORE";
    public static final String CURRENT_DATA = "CURRENT_DATA";

    private static SharedPreferences scorePref;

    //Chỉ lưu score lớn nhất
    public static void saveBestScore(Context context, int score) {
        scorePref = context.getSharedPreferences(SLOW_PREF, Context.MODE_PRIVATE);
        scorePref.edit().putInt(BEST_SCORE, score).commit();
        Log.d(TAG, "saveBestScore: updated new score : " + score);
    }

    public static int getBestScore(Context context) {
        scorePref = context.getSharedPreferences(SLOW_PREF, Context.MODE_PRIVATE);
        return scorePref.getInt(BEST_SCORE, 0);
    }

    //lưu state của resume
    public static void saveResumeState(Context context, boolean isEnable) {
        scorePref = context.getSharedPreferences(SLOW_PREF, Context.MODE_PRIVATE);
        scorePref.edit().putBoolean(RESUME_ENABLE, isEnable).commit();
        Log.d(TAG, "saveResumeState: updated resume state : " + isEnable);
    }

    public static boolean getResumeState(Context context) {
        scorePref = context.getSharedPreferences(SLOW_PREF, Context.MODE_PRIVATE);
        return scorePref.getBoolean(RESUME_ENABLE, false);
    }

    //Chỉ lưu current score khi resume = true
    public static void saveCurrentScore(Context context, int score) {
        scorePref = context.getSharedPreferences(SLOW_PREF, Context.MODE_PRIVATE);
        scorePref.edit().putInt(CURRENT_SCORE, score).commit();
        Log.d(TAG, "saveCurrentScore: updated new saveCurrentScore : " + score);
    }

    public static int getCurrentScore(Context context) {
        scorePref = context.getSharedPreferences(SLOW_PREF, Context.MODE_PRIVATE);
        Log.d(TAG, "getCurrentScore: "+scorePref.getInt(CURRENT_SCORE, 0));
        return scorePref.getInt(CURRENT_SCORE, 0);
    }

    //Chỉ lưu current data khi resume = true
    public static void saveCurrentData(Context context, String data) {
        data = data.replaceAll(" ","").replaceAll("^\\[|]$","");
        scorePref = context.getSharedPreferences(SLOW_PREF, Context.MODE_PRIVATE);
        scorePref.edit().putString(CURRENT_DATA, data).commit();
        Log.d(TAG, "saveCurrentData: updated new saveCurrentScore : " + data);
    }

    public static String getCurrentData(Context context) {
        scorePref = context.getSharedPreferences(SLOW_PREF, Context.MODE_PRIVATE);
        return scorePref.getString(CURRENT_DATA, "");
    }

}
