import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.parallelTests
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest
import jetbrains.buildServer.configs.kotlin.projectFeatures.kubernetesExecutor
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.12"

project {

    buildType(Build)

    features {
        kubernetesExecutor {
            id = "PROJECT_EXT_17"
            connectionId = "PROJECT_EXT_2"
            profileName = "Kubernetes Executor"
            templateContainer = """
                apiVersion: v1
                kind: Pod
                spec:
                  containers:
                      # This is a template container, where all the presented settings will be used for the real containers generated by TeamCity
                      # Please refrain from adding new containers, as those can't be added to your build
                      # To modify the volume used to run the build, add a volume with the name agent-volume
                    - name: template-container
            """.trimIndent()
            buildsLimit = "2"
            enabled = false
            description = "Executor for this Project"
            serverURL = "http://10.128.93.57:8281/"
        }
        kubernetesExecutor {
            id = "PROJECT_EXT_172"
            connectionId = "PROJECT_EXT_2"
            profileName = "K8S"
            containerParameters = "teamcity.agent.jvm.os.name=Linux"
            templateName = "ubuntu-agent"
        }
    }
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "money/Money.csproj"
            sdk = "6"
            coverage = dotcover {
            }
        }
        dotnetTest {
            id = "dotnet_1"
            projects = "syntax/AssertSyntax.csproj"
            sdk = "6"
            coverage = dotcover {
            }
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
        parallelTests {
            enabled = false
            numberOfBatches = 2
        }
    }

    requirements {
        exists("k8s")
    }
})
