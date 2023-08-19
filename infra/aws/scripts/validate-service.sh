#!/bin/bash
set -x

NUMBER_OF_ATTEMPTS=10
SLEEP_TIME=3

FRONTEND_HEALTH_URL=http://localhost
BACKEND_HEALTH_URL=http://localhost:8080/api/health

# Ensure Tomcat is running by making an HTTPS GET request to the default page.
# Don't try and verify the certificate; use the --insecure flag.
for i in $(seq 1 $NUMBER_OF_ATTEMPTS);
do
  FRONTEND_HTTP_CODE=$(curl --insecure --write-out '%{http_code}' -o /dev/null -m 10 -q -s $FRONTEND_HEALTH_URL)
  if [ "$FRONTEND_HTTP_CODE" == "200" ]; then
    echo "Frontend is running."
  fi
  BACKEND_HTTP_CODE=$(curl --insecure --write-out '%{http_code}' -o /dev/null -m 10 -q -s $BACKEND_HEALTH_URL)
  if [ "$BACKEND_HTTP_CODE" == "200" ]; then
    echo "Backend is running."
  fi
  if [[ "$FRONTEND_HTTP_CODE" == "200" && "$BACKEND_HTTP_CODE" == "200" ]]; then
    exit 0
  fi
  echo "Attempt to curl frontend endpoint returned HTTP Code $FRONTEND_HTTP_CODE."
  echo "Attempt to curl backend endpoint returned HTTP Code $BACKEND_HTTP_CODE."
  echo " Backing off and retrying."
  sleep $SLEEP_TIME
done
echo "Frontend or backend did not come up after expected time. Failing."
exit 1