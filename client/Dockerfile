# Stage 1: Build
FROM node:18-alpine AS builder

# Set working directory inside the container
WORKDIR /client

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install --legacy-peer-deps

# Copy the rest of the application files
COPY . .

# Build the application for production
RUN npm run build

# Stage 2: Serve with a lightweight web server
FROM nginx:alpine

# Copy the build output from the previous stage to the nginx public directory
COPY --from=builder /client/dist /usr/share/nginx/html

# Expose the default port used by nginx
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
