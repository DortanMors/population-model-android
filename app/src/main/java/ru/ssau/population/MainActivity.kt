package ru.ssau.population

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.ssau.population.ui.screen.PrepareFragment

class  MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PrepareFragment.newInstance())
                .commitNow()
        }
    }
}