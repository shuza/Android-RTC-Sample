package me.shuza.android_rtc_sample

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.json.JSONObject
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), ServerConnection.ServerMessageHandler {
    var dialog: ProgressDialog by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_login.onClick { requestForLogin() }
        btn_call.onClick { makeCall(et_caller_name.text.toString().trim()) }

        dialog = ProgressDialog(this)
        dialog.setCancelable(false)
    }

    private fun makeCall(name: String) {
        if (name.isNullOrEmpty()) {
            toast("Please enter valid username")
            return
        }
        gl_view_call.visibility = View.VISIBLE
        gl_view_call.preserveEGLContextOnPause = true
        gl_view_call.keepScreenOn = true
    }

    private fun requestForLogin() {
        val username = et_username.text.toString().trim()
        if (username.isNullOrEmpty()) {
            toast("Please enter a valid username")
            return
        }
        showDialog()
        val jsonObj = JsonObject()
        jsonObj.addProperty("type", "login")
        jsonObj.addProperty("name", username)
        ServerConnection.getInstance().listener = this
        ServerConnection.getInstance().send(jsonObj.toString())
    }

    override fun onMessageReceived(message: String) {
        dismissDialog()
        val json = JSONObject(message)
        val type = json.get("type").toString()
        when (type) {
            "login" -> {
                if (json.getBoolean("success")) {
                    et_username.visibility = View.GONE
                    btn_login.visibility = View.GONE
                    et_caller_name.visibility = View.GONE
                    btn_call.visibility = View.GONE
                } else {
                    toast("Username already exist")
                }
            }
        }
    }

    override fun onMessageError(e: Exception) {

    }

    fun showDialog() {
        dialog.show()
    }

    fun dismissDialog() {
        if (dialog.isShowing) dialog.dismiss()
    }
}
