package at.fh.swengb.lesch

import com.squareup.moshi.JsonClass

@JsonClass (generateAdapter = true)
class AuthenticationResponse(val token: String) {
}