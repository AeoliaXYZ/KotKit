package xyz.aeolia.kotkit

import org.bukkit.plugin.java.JavaPlugin

class KotKit : JavaPlugin() {

  override fun onEnable() {
    logger.info("KotKit enabled")
  }

  override fun onDisable() {
    logger.info("KotKit disabled")
  }
}
