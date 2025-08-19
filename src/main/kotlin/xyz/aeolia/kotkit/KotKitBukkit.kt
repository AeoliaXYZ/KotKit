package xyz.aeolia.kotkit

import org.bukkit.plugin.java.JavaPlugin

class KotKitBukkit : JavaPlugin() {
  override fun onEnable() {
    logger.info("KotKit enabled")
  }

  override fun onDisable() {
    logger.info("KotKit disabled")
  }
}
