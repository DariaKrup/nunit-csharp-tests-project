import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.DotCoverBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.DotnetTestStep
import jetbrains.buildServer.configs.kotlin.buildSteps.dotCover
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetBuild
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetCustom
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetRestore
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

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

    vcsRoot(HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster1)

    buildType(id202334eapWindowsAgentNetDotCover)
    buildType(id20212NetDotCover)
    buildType(id20224CrossPlatformNetDotCover)
    buildType(id20241eapWindowsAgentNetDotCover)
    buildType(id20191WindowsNetDotCover)
    buildType(id20232WindowsNetDotCover)
    buildType(Uploaded202224WindowsNetDotCover)
    buildType(id20193CrossPlatformNetDotCover)
    buildType(id20241eapLinuxAgentNetDotCover)
    buildType(id201730WindowsNetTestDotCover)
    buildType(id20234eapLinuxAgentNetDotCover)
    buildType(id20241eapDockerLinuxAgentNetDotCover)
    buildType(BundledWindowsFramework48NetDotCover)
    buildType(id202433WindowsAgentNetDotCover)
    buildType(CustomPath202224WindowsNetDotCover)
    buildType(id201730WindowsNetDotCover)
}

object id201730WindowsNetDotCover : BuildType({
    id("201730WindowsNetDotCover")
    name = "2017.3.0 Windows: .Net + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetRestore {
            id = "dotnet_1"
            enabled = false
            projects = "Samples.sln"
        }
        dotnetCustom {
            id = "dotnet"
            args = "new xunit -n ProjectNet --force"
            sdk = "6.0"
            param("dotNetCoverage.dotCover.home.path", "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2017.3.0%")
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2017.3.0%"
            executable = "%DotNetCLI_Path%"
            commandLineArguments = "test ./ProjectNet"
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

    requirements {
        equals("teamcity.agent.jvm.os.name", "Windows Server 2022")
    }
})

object id201730WindowsNetTestDotCover : BuildType({
    id("201730WindowsNetTestDotCover")
    name = "2017.3.0 Windows: .Net (test) + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "DataDrivenTests/DataDrivenTests.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2017.3.0%"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2017.3.0%"
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

object id20191WindowsNetDotCover : BuildType({
    id("20191WindowsNetDotCover")
    name = "2019.1.3 Windows: .Net + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "DataDrivenTests/DataDrivenTests.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2019.1.3%"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2019.1.3%"
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

object id20193CrossPlatformNetDotCover : BuildType({
    id("20193CrossPlatformNetDotCover")
    name = "2019.3 Cross-Platform: .Net + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "DataDrivenTests/DataDrivenTests.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.DotNetCliTool.2019.3.3%"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.DotNetCliTool.2019.3.3%"
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

object id20212NetDotCover : BuildType({
    id("20212NetDotCover")
    name = "2021.2 Cross-Platform: .Net + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "DataDrivenTests/DataDrivenTests.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.DotNetCliTool.2021.2.3%"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.DotNetCliTool.2021.2.3%"
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

object id20224CrossPlatformNetDotCover : BuildType({
    id("20224CrossPlatformNetDotCover")
    name = "2022.4 Cross-Platform: .Net + dotCover"

    allowExternalStatus = true

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "DataDrivenTests/DataDrivenTests.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.DotNetCliTool.2022.2.4%"
            }
            dockerImage = "mcr.microsoft.com/dotnet/sdk:6.0"
            dockerImagePlatform = DotnetTestStep.ImagePlatform.Linux
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.DotNetCliTool.2022.2.4%"
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

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
    }
})

object id20232WindowsNetDotCover : BuildType({
    id("20232WindowsNetDotCover")
    name = "2023.2 Windows: .Net + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "DataDrivenTests/DataDrivenTests.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2023.2.3%"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2023.2.3%"
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

object id202334eapWindowsAgentNetDotCover : BuildType({
    id("202334eapWindowsAgentNetDotCover")
    name = "2023.3.4 EAP (Windows agent): .Net + dotCover"

    vcs {
        root(HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster1)
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "money/Money.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2023.3.4%"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2023.3.4%"
            executable = "%DotNetCLI_Path%"
            commandLineArguments = """test %teamcity.build.checkoutDir%\DataDrivenTests\DataDrivenTests.csproj"""
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

    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows Server 2022")
    }
})

object id20234eapLinuxAgentNetDotCover : BuildType({
    id("20234eapLinuxAgentNetDotCover")
    name = "2023.3.4 EAP (Linux agent): .Net + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "money/Money.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2023.3.4%"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2023.3.4%"
            executable = "%DotNetCLI_Path%"
            commandLineArguments = """test %teamcity.build.checkoutDir%\DataDrivenTests\DataDrivenTests.csproj"""
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

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
    }
})

object id20241eapDockerLinuxAgentNetDotCover : BuildType({
    id("20241eapDockerLinuxAgentNetDotCover")
    name = "2025.1 EAP (Docker, Linux agent): .Net + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "money/Money.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2025.1.0-eap06%"
            }
            dockerImage = "mcr.microsoft.com/dotnet/sdk:6.0"
            dockerImagePlatform = DotnetTestStep.ImagePlatform.Linux
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2025.1.0-eap06%"
            executable = "%DotNetCLI_Path%"
            commandLineArguments = """test %teamcity.build.checkoutDir%\DataDrivenTests\DataDrivenTests.csproj"""
            dockerImage = "mcr.microsoft.com/dotnet/sdk:6.0"
            dockerImagePlatform = DotCoverBuildStep.ImagePlatform.Linux
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

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
    }
})

object id20241eapLinuxAgentNetDotCover : BuildType({
    id("20241eapLinuxAgentNetDotCover")
    name = "2024.3.6 (Linux agent): .Net + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "money/Money.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2024.3.6%"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2024.3.6%"
            executable = "%DotNetCLI_Path%"
            commandLineArguments = """test %teamcity.build.checkoutDir%\DataDrivenTests\DataDrivenTests.csproj"""
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

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
    }
})

object id20241eapWindowsAgentNetDotCover : BuildType({
    id("20241eapWindowsAgentNetDotCover")
    name = "2025.1 EAP (Windows agent): .Net + dotCover"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = """money\Money.csproj"""
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2025.1.0-eap06%"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2025.1.0-eap06%"
            executable = "%DotNetCLI_Path%"
            commandLineArguments = """test %teamcity.build.checkoutDir%\DataDrivenTests\DataDrivenTests.csproj"""
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

    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows Server 2022")
    }
})

object id202433WindowsAgentNetDotCover : BuildType({
    id("202433WindowsAgentNetDotCover")
    name = "SONAR + 2024.3.3 (Windows agent): .Net + dotCover"

    params {
        password("sonarToken", "credentialsJSON:7e2a9f3b-2bf8-4ea2-9f53-a9c547d702ef")
    }

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetCustom {
            name = "Install sonar"
            id = "Install_sonar"
            args = "tool install dotnet-sonarscanner -g"
        }
        dotnetCustom {
            name = "Sonar Scan"
            id = "Sonar_Scan"
            args = "sonarscanner begin /k:DariaKrup_nunit-csharp-tests-project /o:dariakrup /d:sonar.login=eb07358d2bcdd74e58065449b87b95f1a0a1ff02 /d:sonar.host.url=https://sonarcloud.io"
        }
        dotnetBuild {
            id = "dotnet_1"
            projects = "Samples.sln"
        }
        dotnetTest {
            id = "dotnet"
            projects = "money/Money.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2024.3.3%"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2024.3.3%"
            executable = "%DotNetCLI_Path%"
            commandLineArguments = """test %teamcity.build.checkoutDir%\DataDrivenTests\DataDrivenTests.csproj"""
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

    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows Server 2022")
    }
})

object BundledWindowsFramework48NetDotCover : BuildType({
    name = "Bundled, Windows Framework 4.8: .Net + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetCustom {
            id = "dotnet"
            args = "new xunit -n ProjectNet  --force"
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2017.3.0%"
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

    requirements {
        equals("teamcity.agent.jvm.os.name", "Windows Server 2022")
    }
})

object CustomPath202224WindowsNetDotCover : BuildType({
    name = "[Custom path] 2022.2.4 Windows: .Net + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "DataDrivenTests/DataDrivenTests.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "/opt/teamcity/JetBrains.dotCover.CommandLineTools.2023.3.4"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "/opt/teamcity/JetBrains.dotCover.CommandLineTools.2023.3.4"
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

object Uploaded202224WindowsNetDotCover : BuildType({
    name = "[Uploaded] 2022.2.4 Windows: .Net + dotCover"

    vcs {
        root(AbsoluteId("DotCoverRunner_HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "DataDrivenTests/DataDrivenTests.csproj"
            sdk = "6"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2022.2.4%"
            }
        }
        dotCover {
            id = "dotcover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.2022.2.4%"
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

object HttpsGithubComDariaKrupNunitCsharpTestsProjectGitRefsHeadsMaster1 : GitVcsRoot({
    name = "https://github.com/DariaKrup/nunit-csharp-tests-project.git#refs/heads/master (1)"
    url = "https://github.com/DariaKrup/nunit-csharp-tests-project.git"
    branch = "master"
    branchSpec = "refs/heads/*"
})
