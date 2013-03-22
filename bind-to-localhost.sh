#!/usr/bin/env bash
cp /etc/hosts /etc/hosts.bak
echo "127.0.0.1	football-analytics.com" >> /etc/hosts
echo "127.0.0.1	www.football-analytics.com" >> /etc/hosts
