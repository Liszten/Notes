package at.fh.swengb.lesch

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NotesResponse(val lastSync: Long, val notes: List<Note>) {
}