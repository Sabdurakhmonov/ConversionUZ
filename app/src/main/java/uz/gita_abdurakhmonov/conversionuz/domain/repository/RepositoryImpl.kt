package uz.gita_abdurakhmonov.conversionuz.domain.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse
import uz.gita_abdurakhmonov.conversionuz.data.response.ErrorMessage
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.SavedData
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UIData
import uz.gita_abdurakhmonov.conversionuz.domain.remote.local.AppDao
import uz.gita_abdurakhmonov.conversionuz.domain.remote.network.ApiCrypto
import uz.gita_abdurakhmonov.conversionuz.domain.remote.network.ApiCurse
import uz.gita_abdurakhmonov.conversionuz.utils.toUIData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiCurse: ApiCurse,
    private val apiCrypto: ApiCrypto,
    private val dao: AppDao
) : Repository {
    private val crypto = mutableListOf<UIData>()
    private val currency = mutableListOf<ApiResponse.CursResponse>()
    private val gson = Gson()
    override fun getAllCurse(): Flow<Result<List<ApiResponse.CursResponse>>> = flow {
        val result = apiCurse.getAll()
        if (result.isSuccessful && result.body() != null) {
            currency.clear()
            currency.addAll(result.body()!!)
            emit(Result.success(result.body()!!))
        } else if (result.errorBody() != null) {
            val error: ErrorMessage.Message =
                gson.fromJson(result.errorBody()!!.string(), ErrorMessage.Message::class.java)
            emit(Result.failure(Exception(error.message)))
        } else {
            emit(Result.failure(Exception(result.message())))
        }

    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun getAllCrypto(): Flow<Result<List<UIData>>> = flow {
        val result = apiCrypto.getAll()
        if (result.isSuccessful && result.body() != null) {
            val list = mutableListOf<UIData>()
            result.body()!!.data.forEach { list.add(it.toUIData()) }
            crypto.clear()
            crypto.addAll(list)
            emit(Result.success(list))
        } else if (result.errorBody() != null) {
            val error: ErrorMessage.Message =
                gson.fromJson(result.errorBody()!!.string(), ErrorMessage.Message::class.java)
            emit(Result.failure(Exception(error.message)))
        } else {
            emit(Result.failure(Exception(result.message())))
        }
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun getAllLocalCrypto(): Flow<Result<List<UIData>>> = flow {
        val local = dao.getAllData("crypto")
        val list = mutableListOf<UIData>()
        list.clear()
        local.forEach {
            crypto.forEach { data ->
                if (it.svId.toString() == data.id) {
                    list.add(data)
                }
            }
        }
        emit(Result.success(list))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun getAllLocalCurrency(): Flow<Result<List<ApiResponse.CursResponse>>> = flow{
        val local = dao.getAllData("currency")
        val list = mutableListOf<ApiResponse.CursResponse>()
        currency.forEach {
            local.forEach { data ->
                if (it.id == data.svId) {
                    list.add(it)
                }
            }
        }
        emit(Result.success(list))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun addData(data: SavedData): Flow<Result<Unit>> = flow {
        dao.addData(data)
        emit(Result.success(Unit))

    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun deleteAll(type: String): Flow<Result<Unit>> = flow<Result<Unit>> {
        dao.delete(type)
        Result.success(Unit)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun deleteData(data: SavedData): Flow<Result<Unit>> = flow{
        dao.deleteData(data)
        emit(Result.success(Unit))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}
