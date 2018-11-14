package by.mcs.launcher.params

data class QueryParam(
    val query: String,
    val variables: Map<String, Any?>?,
    val operationName: String?
)