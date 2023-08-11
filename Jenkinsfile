#!/usr/bin/env groovy

pipeline {

    agent any

    parameters {
        choice(name: 'SERVICE', choices: ['Buslogic', 'Origination'], description: 'Select a service to deploy')
        choice(name: 'COUNTRY', choices: ['Ke', 'Ug', 'Zm'], description: 'Select a country to deploy to')
    }

//    tools {
//        jdk 'Coretto 17'
//    }

    options {
        disableConcurrentBuilds(abortPrevious: true)
        skipStagesAfterUnstable()
    }

    environment {
//        PROJECT_VERSION = "${readProperties(file: "${WORKSPACE}/gradle.properties").version}"
        PROJECT_VERSION = "6.0.1"
        TASK = "deploy${params.SERVICE}${params.COUNTRY}"
    }

    stages {

        stage('Build') {
            steps {
                echo ">>> Build version: ${env.PROJECT_VERSION}"
            }
//			script {
//				try {
//					sh 'chmod +x gradlew'
//					sh './gradlew build'
//				} finally {
//					junit '**/build/test-results/test/*.xml'
//					archive includes: '**/build/test-results/test/*.xml'
//				}
//			}
//			archive includes: 'build/reports/changelog/**'
        }

        stage('Publish artifacts') {

            when { anyOf { branch 'master'; branch 'develop'; } }

            steps {
                echo '>>> Publish artifacts'
            }

//    		steps {
//    			script {
//    				sh 'chmod +x gradlew'
//    				sh './gradlew publish'
//    			}
//    			publishHTML([
//                    allowMissing: false,
//                    alwaysLinkToLastBuild: false,
//                    reportDir: 'build/reports/changelog',
//                    reportFiles: 'CHANGELOG.html',
//                    reportName: 'Changelog',
//                    keepAll: false
//    			])
//    		}
        }

        stage('Deploy Test') {
            when {
                branch 'develop'
            }
            steps {
                script {
                    TASK += 'Dev'
                    echo ">>> Deploying to Test using task: ${TASK}"

                    sh 'chmod +x gradlew'
                }
            }
        }

        stage('Deploy Prod') {
            when {
                branch 'master'
            }
            steps {
                script {
                    TASK += 'Prod'
                    echo ">>> Deploying to Test using task: ${TASK}"

                    sh 'chmod +x gradlew'
                    echo '>>> Starting release'
//                    sh './gradlew releaseStart'
//                    sh './gradlew releaseFinish'
                    echo '>>> Release completed'

                }
            }
        }
    }

//	post {
//		success {
//			slackSend(
//					channel: "#unibos-build",
//					token: "Migrated slack token",
//					color: "good",
//					message: "local-services ${BRANCH_NAME} version ${env.PROJECT_VERSION} success"
//			)
//		}
//		failure {
//			slackSend(
//					channel: "#unibos-build",
//					token: "Migrated slack token",
//					color: "danger",
//					message: "local-services ${BRANCH_NAME} version ${env.PROJECT_VERSION} failed"
//			)
//		}
//	}
}