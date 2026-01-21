package com.hkjj.heartbreakprice.data.data_source

interface SearchLocalDataSource {
    suspend fun saveLastSearchTerm(term: String)
    suspend fun getLastSearchTerm(): String?
}
