variable "prefix" {
  description = "Prefix for all resource names"
  type = string
}

variable "env" {
  description = "Target environment, available options are [local, dev, prod]"
  type = string
}

variable "kafka_bootstrap_servers" {
  description = "Kafka bootstrap servers"
  type        = list(string)
}
