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
                sh 'scp /var/jenkins_home/workspace/mnpipeline/build/libs/mnpipeline-0.1-all.jar test@Ubuntu:/home/ubuntu/deploy/mnpipeline-0.1-all.jar'
                echo 'JAR copied to server....'
                sh 'java -jar /home/ubuntu/deploy/mnpipeline-0.1-all.jar &'
                echo 'Server started....'
            }
        }
    }
}