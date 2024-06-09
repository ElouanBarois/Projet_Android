package fr.epf.min1.flags

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class UnlikedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val countryName = intent.getStringExtra("countryName")
        val sharedPreferences = context.getSharedPreferences("liked_countries", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(countryName)
        editor.apply()
    }
}
