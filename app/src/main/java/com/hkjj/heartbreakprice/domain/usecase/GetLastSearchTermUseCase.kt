package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.domain.repository.SearchRepository

class GetLastSearchTermUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(): String? {
        return searchRepository.getLastSearchTerm()
    }
}
