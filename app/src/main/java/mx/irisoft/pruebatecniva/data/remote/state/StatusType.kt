package mx.irisoft.pruebatecniva.data.remote.state

enum class StatusType {
    SUCCESS,
    ERROR,
    LOADING;

    fun isLoading() = this == LOADING
}