package community.flock.workshop.app.user.upstream

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import community.flock.wirespec.kotlin.Wirespec
import community.flock.workshop.api.generated.endpoint.DeleteUserById
import community.flock.workshop.api.generated.endpoint.GetUserById
import community.flock.workshop.api.generated.endpoint.GetUsers
import community.flock.workshop.api.generated.endpoint.PostUser
import community.flock.workshop.api.generated.model.ErrorReason
import community.flock.workshop.app.common.wirespec
import community.flock.workshop.app.user.upstream.UserIdConsumer.validate
import community.flock.workshop.app.user.upstream.UserTransformer.validate
import community.flock.workshop.domain.error.DomainError
import community.flock.workshop.domain.error.UserDomainError
import community.flock.workshop.domain.user.HasUserService
import community.flock.workshop.domain.user.deleteUserByEmail
import community.flock.workshop.domain.user.getUserByEmail
import community.flock.workshop.domain.user.getUsers
import community.flock.workshop.domain.user.saveUser
import org.springframework.web.bind.annotation.RestController

interface UserControllerDI : HasUserService

interface UpstreamUserApi :
    GetUsers.Handler,
    GetUserById.Handler,
    PostUser.Handler,
    DeleteUserById.Handler

@RestController
class UserController(
    private val context: UserControllerDI,
) : UpstreamUserApi {
    override suspend fun getUsers(request: GetUsers.Request): GetUsers.Response<*> =
        wirespec(UsersProducer, GetUsers::Response200) {
            context.userService.getUsers().bind()
        }

    override suspend fun getUserById(request: GetUserById.Request): GetUserById.Response<*> =
        wirespec(UserTransformer, GetUserById::Response200, recover(GetUserById::Response404)) {
            val id =
                request.path.potentialUserId
                    .validate()
                    .bind()
            context.userService
                .getUserByEmail(id)
                .bind()
        }

    override suspend fun postUser(request: PostUser.Request): PostUser.Response<*> =
        wirespec(UserTransformer, PostUser::Response200) {
            val user = request.body.validate().bind()
            context.userService
                .saveUser(user)
                .bind()
        }

    override suspend fun deleteUserById(request: DeleteUserById.Request): DeleteUserById.Response<*> =
        wirespec(UserTransformer, DeleteUserById::Response200, recover(DeleteUserById::Response404)) {
            val id =
                request.path.potentialUserId
                    .validate()
                    .bind()
            context.userService.deleteUserByEmail(id).bind()
        }

    private fun <R : Wirespec.Response<ErrorReason>> recover(block: (ErrorReason) -> R): (DomainError) -> Either<DomainError, R> =
        {
            when (it) {
                is UserDomainError -> block(ErrorReason(it.message)).right()
                else -> it.left()
            }
        }
}
