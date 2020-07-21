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

        /*
        fliptab.setBorderWidth(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                4f,
                resources.displayMetrics
            ).toInt()
        )
        fliptab.getTextViews().forEach {
            it.setTypeface(it.typeface, Typeface.ITALIC)
            it.minLines = 2
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE)
            }
            it.textSize = 16f
        }
        fliptab.getSelectedTextView().apply {
            setTypeface(typeface, Typeface.BOLD)
        }
        */

        /*
        val flipTab = FlipTab(this)
        flipTab.setLeftTabText("Photo")
        flipTab.setRightTabText("Video")

        //Sets color for both text, background & border
        flipTab.setOverallColor(Color.BLUE)
        //Sets color only bg & border
        flipTab.setHighlightColor(Color.GREEN)
        //Sets color only for text
        flipTab.setTextColor(Color.GREEN)

        //Time taken for selected tab to flip
        flipTab.setFlipAnimationDuration(500)
        //Time taken for tabs to revert to original state after wobble
        flipTab.setWobbleReturnAnimationDuration(250)
        //NOTE: totalAnimationDuration = flipAnimationDuration + wobbleReturnAnimationDuration

        //Set angle upto which the tabs wobble
        flipTab.setWobbleAngle(3f)

        //Flip the tab (left -> right & vice versa)
        flipTab.flipTabs()
        //Selects the left tab (if left tab isn't already selected)
        flipTab.selectLeftTab(withAnimation = true)
        //Selects the right tab (if right tab isn't already selected)
        flipTab.selectRightTab(withAnimation = false)
        //(Does flip animation when selecting if withAnimation is true, else skips animation)
         */

        fliptab.setTabSelectedListener(object : FlipTab.TabSelectedListener {
            override fun onTabSelected(isLeftTab: Boolean, tabTextValue: String) {
                textSwitcher.setText(String.format(stringTemplate, tabTextValue))
                Toast.makeText(
                    this@MainActivity,
                    (if (isLeftTab) "Left" else "Right") + " tab selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTabReselected(isLeftTab: Boolean, tabTextValue: String) {
                Toast.makeText(
                    this@MainActivity,
                    (if (isLeftTab) "Left" else "Right") + " tab reselected",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}
