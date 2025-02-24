# Use the official Python image from Docker Hub
FROM python:3.9-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the requirements file to the working directory
COPY dependencies.txt .

# Install the dependencies
RUN pip install --no-cache-dir -r dependencies.txt

# Copy the Flask app to the container
COPY app.py .

# Expose the port the app will run on
EXPOSE 5000

# Define the command to run the Flask app
CMD ["python", "app.py"]
