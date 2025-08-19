package xyz.aeolia.kotkit

import net.md_5.bungee.api.plugin.Plugin

class KotKitBungee : Plugin() {
  override fun onEnable() {
    logger.info("KotKit enabled")
  }

  override fun onDisable() {
    logger.info("KotKit disabled")
  }
}
