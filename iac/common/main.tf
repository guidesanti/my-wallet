terraform {
  required_providers {
    kafka = {
      source  = "Mongey/kafka"
      version = "~> 0.5.4"
    }
  }
}

provider "kafka" {
  bootstrap_servers = var.kafka_bootstrap_servers
  skip_tls_verify   = true
  tls_enabled = false
}
