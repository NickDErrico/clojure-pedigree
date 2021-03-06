#!/usr/bin/env sh

# aliases
alias dcu="docker-compose up"
alias dcud="docker-compose up -d"
alias dcp="docker-compose ps"
alias dcr="docker-compose rm"
alias dcl="docker-compose logs -f"
alias dcb="docker-compose build"
alias gs="docker-compose run --rm dev.frontend gulp setup"
alias gd="docker-compose run --rm dev.frontend gulp dev"
alias gp="docker-compose run --rm dev.frontend gulp prod"
alias gw="docker-compose run --rm -T dev.frontend gulp watch"

function attach {
    docker exec -it $(docker-compose ps | grep "$1" | awk '{print $1}') bash
}

function kill_and_run {
    docker-compose kill $1
    docker-compose up $1
}

function svc.web {
    kill_and_run "svc.web"
}

function svc.person {
    kill_and_run "svc.person"
}

function svc.tree {
    kill_and_run "svc.tree"
}

function svc.api-tree {
    kill_and_run "svc.api-tree"
}

function backup {
    docker-compose -f docker-compose.yml -f docker-compose.backup.yml run backup /backup.sh
}

function restore {
    docker-compose -f docker-compose.yml -f docker-compose.backup.yml run backup /restore.sh
}

function dev.frontend {
    docker-compose run --rm dev.frontend /bin/bash
}
