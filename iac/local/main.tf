locals {
  env = "local"
}

module "common" {
  source                  = "../common"
  prefix                  = "mywallet"
  env                     = local.env
  kafka_bootstrap_servers = [
    "localhost:19092",
    "localhost:19093",
    "localhost:19094"
  ]
}
