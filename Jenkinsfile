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
                sh 'gradle clean test'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}