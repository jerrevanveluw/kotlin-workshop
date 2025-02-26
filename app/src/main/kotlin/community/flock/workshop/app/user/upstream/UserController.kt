package community.flock.workshop.app.user.upstream

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import community.flock.wirespec.kotlin.Wirespec
import community.flock.workshop.api.generated.DeleteUserByIdEndpoint
import community.flock.workshop.api.generated.ErrorReason
import community.flock.workshop.api.generated.GetUserByIdEndpoint
import community.flock.workshop.api.generated.GetUsersEndpoint
import community.flock.workshop.api.generated.PostUserEndpoint
import community.flock.workshop.app.common.wirespec
import community.flock.workshop.app.user.upstream.UserIdConsumer.validate
import community.flock.workshop.app.user.upstream.UserTransformer.validate
import community.flock.workshop.domain.error.DomainError
import community.flock.workshop.domain.error.UserDomainError
import community.flock.workshop.domain.user.UserContext
import community.flock.workshop.domain.user.UserRepository
import community.flock.workshop.domain.user.UserService.deleteUserByEmail
import community.flock.workshop.domain.user.UserService.getUserByEmail
import community.flock.workshop.domain.user.UserService.getUsers
import community.flock.workshop.domain.user.UserService.saveUser
import org.springframework.web.bind.annotation.RestController

interface UpstreamUserApi :
    GetUsersEndpoint.Handler,
    GetUserByIdEndpoint.Handler,
    PostUserEndpoint.Handler,
    DeleteUserByIdEndpoint.Handler

@RestController
class UserController(
    userRepository: UserRepository,
) : UpstreamUserApi {
    private val context =
        object : UserContext {
            override val userRepository = userRepository
        }

    override suspend fun getUsers(request: GetUsersEndpoint.Request): GetUsersEndpoint.Response<*> =
        wirespec(UsersProducer, GetUsersEndpoint::Response200) {
            context.getUsers().bind()
        }

    override suspend fun getUserById(request: GetUserByIdEndpoint.Request): GetUserByIdEndpoint.Response<*> =
        wirespec(UserTransformer, GetUserByIdEndpoint::Response200, recover(GetUserByIdEndpoint::Response404)) {
            val id =
                request.path.potentialUserId
                    .validate()
                    .bind()
            context.getUserByEmail(id).bind()
        }

    override suspend fun postUser(request: PostUserEndpoint.Request): PostUserEndpoint.Response<*> =
        wirespec(UserTransformer, PostUserEndpoint::Response200) {
            val user = request.body.validate().bind()
            context.saveUser(user).bind()
        }

    override suspend fun deleteUserById(request: DeleteUserByIdEndpoint.Request): DeleteUserByIdEndpoint.Response<*> =
        wirespec(UserTransformer, DeleteUserByIdEndpoint::Response200, recover(DeleteUserByIdEndpoint::Response404)) {
            val id =
                request.path.potentialUserId
                    .validate()
                    .bind()
            context.deleteUserByEmail(id).bind()
        }

    private fun <R : Wirespec.Response<ErrorReason>> recover(block: (ErrorReason) -> R): (DomainError) -> Either<DomainError, R> =
        {
            when (it) {
                is UserDomainError -> block(ErrorReason(it.message)).right()
                else -> it.left()
            }
        }
}
