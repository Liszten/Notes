package at.fh.swengb.lesch

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NoteRepository {

    fun addNote(context: Context, newNote: Note) {
        val db = NoteDatabase.getDatabase(context)
        db.NoteDao.insert(newNote)
    }

    fun NoteById (context: Context, id: String):Note {
        val db = NoteDatabase.getDatabase(context)
        return db.NoteDao.findNoteById(id)
    }

    fun getAllNotes (context: Context):List<Note> {
        val db = NoteDatabase.getDatabase(context)
        return db.NoteDao.getAllNotes()
    }

    fun deleteDb (context: Context) {
        val db = NoteDatabase.getDatabase(context)
        return db.NoteDao.deleteAllNotes()
    }

    fun uploadNote (token: String, note2Upload: Note, success: (note: Note)->Unit, error: (errorMessage: String)->Unit){
        NoteApi.retrofitService.addOrUpdateNote(token, note2Upload).enqueue(object : Callback<Note>{

            override fun onFailure(call: Call<Note>, t: Throwable) {
                error("The call failed! " + t.localizedMessage)
            }

            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    success(responseBody)
                } else {
                    error("Something went wrong " + response.message())
                }
            }
        })
    }

    fun getNotes(token: String, lastSync: Long, success:(noteResponse: NotesResponse)->Unit,error:(errorMessage:String)->Unit){
        NoteApi.retrofitService.notes(token, lastSync).enqueue(object : Callback<NotesResponse>{

            override fun onFailure(call: Call<NotesResponse>, t: Throwable) {
                error("The call failed")
            }

            override fun onResponse(call: Call<NotesResponse>, response: Response<NotesResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    success(responseBody)
                } else {
                    error("Something went wrong")
                }
            }
        })
    }

}