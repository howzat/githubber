package com.appliedtype.githubber

import java.util.regex.Matcher
import com.appliedtype.githubber.runner.Commands.Command
import scala.util.matching.Regex

package object runner {

  type CommandMatcher = String => Option[Command]

  object RegexUtils {

    def collect(matcher:Matcher) : List[String] = {
      (0 to matcher.groupCount()).map(matcher.group) toList
    }

    implicit class RichRegex(val underlying: Regex) extends AnyVal {
      def matches(s: String): Option[List[String]] = {
        val matcher = underlying.pattern.matcher(s)
        if (matcher.matches) Some(collect(matcher)) else None
      }
    }
  }
}
