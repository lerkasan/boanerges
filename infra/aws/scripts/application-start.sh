#!/bin/bash
set -xe

APPLICATION_NAME="boanerges"
DEPLOYMENT_GROUP_NAME="production"
BACKEND_IMAGE_NAME="$APPLICATION_NAME-backend"
FRONTEND_IMAGE_NAME="$APPLICATION_NAME-frontend"

REGION=$(curl -s http://169.254.169.254/latest/meta-data/placement/region)

PRIVATE_IP=$(curl -s http://169.254.169.254/latest/meta-data/local-ipv4)
#PRIVATE_IP=$([ "$PRIVATE_IP" == "" ] && echo "127.0.0.1" || echo $PRIVATE_IP)

DEPLOYMENT_ID=$(aws deploy list-deployments --application-name $APPLICATION_NAME --deployment-group-name $DEPLOYMENT_GROUP_NAME --region $REGION --include-only-statuses "InProgress" --query "deployments[0]" --output text --no-paginate)

GITHUB_TOKEN=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_GITHUB_TOKEN --with-decryption --query Parameter.Value --output text)
COMMIT_SHA=$(aws deploy get-deployment --deployment-id $DEPLOYMENT_ID --query "deploymentInfo.revision.gitHubLocation.commitId" --output text)
REPOSITORY=$(aws deploy get-deployment --deployment-id $DEPLOYMENT_ID --query "deploymentInfo.revision.gitHubLocation.repository" --output text)
GITHUB_USER=$(echo $REPOSITORY | cut -d "/" -f 1)

DB_HOST=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_DB_HOST --with-decryption --query Parameter.Value --output text)
DB_NAME=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_DB_NAME --with-decryption --query Parameter.Value --output text)
DB_USERNAME=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_DB_USERNAME --with-decryption --query Parameter.Value --output text)
DB_PASSWORD=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_DB_PASSWORD --with-decryption --query Parameter.Value --output text)

TOKEN=$(curl -u $GITHUB_USER:$GITHUB_TOKEN https://ghcr.io/token\?scope\="repository:$REPOSITORY:pull" | jq -r .token)
BACKEND_MANIFESTS_HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" -H "Authorization: Bearer $TOKEN" https://ghcr.io/v2/$REPOSITORY/$BACKEND_IMAGE_NAME/manifests/sha-$COMMIT_SHA)
BACKEND_MANIFESTS_RESPONSE=$(curl -w "%{http_code}" -H "Authorization: Bearer $TOKEN" https://ghcr.io/v2/$REPOSITORY/$BACKEND_IMAGE_NAME/manifests/sha-$COMMIT_SHA)

FRONTEND_MANIFESTS_HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" -H "Authorization: Bearer $TOKEN" https://ghcr.io/v2/$REPOSITORY/$FRONTEND_IMAGE_NAME/manifests/sha-$COMMIT_SHA)
FRONTEND_MANIFESTS_RESPONSE=$(curl -w "%{http_code}" -H "Authorization: Bearer $TOKEN" https://ghcr.io/v2/$REPOSITORY/$FRONTEND_IMAGE_NAME/manifests/sha-$COMMIT_SHA)

FRONTEND_TAG=$([ "$FRONTEND_MANIFESTS_HTTP_CODE" == 200 ] && echo "sha-$COMMIT_SHA" || echo "latest")
BACKEND_TAG=$([ "$BACKEND_MANIFESTS_HTTP_CODE" == 200 ] && echo "sha-$COMMIT_SHA" || echo "latest")

# FRONTEND_TAG=$([ $REPOSITORY == "lerkasan/boanerges-frontend" ] && echo "sha-$COMMIT_SHA" || echo "latest")
# BACKEND_TAG=$([ $REPOSITORY == "lerkasan/boanerges-backend" ] && echo "sha-$COMMIT_SHA" || echo "latest")

AWS_ACCESS_KEY_ID=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_AWS_ACCESS_KEY_ID --with-decryption --query Parameter.Value --output text)
AWS_SECRET_ACCESS_KEY=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_AWS_SECRET_ACCESS_KEY --with-decryption --query Parameter.Value --output text)
AWS_STS_ROLE_ARN=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_AWS_STS_ROLE_ARN --with-decryption --query Parameter.Value --output text)

DEEPGRAM_API_KEY=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_DEEPGRAM_API_KEY --with-decryption --query Parameter.Value --output text)
DEEPGRAM_PROJECT_ID=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_DEEPGRAM_PROJECT_ID --with-decryption --query Parameter.Value --output text)
OPENAI_API_KEY=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_OPENAI_API_KEY --with-decryption --query Parameter.Value --output text)

SMTP_USERNAME=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_SMTP_USERNAME --with-decryption --query Parameter.Value --output text)
SMTP_PASSWORD=$(aws ssm get-parameter --region $REGION --name ${APPLICATION_NAME^^}_SMTP_PASSWORD --with-decryption --query Parameter.Value --output text)

export DB_HOST=$DB_HOST
export DB_NAME=$DB_NAME
export DB_USERNAME=$DB_USERNAME
export DB_PASSWORD=$DB_PASSWORD
export FRONTEND_TAG=$FRONTEND_TAG
export BACKEND_TAG=$BACKEND_TAG
export PRIVATE_IP=$PRIVATE_IP

export AWS_REGION=${REGION}
export AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
export AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY
export AWS_STS_ROLE_ARN=$AWS_STS_ROLE_ARN
export DEEPGRAM_API_KEY=$DEEPGRAM_API_KEY
export DEEPGRAM_PROJECT_ID=$DEEPGRAM_PROJECT_ID
export OPENAI_API_KEY=$OPENAI_API_KEY
export SMTP_USERNAME=$SMTP_USERNAME
export SMTP_PASSWORD=$SMTP_PASSWORD

echo $GITHUB_TOKEN | docker login ghcr.io -u $GITHUB_USER --password-stdin

cd /home/ubuntu/app

docker compose up -d
