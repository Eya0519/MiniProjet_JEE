FROM node:24-alpine AS build

WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:1.27-alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

ENV API_URL=http://localhost:8081
ENV PORT=80

EXPOSE 80

CMD sed -i "s/listen 80;/listen ${PORT};/" /etc/nginx/conf.d/default.conf && \
    printf '{"apiUrl":"%s"}\n' "$API_URL" > /usr/share/nginx/html/config.json && \
    nginx -g "daemon off;"
