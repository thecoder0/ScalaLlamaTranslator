package com.natalia

import com.natalia.prompt_templates.instruction.JLSPrologConverter
import io.github.ollama4j.OllamaAPI
import io.github.ollama4j.models.OllamaResult
import io.github.ollama4j.utils.Options
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.{HashMap => JHashMap}

class JLSPrologConverterTest extends AnyFlatSpec with Matchers {

  val ollamaAPI: OllamaAPI = new OllamaAPI(Configuration.host)
  ollamaAPI.setRequestTimeoutSeconds(Configuration.requestTimeoutSeconds)

  "JLSPrologConverter" should "translate the following JLS sentence to Prolog" in {
    val sentence = "There are two kinds of types in the Java programming language: primitive types (§4.2) and reference types (§4.3)."
    val prompt = JLSPrologConverter.generateInstructionPrompt(sentence)
    val result: OllamaResult = ollamaAPI.generate(Configuration.model, prompt, false, new Options(new JHashMap[String, Object]))
    val llamaResponse = result.getResponse
    // Check for key Prolog facts
    llamaResponse should (
      include("primitive_type") and
        include("reference_type")
      )
  }

  "JLSPrologConverter" should "correctly map relationships and cross-references for scope and shadowing" in {
    val sentence = "The scope and shadowing of a class declaration is specified in §6.3 and §6.4.1."
    val prompt = JLSPrologConverter.generateInstructionPrompt(sentence)
    val result: OllamaResult = ollamaAPI.generate(Configuration.model, prompt, false, new Options(new JHashMap[String, Object]))
    val llamaResponse = result.getResponse
    // Check for presence of key terms
    llamaResponse should (
      include("class declaration") and
        include("scope") and
        include("shadowing")
      )
    // Check for key terms and their relationships
    llamaResponse should (
      include("scope") and include("§6.3") and
        include("shadowing") and include("§6.4.1")
      )
  }

  it should "correctly translate and represent method overloading concepts" in {
    val sentence = "If two methods of a class (whether both declared in the same class, or both inherited by a class, or one declared and one inherited) have the same name but signatures that are not override-equivalent, then the method name is said to be overloaded."
    val prompt = JLSPrologConverter.generateInstructionPrompt(sentence)
    val result: OllamaResult = ollamaAPI.generate(Configuration.model, prompt, false, new Options(new JHashMap[String, Object]))
    val llamaResponse = result.getResponse
    // Check for key terms and relationships
    llamaResponse should (
      include("method_name") and
        include("method_signature") and
        include("overloaded_method") and
        include("override_equivalent")
      )
  }
}
