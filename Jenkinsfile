#!/usr/bin/env groovy

pipeline {

    agent any

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
            when { branch 'develop'}
            steps {
                echo '>>> Publish artifacts'
//    			script {
//    				sh 'chmod +x gradlew'
//    				sh './gradlew publish'
//    			}
            }
        }

        stage('Feature Approval') {
            when {
                beforeInput true
                branch 'feature/*'
            }

            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    script {
                        def userInput = input message: 'Deployment options:',
                                        parameters: [
                                                booleanParam(defaultValue: false, name: 'Buslogic', description: ''),
                                                booleanParam(defaultValue: false, name: 'Origination', description: ''),
                                                booleanParam(defaultValue: false, name: 'KE', description: ''),
                                                booleanParam(defaultValue: false, name: 'UG', description: ''),
                                                booleanParam(defaultValue: false, name: 'ZM', description: '')
                                        ]

                        echo ">>> Feature branch deployment started"

                        if (userInput.KE && userInput.Buslogic) {
                            TASK = "deployBuslogicKeDev"
                            echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                        }
                        if (userInput.KE && userInput.Origination) {
                            TASK = "deployOriginationKeDev"
                            echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                        }

                        if (userInput.UG && userInput.Buslogic) {
                            TASK = "deployBuslogicUgDev"
                            echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                        }
                        if (userInput.UG && userInput.Origination) {
                            TASK = "deployOriginationUgDev"
                            echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                        }

                        if (userInput.ZM && userInput.Buslogic) {
                            TASK = "deployBuslogicZmDev"
                            echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                        }
                        if (userInput.ZM && userInput.Origination) {
                            TASK = "deployOriginationZmDev"
                            echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                        }

                        echo '>>> Deployment finished'
                    }
                }
            }
        }

        stage('Test Approval') {
            when {
                beforeInput true
                branch 'develop'
            }

            steps {
                script {
                    def userInput = input message: 'Deployment options:',
                            parameters: [
                                    booleanParam(defaultValue: false, name: 'Buslogic', description: ''),
                                    booleanParam(defaultValue: false, name: 'Origination', description: ''),
                                    booleanParam(defaultValue: false, name: 'KE', description: ''),
                                    booleanParam(defaultValue: false, name: 'UG', description: ''),
                                    booleanParam(defaultValue: false, name: 'ZM', description: '')
                            ]

                    echo ">>> Develop branch deployment started"

                    if (userInput.KE && userInput.Buslogic) {
                        TASK = "deployBuslogicKeDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (userInput.KE && userInput.Origination) {
                        TASK = "deployOriginationKeDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    if (userInput.UG && userInput.Buslogic) {
                        TASK = "deployBuslogicUgDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (userInput.UG && userInput.Origination) {
                        TASK = "deployOriginationUgDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    if (userInput.ZM && userInput.Buslogic) {
                        TASK = "deployBuslogicZmDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (userInput.ZM && userInput.Origination) {
                        TASK = "deployOriginationZmDev"
                        echo ">>> Deploying to TEST using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    echo '>>> Deployment finished'
                }
            }
        }

        stage('Release Approval') {
            when {
                beforeInput true
                branch 'develop'
            }
            input {
                message 'Do you want to do a release?'
                parameters {
                    choice(name: 'Release', choices: 'Yes\nNo', description: '')
                }
            }

            steps {
                script {
                    if (Release == 'Yes') {
                        echo '>>> Starting release'
//                        sh './gradlew releaseStart'
//                        sh './gradlew releaseFinish'
                        echo '>>> Release completed'
                    } else {
                        echo '>>> Release not required'
                    }
                }

            }
        }

        stage('Prod Approval') {
            when {
                beforeInput true
                branch 'master'
            }

            steps {
                script {
                    def userInput = input message: 'Deployment options:',
                            parameters: [
                                    booleanParam(defaultValue: false, name: 'Buslogic', description: ''),
                                    booleanParam(defaultValue: false, name: 'Origination', description: ''),
                                    booleanParam(defaultValue: false, name: 'KE', description: ''),
                                    booleanParam(defaultValue: false, name: 'UG', description: ''),
                                    booleanParam(defaultValue: false, name: 'ZM', description: '')
                            ]

                    echo ">>> Master branch deployment started"

                    if (userInput.KE && userInput.Buslogic) {
                        TASK = "deployBuslogicKeProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (userInput.KE && userInput.Origination) {
                        TASK = "deployOriginationKeProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    if (userInput.UG && userInput.Buslogic) {
                        TASK = "deployBuslogicUgProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (userInput.UG && userInput.Origination) {
                        TASK = "deployOriginationUgProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }

                    if (userInput.ZM && userInput.Buslogic) {
                        TASK = "deployBuslogicZmProd"
                        echo ">>> Deploying to PROD using task: ${TASK}"
//                        sh "./gradlew ${TASK}"
                    }
                    if (userInput.ZM && userInput.Origination) {
                        TASK = "deployOriginationZmProd"
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