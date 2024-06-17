FROM node:20-alpine
RUN mkdir /app
COPY dist/bundle.js /app

EXPOSE 3000

CMD node /app/bundle.js
