FROM node:4.4.5
RUN npm install -g nodemon
RUN npm install -g lodash@4.7.0
RUN npm install -g node-uuid@1.4.7
RUN npm install -g winston@2.2.0
RUN npm install -g koa@1.2.0
RUN npm install -g koa-bodyparser@2.0.1
RUN npm install -g koa-json@1.1.1
RUN npm install -g koa-router@5.4.0
RUN npm install -g pg-native@1.10.0
RUN npm install -g change-case-object@2.0.0
RUN npm install -g request@2.71.0
RUN npm install -g koa-multer@0.0.2
RUN npm install -g request@2.72.0

WORKDIR /usr/src/app
COPY bashrc /root/.bashrc
ENV NODE_PATH /usr/local/lib/node_modules

EXPOSE 80
