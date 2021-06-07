#!/bin/sh

heroku apps:destroy --app $TERRAFORM_PG_BACKEND

cd terraform || exit
DATABASE_URL=$(heroku config:get DATABASE_URL --app "$TERRAFORM_PG_BACKEND") && export DATABASE_URL
JWT_SECRET=$JWT_SECRET && export JWT_SECRET
terraform init -backend-config="conn_str=$DATABASE_URL"
terraform destroy -auto-approve -var JWT_SECRET="$JWT_SECRET"