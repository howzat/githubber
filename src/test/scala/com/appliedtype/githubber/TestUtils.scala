package com.appliedtype.githubber

import org.apache.commons.io.IOUtils

object TestUtils {

  def readFile(path: String): String = {
    IOUtils.toString(getClass.getResourceAsStream(path), "UTF-8")
  }
}
