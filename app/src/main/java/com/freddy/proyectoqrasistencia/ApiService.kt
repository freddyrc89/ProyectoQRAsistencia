import android.util.Log
import com.freddy.proyectoqrasistencia.Alumno
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.POST

data class EnvioQR(
    @SerializedName("dni_alumno") val dniAlumno: Int,
    @SerializedName("estado_qr") val estadoQR: Boolean
)
interface ApiService {
    @GET("api/alumnos/{dni}")
    suspend fun obtenerAlumno(@Path("dni") id: String): Alumno
    @POST("api/creacion_qr")
    suspend fun enviarDatosQR(@Body datos: EnvioQR)

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000/"

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
        fun enviarDatosQR(dni: Int, estadoQR: Boolean, callback: (Boolean) -> Unit) {
            val apiService = create()
            val datos = EnvioQR(dni, estadoQR)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    apiService.enviarDatosQR(datos)
                    withContext(Dispatchers.Main) {
                        callback(true)  // Indica que la operación fue exitosa
                    }
                } catch (e: HttpException) {
                    Log.e("API_ERROR", "Error en la solicitud: ${e.message()}")
                    withContext(Dispatchers.Main) { callback(false) }
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Error inesperado: ${e.localizedMessage}")
                    withContext(Dispatchers.Main) { callback(false) }
                }
            }
        }
    }
}