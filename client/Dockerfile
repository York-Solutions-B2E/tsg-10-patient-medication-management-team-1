# Step 1 Build The React Application
FROM node:lts AS build

# Set the working environment
WORKDIR /opt/app

# Copy package.json and package-lock.json
COPY package*json ./

# Install latest npm
RUN npm install -g npm@latest

# Install dependencies
RUN npm install --legacy-peer-deps

# Copy the entire project into the container
COPY . .

# Build the application
RUN npm run build

# Step 2: Serve the React application using Node.js
FROM node:lts AS serve

# Set the working directory
WORKDIR /opt/app

# Install a lightweight static file server (e.g., serve)
RUN npm install -g serve

# Copy the built files from the build stage
COPY --from=build /opt/app/build ./dist

# Expose the port where the app will run
EXPOSE 3000

# Use `serve` to serve the static files
CMD ["serve", "-s", "build", "-l", "3000"]