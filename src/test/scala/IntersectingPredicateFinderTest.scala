package com.natalia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.natalia.prompt_templates.search.IntersectingPredicateFinder
import com.natalia.prompt_templates.instruction.JLSPrologConverter
import io.github.ollama4j.OllamaAPI
import io.github.ollama4j.models.OllamaResult
import io.github.ollama4j.utils.Options

import java.util.{HashMap => JHashMap}

class IntersectingPredicateFinderTest extends AnyFlatSpec with Matchers {

  val ollamaAPI: OllamaAPI = new OllamaAPI(Configuration.host)
  ollamaAPI.setRequestTimeoutSeconds(Configuration.requestTimeoutSeconds)

  "IntersectingPredicateFinder" should "identify common predicates across superclass and subclass definitions" in {
    val jlsSentences = List(
      "The direct superclass of a class is the class named by its direct superclass type.",
      "A class is said to be a direct subclass of its direct superclass, and a subclass of each of its superclasses.",
      "The superclass relationship is the transitive closure of the direct superclass relationship."
    )

    // Convert JLS to Prolog
    val prologTranslations = jlsSentences.map { sentence =>
      val prompt = JLSPrologConverter.generateInstructionPrompt(sentence)
      val result: OllamaResult = ollamaAPI.generate(Configuration.model, prompt, false, new Options(new JHashMap[String, Object]))
      result.getResponse
    }
    val searchPrompt = IntersectingPredicateFinder.generateSearchPrompt(prologTranslations)
    val searchResult: OllamaResult = ollamaAPI.generate(Configuration.model, searchPrompt, false, new Options(new JHashMap[String, Object]))
    val intersectingPredicates = searchResult.getResponse

    // Check for predicates
    intersectingPredicates should (
      include("direct_superclass") and
        include("superclass") and
        include("subclass")
      )
    intersectingPredicates should (
      include("direct_superclass") and include("direct_subclass")
      )
  }
}
