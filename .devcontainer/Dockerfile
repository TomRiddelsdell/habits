FROM mcr.microsoft.com/devcontainers/java:0-17

ENV NODE_VERSION=20.6.0
ENV NVM_DIR=/workspaces/habits/.nvm
RUN mkdir -p $NVM_DIR
RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash
RUN . "$NVM_DIR/nvm.sh" && nvm install ${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm use v${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm alias default v${NODE_VERSION}
ENV PATH="${NVM_DIR}/versions/node/v${NODE_VERSION}/bin/:${PATH}"
RUN node --version
RUN npm --version

RUN . "$NVM_DIR/nvm.sh" && nvm install ${NODE_VERSION}
RUN npm install -g @aws-amplify/cli && amplify help

#RUN mkdir -p /workspaces/habits/aws-bin && \
#    curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "/workspaces/habits/aws-bin/awscliv2.zip" && \
#    unzip "/workspaces/habits/aws-bin/awscliv2.zip" -d "/workspaces/habits/aws-bin/" 

#RUN sudo "/workspaces/habits/aws-bin/aws/install" -i "/workspaces/habits/aws-bin/aws-cli" 
