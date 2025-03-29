import android.util.Log
import com.freddy.proyectoqrasistencia.Alumno
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.HttpException

interface ApiService {
    @GET("api/alumnos/{dni}")
    suspend fun obtenerAlumno(@Path("dni") id: String): Alumno

    companion object {
        private const val BASE_URL = "https://apisenativerde.onrender.com/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

        fun obtenerAlumnoDesdeAPI(dni: String, callback: (Alumno?) -> Unit) {
            val apiService = create()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val alumno = apiService.obtenerAlumno(dni)
                    withContext(Dispatchers.Main) {
                        callback(alumno) //  Devuelve el objeto a `MainActivity`
                    }
                } catch (e: HttpException) {
                    Log.e("API_ERROR", "Error en la solicitudddd: ${e.message()}")
                    withContext(Dispatchers.Main) { callback(null) }
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Error inesperado: ${e.localizedMessage}")
                    withContext(Dispatchers.Main) { callback(null) }
                }
            }
        }
    }
}