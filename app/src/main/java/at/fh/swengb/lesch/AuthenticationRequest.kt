package at.fh.swengb.lesch

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AuthenticationRequest(val username: String, val password: String) {
}