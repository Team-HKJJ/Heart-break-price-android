package com.hkjj.heartbreakprice.domain.repository

interface SearchRepository {
    suspend fun saveLastSearchTerm(term: String)
    suspend fun getLastSearchTerm(): String?
}
