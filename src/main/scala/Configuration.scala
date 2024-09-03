package com.natalia
import com.typesafe.config.ConfigFactory

object Configuration {
  private val config = ConfigFactory.load()

  val host: String = config.getString("ollama.host")
  val model: String = config.getString("ollama.model")
  val requestTimeoutSeconds: Int = config.getInt("ollama.request-timeout-seconds")
}
