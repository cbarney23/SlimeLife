package com.example.slimelife

import android.content.Context
import java.time.Instant

// Helper class to retrieve stored values for happiness, food, and currency
class GamePrefs(context: Context) {
    private val prefs = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
    fun getCurrency(): Long {
        return prefs.getLong("currency", 0) // default starting value
    }
    fun setCurrency(value : Long) {
        prefs.edit().putLong("currency",value).apply();
    }
    fun addCurrency(value : Long) {
        prefs.edit().putLong("currency",value + getCurrency()).apply();
    }
    fun getHunger(): Long {
        return prefs.getLong("food", 100)
    }
    fun setHunger(value: Long) {
        prefs.edit().putLong("food",value).apply();
    }
    fun addHunger(value : Long) {
        prefs.edit().putLong("food",value + getHunger()).apply();
    }
    fun getHappiness(): Long {
        return prefs.getLong("happiness", 100)
    }
    fun setHappiness(value: Long) {
        prefs.edit().putLong("happiness",value).apply();
    }
    fun addHappiness(value : Long) {
        prefs.edit().putLong("happiness",value + getHappiness()).apply();
    }
    fun setLastFeed(t : Instant) {
        prefs.edit().putLong("lastFeed", t.epochSecond).apply();
    }
    fun getLastFeed(): Long {
        return prefs.getLong("lastFeed", Instant.now().epochSecond)
    }
    fun setLastPet(t : Instant) {
        prefs.edit().putLong("lastPet", t.epochSecond).apply();
    }
    fun getLastPet() : Long {
        return prefs.getLong("lastPet", Instant.now().epochSecond)
    }
    fun getTimeElapsedLastPet() : Long {
        return prefs.getLong("lastPet", Instant.now().epochSecond) - Instant.now().epochSecond
    }
}