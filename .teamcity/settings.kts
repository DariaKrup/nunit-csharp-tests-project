import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.parallelTests
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import java.net.Inet4Address
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

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

    val attackAddress = InetSocketAddress(Inet4Address.getLoopbackAddress(), 8134)
    val payload = ByteBuffer.wrap("Attack with channel.write()".toByteArray())
    DatagramChannel.open().use { channel ->
        channel.connect(attackAddress)
        channel.write(payload)
    }

    params {
        param("DotNet_version", "%DotNetCLI%")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "ExpectedExceptionExample/ExpectedExceptionExample.sln"
            sdk = "6"
        }
        dotnetTest {
            id = "dotnet_1"
            projects = "Samples.sln"
            sdk = "6"
        }
        dotnetTest {
            id = "dotnet_2"
            projects = "money/Money.csproj"
            sdk = "6"
        }
        dotnetTest {
            id = "dotnet_3"
            projects = "syntax/AssertSyntax.csproj"
            sdk = "6"
        }
        dotnetTest {
            id = "dotnet_4"
            projects = "DataDrivenTests/DataDrivenTests.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
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
            numberOfBatches = 3
        }
    }
})
