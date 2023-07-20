#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        gradle '7.6.1'
    }
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out..'
            }
        }
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
                echo 'Copy new file...'
                sh 'scp /var/jenkins_home/workspace/mnpipeline/build/libs/*-all.jar test@Ubuntu:/home/ubuntu/deploy/mnpipeline.jar'
                echo 'JAR copied to server....'
                sh 'ssh test@ubuntu "/home/ubuntu/deploy/doDeploy.sh" &'
                echo 'Server started....'
            }
        }
    }
}