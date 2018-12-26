package com.appliedtype.githubber.client.models.http

object Model {

  type Request[F[_], R, T] = R => F[T]
}
