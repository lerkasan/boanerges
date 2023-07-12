#!/bin/sh
set -xe

# PRIVATE_IP=$(curl -s http://169.254.169.254/latest/meta-data/local-ipv4)
# export PRIVATE_IP=${PRIVATE_IP}
# echo "Private IP: ${PRIVATE_IP}"

# sed -i "s/%PRIVATE_IP%/${PRIVATE_IP}/g; s/%DOMAIN_NAME%/${DOMAIN_NAME}/g" /etc/nginx/conf.d/default.conf
# sed -i "s/%DOMAIN_NAME%/${DOMAIN_NAME}/g" /etc/nginx/conf.d/default.conf