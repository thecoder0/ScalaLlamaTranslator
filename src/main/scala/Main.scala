package com.natalia

import prompt_templates.instruction.JLSPrologConverter

import com.typesafe.scalalogging.Logger
import io.github.ollama4j.OllamaAPI
import io.github.ollama4j.models.OllamaResult
import io.github.ollama4j.utils.Options

import java.util.{HashMap => JHashMap}

object Main {
  val logger: Logger = Logger(getClass)
  def main(args: Array[String]): Unit = {
    val ollamaAPI: OllamaAPI = new OllamaAPI(Configuration.host)
    ollamaAPI.setRequestTimeoutSeconds(Configuration.requestTimeoutSeconds)

    val jlsStatement = "The scope and shadowing of a class declaration is specified in §6.3 and §6.4.1."

    try {
      // 1.) Translate JLS statements to Prolog
        val instructionPrompt = JLSPrologConverter.generateInstructionPrompt(jlsStatement)
        val result: OllamaResult = ollamaAPI.generate(Configuration.model, instructionPrompt, false, new Options(new JHashMap[String, Object]))
        logger.info(s"INPUT: $jlsStatement")
        logger.info(s"OUTPUT: ${result.getResponse}")
        result.getResponse
      } catch {
      case e: Exception =>
        logger.error("PROCESS FAILED", e.getMessage)
    }
  }
}
