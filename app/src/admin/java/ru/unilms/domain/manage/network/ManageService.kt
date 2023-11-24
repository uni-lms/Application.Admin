package ru.unilms.domain.manage.network

import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.Response
import ru.unilms.domain.manage.model.User

interface ManageService {
    suspend fun getUsers(): Response<List<User>, ErrorResponse>
}