package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.domain.repository.SearchRepository

class SaveLastSearchTermUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(term: String) {
        searchRepository.saveLastSearchTerm(term)
    }
}
