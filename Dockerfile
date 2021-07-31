# BUILD
FROM node:10.18.1 as build-stage
WORKDIR /app
COPY . .
RUN npm i
RUN npm run build

# PROD
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /var/www
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]