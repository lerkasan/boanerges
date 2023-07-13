#!/bin/bash
set -xe

cd /home/ubuntu/app
docker compose down

/opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -m ec2 -a stop