#!/usr/bin/env groovy

pipeline {

    agent any

//    parameters {
//        choice(name: 'SERVICE', choices: ['Buslogic', 'Origination'], description: 'Select a service to deploy')
//        booleanParam(defaultValue: false, name: 'KE', description: '')
//        booleanParam(defaultValue: false, name: 'UG', description: '')
//        booleanParam(defaultValue: false, name: 'ZM', description: '')
//    }

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
            agent any
            when { branch 'develop'}
            steps {
                echo '>>> Publish artifacts'
//    			script {
//    				sh 'chmod +x gradlew'
//    				sh './gradlew publish'
//    			}
            }
        }

        stage('TEST Approval') {
            when {
                branch 'develop'
            }
            input {
                message 'Please select parameters for TEST deployment/approval'
                parameters {
                        booleanParam(defaultValue: false, name: 'Buslogic', description: '')
                        booleanParam(defaultValue: false, name: 'Origination', description: '')
                        booleanParam(defaultValue: false, name: 'KE', description: '')
                        booleanParam(defaultValue: false, name: 'UG', description: '')
                        booleanParam(defaultValue: false, name: 'ZM', description: '')
                    }
            }

            steps {
                script {
                    echo '>>> TEST Deployment started'

                    if (KE == 'true' && Buslogic == 'true') {
                        TASK = "deployBuslogicKeDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (KE == 'true' && Origination == 'true') {
                        TASK = "deployOriginationKeDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    if (UG == 'true' && Buslogic == 'true') {
                        TASK = "deployBuslogicUgDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (UG == 'true' && Origination == 'true') {
                        TASK = "deployOriginationUgDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    if (ZM == 'true' && Buslogic == 'true') {
                        TASK = "deployBuslogicZmDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (ZM == 'true' && Origination == 'true') {
                        TASK = "deployOriginationZmDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    echo '>>> Deployment finished'
                }
            }
        }

        stage('Release Approval') {
            when {branch 'develop'}
            input {message 'Do you want to approve a release?'}
            steps {
                script {
                    echo '>>> Starting release'
//                    sh './gradlew releaseStart'
//                    sh './gradlew releaseFinish'
                    echo '>>> Release completed'
                }

            }
        }

        stage('PROD Approval') {
            when {branch 'master'}
            input {
                message 'Please select parameters for PROD deployment/approval'
                id 'envId'
                ok 'Submit'
                submitterParameter 'approverId'
                parameters {
                    booleanParam(defaultValue: false, name: 'Buslogic', description: '')
                    booleanParam(defaultValue: false, name: 'Origination', description: '')
                    booleanParam(defaultValue: false, name: 'KE', description: '')
                    booleanParam(defaultValue: false, name: 'UG', description: '')
                    booleanParam(defaultValue: false, name: 'ZM', description: '')
                }
            }
            steps {

                script {
                    if (KE == 'true' && Buslogic == 'true') {
                        TASK = "deployBuslogicKeProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (KE == 'true' && Origination == 'true') {
                        TASK = "deployOriginationKeProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    if (UG == 'true' && Buslogic == 'true') {
                        TASK = "deployBuslogicUgProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (UG == 'true' && Origination == 'true') {
                        TASK = "deployOriginationUgProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    if (ZM == 'true' && Buslogic == 'true') {
                        TASK = "deployBuslogicZmProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (ZM == 'true' && Origination == 'true') {
                        TASK = "deployOriginationZmProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
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