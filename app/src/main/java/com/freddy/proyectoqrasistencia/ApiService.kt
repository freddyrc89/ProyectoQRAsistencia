import com.freddy.proyectoqrasistencia.Alumno
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("alumnos/{dni}")
    suspend fun obtenerAlumno(@Path("dni") id: String): Alumno

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000/api/" // Cambia esto por la URL real de tu API

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
            }
        }
}