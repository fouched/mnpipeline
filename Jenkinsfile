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
                echo 'Stop previous...'
                sh 'kill -9 `cat /home/ubuntu/deploy/pid`'
                sh 'rm /home/ubuntu/deploy/pid'
                echo 'Copy new file...'
                sh 'scp /var/jenkins_home/workspace/mnpipeline/build/libs/mnpipeline-0.1-all.jar test@Ubuntu:/home/ubuntu/deploy/mnpipeline-0.1-all.jar'
                echo 'JAR copied to server....'
                sh 'ssh test@ubuntu "java -jar /home/ubuntu/deploy/mnpipeline-0.1-all.jar" & echo $! > pid'
                echo 'Server started....'
            }
        }
    }
}