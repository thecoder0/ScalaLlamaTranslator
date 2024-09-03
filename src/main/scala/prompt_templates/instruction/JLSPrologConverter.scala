package com.natalia
package prompt_templates.instruction

object JLSPrologConverter {
  def generateInstructionPrompt(jlsSentence: String): String = {
    s""" Translate the following Java Language Specification (JLS) sentence to Prolog code,
       | without any preamble or additional explanation:
       | $jlsSentence
       | 1. PROLOG FACTS:
       | [Insert Prolog facts, one per line]
       |
       | 2. PROLOG RULES:
       | [Insert Prolog rules, one per line]
       |
       | 3. KEY TERMS AND RELATIONSHIPS:
       | [List key terms and their relationships, e.g., Term1 -> Relationship -> Term2]
       |
       | Ensure each section is clearly separated and formatted for easy parsing.
       |""".stripMargin
  }
}
