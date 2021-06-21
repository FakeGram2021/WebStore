#!/bin/sh

HEROKU_EMAIL=${1}
HEROKU_API_KEY=${2}
TERRAFORM_PG_BACKEND=${3}
CONTAINER_NAME=${4:-terraform-deploy}

docker create \
  --workdir /deployment/dev \
  --entrypoint sh \
  --env HEROKU_API_KEY="${HEROKU_API_KEY}" \
  --env HEROKU_EMAIL="${HEROKU_EMAIL}" \
  --env TERRAFORM_PG_BACKEND="${TERRAFORM_PG_BACKEND}" \
  --env JWT_SECRET="${JWT_SECRET}" \
  --name "$CONTAINER_NAME" \
  danijelradakovic/heroku-terraform \
  deploy.sh

docker cp deployment/. "${CONTAINER_NAME}":/deployment/
docker start -i "${CONTAINER_NAME}"
docker rm "${CONTAINER_NAME}"
