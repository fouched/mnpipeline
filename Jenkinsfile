#!/usr/bin/env groovy

pipeline {

    agent any

    parameters {
        choice(name: 'SERVICE', choices: ['Buslogic', 'Origination'], description: 'Select a service to deploy')
        booleanParam(defaultValue: false, name: 'KE', description: '')
        booleanParam(defaultValue: false, name: 'UG', description: '')
        booleanParam(defaultValue: false, name: 'ZM', description: '')
    }

//    tools {
//        jdk 'Coretto 17'
//    }

    triggers { pollSCM('H/2 * * * *') } // every 2 minutes

    options {
        disableConcurrentBuilds(abortPrevious: true)
        skipStagesAfterUnstable()
    }

    environment {
//        PROJECT_VERSION = "${readProperties(file: "${WORKSPACE}/gradle.properties").version}"
        PROJECT_VERSION = "6.0.1"
    }

    stages {

        stage('CommitDetails') {
            steps {
                script {
                    env.GIT_COMMIT_MSG = sh (script: 'git log -1 --pretty=%B ${GIT_COMMIT}', returnStdout: true).trim()
                    env.GIT_AUTHOR = sh (script: 'git log -1 --pretty=%cn ${GIT_COMMIT}', returnStdout: true).trim()
                }
            }
        }

        stage('Build') {
            steps {
                echo ">>> Build version: ${env.PROJECT_VERSION}  ${env.JOB_BASE_NAME}"
                script {
                    try {
                        sh 'chmod +x gradlew'
                        sh './gradlew build'
                    } finally {
                        junit '**/build/test-results/test/*.xml'
                        archiveArtifacts artifacts: '**/build/test-results/test/*.xml', allowEmptyArchive: true
                    }
                }
            }
        }

        stage('Publish artifacts') {
            when { anyOf { branch 'master'; branch 'develop' } }
            steps {
                echo '>>> Publish artifacts'
//    			script {
//    				sh 'chmod +x gradlew'
//    				sh './gradlew publish'
//    			}
            }
        }

        stage('Deploy Test') {
            when {branch 'develop'}
            steps {
                script {
                    echo '>>> TEST Deployment started'

                    if (params.KE) {
                        TASK = "deploy${params.SERVICE}KeDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (params.UG) {
                        TASK = "deploy${params.SERVICE}UgDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (params.ZM) {
                        TASK = "deploy${params.SERVICE}ZmDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    echo '>>> Deployment finished'
                }
            }
        }

        stage('Prod Approval') {
            when {branch 'master'}
            input {
                message: 'Do you want to approve the deployment?'
            }
            steps {
                echo ">>> Approval granted"
            }
        }

        stage('Release') {
            when {branch 'master'}
            steps {
                script {
                    echo '>>> Starting release'
//                    sh './gradlew releaseStart'
//                    sh './gradlew releaseFinish'
                    echo '>>> Release completed'
                }
            }
        }

        stage('Prod Deploy') {
            when {branch 'master'}
            steps {
                script {
                    echo '>>> PROD Deployment started'

                    if (params.KE) {
                        TASK = "deploy${params.KE}KeProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (params.UG) {
                        TASK = "deploy${params.UG}UgProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (params.ZM) {
                        TASK = "deploy${params.ZM}ZmProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    echo '>>> Deployment finished'

                }
            }
        }
    }

	post {
		success {
            echo '>>> Sending success msg to Slack channel'
//			slackSend(
//					channel: "#unibos-build",
//					token: "Migrated slack token",
//					color: "good",
//                    message: """
//						Job: ${env.JOB_BASE_NAME} version ${env.PROJECT_VERSION} success
//						Comitted by: ${env.GIT_AUTHOR}
//						Last commit message: ${env.GIT_COMMIT_MSG}
//					"""
//			)
		}
		failure {
            echo '>>> Sending failure msg to Slack channel'
//			slackSend(
//					channel: "#unibos-build",
//					token: "Migrated slack token",
//					color: "danger",
//                    message: """
//						Job: ${env.JOB_BASE_NAME} version ${env.PROJECT_VERSION} failed
//						Comitted by: ${env.GIT_AUTHOR}
//						Last commit message: ${env.GIT_COMMIT_MSG}
//					"""
//			)
		}
	}
}