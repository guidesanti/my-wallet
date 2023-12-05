FROM mongo:latest

# Create folders
RUN mkdir /data/mongo
RUN mkdir /data/mongo/init
RUN mkdir /data/mongo/data
RUN mkdir /data/mongo/config
RUN mkdir /data/mongo/certs
RUN mkdir /data/mongo/logs

# Add files
ADD ./mongodb-replica-set-init.js /data/mongo/init/mongodb-replica-set-init.js
ADD ./create-users.js /data/mongo/init/create-users.js
ADD ./mongod.conf.yaml /data/mongo/config/mongod.conf
ADD ./keyfile /data/mongo/config/keyfile
ADD ./ca.pem /data/mongo/certs/ca.pem
ADD ./mongo.pem /data/mongo/certs/mongo.pem

RUN chown -R mongodb:mongodb /data/mongo

# Expose port #27017 from the container to the host
EXPOSE 27017

# Define default command
CMD mongod --config /data/mongo/config/mongod.conf
