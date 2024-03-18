import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.DotCoverBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.dotCover
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest
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

version = "2023.11"

project {

    buildType(Build)
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
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
            }
        }
        dotnetTest {
            id = "dotnet_1"
            projects = "syntax/AssertSyntax.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
            }
        }
        dotCover {
            name = "Merge snapshots from the previous build steps + add one"
            id = "Merge_snapshots_from_the_previous_build_steps_add_one"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
            executable = "/usr/bin/dotnet"
            commandLineArguments = """test %teamcity.build.checkoutDir%\DataDrivenTests\DataDrivenTests.csproj"""
            dockerImage = "mcr.microsoft.com/dotnet/sdk:6.0"
            dockerImagePlatform = DotCoverBuildStep.ImagePlatform.Linux
            dockerPull = true
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})
