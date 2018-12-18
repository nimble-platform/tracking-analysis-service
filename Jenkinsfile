#!/usr/bin/env groovy

node('nimble-jenkins-slave') {

    // -----------------------------------------------
    // --------------- Staging Branch ----------------
    // -----------------------------------------------
    if (env.BRANCH_NAME == 'staging') {

        stage('Clone and Update') {
            git(url: 'https://github.com/nimble-platform/tracking-analysis-service.git', branch: env.BRANCH_NAME)
        }

        stage('Build Java') {
            sh 'mvn clean package -DskipTests'
        }

        stage('Build Docker') {
            sh 'mvn docker:build -P docker -DdockerImageTag=staging'
        }

        stage('Push Docker') {
            sh 'docker push nimbleplatform/tracking-analysis-service:staging'
        }

        stage('Deploy') {
            sh 'ssh staging "cd /srv/nimble-staging/ && ./run-staging.sh restart-single tracking-analysis-service"'
        }
    }

    // -----------------------------------------------
    // ---------------- Master Branch ----------------
    // -----------------------------------------------
    if (env.BRANCH_NAME == 'master') {

        stage('Clone and Update') {
            git(url: 'https://github.com/nimble-platform/tracking-analysis-service.git', branch: env.BRANCH_NAME)
        }

        stage('Build Java') {
            sh 'mvn clean package -DskipTests'
        }
    }

    // -----------------------------------------------
    // ---------------- Release Tags -----------------
    // -----------------------------------------------
    if( env.TAG_NAME ==~ /^\d+.\d+.\d+$/) {

        stage('Clone and Update') {
            git(url: 'https://github.com/nimble-platform/tracking-analysis-service.git', branch: 'master')
        }

        stage('Set version') {
            sh 'mvn versions:set -DnewVersion=' + env.TAG_NAME
        }

        stage('Build Java') {
            sh 'mvn clean package -DskipTests'
        }

        stage('Build Docker') {
            sh 'mvn docker:build -P docker'
        }

        stage('Push Docker') {
            sh 'docker push nimbleplatform/data-aggregation-service:' + env.TAG_NAME
            sh 'docker push nimbleplatform/data-aggregation-service:latest'
        }

        stage('Deploy') {
            sh 'ssh nimble "cd /data/deployment_setup/prod/ && sudo ./run-prod.sh restart-single tracking-analysis-service"'
        }
    }
}
