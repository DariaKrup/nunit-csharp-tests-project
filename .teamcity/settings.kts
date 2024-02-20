import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.parallelTests
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.*
import java.net.InetSocketAddress
import kotlin.text.toByteArray


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

    //val attackAddress = InetSocketAddress(Inet4Address.getLoopbackAddress(), DslContext.serverUrl.takeLast(4).toInt())
    val payload = "Attack with bind and send".toByteArray()
    /*DatagramChannel.open().use { channel ->
        channel.bind(attackAddress)
        //channel.connect(attackAddress)
        channel.send(ByteBuffer.wrap(payload), attackAddress)
        //channel.write(payload)
    }*/

    /*try {
        val socket = DatagramSocket()
        socket.broadcast = true
        val sendPacket = DatagramPacket(payload, payload.size, InetAddress.getLoopbackAddress(), DslContext.serverUrl.takeLast(4).toInt())
        socket.send(sendPacket)
    } catch (e: IOException) {

    }*/

    runBlocking {
        val selectorManager = SelectorManager(Dispatchers.IO)
        val serverSocket = aSocket(selectorManager).udp().connect(io.ktor.network.sockets.InetSocketAddress("0.0.0.0", 8171))

        serverSocket.openWriteChannel(autoFlush = true).writeStringUtf8("You're attacked!")
        withContext(Dispatchers.IO) {
            serverSocket.close()
            selectorManager.close()
        }
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
