package ru.osa.gb.homework.movless02.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MainBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
//Достаём данные из интента
        Log.d("MYTAG", "BroadcastReceiver --- ${intent.action}")

        Toast.makeText(context, intent.action, Toast.LENGTH_LONG).show()
    }
}
