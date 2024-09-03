package com.natalia
package prompt_templates.search

object IntersectingPredicateFinder {
  def generateSearchPrompt(prologTranslations: List[String]): String = {
    val numberedTranslations = prologTranslations.zipWithIndex.map { case (translation, index) =>
      s"Translation ${index + 1}:\n$translation"
    }.mkString("\n\n")

    s"""Given the following Prolog translations of JLS sentences:
       |
       |$numberedTranslations
       |
       |Please identify the intersecting Prolog predicates that appear in more than one of the translations, including their arguments.
       |
       |Provide your response in the following format:
       |
       |INTERSECTING PREDICATES:
       |[List the intersecting predicates and their arguments, e.g., predicate_name/argument]
       |
       |Ensure your response is clearly formatted for easy parsing.
       |""".stripMargin
  }
}
