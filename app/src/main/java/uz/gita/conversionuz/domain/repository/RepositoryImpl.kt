package uz.gita.conversionuz.domain.repository



import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.conversionuz.data.response.ApiResponse
import uz.gita.conversionuz.data.response.ErrorMessage
import uz.gita.conversionuz.domain.network.ApiCurse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: ApiCurse
) : Repository {
    private val gson = Gson()
    override fun getAll(): Flow<Result<List<ApiResponse.CursResponse>>>  = flow{
        val result = api.getAll()
        if (result.isSuccessful && result.body() != null) {
            emit(Result.success(result.body()!!))
        } else if (result.errorBody() != null) {
            val error: ErrorMessage.Message =
                gson.fromJson(result.errorBody()!!.string(), ErrorMessage.Message::class.java)
            emit(Result.failure(Exception(error.message)))
        } else {
            Log.d("AAA", "getAll: ${result.message()}")
            emit(Result.failure(Exception(result.message())))
        }

    }
}