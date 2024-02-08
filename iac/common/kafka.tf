resource "kafka_topic" "kafka_topic" {
  for_each            = local.topics
  name                = "${var.env}-${each.value.name}"
  replication_factor  = each.value.replication_factor
  partitions          = each.value.partitions
  config              = each.value.config
}

locals {
  ######################################################################################################################
  # Kafka Topics
  #
  # Topic Types:
  # - EVENT:
  #   Topics used to send (broadcast) events to notify that something occured or the system state has changed
  #   Should not have any reply (fire and forget)
  # - COMMAND_QUERY:
  #   Topics used to send command/query requests
  #   For commands generally a reply is expected, but not required
  #   For queries a reply is always expected
  # - COMMAND_QUERY_REPLY:
  #   Topics used to send command/query reply with status and potentially some result
  # - DLQ
  #   Topics used as dead letter queues, when a message couldn't be processed for some reason and is redirected to
  #   respective DQL
  #
  # Topic Naming Convention:
  #
  # Type: EVENT
  # Format: {env}-{context}-{event-name}
  # Where: {event-name} should be composed of a noun and a verb in the past
  # Example: prod-assets-asset-created
  #
  # Type: COMMAND_QUERY
  # Format: {env}-{context}-{command-query}
  # Where: {command-query} should be composed of a verb in imperative and a noun
  # Examples:
  #   prod-assets-create-asset
  #   prod-assets-get-asset
  #
  # Type: COMMAND_QUERY_REPLY
  # Format: {env}-{context}-{command-query}-reply
  # Where: {command-query} should be the same as in the respective command/query request
  # Examples:
  #   prod-transactions-create-asset-reply
  #   prod-transactions-get-asset-reply
  #
  # Type: DLQ
  # Format: {env}-{context}-{?}-dlq
  # Where: {?} should be one of [{event-name}, {command-query}, {command-query}-reply]
  # Examples:
  #   prod-assets-asset-created-dlq
  #   prod-assets-create-asset-dlq
  #   prod-transactions-create-asset-reply-dlq
  #
  # NOTE: The {env} should not be explicitly added, since it will be added automatically
  ######################################################################################################################
  topics = {
    ####################################################################################################################
    # Topic: assets-asset-created
    # Type: EVENT
    # Description: Topic to broadcast asset created events
    ####################################################################################################################
    assets_asset_created = {
      name                = "assets-asset-created"
      replication_factor  = 3
      partitions          = 1

      config = {
        "retention.ms"    = "604800000"
        "segment.ms"      = "604800000"
        "cleanup.policy"  = "delete"
      }
    }

    ####################################################################################################################
    # Topic: assets-asset-updated
    # Type: EVENT
    # Description: Topic to broadcast asset updated events
    ####################################################################################################################
    assets_asset_updated = {
      name                = "assets-asset-updated"
      replication_factor  = 3
      partitions          = 1

      config = {
        "retention.ms"    = "604800000"
        "segment.ms"      = "604800000"
        "cleanup.policy"  = "delete"
      }
    }

    ####################################################################################################################
    # Topic: assets-get-asset
    # Type: COMMAND_QUERY
    # Description: Topic to send command get asset
    ####################################################################################################################
    assets_get_asset = {
      name                = "assets-get-asset"
      replication_factor  = 3
      partitions          = 1

      config = {
        "retention.ms"    = "604800000"
        "segment.ms"      = "604800000"
        "cleanup.policy"  = "delete"
      }
    }

    ####################################################################################################################
    # Topic: assets-get-asset-dlq
    # Type: DLQ
    # Description: Topic to send dead letters ?
    ####################################################################################################################
    assets_get_asset_dlq = {
      name                = "assets-get-asset-dlq"
      replication_factor  = 3
      partitions          = 1

      config = {
        "retention.ms"    = "604800000"
        "segment.ms"      = "604800000"
        "cleanup.policy"  = "delete"
      }
    }

    ####################################################################################################################
    # Topic: transactions-transaction-created
    # Type: EVENT
    # Description: Topic to broadcast transaction created events
    ####################################################################################################################
    transactions_transaction_created = {
      name                = "transactions-transaction-created"
      replication_factor  = 3
      partitions          = 1

      config = {
        "retention.ms"    = "604800000"
        "segment.ms"      = "604800000"
        "cleanup.policy"  = "delete"
      }
    }
  }
}
