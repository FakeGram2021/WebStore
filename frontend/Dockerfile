FROM node:lts-alpine AS dependency
WORKDIR /usr/src/app
COPY package.json yarn.lock ./
ENV YARN_CACHE_FOLDER=/usr/src/app/yarn_cache
RUN yarn install --frozen-lockfile

FROM node:lts-alpine AS builder

ENV NODE_ENV=production
WORKDIR /opt/app
COPY . .
COPY --from=dependency /usr/src/app/node_modules ./node_modules
RUN yarn build

FROM node:lts-alpine AS runner

WORKDIR /opt/app
ENV NODE_ENV=production
ENV INVENTORY_MANAGEMENT_API=${INVENTORY_MANAGEMENT_API}
COPY --from=builder /opt/app/next.config.js ./
COPY --from=builder /opt/app/public ./public
COPY --from=builder /opt/app/.next ./.next
COPY --from=builder /opt/app/node_modules ./node_modules
EXPOSE 3000
CMD ["node_modules/.bin/next", "start"]