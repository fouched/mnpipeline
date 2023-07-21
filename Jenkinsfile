#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        gradle '7.6.1'
    }
    stages {
        stage('Build') {
            steps {
                sh 'gradle clean build'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                sh 'gradle test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
                sh 'ssh test@ubuntu "/home/ubuntu/deploy/doPrep.sh"'
                echo "Backed up previous deploy, copying new file from: ${WORKSPACE}/build/libs/*-all.jar"
                sh "scp ${WORKSPACE}/build/libs/*-all.jar test@Ubuntu:/home/ubuntu/deploy/mnpipeline.jar"
                echo 'JAR copied to server....'
                sh 'ssh test@ubuntu "/home/ubuntu/deploy/doDeploy.sh"'
                echo 'Server started....'
            }
        }
    }
}