package mx.irisoft.pruebatecniva.domain.models

import mx.irisoft.pruebatecniva.data.remote.state.Resource

data class ProgressUploadModel(
    val resource: Resource<String>,
    val totalByteCount: Long,
    val bytesTransferred: Long,
)