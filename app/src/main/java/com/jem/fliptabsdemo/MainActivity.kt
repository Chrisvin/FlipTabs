package com.jem.fliptabsdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.jem.fliptabs.FlipTab
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stringTemplate = "This is the %s content."
        textSwitcher.setInAnimation(this@MainActivity, android.R.anim.slide_in_left)
        textSwitcher.setOutAnimation(this@MainActivity, android.R.anim.slide_out_right)

        fliptab.setTabSelectedListener(object: FlipTab.TabSelectedListener {
            override fun onTabSelected(isLeftTab: Boolean, tabTextValue: String) {
                textSwitcher.setText(String.format(stringTemplate, tabTextValue))
                Toast.makeText(this@MainActivity, (if (isLeftTab) "Left" else "Right") + " tab selected", Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(isLeftTab: Boolean, tabTextValue: String) {
                Toast.makeText(this@MainActivity, (if (isLeftTab) "Left" else "Right") + " tab reselected", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
