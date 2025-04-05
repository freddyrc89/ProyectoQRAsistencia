import android.util.Log
import com.freddy.proyectoqrasistencia.Alumno
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
        private const val BASE_URL = "https://apisenativerde.onrender.com/"
        private const val MAX_INTENTOS = 3 // Número máximo de intentos
        private const val RETRY_DELAY = 2000L // 2 segundos entre intentos

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
                var resultado: Alumno? = null
                var requiereReintento = false
                repeat(MAX_INTENTOS){ intento-> //Mejorar logica porque se repite 3 veces
                    try {
                        resultado = apiService.obtenerAlumno(dni)
                        withContext(Dispatchers.Main) {
                            Log.v("Logica_API","Alumno obtenido")
                            callback(resultado) //  Devuelve el objeto a `MainActivity`
                        }
                        return@launch

                    } catch (e: HttpException) {
                        Log.e("API_ERROR", "Error en la solicitudddd: ${e.message()}")
                    } catch (e: Exception) {
                        Log.e("API_ERROR", "Error inesperado: ${e.localizedMessage}")
                    }
                    delay(RETRY_DELAY)
                }
                requiereReintento = true
                withContext(Dispatchers.Main) {
                    callback(null)
                }
            }
        }
        fun enviarDatosQR(dni: Int, estadoQR: Boolean, callback: (Boolean) -> Unit) {
            val apiService = create()
            val datos = EnvioQR(dni, estadoQR)

            CoroutineScope(Dispatchers.IO).launch {


                repeat(MAX_INTENTOS) { intento ->
                    try {
                        apiService.enviarDatosQR(datos)
                        withContext(Dispatchers.Main) { callback(true) } // Éxito
                        return@launch
                    } catch (e: HttpException) {
                        Log.e("API_ERROR", "Intento ${intento + 1}: ${e.message()}")
                    } catch (e: Exception) {
                        Log.e("API_ERROR", "Intento ${intento + 1}: ${e.localizedMessage}")
                    }
                    delay(RETRY_DELAY)
                }
                withContext(Dispatchers.Main) { callback(false) }
            }
        }
    }
}