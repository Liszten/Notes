package at.fh.swengb.lesch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    companion object{
        val TOKEN = "TOKEN"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)

        if(sharedPreferences.getString(TOKEN,null)== null){
            Log.e("TOKEN","YOU SHALL NOT PASS!")
        }
        else{
            val intent = Intent(this, NoteListActivity::class.java)
            startActivity(intent)
        }

        login_button.setOnClickListener {
            if(login_username.text.toString().isNotEmpty() and login_password.text.toString().isNotEmpty()) {
                val AuthenticaiontRequest =
                    AuthenticationRequest(login_username.text.toString(), login_password.text.toString())
                login(AuthenticaiontRequest,
                    success = {
                        sharedPreferences.edit().putString(TOKEN, it.token).apply()
                        val intent = Intent(this, NoteListActivity::class.java)
                        startActivity(intent)

                    },
                    error = {
                        Log.e("Error", it)
                    }
                )
            }
            else {
                Toast.makeText(this, this.getString(R.string.login_missing_message) , Toast.LENGTH_LONG).show()
            }
        }
    }

    fun login (
        request: AuthenticationRequest,
        success: (response: AuthenticationResponse) -> Unit,
        error: (errorMessage: String) -> Unit) {
        NoteApi.retrofitService.login(request).enqueue(object: retrofit2.Callback<AuthenticationResponse>{
            override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                error("Log in Failed")
            }

            override fun onResponse(call: Call<AuthenticationResponse>, response: Response<AuthenticationResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    success(responseBody)
                }
                else {
                    error("an error occurred")
                }

            }
        })
    }
}