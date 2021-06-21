terraform {
  backend "pg" {
  }
}

provider "heroku" {
}

resource "heroku_app" "inventory-management" {
  name   = "devops21-inventory-management"
  region = "eu"
  stack  = "container"
  config_vars = {
    JWT_SECRET = var.JWT_SECRET
  }
}

resource "heroku_build" "inventory-management" {
  app = heroku_app.inventory-management.id

  source {
    path = "inventory-management"
  }
}

resource "heroku_addon" "database" {
  app  = heroku_app.inventory-management.name
  plan = "heroku-postgresql:hobby-dev"
}

resource "heroku_app" "frontend" {
  name   = "devops21-webstore-frontend"
  region = "eu"
  stack  = "container"
  config_vars = {
    INVENTORY_MANAGEMENT_API = "${heroku_app.inventory-management.web_url}api"
  }
}

resource "heroku_build" "frontend" {
  app = heroku_app.frontend.id

  source {
    path = "frontend"
  }
}

output "inventory-management_app_url" {
  value = "https://${heroku_app.inventory-management.name}.herokuapp.com"
}
output "frontend_app_url" {
  value = "https://${heroku_app.frontend.name}.herokuapp.com"
}