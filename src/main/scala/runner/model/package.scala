package runner

package object model {

  type Command[F[_], R<:CommandResult] = Seq[String] => F[R]

  sealed trait CommandResult {
    def stringValue: String
  }

  case object HelloWorld extends CommandResult {
    override def stringValue: String = "HelloWorld"
  }

  case object Info extends CommandResult {
    override def stringValue: String = "This is some information that would be useful"
  }
}
