package com.hkjj.heartbreakprice.data.data_source.local

import android.content.Context
import androidx.core.content.edit
import com.hkjj.heartbreakprice.data.data_source.SearchLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchLocalDataSourceImpl(
    private val context: Context
) : SearchLocalDataSource {

    companion object {
        private const val PREFS_NAME = "heartbreakprice_prefs"
        private const val KEY_LAST_SEARCH_TERM = "last_search_term"
    }

    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override suspend fun saveLastSearchTerm(term: String) = withContext(Dispatchers.IO) {
        prefs.edit {
            putString(KEY_LAST_SEARCH_TERM, term)
        }
    }

    override suspend fun getLastSearchTerm(): String? = withContext(Dispatchers.IO) {
        prefs.getString(KEY_LAST_SEARCH_TERM, null)
    }
}
