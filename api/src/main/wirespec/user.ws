type UserDto {
    email: String,
    firstName: String,
    lastName: String,
    birthDate: String
}

type ErrorReason {
    reason: String
}

endpoint GetUsers GET /api/users -> {
    200 -> UserDto[]
}

endpoint GetUserById GET /api/users/{potentialUserId: String} -> {
    200 -> UserDto
    404 -> ErrorReason
}

endpoint PostUser POST UserDto /api/users -> {
    200 -> UserDto
}

endpoint DeleteUserById DELETE /api/users/{potentialUserId: String} -> {
    200 -> UserDto
    404 -> ErrorReason
}
