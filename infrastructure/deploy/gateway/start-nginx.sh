#!/usr/bin/env bash

echo "## START NGINX APP"
envsubst "`printf '${%s} ' $(bash -c "compgen -A variable")`" < /etc/nginx/conf.d/conf.nginx > /etc/nginx/conf.d/default.conf
exec nginx -g "daemon off;"
echo "## START WHILE"
while :; do sleep 6h & wait $${!}; nginx -s reload; done & nginx -g \"daemon off;\"
echo "## SCRIPT END"
#/bin/sh -c 'while :; do sleep 6h & wait $${!}; nginx -s reload; done & nginx -g \"daemon off;\"'
