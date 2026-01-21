package com.hkjj.heartbreakprice.data.repository

import com.hkjj.heartbreakprice.data.data_source.SearchLocalDataSource
import com.hkjj.heartbreakprice.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val searchLocalDataSource: SearchLocalDataSource
) : SearchRepository {
    override suspend fun saveLastSearchTerm(term: String) {
        searchLocalDataSource.saveLastSearchTerm(term)
    }

    override suspend fun getLastSearchTerm(): String? {
        return searchLocalDataSource.getLastSearchTerm()
    }
}
