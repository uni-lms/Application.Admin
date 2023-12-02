package ru.unilms.domain.manage.network

import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.Response
import ru.unilms.domain.manage.model.Group
import ru.unilms.domain.manage.model.UpdateUserRequest
import ru.unilms.domain.manage.model.User
import java.util.UUID

interface ManageService {
    suspend fun getUsers(): Response<List<User>, ErrorResponse>
    suspend fun getUser(userId: UUID): Response<User, ErrorResponse>
    suspend fun updateUser(request: UpdateUserRequest): Response<User, ErrorResponse>
    suspend fun getGroups(): Response<List<Group>, ErrorResponse>
}