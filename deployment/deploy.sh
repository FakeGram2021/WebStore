#!/bin/sh

#heroku create $TERRAFORM_PG_BACKEND
#heroku addons:create heroku-postgresql:hobby-dev --app $TERRAFORM_PG_BACKEND
cd terraform || exit
DATABASE_URL=$(heroku config:get DATABASE_URL --app "$TERRAFORM_PG_BACKEND") && export DATABASE_URL
INVENTORY-MANAGEMENT_URL=$(heroku config:get DATABASE_URL --app "$TERRAFORM_PG_BACKEND") && export DATABASE_URL
terraform init -backend-config="conn_str=$DATABASE_URL"
terraform apply -auto-approve -var JWT_SECRET="$JWT_SECRET"