package ai.livereply.nativeapp

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val requestCapture = 4102
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 12, 16, 8)
        }

        status = TextView(this).apply {
            text = "Screen sharing is OFF"
            textSize = 14f
        }

        val start = Button(this).apply {
            text = "Start Screen Sharing"
            setOnClickListener { requestScreenCapture() }
        }

        val stop = Button(this).apply {
            text = "Stop Screen Sharing"
            setOnClickListener {
                stopService(Intent(this@MainActivity, ScreenCaptureService::class.java))
                status.text = "Screen sharing stopped."
            }
        }

        val web = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            loadUrl("https://livereply-ai-otvni7.v2.appdeploy.ai/")
        }

        layout.addView(status)
        layout.addView(start)
        layout.addView(stop)
        layout.addView(web, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))

        setContentView(layout)
    }

    private fun requestScreenCapture() {
        val manager = getSystemService(MEDIA_PROJECTION_SERVICE)
            as MediaProjectionManager
        startActivityForResult(
            manager.createScreenCaptureIntent(),
            requestCapture
        )
    }

    @Deprecated("Deprecated Android API")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == requestCapture) {
            if (resultCode == RESULT_OK && data != null) {
                val intent = Intent(this, ScreenCaptureService::class.java)
                intent.putExtra(ScreenCaptureService.EXTRA_RESULT_CODE, resultCode)
                intent.putExtra(ScreenCaptureService.EXTRA_RESULT_DATA, data)
                startForegroundService(intent)
                status.text = "Screen sharing is active."
            } else {
                status.text = "Screen sharing permission denied."
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
