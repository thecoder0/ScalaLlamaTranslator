package com.natalia

import scala.util.matching.Regex

object JLSTermExtractor {
  // Pattern for terms with explicit references
  final val refPattern: Regex = """((?:\w+\s+)*\w+)\s*\((§[0-9.]+(?:\s+and\s+§[0-9.]+)*)\)""".r

  def extractTermsWithReferences(jlsStatement: String): List[(String, String)] = {
    refPattern.findAllMatchIn(jlsStatement).map { m =>
      // Remove HTML tags and asterisks in the extracted term
      val term = m.group(1).replaceAll("\\*", "").replaceAll("<[^>]+>", "").trim
      val references = m.group(2)
      (term, references)
    }.toList
  }

  def containsCrossReferences(jlsStatement: String): Boolean = {
    refPattern.findFirstIn(jlsStatement).isDefined
  }

}
