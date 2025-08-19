package xyz.aeolia.kotkit

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import org.slf4j.Logger

@Suppress("unused")
class KotKitVelocity @Inject constructor(val logger: Logger) {

  @Subscribe
  fun onProxyInitialization(event: ProxyInitializeEvent) {
    logger.info("KotKit enabled")
  }

  @Subscribe
  fun onProxyShutdown(event: ProxyShutdownEvent) {
    logger.info("KotKit disabled")
  }
}
