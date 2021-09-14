terraform {
  backend "pg" {
  }
}

provider "heroku" {
}

resource "heroku_addon" "database" {
  app  = heroku_app.inventory-management.name
  plan = "heroku-postgresql:hobby-dev"
}


resource "heroku_app" "inventory-management" {
  name   = "prod-agent-inv-management"
  region = "eu"
  stack  = "container"
  config_vars = {
    JWT_SECRET = var.JWT_SECRET
  }
}

resource "heroku_build" "inventory-management" {
  app = heroku_app.inventory-management.id
  depends_on = [heroku_addon.database]
  source {
    path = "inventory-management"
  }
}

resource "heroku_app" "ordering" {
  name   = "prod-agent-ordering"
  region = "eu"
  stack  = "container"
  config_vars = {
    JWT_SECRET = var.JWT_SECRET
  }
}

resource "heroku_build" "ordering" {
  app = heroku_app.ordering.id
  depends_on = [heroku_build.inventory-management]
  source {
    path = "ordering"
  }
}

resource "heroku_addon_attachment" "ordering_database" {
  app_id = heroku_app.ordering.id
  addon_id = heroku_addon.database.id
}

resource "heroku_app" "frontend" {
  name   = "prod-agent-frontend"
  region = "eu"
  stack  = "container"
  depends_on = [heroku_build.reporting]
  config_vars = {
    INVENTORY_MANAGEMENT_API = "${heroku_app.inventory-management.web_url}api"
    ORDERING_API = "${heroku_app.ordering.web_url}api"
    REPORTS_API = "${heroku_app.reporting.web_url}api"
  }
}

resource "heroku_build" "frontend" {
  app = heroku_app.frontend.id
  source {
    path = "frontend"
  }
}


resource "heroku_app" "reporting" {
  name   = "dev-agent-reporting"
  region = "eu"
  stack  = "container"
  config_vars = {
    JWT_SECRET = var.JWT_SECRET
  }
}

resource "heroku_build" "reporting" {
  app = heroku_app.reporting.id
  depends_on = [heroku_build.ordering]
  source {
    path = "reporting"
  }
}

resource "heroku_addon_attachment" "reporting_database" {
  app_id = heroku_app.reporting.id
  addon_id = heroku_addon.database.id
}

output "inventory-management_app_url" {
  value = "https://${heroku_app.inventory-management.name}.herokuapp.com"
}

output "ordering_app_url" {
  value = "https://${heroku_app.ordering.name}.herokuapp.com"
}

output "reporting_app_url" {
  value = "https://${heroku_app.reporting.name}.herokuapp.com"
}

output "frontend_app_url" {
  value = "https://${heroku_app.frontend.name}.herokuapp.com"
}
